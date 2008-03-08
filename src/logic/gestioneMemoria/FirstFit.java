/*
 * Azienda: Stylosoft
 * Nome file: FirstFit.java
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
 * Alloca secondo la politica FirstFit
 * @author Davide Compagnin
 */
public class FirstFit implements IAllocazione {
    /**
     * Alloca un frame secondo la politica FirstFit
     * @param F
     *   FrameMemoria da allocare
     * @param Liberi
     *   Lista dei Frame liberi
     * @return
     *   Ritorna il frame sul quale è avvenuto l'inserimento.
     */
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
