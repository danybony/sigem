/*
 * Azienda: Stylosoft
 * Nome file: RAMPaginata.java
 * Package: logic.gestioneMemoria
 * Autore: Alberto Zatton
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.3 (04/03/2008): Modificato il costruttore: ora ha 2 parametri e può
 *                        lanciare l'eccezione PaginaNulla
 *  - v.1.2 (03/03/2008): Modificato il metodo rimuovi: ora ritorna un bool
 *  - v.1.1 (02/03/2008): Nuovo costruttore come da issue 28
 *                        Nuovo metodo indiceDi(FrameMemoria) come da issue 29
 *                        Aggiunti i commenti sui metodi e sul tipo di ritorno
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;

/**
 * Classe che modella la RAM, gestendola come collezione di pagine.
 */
class RAMPaginata extends MemoriaPaginata{

    /**
     * Costruttore di RAMPaginata. Imposta la capienza della RAM, quantificata
     * in numero di pagine.
     * 
     * @param conf
     *      Riferimento all'istanza di ConfigurazioneIniziale
     */
    public RAMPaginata(ConfigurazioneIniziale conf) throws PaginaNulla{
        super(conf.getDimensioneRAM(),conf.getDimensionePagina());
    }
    
    
    /**
     * Metodo che aggiunge una pagina nella RAM. Se ha successo, ritorna un int
     * che rappresenta l'id della "cella" in cui ha inserito la pagina; in caso
     * di fallimento solleva un'eccezione che deve essere opportunamente gestita
     * 
     * @param pag
     *      Riferimento alla pagina da aggiungere in RAM
     * @return
     *      La posizione della pagina all'interno della RAM dopo l'inserimento
     *      (indice int del Vector)
     */
    @Override
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
    
    
    /**
     * Metodo che marca la pagina come non più in RAM, liberando spazio
     * 
     * @param pag
     *      Pagina da marcare come non più in RAM
     * 
     */
    @Override
    public boolean rimuovi(FrameMemoria pag){
        pag.setInRAM(false);
        pagineResidue++;
        return true;
    }
    
    
    /**
     * Metodo che cerca se una pagina è già presente in RAM
     * 
     * @param pag
     *      Il riferimento alla pagina da cercare
     * @return
     *      TRUE se la pagina è in RAM, FALSE altrimenti
     */
    @Override
    public boolean cerca(FrameMemoria pag){
        return memoria.contains(pag);
    }
    
    /**
     * Metodo che, dato un frame memoria, ritorna la sua posizione all'interno
     * della RAM.
     * 
     * @param pag
     *      Il riferimento alla pagina di cui si vuole trovare l'indice
     * @return
     *      L'indice della pagina riferita da pag (se è presente in RAM),
     *      altrimenti ritorna -1
     * 
     */
    public int indiceDi(FrameMemoria pag){
        return memoria.indexOf(pag);
    }
    
    
    /**
     * Metodo che marca come eliminabili le pagine riferite da un processo che
     * ha finito la sua esecuzione. Durante il prossimo inserimento, queste
     * pagine saranno considerate memoria libera, quindi si potranno inserire
     * nuove pagine senza interrogare la politica di rimpiazzo.
     * 
     * @param idProcesso
     *      Intero che identifica il processo che ha finito la sua esecuzione
     */
    @Override
    public void liberaMemoria(int idProcesso){
        FrameMemoria paginaAux;
        for(int i=0; i<memoria.size(); i++){
            paginaAux=memoria.get(i);
            if(paginaAux.getIdProcesso()==idProcesso){
                paginaAux.setInRAM(false);
                pagineResidue++;
            }
        }
    }
}
