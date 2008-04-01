/**
 * Azienda: Stylosoft
 * Nome file: SwapPaginata.java
 * Package: logic.gestioneMemoria
 * Autore: Alberto Zatton
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.3 (04/03/2008): Modificato il costruttore: ora prende 2 parametri e
 *                        puo' lanciare l'eccezione PaginaNulla
 *  - v.1.2 (03/03/2008): Modificato il metodo rimuovi: ora ritorna un bool
 *  - v.1.1 (02/03/2008): Aggiunti i commenti sui parametri e sul tipo di ritorno
 *                        dei metodi
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;

/**
 * Classe che rappresenta lo swap, implementato tramite collezione di pagine
 */
public class SwapPaginata extends MemoriaPaginata{
    
/**
 * Costruttore che inizializza il numero delle pagine che puo' contenere la
 * memoria
 * 
 * @param conf
 *      Riferimento all'istanza di ConfigurazioneIniziale
 */
public SwapPaginata(ConfigurazioneIniziale conf)throws PaginaNulla{
        super(conf.getDimensioneSwap(),conf.getDimensionePagina());
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
            return memoria.size()-1;
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
    public boolean rimuovi(FrameMemoria pag){
        if(memoria.remove(pag)) {
            pagineResidue++;
            return true;
        }
        
        return false;
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
     * Metodo che ritorna TRUE se la pagina e' in memoria, FALSE altrimenti
     */
    @Override
    public boolean cerca(FrameMemoria pag){
        return memoria.contains(pag);
    }
}
