package logic;

import java.util.ArrayList;
import java.util.LinkedList;
import logic.parametri.Accesso;
import logic.schedulazione.PCB;
import logic.schedulazione.Scheduler;

/*
 * Azienda: Stylosoft
 * Nome file: Processore.java
 * Package: logic
 * Autore: Daniele Bonaldo
 * Data: 26/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.1 (26/02/2008): Aggiunta la descrizione dei parametri nella javadoc.
 *  - v.1.0 (26/02/2008): Creazione e scrittura documentazione.
 */

/**
 * 
 * 
 * @author Daniele Bonaldo
 * @version 1.1 26/02/2008
 */

public class Processore {
    
    /**
     * Questo campo dati specifica lo scheduler da utilizzare per l'ordinamento 
     * dei processi.
     */
    private Scheduler scheduler = null;    
            
    /**
     * Costruttore della classe Processore
     * 
     * @param scheduler 
     *      Indica lo scheduler da utilizzare nella simulazione
     */
    public Processore(Scheduler scheduler){
        this.scheduler = scheduler;    
    }
    
    /**
     * 
     * @return Ritorna la simulazione sotto forma di LinkedList di Istante
     */
    public LinkedList<Istante> creaSimulazione(){
        
        LinkedList<Istante> simulazione = new LinkedList<Istante>();
        
        scheduler.esegui();
        
        while(!scheduler.fineSimulazione()){
            
            /* Ottiene dallo scheduler il PCB del processo correntemente in 
             esecuzione */
            PCB corrente = scheduler.getPCBCorrente();
            
            /* Controlla che ci sia effettivamente un proceso in esecuzione */
            if (corrente != null){
                
                /* Estrae i FrameMemoria necessari al processo in esecuzione
                 nell'istante della sua esecuzione corrente */
                LinkedList frameNecessari = estraiFrame(corrente);
                
                /* Interroga il gestore della memoria sulla disponibilità dei 
                 FrameMemoria necessari al processo in esecuzione e riceve la 
                 lista delle istruzioni effettuate dal gestore della memoria per
                 portare in RAM quei FrameMemoria */
                LinkedList istruzioni;// = gestoreMemoria.esegui(frameNecessari);
                
                simulazione.add(creaIstante(corrente,istruzioni));
                //da aggiungere il PCB dell'ultimo terminato
            
            } else {
                /* Non c'è un processo in esecuzione */
                simulazione.add(creaIstante(corrente,null));
            }
            
            /* Esegue nuovamente il metodo principale dello scheduler 
             incrementandone il contatore interno */
            scheduler.esegui();
        }
        
        /* La simulazione è terminata */
        return simulazione;
    }

    /**
     * 
     * @param istruzioni 
     *      La lista di istruzioni effettuate dal gestore della memoria in questo
     *      istante. Può essere uguale a null nel caso non siano state effettuate 
     *      istruzioni.
     * 
     * @return Il numero di fault di pagina avvenuti in questo istante.
     */
    private int calcolaFault(LinkedList istruzioni) {
       
        if(istruzioni == null){
            
            return 0;
            
        }
           
        return 42;
    }
    
    /**
     * 
     * @param istruzioni
     *      La lista di istruzioni effettuate dal gestore della memoria in questo
     *      istante. Può essere uguale a null nel caso non siano state effettuate 
     *      istruzioni.
     * 
     * @return Ritorna l'istante corrente.
     */
    private Istante creaIstante(PCB corrente, LinkedList istruzioni){
        int fault = calcolaFault(istruzioni);
        Istante istante = new Istante();
        
        return istante;
    }
    
    /**
     * 
     * @param corrente
     *      Il PCB relativo al processo attualmente in esecuzione
     * 
     * @return Ritorna la LinkedList dei FrameMemoria necessari al PCB corrente
     * nell'istante attuale.
     */
    private LinkedList estraiFrame(PCB corrente){
        
        /* Ottiene l'identificativo dell'istante di esecuzione corrente */
        int istanteCorrente = corrente.getIstantiEseguiti();
        
        /* Crea una lista vuota in cui inserire i FrameMemoria */
        LinkedList frameNecessari = new LinkedList();
        
        /* Ottiene la lista totale delle richieste di accesso a memoria del
         processo a cui appartiene il PCB corrente */
        ArrayList frameTotali = corrente.getRifProcesso().getAccessi();
        
        /* Scorre le richieste finchè non passa ad un istante successivo a quello 
         corrente o raggiunge la fine della lista.
         Da notare che la lista è ordinata in ordine crescente di richiesta */
        for(int i=0; i <= istanteCorrente && i < frameTotali.size(); i++)
            
            if(((Accesso) frameTotali.get(i)).getIstanteRichiesta() == istanteCorrente)
                
                frameNecessari.add(((Accesso) frameTotali.get(i)).getRisorsa());
        
        return frameNecessari;
    }
    
    
}
