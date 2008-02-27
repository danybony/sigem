/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;


class SwapPaginata extends MemoriaPaginata{
    

public SwapPaginata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneSwap()/conf.getDimensionePagina());
    }
    
    @Override
    /**Metodo che aggiunge una pagina nello Swap.
     */
    public int aggiungi(FrameMemoria pag) throws MemoriaEsaurita{
        if(pagineResidue>0){
            memoria.add(pag);
            pagineResidue--;
            return memoria.size();
        }
        else{
            throw new MemoriaEsaurita(0);
        }
    }
    
    @Override
    /**Metodo che toglie una pagina dallo Swap.
     */
    public FrameMemoria rimuovi(FrameMemoria pag){
        memoria.remove(pag);
        pagineResidue++;
        return pag;
    }
    
    @Override
    /**Metodo che elimina dallo Swap le pagine riferite ad un processo che ha
     * finito la sua esecuzione.
     */
    public void liberaMemoria(int idProcesso){
        for(int i=0; i<memoria.size(); i++){
            if(memoria.get(i).getIdProcesso()==idProcesso){
                memoria.remove(i);
                pagineResidue++;
            }
        }
    }
    
    @Override
    /**Metodo lasciato vuoto in quanto mai usato in Swap
     */
    public boolean cerca(FrameMemoria pag){
        return true;
    }
}
