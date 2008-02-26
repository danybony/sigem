/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;

/**
 *
 * @author PC
 */
class RAMPaginata extends MemoriaPaginata{

    public RAMPaginata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneRAM()/conf.getDimensionePagina());
    }
    
    @Override
    /**Metodo che aggiunge una pagina nella RAM. Se ha successo, ritorna un int
     * che rappresenta l'id della "cella" in cui ha inserito la pagina; in caso
     * di fallimento solleva un'eccezione che deve essere opportunamente gestita
     */
    public int aggiungi(FrameMemoria pag) throws MemoriaEsaurita{

        if(pagineResidue>0) {
            /*C'è ancora memoria libera sufficiente: Cerco nelle pagine della 
             * RAM se ce n'è una già marcata come rimovibile. Decremento subito
             * il numero delle pagine libere di 1 e setto la pagina come in RAM
             */
            pagineResidue--;
            pag.setInRAM(true);
            
            for(int i=0;i<memoria.size();i++) {
                if(!memoria.get(i).getInRAM()) {
                    /*Sostituisco la nuova pagina alla vecchia
                     */
                    memoria.set(i, pag);
                    return i;
                }
            }
        
            /*Non ci sono pagine marcate come rimovibili, tento di aggiungere la 
            * pagina in una nuova cella della RAM
            */
            
            memoria.add(pag);
            return (memoria.size()-1);
        }
        
        else{
            /*Non c'è più posto: lancio quindi l'eccezione MemoriaEsaurita che
             * farà entrare in gioco la politica di rimpiazzo delle pagine.
             */
            throw new MemoriaEsaurita(0);
        }
    }
    
    @Override
    /**Metodo che marca la pagina come non più in RAM, liberando spazio
     */
    public FrameMemoria rimuovi(FrameMemoria pag){
        pag.setInRAM(false);
        pagineResidue++;
        return pag;
    }
    
    @Override
    /**Metodo che cerca se una pagina è già presente in RAM
     * 
     */
    public boolean cerca(FrameMemoria pag){
        return memoria.contains(pag);
    }
    
    @Override
    /**Metodo che marca come eliminabili le pagine riferite da un processo che
     * ha finito la sua esecuzione. Durante il prossimo inserimento, queste
     * pagine saranno considerate memoria libera, quindi si potranno inserire
     * nuove pagine senza interrogare la politica di rimpiazzo.
     */
    public void liberaMemoria(String idProcesso){
        FrameMemoria paginaAux;
        for(int i=0; i<memoria.size(); i++){
            paginaAux=memoria.get(i);
            if(paginaAux.getIdProcesso().equals(idProcesso)){
                paginaAux.setInRAM(false);
                pagineResidue++;
            }
        }
    }
}
