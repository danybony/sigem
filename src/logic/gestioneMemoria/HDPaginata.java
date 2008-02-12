/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;


class HDPaginata extends MemoriaPaginata implements Memoria{

    public HDPaginata(){
        /**Inizializzo il numero massimo di pagine ad un valore fittizio*/
        super(999);
    }
    
    @Override
    /**Metodo che aggiunge una pagina nell'HD.
     */
    public void aggiungi(Pagina pag){
        
    }
    
    @Override
    /**Metodo che toglie una pagina dall'HD.
     */
    public Pagina rimuovi(String idPagina){
        
    }
    
    @Override
    /**Questo metodo non verrà mai usato nell'HD
     */
    public void liberaMemoria(String idProcesso){
        
    }
}
