/*
 * Azienda: Stylosoft
 * Nome file: WorstFit.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.Vector;
/**
 * Alloca secondo la politica WorstFit
 * @author Davide Compagnin
 */
public class WorstFit implements IAllocazione {
    /**
     * Alloca un frame secondo la politica WorstFit
     * @param F
     *   FrameMemoria da allocare
     * @param Liberi
     *   Lista dei Frame liberi
     * @return
     *   Ritorna il frame sul quale è avvenuto l'inserimento.
     */
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
