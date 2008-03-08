/*
 * Azienda: Stylosoft
 * Nome file: Azione.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.1 (02/03/2008): Definiti nuovi valori per le azioni
 *  - v.1.0 (29/02/2008): Prima Scrittura
 */

package logic.gestioneMemoria;

/**
 *
 * @author Davide Compagnin
 * Classe pubblica, astratta. Questa classe rappresenta un'azione generica 
 * effettuata in memoria
 */
public abstract class Azione {
    /**
    *
    * Contiene il timpo di azione (intero)
     * -1    SWAP PIENA
     *  0    RAMP IENA
     *  1    INSERT RAM
     *  2    INSERT SWAP
     *  3    REMOVE RAM
     *  4    REMOVE SWAP
     *  5    LEGGI
    */
    private int TipoAzione;
    
    /**
     * Riferimento al FrameMemoria 
     */
    
    private FrameMemoria Frame;
    
    /**
     * Costruttore
     * @param T
     *   Tipo di Azione
     * @param F
     *   Frame conivolto nell'azione
     */
    
    public Azione( int T, FrameMemoria F ) {
        TipoAzione=T; Frame=F;
    }
    /**
     * 
     * @return
     *   Ritorna il tipo di azione
     */
    public int getAzione() {
        return TipoAzione;
    }
    /**
     * 
     * @return
     *  Ritorna il FrameMemoria
     */
    public FrameMemoria getFrame() {
        return Frame;
    }
    
}
