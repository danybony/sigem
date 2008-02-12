/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author PC
 */
class SwapPaginata extends MemoriaPaginata implements Memoria{
    
public SwapPaginata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneSwap/conf.getDimensionePagina);
    }
    
    @Override
    /**Metodo che aggiunge una pagina nello Swap.
     */
    public void aggiungi(Pagina pag){
        
    }
    
    @Override
    /**Metodo che toglie una pagina dallo Swap.
     */
    public Pagina rimuovi(String idPagina){
        
    }
    
    @Override
    /**Metodo che elimina dallo Swap le pagine riferite ad un processo che ha
     * finito la sua esecuzione.
     */
    public void liberaMemoria(String idProcesso){
        
    }
}
