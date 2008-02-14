/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;


class SwapSegmentata extends MemoriaSegmentata{

    public SwapSegmentata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneSwap());
    }
    
    @Override
    /**Metodo che aggiunge un segmento nello Swap.
     */
    public void aggiungi(FrameMemoria seg){
        
    }
    
    @Override
    /**Metodo che toglie un segmento dallo Swap.
     */
    public FrameMemoria rimuovi(FrameMemoria seg){
        
    }
    
    @Override
    /**Metodo che elimina dallo Swap i segmenti relativi ad un processo che ha
     * finito la sua esecuzione.
     */
    public void liberaMemoria(String idProcesso){
        
    }
}
