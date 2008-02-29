
package logic.gestioneMemoria;

import logic.schedulazione.PCB;


/**
 * Interfaccia che rappresenta la struttura base di una pagina o segmento.<br>
 */
public interface FrameMemoria {

    /**
     * Ritorna l'indirizzo di un elemento che modella la memoria.
     */
    public String getIndirizzo ();

    /**
     * Ritorna la dimensione di un elemento che modella la memoria in KB.
     */
    public int getDimensione ();

    /**
     * Ritorna se l'elemento che modella la memoria e' in RAM.
     */
    public boolean getInRAM ();

    /**
     * Sposta un elemento che modella la memoria in RAM o lo toglie.
     */
    public boolean setInRAM (boolean nuovoStato);

    /**
     * Ritorna un booleano che indica se l'elemento che modella la memoria e'
     * di sola lettura.
     */
    public boolean getSolaLettura ();

    /**
     * Imposta un elemento che modella la memoria in modalita' sola lettura o meno.
     */
    public boolean setSolaLettura (boolean nuovoStato);
    
    /**
     * Ritorna il processo che detiene elemento che modella la memoria.
     */
    public PCB getIdProcesso();
    
    /**
     * Imposta il processo che detiene l'elemento che modella la memoria.
     */
    public boolean setIdProcesso(PCB idProcessoPassato);

}

