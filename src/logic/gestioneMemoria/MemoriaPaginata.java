/**
 * Azienda: Stylosoft
 * Nome file: Memoria.java
 * Package: logic.gestioneMemoria
 * Autore: Alberto Zatton
 * Data: 29/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.1 (02/03/2008): Aggiunti i commenti sui parametri e sul tipo di ritorno
 *                        dei metodi
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

/**
 * Classe astratta che rappresenta le memorie che utilizzano paginazione. Verr�
 * concretizzata poi dalle classi RAMPaginata, SwapPaginata.
 */
abstract class MemoriaPaginata extends Memoria{

    
    /**
     * Campo dati che indica il numero di pagine che la memoria pu� ancora
     * contenere.
     */
    protected int pagineResidue=0;
    
    /**
     * Costruttore di MemoriaPaginata: si limita a inizializzare il numero
     * di pagine residue 
     * 
     * @param numeroPagine
     *      Capacit� della memoria espressa in numero di pagine che pu� contenere
     */
    public MemoriaPaginata(int numeroPagine) {
        pagineResidue=numeroPagine;
    }

    /**
     * Metodo la cui implementazione nelle sottoclassi dovr� permettere l'inserimento
     * di una pagina in memoria
     * 
     * @param frame
     *      Riferimento alla pagina da inserire
     * @return
     *      La posizione di inserimento della pagina
     * @throws logic.gestioneMemoria.MemoriaEsaurita
     */
    public abstract int aggiungi(FrameMemoria frame) throws MemoriaEsaurita;

}
