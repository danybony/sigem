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
class RAMPaginata extends MemoriaPaginata{

    public RAMPaginata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneRAM()/conf.getDimensionePagina());
    }
    
    @Override
    /**Metodo che aggiunge una pagina nella RAM.
     */
    public void aggiungi(FrameMemoria pag){
        
    }
    
    @Override
    /**Metodo che toglie una pagina dalla RAM.
     */
    public FrameMemoria rimuovi(FrameMemoria pag){
        
    }
    
    @Override
    /**Metodo che marca come eliminabili le pagine riferite da un processo che
     * ha finito la sua esecuzione. Nel prossimo rimpiazzo delle pagine, queste
     * pagine marcate non finiranno più nello Swap ma saranno eliminate.
     */
    public void liberaMemoria(String idProcesso){
        
    }
}
