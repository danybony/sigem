/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author PC
 */
class RAMSegmentata extends MemoriaSegmentata implements Memoria{

    public RAMSegmentata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneRAM);
    }
    
    @Override
    /**Metodo che aggiunge un segmento nella RAM.
     */
    public void aggiungi(Segmento seg){
        
    }
    
    @Override
    /**Metodo che toglie un segmento dalla RAM.
     */
    public Segmento rimuovi(String idSeg){
        
    }
    
    @Override
    /**Metodo che marca come eliminabili i segmenti riferiti da un processo che
     * ha finito la sua esecuzione. Nel prossimo rimpiazzo dei segmenti, queste
     * pagine marcate non finiranno più nello Swap ma saranno eliminate.
     */
    public void liberaMemoria(String idProcesso){
        
    }
    
    /**Metodo che costruisce un Vector composto dagli spazi vuoti disponibili in
     * RAM. Utile per gli algoritmi di rimpiazzo dei segmenti.
     * 
     * @return
     */
    public Vector<FrameMemoria> getSituazione(){
        
    }
}
