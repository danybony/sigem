/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author Davide
 */
public class Azione {
    
    private int TipoAzione;
    /* 
     * 0 ERROR
     * 1 INSERISCI
     * 2 RIMUOVI
     * 3 RAMTOSWAP
     * 4 SWAPTORAM
     * 5 LEGGIRAM 
     */
    
    private FrameMemoria Frame;
    private int Pos;
    
    public Azione( int T, FrameMemoria F ) {
        TipoAzione=T; Frame=F; Pos=-1;
    }
    
    public Azione( int T, FrameMemoria F,int P)  {
        TipoAzione=T; Frame=F; Pos=P;
    }
    
    public int getAzione() {
        return TipoAzione;
    }
    
    public FrameMemoria getFrame() {
        return Frame;
    }
    
    public int getPos() {
        return Pos;
    }
    
}
