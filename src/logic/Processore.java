package logic;

/*
 * Azienda: Stylosoft
 * Nome file: Processore.java
 * Package: logic
 * Autore: Daniele Bonaldo
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche: * 
 *  - v.1.3 (29/02/2008): Aggiornamento del costruttore per la nuova ConfigurazioneIniziale. 
 *  - v.1.2 (27/02/2008): Modifica del metodo calcolaFault.
 *  - v.1.1 (26/02/2008): Aggiunta la descrizione dei parametri nella javadoc.
 *  - v.1.0 (26/02/2008): Creazione e scrittura documentazione.
 */

import logic.simulazione.Istante;
import java.util.ArrayList;
import java.util.LinkedList;
import logic.gestioneMemoria.*;
import logic.parametri.ConfigurazioneIniziale;
import logic.parametri.Processo.Accesso;
import logic.schedulazione.*;

/**
 * Classe che ha il compito di
 * 
 * @author Daniele Bonaldo
 * @version 1.3 29/02/2008
 */

public class Processore {
    
    /**
     * Questo campo dati specifica lo scheduler da utilizzare per l'ordinamento 
     * dei processi.
     * 
     */
    private Scheduler scheduler = null;    
    
    /**
     * Questo campo dati specifica il gestore della memoria incaricato di verificare
     * la presenza in RAM delle pagine o dei processi necessari al processo in 
     * esecuzione e di operare sulla memoria.
     * 
     */
    private GestoreMemoria gestoreMemoria = null;
            
    /**
     * Riferimento dell'ultimo PCB mandato in esecuzione. Servirà nel metodo
     * creaIstante e, nel caso corrisponda con il primo elemento della lista dei
     * processi terminato, deve essere essere inserito nell'istante corrente come 
     * processo appena terminato.
     */
    private PCB ultimoEseguito = null;
    
    /**
     * Unico costruttore della classe Processore.
     * Imposta lo scheduler e il gestore della memoria necessari per la simulazione.
     * 
     * @param conf 
     *      Indica la configurazione iniziale da cui inizializzare lo Scheduler
     */
    public Processore(ConfigurazioneIniziale conf){
        
        PoliticaOrdinamentoProcessi politica = null;
        
        switch (conf.getPoliticaSchedulazioneProcessi()){            
            case 1:politica = new FCFS(); break;
            case 2:politica = new SJF(); break;
            case 3:politica = new SRTN(); break;
            case 4:politica = new RR(); break;
            case 5:politica = new RRConPriorita(); break;
            case 6:politica = new RRConPrioritaConPrerilascio(); break;
            case 7:politica = new Priorita(); break;
        }
        
        this.scheduler = new Scheduler(politica, conf.getListaProcessi()); 
        
        if(conf.getModalitaGestioneMemoria() == 1){
            
            this.gestoreMemoria = new GestoreMemoriaPaginata(conf);
            
        }
        else {
            
            this.gestoreMemoria = new GestoreMemoriaSegmentata(conf);
            
        }      
    }
    
    /**
     * Metodo principale della classe Processore, incaricato di creare tutta la 
     * simulazione, rappresentata da una collezione di istanti.
     * La terminazione della simulazione è segnalata dallo scheduler, che è a 
     * conoscenza dello stato dei processi in attesa o in arrivo, oltre che di
     * quello in esecuzione.<BR>
     * Dopo aver attivato il processo in esecuzione nell'istante di tempo corrente,
     * viene chiesto allo scheduler un riferimento al suo PCB, in modo da poter
     * estrarre una lista di FrameMemoria necessari al processo in esecuzione.
     * Questa lista viene passata al gestore della memoria che ha il compito di 
     * caricare i frame in RAM, ritornando la lista di operazioni eseguite.<BR>
     * Da questa lista di operazioni verrà poi ricavato il numero di fault di 
     * pagina e verrà quindi creata l'istanza di classe Istante corrispondente
     * al momento attuale della simulazione.
     * 
     * 
     * @return Ritorna la simulazione sotto forma di LinkedList di Istante
     */
    public LinkedList<Istante> creaSimulazione(){
        
        int tempoCorrente = 0;
        
        LinkedList<Istante> simulazione = new LinkedList<Istante>();  
        
        boolean stop, nuovoProcesso, SwapPiena = false;
        
        while(!scheduler.fineSimulazione() && !SwapPiena){
            
            stop = false;
            
            nuovoProcesso = false;
            
            /* Memorizza il numero di processi in arrivo prima dell'attivazione */
            int numInArrivo = scheduler.getProcessiInArrivo().size();
            
            stop = scheduler.eseguiAttivazione();
            
            /* Controlla se il numero di processi in arrivo è dimunuito */
            if (numInArrivo < scheduler.getProcessiInArrivo().size()){
                
                nuovoProcesso = true;
                
            }
            /* Ottiene dallo scheduler il PCB del processo correntemente in 
             esecuzione */
            PCB corrente = scheduler.getPCBCorrente();
            
            /* Controlla che ci sia effettivamente un proceso in esecuzione */
            if (corrente != null){
                
                /* Estrae i FrameMemoria necessari al processo in esecuzione
                 nell'istante della sua esecuzione corrente */
                LinkedList<FrameMemoria> frameNecessari = estraiFrame(corrente);
                
                /* Interroga il gestore della memoria sulla disponibilità dei 
                 FrameMemoria necessari al processo in esecuzione e riceve la 
                 lista delle istruzioni effettuate dal gestore della memoria per
                 portare in RAM quei FrameMemoria */
                LinkedList<Azione> istruzioni = gestoreMemoria.esegui(frameNecessari,
                                                             tempoCorrente);
                
                SwapPiena = controllaSwapPiena(istruzioni);
                
                simulazione.add(creaIstante(corrente,istruzioni,nuovoProcesso,SwapPiena));
                //da aggiungere il PCB dell'ultimo terminato
        
            } else {
                /* Non c'è un processo in esecuzione */
                simulazione.add(creaIstante(corrente,null,nuovoProcesso,SwapPiena));
            }
            
            /* Se c'è un processo in esecuzione esegue nuovamente il metodo 
             principale dello scheduler incrementandone il contatore interno */
            if (!stop){
                
                scheduler.eseguiIncremento();
                
            }
            
            ultimoEseguito = corrente;
            
            tempoCorrente ++;
            
        }
        
        /* La simulazione è terminata */
        return simulazione;
    }

    /**
     * Metodo che si occupa di estrarre il numero fdi fault di pagina da una lista
     * di istruzioni sulla memoria.
     * Il numero di fault è costituito dalla somma delle operazioni corrispondenti 
     * ad una scrittura di una pagina su RAM.
     * Queste si possono verificare solo nel caso il valore del TipoAzione sia
     * <li>1 INSERISCI
     * <li>4 SWAPTORAM
     * <br>
     * 
     * @param istruzioni 
     *      La lista di istruzioni effettuate dal gestore della memoria in questo
     *      istante. Può essere uguale a null nel caso non siano state effettuate 
     *      istruzioni.
     * 
     * @return Il numero di fault di pagina avvenuti in questo istante.
     */
    private int calcolaFault(LinkedList<Azione> istruzioni) {
       
        if(istruzioni == null){
            
            return 0;
            
        }
        
        int numeroFault = 0;
        
        for (int i = 0; i < istruzioni.size(); i++){
            
            if (istruzioni.get(i).getAzione() == 1 || istruzioni.get(i).getAzione() == 4){
                
                numeroFault ++;
                
            }                
            
        }
        
        return numeroFault;
    }

    /**
     * Metodo il cui compito è ritornare tramite un booleano se l'area di Swap è 
     * piena o meno.
     * 
     * @param istruzioni
     *      La lista delle istruzioni ritornate dal GestoreMemoria
     * 
     * @return Ritorna un boolean rappresentante il fatto che la memoria di Swap
     * sia piena.
     */
    private boolean controllaSwapPiena(LinkedList<Azione> istruzioni) {
        
        if(istruzioni == null){
            
            return false;
            
        }
        
        for (int i = 0; i < istruzioni.size(); i++){
            
            if (istruzioni.get(i).getAzione() == -1){
                
                return true;
                
            }                
            
        }
        
        return false;
    }
    
    /**
     * Metodo il cui compito è ritornare tramite un booleano se la RAM è piena o 
     * meno.
     * 
     * @param istruzioni
     *      La lista delle istruzioni ritornate dal GestoreMemoria
     * 
     * @return Ritorna un boolean rappresentante il fatto che la memoria di Swap
     * sia piena.
     */
    private boolean controllaRAMPiena(LinkedList<Azione> istruzioni) {
        
        if(istruzioni == null){
            
            return false;
            
        }
        
        for (int i = 0; i < istruzioni.size(); i++){
            
            if (istruzioni.get(i).getAzione() == 0){
                
                return true;
                
            }                
            
        }
        
        return false;
    }
    
    /**
     * Metodo con il compito di creare una istanza della classe Istante riguardante
     * l'istante corrente della simulazione.
     * Controlla se l'ultimo PCB eseguito è lo stesso in testa alla testa dei terminati.
     * In caso affermativo, ultimoEseguito è appena terminato, e quindi verrà salvato 
     * nell'istante corrente.
     * 
     * @param istruzioni
     *      La lista di istruzioni effettuate dal gestore della memoria in questo
     *      istante. Può essere uguale a null nel caso non siano state effettuate 
     *      istruzioni.
     * 
     * @return Ritorna l'istante corrente.
     */   
    private Istante creaIstante(PCB corrente, LinkedList<Azione> istruzioni,
                                boolean nuovoProcesso, boolean SwapPiena){
        
        int fault = calcolaFault(istruzioni);
        
        boolean RAMPiena = controllaRAMPiena(istruzioni);
        
        Istante istante;
        
        if(scheduler.getProcessiTerminati().size() > 0 && 
                (PCB)scheduler.getProcessiTerminati().get(0) == ultimoEseguito){
            
            gestoreMemoria.notificaProcessoTerminato(ultimoEseguito.getRifProcesso().getId());
            
            istante = new Istante(corrente, ultimoEseguito, nuovoProcesso, fault, 
                                istruzioni, RAMPiena, SwapPiena);
            
        }
        else{
            
            istante = new Istante(corrente, null, nuovoProcesso, fault, 
                                istruzioni, RAMPiena, SwapPiena);
            
        }
        
        return istante;
    }
    
    /**
     * Metodo che ha il compito di estrarre i FrameMemoria necessari al processo 
     * correntemente in esecuzione nell'istante corrente della simulazione.<BR>
     * Questa azione viene resa semplice dall'avere inserito le richieste di 
     * accesso ad un FrameMemoria da parte di un processo in ordine di istante 
     * della richiesta stessa.<BR>
     * Viene ritornata una LinkedList contenente i FrameMemoria.
     * 
     * 
     * @param corrente
     *      Il PCB relativo al processo attualmente in esecuzione
     * 
     * @return Ritorna la LinkedList dei FrameMemoria necessari al PCB corrente
     * nell'istante attuale.
     */
    private LinkedList<FrameMemoria> estraiFrame(PCB corrente){
        
        /* Ottiene l'identificativo dell'istante di esecuzione corrente */
        int istanteCorrente = corrente.getIstantiEseguiti();
        
        /* Crea una lista vuota in cui inserire i FrameMemoria */
        LinkedList frameNecessari = new LinkedList();
        
        /* Ottiene la lista totale delle richieste di accesso a memoria del
         processo a cui appartiene il PCB corrente */
        ArrayList<Accesso> frameTotali = corrente.getRifProcesso().getAccessi();
        
        /* Scorre le richieste finchè non passa ad un istante successivo a quello 
         corrente o raggiunge la fine della lista.
         Da notare che la lista è ordinata in ordine crescente di richiesta */
        for(int i=0; i <= istanteCorrente && i < frameTotali.size(); i++)
            
            if((frameTotali.get(i)).getIstanteRichiesta() == istanteCorrente)
                
                frameNecessari.add((frameTotali.get(i)).getRisorsa());
        
        return frameNecessari;
    }
    
    
}
