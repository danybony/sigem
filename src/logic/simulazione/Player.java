
package logic.simulazione;

/*
 * Azienda: Stylosoft
 * Nome file: Player.java
 * Package: logic.simulazione
 * Autore: Luca Rubin
 * Data: 29/02/2008
 * Versione: 1.6
 * Licenza: open-source
 * Registro delle modifiche:
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
     * Iteratore che punta all'elemento corrente restituito dal player.
     */
    private ListIterator<Istante> istanteCorrente;
    
    /**
     * Possibili eventi da ricercare nella simulazione
     */
    public enum Evento{FAULT, SWITCH, FULL_RAM, FULL_SWAP, END_PROC, NEW_PROC}
    
    /**
     * L'istanza della classe Statistiche.
     */
    public static Statistiche stat;
    
    /**
     * Classe che raccoglie dati statistici rispetto all'istante corrente.
     * Questa classe e' necessaria per il fatto che gli Istanti sono
     * rappresentati in modo differenziale.
     */
    public static class Statistiche{
        
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
            this.numeroIstantiRimanenti = 0;
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
        public void AggiornaStatistiche(Istante nuovoIstante, boolean avanti ){
            if(avanti){ // istante successivo a quello corrente
                utilizzoRAM=;
                utilizzoSwap=;
                numeroFault += nuovoIstante.getFault();
                numeroIstantiRimanenti--;
            }
            else{ // istante precedente a quello corrente
                utilizzoRAM=;
                utilizzoSwap=;
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
        public void AggiornaStatistiche(LinkedList<Istante> listaNuoviIstanti,
                                        boolean avanti)
        {
            int numeroNuoviIstanti = listaNuoviIstanti.size();
            int corrente = 0;
            if(avanti){ // istante successivo a quello corrente
                while(corrente<numeroNuoviIstanti){
                    utilizzoRAM=;
                    utilizzoSwap=;
                    numeroFault += listaNuoviIstanti.get(corrente).getFault();
                    numeroIstantiRimanenti--;
                }
            }
            else{ // istante precedente a quello corrente
                while(corrente<numeroNuoviIstanti){
                    utilizzoRAM=;
                    utilizzoSwap=;
                    numeroFault -= listaNuoviIstanti.get(corrente).getFault();
                    numeroIstantiRimanenti++;
                }
            }
        }
        
        /**
         * Azzera tutte le statistiche
         */
        public void azzera(){
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
        stat = new Statistiche();
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
        istanteCorrente=listaIstanti.listIterator();
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
        /*
         * Dato che l'iteratore punta in mezzo agli elementi della lista,
         * puo' capitare, in certi casi, che la funzione ritorni l'elemento
         * attuale, al posto del precedente. Per questo motivo sono stati
         * aggiunti dei controlli.
         */
        if(istanteCorrente.hasPrevious()){
            if(istanteCorrente.previousIndex()==indiceElementoCorrente){
                istanteCorrente.previous();
            }
        }
        if(istanteCorrente.hasPrevious()){
            indiceElementoCorrente--;
            Istante prev = istanteCorrente.previous();
            stat.AggiornaStatistiche(prev, false);
            return prev;
        }
        else
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
        /*
         * Dato che l'iteratore punta in mezzo agli elementi della lista,
         * puo' capitare, in certi casi, che la funzione ritorni l'elemento
         * attuale, al posto del successivo. Per questo motivo sono stati
         * aggiunti dei controlli.
         */
        if(istanteCorrente.hasNext()){
            if(istanteCorrente.nextIndex()==indiceElementoCorrente){
                istanteCorrente.next();
            }
        }
        if(istanteCorrente.hasNext()){
            indiceElementoCorrente++;
            Istante next = istanteCorrente.next();
            stat.AggiornaStatistiche(next, true);
            return next;
        }
        else
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
        Istante nuovo = istantePrecedente();
        boolean trovato = false;
        while(nuovo!=null && !trovato){
            listaIstantiDaRitornare.add(nuovo);
            switch(e){
                case FAULT:
                    if(nuovo.getFault()!=0)
                        trovato=true;
                    break;
                case SWITCH:
                    
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
            nuovo = istantePrecedente();
                    
        }
        if(trovato){
            stat.AggiornaStatistiche(listaIstantiDaRitornare, false);
            return listaIstantiDaRitornare;
        }
        else
            return null;
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
        Istante nuovo = istanteSuccessivo();
        boolean trovato = false;
        while(nuovo!=null && !trovato){
            listaIstantiDaRitornare.add(nuovo);
            switch(e){
                case FAULT:
                    if(nuovo.getFault()!=0)
                        trovato=true;
                    break;
                case SWITCH:
                    
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
            nuovo = istanteSuccessivo();
                    
        }
        if(trovato){
            stat.AggiornaStatistiche(listaIstantiDaRitornare, true);
            return listaIstantiDaRitornare;
        }
        else
            return null;
    }
    
    /**
     * Ritorna il primo istante della simulazione.<br>
     * Se viene ritornato un riferimento nullo, significa che la simulazione
     * è vuota.
     * Vengono automaticamente aggiornate le statistiche mediante la creazione 
     * di un nuovo oggetto interno Statistiche.
     */
    public Istante primoIstante(){
        while(istanteCorrente.hasPrevious()){
            istanteCorrente.previous();
        }
        if(istanteCorrente.hasNext()){
            Istante primo = istanteCorrente.next();
            stat.azzera();
            stat.AggiornaStatistiche(primo, true);
            return primo;
        }
        return null;
        
    }
    
    /**
     * Ritorna la lista degli istanti che portano dall'istante attuale a quello
     * finale. Il ritorno di una lista vuota, sta a significare che siamo gia'
     * all'istante finale oppure la simulazione è vuota.
     * Vengono automaticamente aggiornate le statistiche.
     */
    public LinkedList<Istante> ultimoIstante(){
        LinkedList<Istante> listaAllaFine = new LinkedList<Istante>();
        Istante nuovo = istanteSuccessivo(); 
        while(nuovo!=null){
            listaAllaFine.add(nuovo);
            nuovo = istanteSuccessivo(); 
        }
        stat.AggiornaStatistiche(listaAllaFine, true);
        return listaAllaFine;
    }
    
    /**
     * Ritorna il numero di istanti che copongono la simulazione.
     */
    public int numeroIstanti(){
        return this.listaIstanti.size();
    }
    
    /**
     * Ritorna un riferimento all'oggetto interno Statistiche
     */
    public Statistiche getStatistiche(){
        return this.stat;
    }
}