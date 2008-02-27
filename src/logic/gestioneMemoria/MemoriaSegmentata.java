/**
 * Classe astratta che rappresenta le memorie che utilizzano segmentazione. Verrà
 * concretizzata poi dalle classi RAMSegmentata, SwapSegmentata.
 */

package logic.gestioneMemoria;


abstract class MemoriaSegmentata extends Memoria{
    

    /**Campo dati di tipo int che indica lo spazio residuo della RAM espresso 
     * in byte.
     */
    protected int spazioResiduo=0;
    
    /**Costruttore di MemoriaPaginata: si limita a inizializzare lo spazio
     * residuo @param spazio.
     */
    public MemoriaSegmentata(int spazio) {
        spazioResiduo=spazio;
    }

    public abstract void aggiungi(FrameMemoria frame, FrameMemoria spazio);

}
