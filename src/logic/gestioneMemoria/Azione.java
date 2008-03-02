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
