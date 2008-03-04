/**
 * Azienda: Stylosoft
 * Nome file: SwapSegmentata.java
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
 * Classe astratta che rappresenta le memorie che utilizzano segmentazione. Verrà
 * concretizzata poi dalle classi RAMSegmentata, SwapSegmentata.
 */
abstract class MemoriaSegmentata extends Memoria{
    

    /**
     * Campo dati di tipo int che indica lo spazio residuo della RAM espresso 
     * in byte.
     */
    protected int spazioResiduo=0;
    
    /**
     * Costruttore di MemoriaPaginata: si limita a inizializzare lo spazio
     * residuo 
     * 
     * @param spazio
     *      Intero che rappresenta la grandezza della RAM espressa in byte
     */
    public MemoriaSegmentata(int spazio) {
        spazioResiduo=spazio;
    }
    
    /**
     * Metodo la cui implementazione nelle sottoclassi dovrà permettere l'aggiunta
     * di un segmento in memoria
     * 
     * @param frame
     *      Il riferimento al segmento da inserire
     * @param spazio
     *      Riferimento allo spazio scelto per inserire il segmento
     */
    public abstract void aggiungi(FrameMemoria frame, FrameMemoria spazio)throws MemoriaEsaurita;

}
