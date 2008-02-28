package logic.parametri;

import java.util.ArrayList; 
import logic.gestioneMemoria.FrameMemoria; 

/**
 * Classe per la memorizzazione dei dati si configurazione del sistema.
 * Le classi di SiGeM dovranno raccogliere da tale classe i parametri 
 * di configurazione.
 * Sono predisposti medodi per la modifica e l'interrogazione dei suoi campi
 * dati.
 */

public class ConfigurazioneIniziale {

    /**
     * Capacità del bus dati utilizzato per la comunicazione
     * tra hard-disk e RAM.Esso influenza i tempi per il caricamento/salvataggio
     * di pagine/segmenti.
     */
    private static int bandaBusDati = 0;
    
    /**
     * Specifica la politica di gestione della memoria centrale.
     * La politica scelta viene specificata da un intero secondo quanto segue:
     * 
     *    Pagine    Segmenti
     * 
     *  0                           nessuna polititica specificata
     *  1  NRU      First-Fit
     *  2  FIFO     Next-Fit
     *  3  FC       Best-Fit
     *  4  C        Worst-Fit
     *  5  LRU      Quick-Fit
     *  6  NFU      -
     *  7  A        -
     * 
     * Valori non previsti causeranno il sollevamento di un eccezione
     * 
     */
    private static int politicaGestioneMemoria = 0;
    
    /**
     * Specifica la modalità di gestione della memoria.
     * Tale modalità viene specificata con un valore intero
     * secondo questo schema:
     * 
     *  0                   modalità non specificata
     *  1   Paginazione
     *  2   Segmentazione
     * 
     * Valori non previsti causeranno il sollevamento di un eccezione
     * 
     */
    private static int modalitaGestioneMemoria;
    
    /**
     * Specifica la politica si schedulazione che lo scheduler dovrà usare
     * per scegliere il prossimo processo da mandare in esecuzione.
     * Il parametro intero che specifica la politica avrà il seguente 
     * significato: 
     * 
     *  0               politica non specificata
     *  1   FCFS
     *  2   SJF
     *  3   SRTN
     *  4   RR
     *  5   RRP
     *  6   P
     *  7   L
     * 
     * Valori non previsti causeranno il sollevamento di un eccezione
     * 
     */
    private static int politicaSchedulazioneProcessi;
    
    /**
     * Specifica la dimensione della RAM espressa in KB.
     */
    private static int dimensioneRAM;
    
    /**
     * Specifica la dimensione dell'area di Swap espressa in KB.
     */
    private static int dimensioneSwap;
    
    /**
     * Specifica il tempo necessario  al processore per togliere un processo
     * in esecuzione e rimpiazzarlo con un altro.
     * Viene espresso in millisecondi.
     */
    private static int tempoContextSwitch;
    
    /**
     * Tempo impiegato dal disco per adempiere ad una richiesta di scrittura o
     * lettura. Viene espressa in millesecondi.
     */
    private static int tempoAccessoDisco;
    
    /**
     * A cosa servono?
     */
    private static ArrayList<FrameMemoria> configurazioneRAM;
    private static ArrayList<FrameMemoria> configurazioneSwap;
    
    /**
     * Specifica la dimensione di una pagina espressa in KB.
     */
    private static int dimensionePagina;

    /**
     * Ritorna un intero che esprime la banda del bus-dati impostata.
     */
    public static int getBandaBusDati () {
        return bandaBusDati;
    }
    
    /**
     * Cambia il valore della banda del bus-dati.
     * 
     * @param nuovaBanda = parametro intero che specifica il nuovo valore per 
     * la banda del bus-dati.
     */
    public void setBandaBusDati (int nuovaBanda) {
        this.bandaBusDati = nuovaBanda;
    }
    
    /**
     * 
     *
    public static ArrayList<FrameMemoria> getConfigurazioneRAM () {
        return configurazioneRAM;
    }

    public void setConfigurazioneRAM (ArrayList<FrameMemoria> val) {
        this.configurazioneRAM = val;
    }

    public static ArrayList<FrameMemoria> getConfigurazioneSwap () {
        return configurazioneSwap;
    }

    public void setConfigurazioneSwap (ArrayList<FrameMemoria> val) {
        this.configurazioneSwap = val;
    }

    /**
     * Ritorna la rimensione di una pagina espressa in KB.
     */
    public static int getDimensionePagina () {
        return dimensionePagina;
    }

    /**
     * Imposta la dimensione di una pagina espressa in KB.
     * 
     * @param nuovaDimensionePagina = nuova dimensione per una pagina
     *
    public void setDimensionePagina (int val) {
        this.dimensionePagina = val;
    }
     * 
     * metodo eliminato perchè non è possibile la modifica delle dimensioni di
     * una pagina
    */
    
    /**
     * Ritorna la dimensione della memoria centrale RAM espressa in KB.
     */
    public static int getDimensioneRAM () {
        return dimensioneRAM;
    }

    /**
     * Metodo da togliere perchè non è possibile la modifica della dimensione 
     * della RAM
     *
    public void setDimensioneRAM (int val) {
        this.dimensioneRAM = val;
    }
     * 
     * 
     */

    /**
     * Ritorna la dimensione dell'area di Swap espressa in KB.
     */
    public static int getDimensioneSwap () {
        return dimensioneSwap;
    }

    /**
     * da togliere
    public void setDimensioneSwap (int val) {
        this.dimensioneSwap = val;
    }
     * 
     */

    /**
     * Restituisce la modalità di gestione di memoria.
     * Il tipo di ritorno è un intero con il seguente significato:
     *  
     *  1   Paginazione
     *  2   Segmentazione
     * 
     */
    public static int getModalitaGestioneMemoria () {
        return modalitaGestioneMemoria;
    }

    /* eliminare
    public void setModalitaGestioneMemoria (int val) {
        this.modalitaGestioneMemoria = val;
    }
*/
    /**
     * Ritorna un intero che specifica la politica di gestione della memoria
     * scelta.
     * L'intero ritornato avrà il seguente significato:
     * 
     *    Pagine    Segmenti
     * 
     *  1  NRU      First-Fit
     *  2  FIFO     Next-Fit
     *  3  FC       Best-Fit
     *  4  C        Worst-Fit
     *  5  LRU      Quick-Fit
     *  6  NFU      -
     *  7  A        -
     * 
     */
    public static int getPoliticaGestioneMemoria () {
        return politicaGestioneMemoria;
    }

    /**
     * elimina
     *
    public void setPoliticaGestioneMemoria (int val) {
        this.politicaGestioneMemoria = val;
    }
*/
    
    /**
     * Ritorna un intero che esprime la politica di schedulazione dei processi.
     * Il significato del valore di ritorno è il seguente:
     * 
     *  0               politica non specificata
     *  1   FCFS
     *  2   SJF
     *  3   SRTN
     *  4   RR
     *  5   RRP
     *  6   P
     *  7   L
     * 
     */
    public static int getPoliticaSchedulazioneProcessi () {
        return politicaSchedulazioneProcessi;
    }

    /**
     * elimina
     *
    public void setPoliticaSchedulazioneProcessi (int val) {
        this.politicaSchedulazioneProcessi = val;
    }
*/
    /**
     * Ritorna un valore intero che esprime il tempo di acesso del disco, 
     * espresso in millisecondi.
     */
    public static int getTempoAccessoDisco () {
        return tempoAccessoDisco;
    }
/*elimina
    public void setTempoAccessoDisco (int val) {
        this.tempoAccessoDisco = val;
    }
*/
    /**
     * Ritorna un intero che esprime il tempo di context-swich espresso in 
     * millisecondi.
     */
    public static int getTempoContextSwitch () {
        return tempoContextSwitch;
    }
/*elimina
    public void setTempoContextSwitch (int val) {
        this.tempoContextSwitch = val;
    }
*/
}

