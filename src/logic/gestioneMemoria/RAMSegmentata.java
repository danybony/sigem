/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;


import java.util.Vector;
import logic.parametri.ConfigurazioneIniziale;

class RAMSegmentata extends MemoriaSegmentata{

    public RAMSegmentata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneRAM());
    }
    
    @Override
    /**Metodo che aggiunge un segmento nella RAM.
     */
    public void aggiungi(FrameMemoria seg){
        
    }
    
    @Override
    /**Metodo che toglie un segmento dalla RAM.
     */
    public FrameMemoria rimuovi(FrameMemoria seg){
        
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
    public Vector<FrameMemoria> getFrameLiberi(){
        
    }
    
    /**Metodo che costruisce un Vector composto dagli spazi occupati disponibili in
     * RAM. Utile per gli algoritmi di rimpiazzo dei segmenti.
     * 
     * @return
     */
    public Vector<FrameMemoria> getFrameOccupati(){
        
    }
    /**Metodo che restituisce il segmento con lo spazio libero più grande.
     * Utile per gli algoritmi di rimpiazzo dei segmenti.
     * 
     * @return
     */
    public FrameMemoria getSpazioMaggiore(){
        
    }
}
