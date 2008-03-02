/**
 * Azienda: Stylosoft
 * Nome file: SwapPaginata.java
 * Package: logic.gestioneMemoria
 * Autore: Alberto Zatton
 * Data: 29/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.1 (02/03/2008): Aggiunti i commenti sui parametri e sul tipo di ritorno
 *                        dei metodi
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;

/**
 * Classe che rappresenta lo swap, implementato tramite collezione di pagine
 */
class SwapPaginata extends MemoriaPaginata{
    
/**
 * Costruttore che inizializza il numero delle pagine che può contenere la
 * memoria
 * 
 * @param numPagine
 *      Numero di pagine che può contenere la memoria
 */
public SwapPaginata(int numPagine){
        super(numPagine);
    }
    
    
    /**
     * Metodo che aggiunge una pagina nello Swap.
     * 
     * @param pag
     *      Riferimento alla pagina da aggiungere
     */
    @Override
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
    
    
    /**
     * Metodo che toglie una pagina dallo Swap.
     * 
     * @param pag
     *      Riferimento alla pagina da togliere
     */
    @Override
    public FrameMemoria rimuovi(FrameMemoria pag){
        memoria.remove(pag);
        pagineResidue++;
        return pag;
    }
    
    
    /**
     * Metodo che elimina dallo Swap le pagine riferite ad un processo che ha
     * finito la sua esecuzione.
     * 
     * @param idProcesso
     *      Intero che identifica il processo che ha finito di eseguire
     */
    @Override
    public void liberaMemoria(int idProcesso){
        for(int i=0; i<memoria.size(); i++){
            if(memoria.get(i).getIdProcesso()==idProcesso){
                memoria.remove(i);
                pagineResidue++;
            }
        }
    }
    
    
    /**
     * Metodo lasciato vuoto in quanto mai usato in Swap
     */
    @Override
    public boolean cerca(FrameMemoria pag){
        return true;
    }
}
