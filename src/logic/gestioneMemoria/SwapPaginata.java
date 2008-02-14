/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;

/**
 *
 * @author PC
 */
class SwapPaginata extends MemoriaPaginata{
    
public SwapPaginata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneSwap()/conf.getDimensionePagina());
    }
    
    @Override
    /**Metodo che aggiunge una pagina nello Swap.
     */
    public void aggiungi(FrameMemoria pag){
        
    }
    
    @Override
    /**Metodo che toglie una pagina dallo Swap.
     */
    public FrameMemoria rimuovi(FrameMemoria pag){
        
    }
    
    @Override
    /**Metodo che elimina dallo Swap le pagine riferite ad un processo che ha
     * finito la sua esecuzione.
     */
    public void liberaMemoria(String idProcesso){
        
    }
}
