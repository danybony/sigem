package logic.parametri;

class EccezioneConfigurazioneNonValida extends Exception{}


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
     * di pagine/segmenti. Espressa in KB/sec.
     */
    private int bandaBusDati = 0;
    
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
    private int politicaGestioneMemoria = 0;
    
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
    private int modalitaGestioneMemoria = 0;
    
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
     * 
     * Valori non previsti causeranno il sollevamento di un eccezione
     * 
     */
    private int politicaSchedulazioneProcessi = 0;
    
    /**
     * Specifica la dimensione della RAM espressa in KB.
     */
    private int dimensioneRAM = 0;
    
    /**
     * Specifica la dimensione dell'area di Swap espressa in KB.
     */
    private int dimensioneSwap = 0;
    
    /**
     * Specifica il tempo necessario  al processore per togliere un processo
     * in esecuzione e rimpiazzarlo con un altro.
     * Viene espresso in millisecondi.
     */
    private int tempoContextSwitch = 0;
    
    /**
     * Tempo impiegato dal disco per adempiere ad una richiesta di scrittura o
     * lettura. Viene espressa in millesecondi.
     */
    private int tempoAccessoDisco = 0;
    
    /**
     * Specifica la dimensione di una pagina espressa in KB.
     */
    private int dimensionePagina = 0;
    
    /**
     *
     */
    private Processo[] listaProcessi = null;
    
    /**
     * Costruttore della classe.
     */
    public ConfigurazioneIniziale(int bandaBusDati, 
                                  int politicaGestioneMemoria,
                                  int modalitaGestioneMemoria,
                                  int politicaSchedulazione,
                                  int dimRAM,
                                  int dimSwap,
                                  int tempoContextSwitch,
                                  int tempoAccessoDisco,
                                  int dimPagina,
                                  Processo[] listaProcessi) throws EccezioneConfigurazioneNonValida{
        
        if(bandaBusDati<=0){throw new EccezioneConfigurazioneNonValida();}
        switch (modalitaGestioneMemoria){
            case 1: // Paginazione
                if(politicaGestioneMemoria<1 || politicaGestioneMemoria>7)
                    throw new EccezioneConfigurazioneNonValida();
                break;
            case 2: // Segmentazione
                if(politicaGestioneMemoria<1 || politicaGestioneMemoria>5)
                    throw new EccezioneConfigurazioneNonValida();
                break;
            default: // Modalita gestione memoria errata
                throw new EccezioneConfigurazioneNonValida();
        }
        if(politicaSchedulazione<1 || politicaSchedulazione>6)
            throw new EccezioneConfigurazioneNonValida();
        if(dimRAM<=0)throw new EccezioneConfigurazioneNonValida();
        if(dimSwap<=0)throw new EccezioneConfigurazioneNonValida();
        if(tempoContextSwitch<=0)throw new EccezioneConfigurazioneNonValida();
        if(tempoAccessoDisco<=0)throw new EccezioneConfigurazioneNonValida();
        if(dimPagina<=0)throw new EccezioneConfigurazioneNonValida();
        if(listaProcessi==null)throw new EccezioneConfigurazioneNonValida();
        
        this.bandaBusDati=bandaBusDati;
        this.politicaGestioneMemoria=politicaGestioneMemoria;
        this.modalitaGestioneMemoria=modalitaGestioneMemoria;
        this.politicaSchedulazioneProcessi=politicaSchedulazione;
        this.dimensioneRAM=dimRAM;
        this.dimensioneSwap=dimSwap;
        this.tempoContextSwitch=tempoContextSwitch;
        this.tempoAccessoDisco=tempoAccessoDisco;
        this.dimensionePagina=dimPagina;
        this.listaProcessi=listaProcessi;
    }

    /**
     * Ritorna un intero che esprime la banda del bus-dati impostata.
     */
    public int getBandaBusDati () {
        return bandaBusDati;
    }
    
    /**
     * Ritorna la rimensione di una pagina espressa in KB.
     */
    public int getDimensionePagina () {
        return dimensionePagina;
    }
    
    /**
     * Ritorna la dimensione della memoria centrale RAM espressa in KB.
     */
    public int getDimensioneRAM () {
        return dimensioneRAM;
    }

    /**
     * Ritorna la dimensione dell'area di Swap espressa in KB.
     */
    public int getDimensioneSwap () {
        return dimensioneSwap;
    }

    /**
     * Restituisce la modalità di gestione di memoria.
     * Il tipo di ritorno è un intero con il seguente significato:
     *  
     *  1   Paginazione
     *  2   Segmentazione
     * 
     */
    public int getModalitaGestioneMemoria () {
        return modalitaGestioneMemoria;
    }

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
    public int getPoliticaGestioneMemoria () {
        return politicaGestioneMemoria;
    }
    
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
     * 
     */
    public int getPoliticaSchedulazioneProcessi () {
        return politicaSchedulazioneProcessi;
    }

    /**
     * Ritorna un valore intero che esprime il tempo di acesso del disco, 
     * espresso in millisecondi.
     */
    public int getTempoAccessoDisco () {
        return tempoAccessoDisco;
    }
    
    /**
     * Ritorna un intero che esprime il tempo di context-swich espresso in 
     * millisecondi.
     */
    public int getTempoContextSwitch () {
        return tempoContextSwitch;
    }
    
    /**
     *
     */
    public Processo[] getListaProcessi(){
        return listaProcessi;
    }
}

