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
public class QuickFit implements IAllocazione {
    public FrameMemoria Alloca ( FrameMemoria F, Vector<FrameMemoria> Liberi ) {
        int Dim=F.getDimensione(),Pos=0,Min=Dim-Liberi.elementAt(Pos).getDimensione();
        if ( Min<0 ) Min=-Min;
        for( int i=1; i<Liberi.size(); i++ ) {
            int valore=Dim-Liberi.elementAt(0).getDimensione();
            if ( valore<0 ) valore=-valore;
            if ( valore < Dim ) { Dim=valore; Pos=i; } 
        }
        return Liberi.elementAt(Pos);
    }
}
