/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author Davide
 */
public class AzionePagina extends Azione {
    
    /* -1    SWAP PIENA
     *  0    RAMP IENA
     *  1    INSERT RAM
     *  2    INSERT SWAP
     *  3    REMOVE RAM
     *  4    REMOVE SWAP
     *  5    LEGGI
     */
    
    private int Pos;
    
    public AzionePagina( int T, FrameMemoria F, int P ) {
        super(T,F); Pos=P;
    }
    
    public AzionePagina( int T, FrameMemoria F) {
        super(T,F); Pos=-1;
    }
    
    public int getPosizione() { return Pos; }
    
}
