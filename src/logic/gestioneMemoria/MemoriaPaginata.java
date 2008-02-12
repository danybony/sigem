/*
 * Classe astratta che rappresenta le memorie che utilizzano paginazione. Verr�
 * concretizzata poi dalle classi RAMPaginata, SwapPaginata e HDPaginata.
 */

package logic.gestioneMemoria;

import java.util.Vector;

abstract class MemoriaPaginata{
    
    /**Struttura dati Vector che conterr� le pagine residenti in memoria.
     */
    protected final Vector<Pagina> memoria=new Vector<Pagina>();
    
    /**Campo dati che indica il numero di pagine che la memoria pu� ancora
     * contenere.
     */
    protected int pagineResidue=0;
    
    /**Costruttore di MemoriaPaginata: si limita a inizializzare il numero
     * di pagine residue @param numeroPagine.
     */
    public MemoriaPaginata(int numeroPagine) {
        pagineResidue=numeroPagine;
    }

    

}
