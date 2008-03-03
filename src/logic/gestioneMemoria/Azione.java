/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author Davide
 */
public abstract class Azione {
    
    private int TipoAzione;
    
    /* 0 ERROR
     * 1 INSERT RAM
     * 2 INSERT SWAP
     * 3 REMOVE RAM
     * 4 REMOVE SWAP
     * 5 LEGGI
     */
    
    private FrameMemoria Frame;
    
    public Azione( int T, FrameMemoria F ) {
        TipoAzione=T; Frame=F;
    }
    
    public int getAzione() {
        return TipoAzione;
    }
    
    public FrameMemoria getFrame() {
        return Frame;
    }
    
}
