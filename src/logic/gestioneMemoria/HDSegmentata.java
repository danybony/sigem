/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author PC
 */
class HDSegmentata extends MemoriaSegmentata implements Memoria{

    public HDSegmentata(ConfigurazioneIniziale conf){
        
        /*Valore fittizio che inizializza dimensione della memoria*/
        super(999999999);
    }
    
    @Override
    /**Metodo che aggiunge un segmento nell'HD.
     */
    public void aggiungi(Segmento seg){
        
    }
    
    @Override
    /**Metodo che toglie un segmento dall'HD.
     */
    public Segmento rimuovi(String idSeg){
        
    }
    
    @Override
    /**Metodo non utilizzato in HD
     */
    public void liberaMemoria(String idProcesso){
        
    }
}
