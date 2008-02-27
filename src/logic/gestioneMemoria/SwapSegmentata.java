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
    public void aggiungi(FrameMemoria seg, FrameMemoria spazio){
        memoria.add(seg);
        spazioResiduo-=seg.getDimensione();
    }
    
    @Override
    /**Metodo che toglie un segmento dallo Swap.
     */
    public FrameMemoria rimuovi(FrameMemoria seg){
        memoria.remove(seg);
        spazioResiduo-=seg.getDimensione();
        return seg;
    }
    
    @Override
    /**Metodo che elimina dallo Swap i segmenti relativi ad un processo che ha
     * finito la sua esecuzione.
     */
    public void liberaMemoria(int idProcesso){
        for(int i=0;i<memoria.size();i++){
            if (memoria.get(i).getIdProcesso()==idProcesso){
                rimuovi(memoria.get(i));
                i-=1;
            }
        }
    }
    
    @Override
    /**Metodo cerca non usato in SwapSegmentata*/
    public boolean cerca(FrameMemoria seg){
        return true;
    }
}
