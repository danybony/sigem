/*
 * Azienda: Stylosoft
 * Nome file: BestFit.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 30/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.0 (30/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.Vector;

/**
 * Alloca secondo la politica BestFit.
 * @author Davide Compagnin
 */
public class BestFit implements IAllocazione {
    /**
     * Alloca un frame secondo la politica BestFit
     * @param F
     *   FrameMemoria da allocare
     * @param Liberi
     *   Lista dei Frame liberi
     * @return
     *   Ritorna il frame sul quale è avvenuto l'inserimento.
     */
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
