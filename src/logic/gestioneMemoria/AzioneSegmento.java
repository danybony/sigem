/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author Davide
 */
public class AzioneSegmento extends Azione {
    
    /* 0 ERROR
     * 1 INSERT RAM
     * 2 INSERT SWAP
     * 3 REMOVE RAM
     * 4 REMOVE SWAP
     * 5 LEGGI
     */
    
    private FrameMemoria Destinazione=null;
    
    public AzioneSegmento( int T, FrameMemoria F, FrameMemoria D ) {
        super(T,F); Destinazione=D;
    }
    
    public AzioneSegmento( int T, FrameMemoria F) {
        super(T,F); Destinazione=null;
    }
    
    public FrameMemoria getDestinazione() { return Destinazione; }
    
}
