
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
import java.util.ListIterator;
import logic.gestioneMemoria.Azione;
import logic.parametri.ConfigurazioneIniziale;

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
     * Lista degli istanti generati dalla simulazione.
     */
    private LinkedList<Istante> listaIstanti;
    
    /**
     * Utilizzato per ottimizzare lo scorrimento della lista degli istanti.
     */
    private int indiceElementoCorrente = 0;
    
    /**
     * L'istanza della classe Statistiche.
     */
    public static Statistiche stat;
    
    /**
     * Possibili eventi da ricercare nella simulazione
     */
    public enum Evento{FAULT, SWITCH, FULL_RAM, FULL_SWAP, END_PROC, NEW_PROC}
    
    /**
     * L'istanza della classe Statistiche.
     */
   // public static Statistiche stat;
    
   public class Statistiche{
        
        /**
         * Numero di KB utilizzati della RAM.
         */
        private int utilizzoRAM;
        
        /**
         * Numero di KB utilizzati nell'area di Swap.
         */
        private int utilizzoSwap;
        
        /**
         * Numero di fault in memoria.
         */
        private int numeroFault;
        
        /**
         * Numero di istanti rimanenti alla fine della simulazione.
         */
        private int numeroIstantiRimanenti;
        
        /**
         * Costruttore della classe. Inizializza a zero i campi dato.
         */
        Statistiche(){
            this.numeroFault = 0;
            this.utilizzoRAM = 0;
            this.utilizzoSwap = 0;
            this.numeroIstantiRimanenti = Player.this.numeroIstanti();
        }
        
        /**
         * Ritorna il numero di KB utilizzati nella RAM.
         */
        public int getUtilizzoRAM(){
            return this.utilizzoRAM;
        }
        
        /**
         * Ritorna il numero di KB utilizzati nell'area di Swap.
         */
        public int getUtilizzoSwap(){
            return this.utilizzoSwap;
        }
        
        /**
         * Ritorna il numero di fault avvenuti in memoria.
         */
        public int getNumeroFault(){
            return this.numeroFault;
        }
        
        /**
         * Ritorna il numero di istanti rimanenti per la conclusione della
         * simulazione.
         */
        public int getNumeroIstantiRimanenti(){
            return this.numeroIstantiRimanenti;
        }
        
        /**
         * Aggiorna l'occupazione della RAM e dello Swap.
         */
        private void AggiornaOccupazioni(Istante nuovoIstante, boolean avanti){
            LinkedList<Azione> listaAzioni = nuovoIstante.getCambiamentiInMemoria();
            int numeroAzioniMemoria = 0;
            if(listaAzioni!=null)
                listaAzioni.size();
            int i=0;
            Azione azioneCorrente = null;
            if(avanti){ // Istante successivo al corrente
                while(i<numeroAzioniMemoria){
                    azioneCorrente = listaAzioni.get(i);
                    switch(azioneCorrente.getAzione()){
                        case 1: // INSERT RAM
                                utilizzoRAM += azioneCorrente.getFrame().getDimensione();
                                break;
                        case 2: // INSERT SWAP
                                utilizzoSwap += azioneCorrente.getFrame().getDimensione();
                                break;
                        case 3: // REMOVE RAM
                                utilizzoRAM -= azioneCorrente.getFrame().getDimensione();
                                break;
                        case 4: // REMOVE SWAP
                                utilizzoSwap -= azioneCorrente.getFrame().getDimensione();
                                break;
                    
                    }
                    i++;
                }
            }
            else{ // Istante precedente al corrente
                while(i<numeroAzioniMemoria){
                    azioneCorrente = listaAzioni.get(i);
                    switch(azioneCorrente.getAzione()){
                        case 1: // INSERT RAM
                                utilizzoRAM -= azioneCorrente.getFrame().getDimensione();
                                break;
                        case 2: // INSERT SWAP
                                utilizzoSwap -= azioneCorrente.getFrame().getDimensione();
                                break;
                        case 3: // REMOVE RAM
                                utilizzoRAM += azioneCorrente.getFrame().getDimensione();
                                break;
                        case 4: // REMOVE SWAP
                                utilizzoSwap += azioneCorrente.getFrame().getDimensione();
                                break;
                    
                    }
                    i++;
                }
            }
        }
        
        /**
         * Aggiorna le statistiche in base al nuovo istante corrente.
         * 
         * @param nuovoIstante
         *          il nuovo istante corrente
         * 
         * @param avanti
         *          parametro booleano che indica se il nuovo istante e'
         *          successivo (true) o precedente (false) rispetto al vecchio
         *          istante corrente.
         */
        void AggiornaStatistiche(Istante nuovoIstante, boolean avanti ){
            if(avanti){ // istante successivo a quello corrente
                AggiornaOccupazioni(nuovoIstante, avanti);
                numeroFault += nuovoIstante.getFault();
                numeroIstantiRimanenti--;
            }
            else{ // istante precedente a quello corrente
                AggiornaOccupazioni(nuovoIstante, avanti);
                numeroFault -= nuovoIstante.getFault();
                numeroIstantiRimanenti++;
            }
        }
        
        /**
         * Aggiorna le statistiche in base ad una lista di istanti consecutivi
         * rispetto al vecchio istante corrente.
         * 
         * @param listaNuoviIstanti
         *          la lista di nuovi istanti
         * 
         * @param avanti
         *          parametro booleano che indica se i nuovi istanti sono
         *          successivi (true) o precedenti (false) rispetto al vecchio
         *          istante corrente.
         * 
         */
        void AggiornaStatistiche(LinkedList<Istante> listaNuoviIstanti,
                                        boolean avanti)
        {
            int numeroNuoviIstanti = 0;
            if(listaNuoviIstanti!=null)
                numeroNuoviIstanti = listaNuoviIstanti.size();
            int corrente = 0;
            Istante nuovoIstante = null;
            if(avanti){ // istante successivo a quello corrente
                while(corrente<numeroNuoviIstanti){
                    nuovoIstante = listaNuoviIstanti.get(corrente);
                    AggiornaOccupazioni(nuovoIstante, avanti);
                    numeroFault += nuovoIstante.getFault();
                    numeroIstantiRimanenti--;
                    corrente++;
                }
            }
            else{ // istante precedente a quello corrente
                while(corrente<numeroNuoviIstanti){
                    nuovoIstante = listaNuoviIstanti.get(corrente);
                    AggiornaOccupazioni(nuovoIstante, avanti);
                    numeroFault -= nuovoIstante.getFault();
                    numeroIstantiRimanenti++;
                    corrente++;
                }
            }
        }
        
        /**
         * Azzera tutte le statistiche
         */
        void AzzeraStatistiche(){
            this.numeroFault = 0;
            this.utilizzoRAM = 0;
            this.utilizzoSwap = 0;
            this.numeroIstantiRimanenti = 0;
        }
    }
    
    
    
    /**
     * Costruttore della classe.<br>
     * Si occupa dell'istanziazione dell'oggetto simulazione, sulla base della
     * configurazione iniziale passata.
     * 
     * @param   conf
     *              configurazione iniziale per il sistema di simulazione
     */
    public Player(ConfigurazioneIniziale conf){
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
        //Metto l'iteratore prima del primo elemento
        indiceElementoCorrente = 0;
        stat = new Statistiche();
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
        if(hasPrev()){
            this.indiceElementoCorrente--;
            Istante prev = this.listaIstanti.get(indiceElementoCorrente);
            stat.AggiornaStatistiche(prev, true);
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
        if(hasNext()){
            this.indiceElementoCorrente++;
            Istante next = this.listaIstanti.get(indiceElementoCorrente);
            stat.AggiornaStatistiche(next, true);
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
     *  <tr align="center"><td>SWITCH</td><td>Context-switch (non implementato)</td></tr>
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
        boolean trovato = false;
        Istante nuovo;
        
        int istantePrimaDellaRicerca = this.indiceElementoCorrente;
        
        while(hasPrev() && !trovato){
            nuovo = this.istantePrecedente();
            switch(e){
                case FAULT:
                    if(nuovo.getFault()!=0)
                        trovato=true;
                    break;
                case SWITCH:
                    System.out.println("Funzione non ancora implementata");
                    //if(idProcessoPrecedente!=nuovo.getProcessoInEsecuzione().getRifProcesso().getId())
                      //  trovato=true;
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
            stat.AggiornaStatistiche(listaIstantiDaRitornare, true);
            listaIstantiDaRitornare.removeLast();
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
     *  <tr align="center"><td>SWITCH</td><td>Context-switch (non implementato)</td></tr>
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
        boolean trovato = false;
        Istante nuovo;
        
        int istantePrimaDellaRicerca = this.indiceElementoCorrente;
        
        while(hasNext() && !trovato){
            nuovo = this.istanteSuccessivo();
            switch(e){
                case FAULT:
                    if(nuovo.getFault()!=0)
                        trovato=true;
                    break;
                case SWITCH:
                    System.out.println("Funzione non ancora implementata");
                    //if(idProcessoSuccessivo!=nuovo.getProcessoInEsecuzione().getRifProcesso().getId())
                        //trovato=true;
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
            stat.AggiornaStatistiche(listaIstantiDaRitornare, true);
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
        stat.AzzeraStatistiche();
        stat.AggiornaStatistiche(primo, true);
        return primo;
    }
    
    /**
     * Ritorna la lista degli istanti che portano dall'istante attuale a quello
     * finale. Il ritorno di una lista vuota, sta a significare che siamo gia'
     * all'istante finale oppure la simulazione è vuota.
     * Vengono automaticamente aggiornate le statistiche.
     */
    public LinkedList<Istante> ultimoIstante(){
        LinkedList<Istante> listaAllaFine = new LinkedList<Istante>();
        if(!hasNext()) return listaAllaFine;
        while(hasNext()){
            this.indiceElementoCorrente++;
            listaAllaFine.add(this.listaIstanti.get(indiceElementoCorrente));
        }
        stat.AggiornaStatistiche(listaAllaFine, true);
        return listaAllaFine;
    }
    
    /**
     * Ritorna il numero di istanti che copongono la simulazione.
     */
    public int numeroIstanti(){
        if(listaIstanti==null)
            return 0;
        return this.listaIstanti.size();
    }
    
    /**
     * Ritorna l'indice dell'istante corrente
     */
    public int getIndiceIstanteCorrente(){
        return this.indiceElementoCorrente;
    }
    
    /**
     * Ritorna un riferimento all'oggetto interno Statistiche
     */
    public Statistiche getStatistiche(){
        return this.stat;
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
    
}