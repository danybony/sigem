/*
 * Classe astratta che rappresenta le memorie che utilizzano paginazione. Verrà
 * concretizzata poi dalle classi RAMPaginata, SwapPaginata e HDPaginata.
 */

package logic.gestioneMemoria;

abstract class MemoriaPaginata extends Memoria{

    
    /**Campo dati che indica il numero di pagine che la memoria può ancora
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
