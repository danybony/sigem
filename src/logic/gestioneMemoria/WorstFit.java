/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.Vector;
/**
 *
 * @author Davide
 */
public class WorstFit implements IAllocazione {
    public FrameMemoria Alloca ( FrameMemoria F, Vector<FrameMemoria> Liberi ) {
        int Pos=0,MaxDim=Liberi.elementAt(Pos).getDimensione();
        for( int i=1; i<Liberi.size(); i++ ) 
            if ( Liberi.elementAt(i).getDimensione() > MaxDim ) { 
                Pos=i; MaxDim=Liberi.elementAt(i).getDimensione();
            }
        if ( MaxDim>=F.getDimensione() ) return Liberi.elementAt(Pos);
        else return null;
    }
}
