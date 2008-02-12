/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author PC
 */
class SwapSegmentata extends MemoriaSegmentata implements Memoria{

    public SwapSegmentata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneSwap);
    }
    
    @Override
    /**Metodo che aggiunge un segmento nello Swap.
     */
    public void aggiungi(Segmento seg){
        
    }
    
    @Override
    /**Metodo che toglie un segmento dallo Swap.
     */
    public Segmento rimuovi(String idSeg){
        
    }
    
    @Override
    /**Metodo che elimina dallo Swap i segmenti relativi ad un processo che ha
     * finito la sua esecuzione.
     */
    public void liberaMemoria(String idProcesso){
        
    }
}
