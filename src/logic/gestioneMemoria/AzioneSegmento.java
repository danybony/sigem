/*
 * Azienda: Stylosoft
 * Nome file: AzioneSegmento.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche:
 *  
 *  - v.1.0 (29/02/2008): Impostazione base della classe in seguito ad estensione da classe astratta
 */

package logic.gestioneMemoria;

/**
 *
 * @author Davide
 */
public class AzioneSegmento extends Azione {
    
    /* -1    SWAP PIENA
     *  0    RAMP IENA
     *  1    INSERT RAM
     *  2    INSERT SWAP
     *  3    REMOVE RAM
     *  4    REMOVE SWAP
     *  5    LEGGI
     */
    /**
     * Frame libero di Destinazione
     */
    private FrameMemoria Destinazione=null;
    /**
     * Costruttore a tre parametri
     * @param T
     *   Tipo di Azione
     * @param F
     *   Frame coinvolto
     * @param D
     *   Frame Destinazione
     */
    public AzioneSegmento( int T, FrameMemoria F, FrameMemoria D ) {
        super(T,F); Destinazione=D;
    }
    /**
     * Costruttore a due parametri
     * @param T
     *   Tipo di Azione
     * @param F
     *   Frame coinvolto
     *
     */
    public AzioneSegmento( int T, FrameMemoria F) {
        super(T,F); Destinazione=null;
    }
    /**
     * Ottieni il Frame Destinazione
     * @return
     *    FrameMemoria di Deastinazione
     */
    public FrameMemoria getDestinazione() { return Destinazione; }
    
}
