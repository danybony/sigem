package logic.parametri;

/*
 * Azienda: Stylosoft
 * Nome file: ConfigurazioneIniziale.java
 * Package: logic.parametri
 * Autore: Luca Rubin
 * Data: 29/02/2008
 * Versione: 1.2
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.2 (28/02/2008): Migliorata la documentazione JavaDoc con la formattazione
 *                        HTML; ottimizzati i controlli fatti dal costruttore, riducendo
 *                        la complessita' ciclomatica
 *  - v.1.1 (26/02/2008): Aggiunta la documentazione JavaDoc.
 *  - v.1.0 (26/02/2008): Impostazione base della classe
 */

import java.util.LinkedList;


/**
 * Classe per la memorizzazione dei dati si configurazione del sistema.<br>
 * Le classi di SiGeM dovranno raccogliere da tale classe i parametri 
 * di configurazione.<br>
 * Sono predisposti medodi per la modifica e l'interrogazione dei suoi campi
 * dati.<br>
 */

public class ConfigurazioneIniziale {

    /**
     * Capacità del bus dati utilizzato per la comunicazione
     * tra hard-disk e RAM.<br>Esso influenza i tempi per il caricamento/salvataggio
     * di pagine/segmenti.<br>Espressa in KB/sec.
     */
    private final int BANDA_BUS_DATI;
    
    /**
     * Specifica la politica di gestione della memoria centrale. <br>
     * La politica scelta viene specificata da un intero secondo quanto segue:
     * <br><br>
     * <table border="1">
     *  <tr align="center"><td><b>Parametro</b></td><td><b>Pagine</b></td><td><b>Segmenti</b></td></tr>
     *  <tr align="center"><td>0</td><td colspan="2">nessuna polititica specificata</td></tr>
     *  <tr align="center"><td>1</td><td>  NRU  </td><td>    First-Fit   </td></tr>
     *  <tr align="center"><td>2</td><td>  FIFO </td><td>    Next-Fit    </td></tr>
     *  <tr align="center"><td>3</td><td>  SC   </td><td>    Best-Fit    </td></tr>
     *  <tr align="center"><td>4</td><td>  C    </td><td>    Worst-Fit   </td></tr>
     *  <tr align="center"><td>5</td><td>  LRU  </td><td>    Quick-Fit   </td></tr>
     *  <tr align="center"><td>6</td><td>  NFU  </td><td>    -           </td></tr>
     *  <tr align="center"><td>7</td><td>  A    </td><td>    -           </td></tr>
     * </table>
     * <br>
     * Valori non previsti causeranno il sollevamento di un eccezione
     * 
     */
    private final int POLITICA_GESTIONE_MEMORIA;
    
    /**
     * Specifica la modalità di gestione della memoria.<br>
     * Tale modalità viene specificata con un valore intero
     * secondo questo schema:<br><br>
     * 
     * <table border="1">
     *  <tr align="center"><td><b>Parametro</b></td><td><b>Modalita'</b></td></tr>
     *  <tr align="center"><td>0</td><td>modalità non specificata</td></tr>
     *  <tr align="center"><td>1</td><td>  Paginazione  </td></tr>
     *  <tr align="center"><td>2</td><td>  Segmentazione </td></tr>
     * </table>
     * <br>
     * Valori non previsti causeranno il sollevamento di un eccezione
     * 
     */
    private final int MODALITA_GESTIONE_MEMORIA;
    
    /**
     * Specifica la politica si schedulazione che lo scheduler dovra usare
     * per scegliere il prossimo processo da mandare in esecuzione.<br>
     * Il parametro intero che specifica la politica avra il seguente 
     * significato: 
     * <br><br>
     * <table border="1">
     *  <tr align="center"><td><b>Parametro</b></td><td><b>Politica di schedulazione</b></td></tr>
     *  <tr align="center"><td>0</td><td colspan="2">nessuna polititica specificata</td></tr>
     *  <tr align="center"><td>1</td><td> FCFS </td></tr>
     *  <tr align="center"><td>2</td><td> SJF  </td></tr>
     *  <tr align="center"><td>3</td><td> SRTN </td></tr>
     *  <tr align="center"><td>4</td><td> RR   </td></tr>
     *  <tr align="center"><td>5</td><td> RRP  </td></tr>
     *  <tr align="center"><td>6</td><td> RRPP </td></tr>
     *  <tr align="center"><td>7</td><td> P    </td></tr>
     * </table>
     * <br>
     * 
     * Valori non previsti causeranno il sollevamento di un eccezione
     * 
     */
    private final int POLITICA_SCHEDULAZIONE_PROCESSI;
    
    /**
     * Specifica la dimensione della RAM espressa in KB.
     */
    private final int DIMENSIONE_RAM;
    
    /**
     * Specifica la dimensione dell'area di Swap espressa in KB.
     */
    private final int DIMENSIONE_SWAP;
    
    /**
     * Specifica il tempo necessario  al processore per togliere un processo
     * in esecuzione e rimpiazzarlo con un altro.<br>
     * Viene espresso in millisecondi.
     */
    private final int TEMPO_CONTEXT_SWITCH;
    
    /**
     * Tempo impiegato dal disco per adempiere ad una richiesta di scrittura o
     * lettura. Viene espressa in millesecondi.
     */
    private final int TEMPO_ACCESSO_DISCO;
    
    /**
     * Specifica la dimensione di una pagina espressa in KB.
     */
    private final int DIMENSIONE_PAGINA;
    
    /**
     * Lista di processi da utilizzare nella simulazione.
     */
    private final LinkedList<Processo> LISTA_PROCESSI;
    
    /**
     * Costruttore della classe.<br>
     * Oltre a costruire un oggetto della classe ConfigurazioneIniziale,
     * controlla che tutti i campi dati della classe assumano valori adeguati
     * e corretti. Il controllo viene essettuato sui sul valore dei parametri
     * passati. <br>
     * Nel caso anche solo uno dei parametri contenga un valore errato, verra'
     * sollevata un eccezione EccezioneConfigurazioneNonValida. <br>
     * Attenzione: una volta creato un oggetto di questa classe, i suoi campi
     * dati non saranno piu' modificabili. <br>Nel caso la configurazioneIniziale
     * creata non fosse quella desiderata, si dovra' quindi procedere con la
     * creazione di una nuova istanza.
     * 
     * @param bandaBusDati
     *            la banda del bus-dati espressa in KB
     * @param politicaGestioneMemoria
     *            la politica di gestione della memoria sottoforma di un intero
     *            secondo la tabella del metodo getPoliticaGestioneMemoria().
     * @param modalitaGestioneMemori
     *            la modalita' di gestione della memoria sottoforma di un intero
     *            secondo la tabella del metodo getModalitaGestioneMemoria().
     * @param politicaSchedulazione
     *            la politica di schedulazione dei processi sottoforma di un intero
     *            secondo la tabella del metodo getPoliticaSchedulazioneProcessi().
     * @param dimRAM
     *            la dimensione della memoria centrale RAM, espressa in KB.
     * @param dimSwap
     *            la dimensione dell'area di Swap, espressa in KB.
     * @param tempoContextSwitch
     *            il tempo di context switch espresso in millisecondi
     * @param tempoAccessoDisco
     *            il tempo di accesso al disco espresso in millisecondi
     * @param dimPagina
     *            dimensione delle pagine in KB. Se si utilizza la segmentazione
     *            questo parametro puo' essere posto a zero.
     * @param listaProcessi
     *            la lista dei processi che saranno trattati nella simulazione.
     * 
     */
    public ConfigurazioneIniziale(
                                  int bandaBusDati, 
                                  int politicaGestioneMemoria,
                                  int modalitaGestioneMemoria,
                                  int politicaSchedulazione,
                                  int dimRAM,
                                  int dimSwap,
                                  int tempoContextSwitch,
                                  int tempoAccessoDisco,
                                  int dimPagina,
                                  LinkedList<Processo> listaProcessi
                                  ) 
                                  throws EccezioneConfigurazioneNonValida{
        
        // il bus-dati deve essere > 0
        if(bandaBusDati<=0){throw new EccezioneConfigurazioneNonValida();}
        
        // modalità di gestione della memoria
        switch (modalitaGestioneMemoria){
            case 1: // Paginazione
                // controllo il valore della politica di rimpiazzo delle pagine
                if(politicaGestioneMemoria<1 || politicaGestioneMemoria>7)
                    throw new EccezioneConfigurazioneNonValida();
                // la dimensione di una pagina deve essere >0
                if(dimPagina<=0)throw new EccezioneConfigurazioneNonValida();
                break;
            case 2: // Segmentazione
                // controllo il valore della politica di rimpiazzo dei segmenti
                if(politicaGestioneMemoria<1 || politicaGestioneMemoria>5)
                    throw new EccezioneConfigurazioneNonValida();
                break;
            default: // Modalita' gestione memoria errata
                throw new EccezioneConfigurazioneNonValida();
        }
        // controllo il valore della politica di scheduling dei processi
        if(politicaSchedulazione<1 || politicaSchedulazione>7)
            throw new EccezioneConfigurazioneNonValida();
        
        if(dimRAM<=0)throw new EccezioneConfigurazioneNonValida();
        if(dimSwap<=0)throw new EccezioneConfigurazioneNonValida();
        if(tempoContextSwitch<=0)throw new EccezioneConfigurazioneNonValida();
        if(tempoAccessoDisco<=0)throw new EccezioneConfigurazioneNonValida();
        if(listaProcessi==null)throw new EccezioneConfigurazioneNonValida();
        
        // tutti i parametri sono conformi; posso procedere con la creazione
        // dell'oggetto
        this.BANDA_BUS_DATI=bandaBusDati;
        this.POLITICA_GESTIONE_MEMORIA=politicaGestioneMemoria;
        this.MODALITA_GESTIONE_MEMORIA=modalitaGestioneMemoria;
        this.POLITICA_SCHEDULAZIONE_PROCESSI=politicaSchedulazione;
        this.DIMENSIONE_RAM=dimRAM;
        this.DIMENSIONE_SWAP=dimSwap;
        this.TEMPO_CONTEXT_SWITCH=tempoContextSwitch;
        this.TEMPO_ACCESSO_DISCO=tempoAccessoDisco;
        this.DIMENSIONE_PAGINA=dimPagina;
        this.LISTA_PROCESSI=listaProcessi;
    }

    /**
     * Ritorna un intero che esprime la banda del bus-dati impostata.
     */
    public int getBandaBusDati () {
        return BANDA_BUS_DATI;
    }
    
    /**
     * Ritorna la rimensione di una pagina espressa in KB.
     */
    public int getDimensionePagina () {
        return DIMENSIONE_PAGINA;
    }
    
    /**
     * Ritorna la dimensione della memoria centrale RAM espressa in KB.
     */
    public int getDimensioneRAM () {
        return DIMENSIONE_RAM;
    }

    /**
     * Ritorna la dimensione dell'area di Swap espressa in KB.
     */
    public int getDimensioneSwap () {
        return DIMENSIONE_SWAP;
    }

    /**
     * Restituisce la modalita' di gestione di memoria.<br>
     * Il tipo di ritorno è un intero con il seguente significato:
     * <br><br>
     * <table border="1">
     *  <tr align="center"><td><b>Parametro</b></td><td><b>Modalita'</b></td></tr>
     *  <tr align="center"><td>0</td><td>modalità non specificata</td></tr>
     *  <tr align="center"><td>1</td><td>  Paginazione  </td></tr>
     *  <tr align="center"><td>2</td><td>  Segmentazione </td></tr>
     * </table>
     * <br>
     * 
     */
    public int getModalitaGestioneMemoria () {
        return MODALITA_GESTIONE_MEMORIA;
    }

    /**
     * Ritorna un intero che specifica la politica di gestione della memoria
     * scelta.<br>
     * L'intero ritornato avra' il seguente significato:
     * <br><br>
     * <table border="1">
     *  <tr align="center"><td><b>Parametro</b></td><td><b>Pagine</b></td><td><b>Segmenti</b></td></tr>
     *  <tr align="center"><td>0</td><td colspan="2">nessuna polititica specificata</td></tr>
     *  <tr align="center"><td>1</td><td>  NRU  </td><td>    First-Fit   </td></tr>
     *  <tr align="center"><td>2</td><td>  FIFO </td><td>    Next-Fit    </td></tr>
     *  <tr align="center"><td>3</td><td>  SC   </td><td>    Best-Fit    </td></tr>
     *  <tr align="center"><td>4</td><td>  C    </td><td>    Worst-Fit   </td></tr>
     *  <tr align="center"><td>5</td><td>  LRU  </td><td>    Quick-Fit   </td></tr>
     *  <tr align="center"><td>6</td><td>  NFU  </td><td>    -           </td></tr>
     *  <tr align="center"><td>7</td><td>  A    </td><td>    -           </td></tr>
     * </table>
     * <br>
     * 
     */
    public int getPoliticaGestioneMemoria () {
        return POLITICA_GESTIONE_MEMORIA;
    }
    
    /**
     * Ritorna un intero che esprime la politica di schedulazione dei processi.<br>
     * Il significato del valore di ritorno e' il seguente:
     * <br><br>
     * <table border="1">
     *  <tr align="center"><td><b>Parametro</b></td><td><b>Politica di schedulazione</b></td></tr>
     *  <tr align="center"><td>0</td><td colspan="2">nessuna polititica specificata</td></tr>
     *  <tr align="center"><td>1</td><td> FCFS </td></tr>
     *  <tr align="center"><td>2</td><td> SJF  </td></tr>
     *  <tr align="center"><td>3</td><td> SRTN </td></tr>
     *  <tr align="center"><td>4</td><td> RR   </td></tr>
     *  <tr align="center"><td>5</td><td> RRP  </td></tr>
     *  <tr align="center"><td>6</td><td> RRPP </td></tr>
     *  <tr align="center"><td>7</td><td> P    </td></tr>
     * </table>
     * <br>
     * 
     */
    public int getPoliticaSchedulazioneProcessi () {
        return POLITICA_SCHEDULAZIONE_PROCESSI;
    }

    /**
     * Ritorna un valore intero che esprime il tempo di acesso del disco, 
     * espresso in millisecondi.
     */
    public int getTempoAccessoDisco () {
        return TEMPO_ACCESSO_DISCO;
    }
    
    /**
     * Ritorna un intero che esprime il tempo di context-swich espresso in 
     * millisecondi.
     */
    public int getTempoContextSwitch () {
        return TEMPO_CONTEXT_SWITCH;
    }
    
    /**
     * Ritorna la lista di processi
     */
    public LinkedList<Processo> getListaProcessi(){
        return LISTA_PROCESSI;
    }
}

