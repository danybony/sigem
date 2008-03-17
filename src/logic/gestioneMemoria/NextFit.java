/*
 * Azienda: Stylosoft
 * Nome file: RAMPaginata.java
 * Package: logic.gestioneMemoria
 * Autore: Alberto Zatton
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.3 (04/03/2008): Modificato il costruttore: ora ha 2 parametri e pu�
 *                        lanciare l'eccezione PaginaNulla
 *  - v.1.2 (03/03/2008): Modificato il metodo rimuovi: ora ritorna un bool
 *  - v.1.1 (02/03/2008): Nuovo costruttore come da issue 28
 *                        Nuovo metodo indiceDi(FrameMemoria) come da issue 29
 *                        Aggiunti i commenti sui metodi e sul tipo di ritorno
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.Vector;
/**
 * Alloca secondo la politica NextFit
 * @author Davide Compagnin
 */
public class NextFit implements IAllocazione {
    /**
     * Memorizza la posizione ultima di allocazione
     */
    private int P=-1;
    
    /**
     * Alloca un frame secondo la politica NextFit
     * @param F
     *   FrameMemoria da allocare
     * @param Liberi
     *   Lista dei Frame liberi
     * @return
     *   Ritorna il frame sul quale è avvenuto l'inserimento.
     */
    public FrameMemoria Alloca ( FrameMemoria F, Vector<FrameMemoria> Liberi, int Posizioni[] ) {
        int i=0,dim=Liberi.size();
        while( P<=Posizioni[i] && i<dim ) i++;
        boolean trovato=false;
        int j=i%dim; 
        while( j<dim && !trovato ) {
            if ( Liberi.elementAt(j).getDimensione() >= F.getDimensione() )
                trovato=true;
            else j++;
        }
        if ( !trovato )
            for( j=0; j<dim && !trovato; j++ )
                if ( Liberi.elementAt(j).getDimensione() >= F.getDimensione() )
                trovato=true;
            
        P=j%dim;
        return Liberi.elementAt(P);
    }
}
