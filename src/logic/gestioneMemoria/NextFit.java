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
public class NextFit implements IAllocazione {
    public FrameMemoria Alloca ( FrameMemoria F, Vector<FrameMemoria> Liberi ) {
        boolean trovato=false; int i=0;
        while ( i<Liberi.size() && trovato==false ) {
            if ( Liberi.elementAt(i).getDimensione() >= F.getDimensione() )
                trovato=true;
            else i++;
        }
        if ( trovato ) return Liberi.elementAt(i);
        else return null;
    }
}
