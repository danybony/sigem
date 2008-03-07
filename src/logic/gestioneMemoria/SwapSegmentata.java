/**
 * Azienda: Stylosoft
 * Nome file: SwapSegmentata.java
 * Package: logic.gestioneMemoria
 * Autore: Alberto Zatton
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.3 (04/03/2008): Il metodo aggiungi ora pu� lanciare l'eccezione MemoriaEsaurita()
 *  - v.1.2 (03/03/2008): Modificato il metodo rimuovi: adesso ritorna un bool
 *  - v.1.1 (02/03/2008): Aggiunti i commenti sui parametri e sul tipo di ritorno
 *                        dei metodi
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;

/**
 * Classe che rappresenta la memoria di Swap modellata attraverso segmenti
 */
public class SwapSegmentata extends MemoriaSegmentata{

    /**
     * Costruttore di SwapSegmentata. Si limita ad impostare la capienza della
     * memoria
     * 
     * @param conf
     *      Riferimento all'istanza di ConfigurazioneIniziale
     */
    public SwapSegmentata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneSwap());
    }
    
    
    /**
     * Metodo che aggiunge un segmento nello Swap.
     * 
     * @param seg
     *      Riferimento al segmento da inserire
     * @param spazio
     *      Parametro fittizio e inutilizzato, presente in quanto richiesto
     *      nella superclasse
     */
    @Override
    public void aggiungi(FrameMemoria seg, FrameMemoria spazio) throws MemoriaEsaurita{
        if(spazioResiduo>seg.getDimensione()){
            memoria.add(seg);
            spazioResiduo-=seg.getDimensione();
        }
        else{
            throw new MemoriaEsaurita(0);
        }
        
    }
    
    
    /**
     * Metodo che toglie un segmento dallo Swap.
     * 
     * @param seg
     *      Riferimento al segmento da rimuovere
     */
    @Override
    public boolean rimuovi(FrameMemoria seg){
        if(memoria.remove(seg)) {
            spazioResiduo+=seg.getDimensione();
            return true;
        }
        
        return false;
    }
    
    
    /**
     * Metodo che elimina dallo Swap i segmenti relativi ad un processo che ha
     * finito la sua esecuzione.
     * 
     * @param idProcesso
     *      Intero che identifica il processo la cui esecuzione � terminata
     */
    @Override
    public void liberaMemoria(int idProcesso){
        for(int i=0;i<memoria.size();i++){
            if (memoria.get(i).getIdProcesso()==idProcesso){
                spazioResiduo+=memoria.get(i).getDimensione();
                rimuovi(memoria.get(i));
                i-=1;
            }
        }
    }
    
    
    /**
     * Metodo cerca non usato in SwapSegmentata
     */
    @Override
    public boolean cerca(FrameMemoria seg){
        return true;
    }
}
