/**
 * Azienda: Stylosoft
 * Nome file: RAMSegmentata.java
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


import java.util.Vector;
import logic.parametri.ConfigurazioneIniziale;

/**
 * Classe che rappresenta la RAM modellata tramite segmenti
 */
class RAMSegmentata extends MemoriaSegmentata{

    /**
     * Costruttore di RAMSegmentata che setta lo spazio complessivo in memoria
     * e la inizializza con un unico grande segmento che rappresenta lo spazio
     * libero
     * 
     * @param conf
     *      Riferimento all'istanza di ConfigurazioneIniziale
     */
    public RAMSegmentata(ConfigurazioneIniziale conf){
        super(conf.getDimensioneRAM());
        memoria.add(new Segmento("spazio", conf.getDimensioneRAM()));
        memoria.get(0).setIdProcesso(-1);
    }
    
    
    /**
     * Metodo che aggiunge un segmento nella RAM. Come parametri richiede il 
     * riferimento al segmento e il riferimento allo spazio in cui si vuole
     * inserire il segmento
     * 
     * @param seg
     *      Riferimento al nuovo segmento da inserire
     * 
     * @param spazio
     *      Riferimento allo spazio dove inserire il nuovo segmento
     *      
     */
    @Override
    public void aggiungi(FrameMemoria seg, FrameMemoria spazio){
        /**Calcolo lo spazio che rimarrà libero al termine dell'inserimento*/ 
        int spazioRimasto=spazio.getDimensione()-seg.getDimensione();
        /**Cerco il segmento spazio all'interno di memoria*/
        int pos=0;
        for(; pos<memoria.size(); pos++){
            if(memoria.get(pos)==spazio) break;
        }
        /**Inserisco il segmento nella posizione specificata*/
        memoria.add(pos,seg);
        /**Aggiungo lo spazio rimasto, risultato della differenza tra lo spazio
         * originale e la grandezza del nuovo segmento inserito. Come parametri
         * al nuovo segmento passo valori speciali che lo identificano come spazio
         */
        memoria.add(pos+1, new Segmento("spazio",spazioRimasto));
        memoria.get(pos+1).setIdProcesso(-1);
        spazioRimasto-=seg.getDimensione();
    }
    
    
    /**
     * Metodo che toglie un segmento dalla RAM.
     * 
     * @param seg
     *      Riferimento al segmento da togliere dalla RAM
     */
    @Override
    public FrameMemoria rimuovi(FrameMemoria seg){
        /**Salvo la posizione del segmento in memoria. Servirà in seguito*/
        int pos=memoria.indexOf(seg);
        
        /**Tolgo il segmento dalla memoria*/
        memoria.remove(seg);
        spazioResiduo+=seg.getDimensione();
        
        /**Sfruttando la posizione del segmento appena tolto, compatto eventuali
         * spazi liberi adiacenti*/
        if(pos!=0){
            FrameMemoria frameAux1=memoria.get(pos);
            FrameMemoria frameAux2=memoria.get(pos-1);
            if(frameAux1.getIdProcesso()==-1 && frameAux2.getIdProcesso()==-1) {
                memoria.add(new Segmento("spazio",frameAux1.getDimensione()+frameAux2.getDimensione()));
                memoria.remove(pos+1);
                memoria.remove(pos+1);
            }
        }
        return seg;
    }
    
    
    /**
     * Metodo che elimina i segmenti non più riferiti perchè il relativo processo
     * ha terminato l'esecuzione
     * 
     * @param idProcesso
     *      Intero che identifica il processo che ha finito di eseguire
     */
    @Override
    public void liberaMemoria(int idProcesso){
        FrameMemoria segAux1;
        FrameMemoria segAux2;
        
        for(int i=0;i<memoria.size()-1;i++) {
            
            segAux1=memoria.get(i);
            segAux2=memoria.get(i+1);
            
            /**Controllo se il segmento referenziato è uno spazio oppure un
             * segmento da eliminare*/
            if(segAux1.getIndirizzo().equals("spazio") || segAux1.getIdProcesso()==idProcesso){
                
                /**Controllo se il segmento successivo è dello stesso tipo*/
                if(segAux2.getIndirizzo().equals("spazio") || segAux2.getIdProcesso()==idProcesso){
                    
                    /**Unisco i due segmenti*/
                    memoria.add(i,new Segmento("spazio", segAux1.getDimensione()+segAux2.getDimensione()));
                    memoria.remove(i+1);
                    memoria.remove(i+1);
                    
                    /**L'iterazione deve continuare da questo nuovo segmento*/
                    i=i-1;
                }
                else{
                    /**Il segmento successivo è ancora referenziato da qualche
                     * processo. Converto a spazio il segmento corrente nel caso
                     * in cui non sia più referenziato da nessun processo*/
                    if(segAux1.getIdProcesso()==idProcesso){
                        memoria.set(i, new Segmento("spazio",segAux1.getDimensione()));
                        memoria.get(i).setIdProcesso(-1);
                    }
                }
            }
        }
    }
    
    /**
     * Metodo che costruisce un Vector composto dagli spazi vuoti disponibili in
     * RAM. Utile per gli algoritmi di allocazione dei segmenti.
     * 
     * @return
     *      Un Vector contenente tutti gli spazi vuoti in RAM
     */
    public Vector<FrameMemoria> getFrameLiberi(){
        Vector<FrameMemoria> spaziLiberi=new Vector<FrameMemoria>();
        FrameMemoria segAux;
        for(int i=0;i<memoria.size();i++) {
            segAux=memoria.get(i);
            if(segAux.getIndirizzo().equals("spazio")) spaziLiberi.add(segAux);
        }
        return spaziLiberi;
    }
    

    /**
     * Metodo che restituisce il segmento con lo spazio libero più grande.
     * Utile per gli algoritmi di rimpiazzo dei segmenti.
     * 
     * @return
     *      Un riferimento allo spazio libero contiguo più grande disponibile
     *      in RAM
     */
    public FrameMemoria getSpazioMaggiore(){
        FrameMemoria frameMax=new Segmento("spazio",0);
        FrameMemoria frameAux;
        for(int i=0; i<memoria.size();i++){
            frameAux=memoria.get(i);
            if(frameAux.getIdProcesso()==-1 && frameAux.getDimensione()>frameMax.getDimensione()) {
                frameMax=frameAux;
            }
        }
        return frameMax;
        
    }
    
    
    
    /**
     * Metodo che cerca se il segmento richiesto è già presente in RAM
     * 
     * @param seg
     *      Riferimento al segmento da controllare
     * 
     * @return
     *      TRUE se il segmento è in RAM, FALSE altrimenti
     */
    @Override
    public boolean cerca(FrameMemoria seg){
        return memoria.contains(seg);
    }
}
