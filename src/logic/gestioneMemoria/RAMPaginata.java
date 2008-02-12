/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author PC
 */
class RAMPaginata extends MemoriaPaginata implements Memoria{

    public RAMPaginata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneRAM/conf.getDimensionePagina);
    }
    
    @Override
    /**Metodo che aggiunge una pagina nella RAM.
     */
    public void aggiungi(Pagina pag){
        
    }
    
    @Override
    /**Metodo che toglie una pagina dalla RAM.
     */
    public Pagina rimuovi(String idPagina){
        
    }
    
    @Override
    /**Metodo che marca come eliminabili le pagine riferite da un processo che
     * ha finito la sua esecuzione. Nel prossimo rimpiazzo delle pagine, queste
     * pagine marcate non finiranno più nello Swap ma saranno eliminate.
     */
    public void liberaMemoria(String idProcesso){
        
    }
}
