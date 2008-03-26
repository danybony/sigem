
package logic.simulazione;

/*
 * Azienda: Stylosoft
 * Nome file: Player.java
 * Package: logic.simulazione
 * Autore: Luca Rubin
 * Data: 07/03/2008
 * Versione: 1.7
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.7 (07/03/2008): Correzione errori rilevati in fase di test
 *  - v.1.6 (04/03/2008): Aggiunta della Classe interna Statistiche e implementazione
 *                        dei suoi metodi; aggiornamento dei metodi della classe
 *                        esterna per adattarli alla nuova classe.
 *  - v.1.5 (29/02/2008): Completata la documentazione.
 *  - v.1.4 (29/02/2008): Implementata la modalita' di ricerca di istanti significativi;
 *                        corretto il problema di scorrimento degli stati (in alcuni casi
                          veniva ritornato lo stato corrente al posto di quello precedente
                          o successivo).
 *  - v.1.3 (28/02/2008): Modificata la modalita' di scorrimento degli istanti
 *                        con l'utilizzo di un iteratore.
 *  - v.1.2 (27/02/2008): Aggiunti metodi di salto a eventi significativi.
 *  - v.1.1 (26/02/2008): Aggiunta la documentazione JavaDoc.
 *  - v.1.0 (26/02/2008): Impostazione base della classe.
 */

import java.util.LinkedList;
import java.util.Vector;
import logic.gestioneMemoria.Azione;
import logic.gestioneMemoria.FrameMemoria;
import logic.parametri.ConfigurazioneIniziale;
import logic.parametri.Processo;
import logic.schedulazione.PCB;

/**
 * Classe per lo scorrimento di una simulazione.<br>
 * Potra' essere utilizzato dall'interfaccia grafica per visualizzare l'evolvere
 * della simulazione. L'avanzamento potra' avvenire avanti o indietro nel tempo.
 * L'avanzamento potra' essere fatto in unita' di tempo, o in base al verificarsi
 * di eventi.<br>
 * 
 * Sara' possibile avanzare o retrocedere rapidamente ad istanti della simulazione
 * in cui si sono verificati fault in memoria, context-switch, riempimenti della
 * memoria centrale RAM, riempimenti dell'area di swap, terminazione  di processi
 * e arrivo di nuovi processi.
 */
public class Player{
    
    /**
     * Oggetto simulazione, utilizzato per la creazione della simulazione, e
     * quindi, per la creazione degli istanti.<br>
     * La simulazione viene creata in base alla configurazione iniziale passata.
     */
    private Simulazione simulazioneEseguita;
    
    /**
     * La configuazione iniziale con cui viene creata la simulazione
     */
    private ConfigurazioneIniziale config;
    
    /**
     * Lista degli istanti generati dalla simulazione.
     */
    private LinkedList<Istante> listaIstanti;
    
    /**
     * Utilizzato per ottimizzare lo scorrimento della lista degli istanti.
     */
    private int indiceElementoCorrente = 0;
    
    /**
     * Possibili eventi da ricercare nella simulazione
     */
    public enum Evento{FAULT, SWITCH, FULL_RAM, FULL_SWAP, END_PROC, NEW_PROC}
        
    /**
     * Costruttore della classe.<br>
     * Si occupa dell'istanziazione dell'oggetto simulazione, sulla base della
     * configurazione iniziale passata.
     * 
     * @param   conf
     *              configurazione iniziale per il sistema di simulazione
     */
    public Player(ConfigurazioneIniziale conf){
        config = conf;
        simulazioneEseguita = new Simulazione(conf);
        listaIstanti = null;
        indiceElementoCorrente = 0;
    }
    
    /**
     * Si occupa di creare la simulazione, e di inizializzare l'iteratore interno
     * al player in modo che esso punti al primo istante della simulazione
     * generata.
     * 
     * @return  booleano che corrisponde all'successo o meno del caricamento
     */
    public boolean caricaSimulazione(){
        listaIstanti = simulazioneEseguita.crea();
        if(listaIstanti == null)
            return false;
        listaIstanti.addFirst(new Istante(null,null,false,0,null,false,false,null,null));
        /*for(int i =0;i<listaIstanti.size();i++){
            System.out.println("Istante: " + i);
            LinkedList<Azione> azioni = listaIstanti.get(i).getCambiamentiInMemoria();
            if(azioni!=null){
                for(int j=0 ; j<azioni.size();j++){
                    System.out.print("Azione " + j + ":\t");
                    Vector<FrameMemoria> mem = azioni.get(j).getMemoriaRAM();
                    for(int k=0;k<mem.size();k++){
                        System.out.print(mem.get(k).getIndirizzo() + " - ");
                    }
                    System.out.println();
                }
            }
            else{
                System.out.println("NO Azioni ");
            }
        }
        for(int i =0;i<listaIstanti.size();i++){
            System.out.print("Istante: " + i + " arrivo? : ");
            System.out.print(listaIstanti.get(i).getProcessoPrecedenteTerminato()!=null);
            System.out.println();
        }*/
        //Metto l'iteratore prima del primo elemento
        indiceElementoCorrente = 0;
        return true;
    }
    
    /**
     * Ritorna l'istante precente a quello attuale.<br>
     * Se viene ritornato un riferimento nullo significa che non ci sono istanti
     * precedenti a quello attuale, cio' significa che: o la simulazione e' vuota,
     * o siamo all'inizio della simulazione stessa (siamo cioe' all'istante zero).
     * Vengono automaticamente aggiornate le statistiche.
     */
    public Istante istantePrecedente(){
        if(this.listaIstanti==null)
            return null;
        if(this.indiceElementoCorrente > 1){
            this.indiceElementoCorrente--;
            Istante prev = this.listaIstanti.get(indiceElementoCorrente);
            return prev;
        }
        return null;
    }
    
    /**
     * Ritorna l'istante successivo a quello attuale.<br>
     * Se viene ritornato un riferimento nullo significa che non ci sono istanti
     * successivi a quello attuale, cio' significa che: o la simulazione e' vuota,
     * o siamo alla fine della simulazione stessa.
     * Vengono automaticamente aggiornate le statistiche.
     */
    public Istante istanteSuccessivo(){
        if(this.listaIstanti==null)
            return null;
        if(hasNext()){
            this.indiceElementoCorrente++;
            Istante next = this.listaIstanti.get(indiceElementoCorrente);
            return next;
        }
        return null;
    }
    
    /**
     * Ritorna il primo istante significativo che precede quello attuale.<br>
     * Il tipo dell'evento significativo da ricercare viene espresso dal
     * parametro secondo la seguente tabella:
     * 
     * <br><br>
     * <table border="1">
     *  <tr align="center"><td><b>Parametro</b></td><td><b>Descrizione</b></td></tr>
     *  <tr align="center"><td>FAULT</td><td colspan="2">Fault in RAM</td></tr>
     *  <tr align="center"><td>SWITCH</td><td>Context-switch</td></tr>
     *  <tr align="center"><td>FULL_RAM</td><td>Memoria centrale esaurita</td></tr>
     *  <tr align="center"><td>FULL_SWAP (non ancora implementato)</td><td>Area di swap piena</td></tr>
     *  <tr align="center"><td>END_PROC</td><td>Processo terminato</td></tr>
     *  <tr align="center"><td>NEW_PROC</td><td>Arrivo di uno o più processi nuovi</td></tr>
     * </table>
     * <br>
     * 
     * Dato che un evento significativo, puo' essere lontano piu' passi dallo stato
     * attuale, il tipo di ritorno e' una lista di Istante ordinati in modo che 
     * il primo istante della lista e' il piu' vicino allo stato attuale, e 
     * l'ultimo elemento e' l'istante significativo trovato.<br>
     * Se viene ritornato un riferimento nullo significa che non e' stato trovato
     * l'evento significativo.
     * Vengono automaticamente aggiornate le statistiche.
     * 
     * @param tipoEventoSignificativo
     *              specifica il tipo di evento da ricercare
     * 
     * @return  lista di istanti che portano all'evento significativo
     */
    public LinkedList<Istante> precedenteIstanteSignificativo(Evento e){
        LinkedList<Istante> listaIstantiDaRitornare = new LinkedList();
        if(this.listaIstanti==null)
            return null;
        boolean trovato = false;
        Istante nuovo;
        // Sono all'istante zero o all'istante uno
        // in entrambi i casi non posso avere istanti significativi precedenti
        if(this.indiceElementoCorrente==0 || this.indiceElementoCorrente==1)
            return null;

        // Sono ad un istante > 1

        // Il processo correntemente in esecuzione; utile per verificare 
        // l'evento context-switch
        int processoCorrente;
        // Potrebbe non esserci un processo in esecuzione
        try{
            processoCorrente = this.listaIstanti.get(this.indiceElementoCorrente)
                                                .getProcessoInEsecuzione()
                                                .getRifProcesso()
                                                .getId();
        }catch(NullPointerException ex){processoCorrente = -1;}
        
        
        // Istante prima di effettuare la ricerca; utile per ripristinare
        // lo stato attuale in caso di evento non trovato
        // e salvo le statistiche per permetterne il ripristino
        int istantePrimaDellaRicerca = this.indiceElementoCorrente;
        
        // Eseguo la ricerca finchè ho elementi che mi precedono arrivando al
        // massimo a confrontare con l'istante 1
        while(!trovato && this.indiceElementoCorrente > 1){
            // Prendo l'istante precedente
            // Attenzione! Le statistiche vengono aggiornate
            nuovo = this.istantePrecedente();
            switch(e){
                case FAULT:
                    if(nuovo.getFault()!=0)
                        trovato=true;
                    break;
                case SWITCH:
                    // Potrebbe non esserci un processo in esecuzione
                    try{
                        if(processoCorrente!=nuovo.getProcessoInEsecuzione().getRifProcesso().getId())
                            trovato=true;
                    }catch(NullPointerException ex){}
                    break;
                case FULL_RAM:
                    if(nuovo.getFull_RAM())
                        trovato=true;
                    break;
                case FULL_SWAP:
                    if(nuovo.getFull_Swap())
                        trovato=true;
                    break;
                case END_PROC:
                    if(nuovo.getProcessoPrecedenteTerminato()!=null)
                        trovato=true;
                    break;
                case NEW_PROC:
                    if(nuovo.getNuovoProcesso())
                        trovato=true;
                    break;
            }
            listaIstantiDaRitornare.add(nuovo);   
        }
        
        if(trovato){
            // Le statistiche sono già aggiornate
            return listaIstantiDaRitornare;
        }
        else{
            this.indiceElementoCorrente = istantePrimaDellaRicerca;
            return null;
        }
    }
    
    /**
     * Ritorna il primo istante significativo che segue quello attuale.<br>
     * Il tipo dell'evento significativo da ricercare viene espresso dal
     * parametro secondo la seguente tabella:
     * 
     * 
     * <br><br>
     * <table border="1">
     *  <tr align="center"><td><b>Parametro</b></td><td><b>Descrizione</b></td></tr>
     *  <tr align="center"><td>FAULT</td><td colspan="2">Fault in RAM</td></tr>
     *  <tr align="center"><td>SWITCH</td><td>Context-switch</td></tr>
     *  <tr align="center"><td>FULL_RAM</td><td>Memoria centrale esaurita</td></tr>
     *  <tr align="center"><td>FULL_SWAP</td><td>Area di swap piena</td></tr>
     *  <tr align="center"><td>END_PROC</td><td>Processo terminato</td></tr>
     *  <tr align="center"><td>NEW_PROC</td><td>Arrivo di uno o più processi nuovi</td></tr>
     * </table>
     * <br>
     * 
     * Dato che un evento significativo, puo' distare piu' passi dallo stato
     * attuale, il tipo di ritorno e' una lista di Istante ordinati in modo che 
     * il primo istante della lista e' il piu' vicino allo stato attuale, e 
     * l'ultimo elemento e' l'istante significativo trovato.<br>
     * Se viene ritornato un riferimento nullo significa che non e' stato trovato
     * l'evento significativo.
     * Vengono automaticamente aggiornate le statistiche.
     * 
     * @param tipoEventoSignificativo
     *              specifica il tipo di evento da ricercare
     * 
     * @return  lista di istanti che portano all'evento significativo
     */
    public LinkedList<Istante> prossimoIstanteSignificativo(Evento e){
        LinkedList<Istante> listaIstantiDaRitornare = new LinkedList();
        if(this.listaIstanti==null)
            return null;
        boolean trovato = false;
        Istante nuovo;

        // Se sono all'ultimo istante non ci possono essere eventi significativi
        // successivi
        if(this.indiceElementoCorrente == this.numeroIstanti())
            return null;
        
        // Sono ad un istante < ultimo

        // Il processo correntemente in esecuzione; utile per verificare 
        // l'evento context-switch
        int processoCorrente = -1;
        // Se sono all'istante 0 imposto un valore fittizio
        if(this.indiceElementoCorrente!=0){
            // Potrebbe non esserci un processo in esecuzione
            try{
                processoCorrente = this.listaIstanti.get(this.indiceElementoCorrente)
                                                    .getProcessoInEsecuzione()
                                                    .getRifProcesso()
                                                    .getId();
            }catch(NullPointerException ex){processoCorrente = -1;}
        }
        
        // Istante prima di effettuare la ricerca; utile per ripristinare
        // lo stato attuale in caso di evento non trovato
        // e salvo le statistiche per permetterne il ripristino
        int istantePrimaDellaRicerca = this.indiceElementoCorrente;
 
        // Eseguo la ricerca finchè ho istanti successivi e finchè non ho
        // trovato l'evento
        while(hasNext() && !trovato){
            // Ottengo l'istante successivo
            // Attenzione: le statistiche vengono aggiornate continuamente
            nuovo = this.istanteSuccessivo();
            switch(e){
                case FAULT:
                    if(nuovo.getFault()!=0)
                        trovato=true;
                    break;
                case SWITCH:
                    // Potrebbe non esserci un processo in esecuzione
                    try{
                        if(processoCorrente!=nuovo.getProcessoInEsecuzione().getRifProcesso().getId())
                            trovato=true;
                    }catch(NullPointerException ex){}
                    break;
                case FULL_RAM:
                    if(nuovo.getFull_RAM())
                        trovato=true;
                    break;
                case FULL_SWAP:
                    if(nuovo.getFull_Swap())
                        trovato=true;
                    break;
                case END_PROC:
                    if(nuovo.getProcessoPrecedenteTerminato()!=null)
                        trovato=true;
                    break;
                case NEW_PROC:
                    if(nuovo.getNuovoProcesso())
                        trovato=true;
                    break;
            }
            
            listaIstantiDaRitornare.add(nuovo);
        }
        
        if(trovato){
            // Le statistiche sono già aggiornate
            return listaIstantiDaRitornare;
        }
        else{
            this.indiceElementoCorrente = istantePrimaDellaRicerca;
            return null;
        }
    }
    
    /**
     * Ritorna il primo istante della simulazione.<br>
     * Se viene ritornato un riferimento nullo, significa che la simulazione
     * è vuota.
     * Vengono automaticamente aggiornate le statistiche mediante la creazione 
     * di un nuovo oggetto interno Statistiche.
     */
    public Istante primoIstante(){
        if(this.listaIstanti==null) return null;
        this.indiceElementoCorrente = 0;
        Istante primo = this.listaIstanti.getFirst();
        //stat.AggiornaStatistiche(primo, true);
        return primo;
    }
    
    /**
     * Ritorna la lista degli istanti che portano dall'istante attuale a quello
     * finale. Il ritorno di una lista vuota, sta a significare che siamo gia'
     * all'istante finale oppure la simulazione è vuota.
     * Vengono automaticamente aggiornate le statistiche.
     */
    public LinkedList<Istante> ultimoIstante(){
        if(listaIstanti==null) return null;
        LinkedList<Istante> listaAllaFine = new LinkedList<Istante>();
        if(!hasNext()) return listaAllaFine;
        while(hasNext()){
            this.indiceElementoCorrente++;
            listaAllaFine.add(this.listaIstanti.get(indiceElementoCorrente));
        }
        return listaAllaFine;
    }
    
    /**
     * Ritorna il numero di istanti che copongono la simulazione.
     */
    public int numeroIstanti(){
        if(listaIstanti==null)
            return 0;
        return this.listaIstanti.size()-1;
    }
    
    /**
     * Ritorna l'indice dell'istante corrente
     */
    public int getIndiceIstanteCorrente(){
        return this.indiceElementoCorrente;
    }
    
    public boolean hasPrev(){
        if(this.indiceElementoCorrente > 0){
            return  true;
        }
        return false;
    }
    
    
    public boolean hasNext(){
        if(this.indiceElementoCorrente < (this.listaIstanti.size() - 1)){
            return true;
        }
        return false;
    }
    
    public boolean fullSwapNellaSimulazione(){
        return listaIstanti.getLast().getFull_Swap();
    }
}