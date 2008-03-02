
package logic.simulazione;

/*
 * Azienda: Stylosoft
 * Nome file: Istante.java
 * Package: logic.simulazione
 * Autore: Luca Rubin
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.3 (29/02/2008): Completamento documentazione
 *  - v.1.2 (28/02/2008): Implementato il costruttore
 *  - v.1.1 (26/02/2008): Aggiunta la documentazione JavaDoc.
 *  - v.1.0 (26/02/2008): Impostazione base della classe
 */

import java.util.LinkedList;
import logic.gestioneMemoria.Azione;
import logic.gestioneMemoria.OperazioneInMemoria;
import logic.schedulazione.PCB;

/**
 * Classe che memorizza le caratteristiche della simulazione per ogni unita'
 * di tempo.<br>
 * Ogni istanza di questa classe rappresenta in modo differenziale le 
 * caratteristiche che la simulazione assume al tempo t.<br>
 * Tale istanti potranno essere utilizzati dalla GUI tramite la classe
 * Player per visualizzare l'avenzamento della simulazione.
 * Dato che i dati sono memorizzati in modo differenziale, la memoria utilizzata
 * dalla simulazione risulta ridotta.
 */
public class Istante {
    
    /**
     * PCB del processo in esecuzione nell'unita' di tempo rappresentata
     * dall'istanza di Istante.
     */
    private PCB processoInEsecuzione = null;
    
    /**
     * Eventuale PCB del processo terminato all'istante t.
     */
    private PCB processoPrecedenteTerminato = null;
    
    /**
     * Segnala l'arrivo di uno o più processi nella coda dei pronti.
     */
    private boolean nuovoProcesso = false;
    
    /**
     * Numero di fault avvenuti in memoria nell'istante t.
     */
    private int numeroFault = 0;
    
    /**
     * Lista dei cambiamenti avvenuti in memoria: aggiunta o rimozione di 
     * pagine/segmenti.
     */
    private LinkedList<Azione> cambiamentiInMemoria;
    
    /**
     * Segnala l'eventuale riempimento della memoria centrale RAM.
     */
    private boolean full_RAM = false;
    
    /**
     * Segnala l'eventuale riempimento dell'area di Swap.
     */
    private boolean full_Swap = false;
    
    
    /**
     * Costruttore della classe.<br>
     * Provvede all'inizializzazione di tutti i campi dato della classe.
     * 
     * @param inEsecuzione
     *      PCB del processo in esecuzione all'istante t
     * @param terminato
     *      PCB dell'eventuale processo terminato all'istante t
     * @param nuovoProcesso
     *      booleano che dichiara l'arrivo di uno o piu' nuovi processi nella
     *      coda dei pronti all'istante t
     * @param fault
     *      numero di fault avvenuti in memoria all'istante t
     * @param memoria
     *      operazioni effettuate in memoria all'istante t (rimozione e/o aggiunta
     *      di pagine o segmenti)
     * @param full_RAM
     *      dichiara il riempimento della memoria centrale RAM all'istante t
     * @param full_Swap
     *      dichiara il riempimento dell'area di Swap all'istante t
     */
    public Istante(
            PCB inEsecuzione,
            PCB terminato,
            boolean nuovoProcesso,
            int fault,
            LinkedList<Azione> memoria,
            boolean full_RAM,
            boolean full_Swap
            ){
        this.processoInEsecuzione = inEsecuzione;
        this.processoPrecedenteTerminato = terminato;
        this.nuovoProcesso = nuovoProcesso;
        this.numeroFault = fault;
        this.cambiamentiInMemoria = memoria;
        this.full_RAM = full_RAM;
        this.full_Swap = full_Swap;
    }
    
    /**
     * Ritorna il PCB del processo in esecuzione all'istante t.
     */
    public PCB getProcessoInEsecuzione(){
        return this.processoInEsecuzione;
    }
    
    /**
     * Ritorna il PCB del processo terminato all'istante t. Nel caso venga 
     * ritornato un riferimento nullo, nessun processo e' stato terminato.
     */
    public PCB getProcessoPrecedenteTerminato(){
        return this.processoPrecedenteTerminato;
    }
    
    /**
     * Ritorna un booleano che indica l'eventuale arrivo di nuovi processi nella
     * coda dei pronti.
     */
    public boolean getNuovoProcesso(){
        return this.nuovoProcesso;
    }
    
    /**
     * Ritorna il numero di fault avvenuti in memori all'istante t.
     */
    public int getFault(){
        return this.numeroFault;
    }
    
    /**
     * Ritorna una lista che descrive le operazioni avvenute in memoria.
     */
    public LinkedList<Azione> getCambiamentiInMemoria(){
        return this.cambiamentiInMemoria;
    }
    
    /**
     * Ritorna un booleano che indica l'eventuale riempimento della RAM.
     */
    public boolean getFull_RAM(){
        return this.full_RAM;
    }
    
    /**
     * Ritorna un booleano che indica l'eventuale riempimento dell'area di Swap.
     */
    public boolean getFull_Swap(){
        return this.full_Swap;
    }
}
