/*
 * Classe astratta che rappresenta le memorie che utilizzano segmentazione. Verrà
 * concretizzata poi dalle classi RAMSegmentata, SwapSegmentata e HDSegmentata.
 */

package logic.gestioneMemoria;

import java.util.Vector;

abstract class MemoriaSegmentata{
    
    /**Struttura dati Vector che conterrà i segmenti residenti in memoria.
     */
    protected final Vector<Segmento> memoria=new Vector<Segmento>();
    
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

    

}
