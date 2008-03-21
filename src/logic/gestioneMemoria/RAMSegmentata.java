/**
 * Azienda: Stylosoft
 * Nome file: RAMSegmentata.java
 * Package: logic.gestioneMemoria
 * Autore: Alberto Zatton
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.3 (14/03/2008): Rivisto il codice di rimuovi() e liberaMemoria()
 *  - v.1.2 (03/03/2008): Modificate tutte le occorenze del costruttore di Segmento
 *                        secondo la nuova specifica
 *                        Modificato il metodo rimuovi: ora ritorna un bool
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
public class RAMSegmentata extends MemoriaSegmentata{

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
        memoria.add(new Segmento("spazio", conf.getDimensioneRAM(),-1));
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
    public int aggiungi(FrameMemoria seg, FrameMemoria spazio){
        /**Calcolo lo spazio che rimarrï¿½ libero al termine dell'inserimento*/ 
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
        if (spazioRimasto!=0){
            memoria.add(pos+1, new Segmento("spazio",spazioRimasto,-1));
            memoria.remove(pos+2);
        }
        else{
            memoria.remove(pos+1);
        }
        spazioResiduo-=seg.getDimensione();
        
        return pos;
    }
    
    
    /**
     * Metodo che toglie un segmento dalla RAM.
     * 
     * @param seg
     *      Riferimento al segmento da togliere dalla RAM
     */
    @Override
    public boolean rimuovi(FrameMemoria seg){
        if(memoria.contains(seg)){
        /**Salvo la posizione del segmento in memoria. Servirï¿½ in seguito*/
        int pos=memoria.indexOf(seg);
        
        /**Tolgo il segmento dalla memoria*/
        memoria.remove(seg);
        spazioResiduo+=seg.getDimensione();
        
        /**Aggiungo uno spazio grande quanto il segmento tolto*/
        memoria.add(pos, new Segmento("spazio",seg.getDimensione(),-1));
        
        /**Sfruttando la posizione del segmento appena tolto, compatto eventuali
         * spazi liberi adiacenti*/
        FrameMemoria frameAux1;
        FrameMemoria frameAux2;
        
        if(pos+1<memoria.size()){
            frameAux1=memoria.get(pos+1);
            frameAux2=memoria.get(pos);
            if(frameAux1.getIdProcesso()==-1 && frameAux2.getIdProcesso()==-1) {
                memoria.add(pos, new Segmento("spazio",frameAux1.getDimensione()+frameAux2.getDimensione(),-1));
                memoria.remove(pos+1);
                memoria.remove(pos+1);
            }
        }
        if(pos>0){
            frameAux1=memoria.get(pos-1);
            frameAux2=memoria.get(pos);
            if(frameAux1.getIdProcesso()==-1 && frameAux2.getIdProcesso()==-1) {
                memoria.add(pos, new Segmento("spazio",frameAux1.getDimensione()+frameAux2.getDimensione(),-1));
                memoria.remove(pos+1);
                memoria.remove(pos-1);
            }
            
        }
        
        return true;
        }
        return false;
    }
    
    
    /**
     * Metodo che elimina i segmenti non piï¿½ riferiti perchï¿½ il relativo processo
     * ha terminato l'esecuzione
     * 
     * @param idProcesso
     *      Intero che identifica il processo che ha finito di eseguire
     */
    @Override
    public void liberaMemoria(int idProcesso){
        FrameMemoria segAux1;
        FrameMemoria segAux2;
        
        if(memoria.size()==1 && memoria.get(0).getIdProcesso()==idProcesso){
            //Caso in cui tutta la memoria è occupata da un segmento
            //riferito dal processo. Caso MOLTO raro ma non impossibile.
            segAux1=memoria.get(0);
            memoria.add(new Segmento("spazio", segAux1.getDimensione(),-1));
            memoria.remove(segAux1);
        }
        
        else{
        
        for(int i=0;i<memoria.size()-1;i++) {
            
            
            segAux1=memoria.get(i);
            segAux2=memoria.get(i+1);
            
            /**Controllo se il segmento referenziato ï¿½ uno spazio oppure un
             * segmento da eliminare*/
            if(segAux1.getIndirizzo().equals("spazio") || segAux1.getIdProcesso()==idProcesso){
                
                /**Controllo se il segmento successivo ï¿½ dello stesso tipo*/
                if(segAux2.getIndirizzo().equals("spazio") || segAux2.getIdProcesso()==idProcesso){
                    
                    /**Unisco i due segmenti*/
                    memoria.add(i,new Segmento("spazio", segAux1.getDimensione()+segAux2.getDimensione(),-1));
                    
                    //Se i segmenti modificati occupavano spazio, aggiungo lo spazio al totale;
                    //in ogni caso poi elimino i segmenti, diventati ridondanti.
                    if(segAux1.getIdProcesso()==idProcesso) spazioResiduo+=segAux1.getDimensione();
                    memoria.remove(i+1);
                    if(segAux2.getIdProcesso()==idProcesso) spazioResiduo+=segAux2.getDimensione();
                    memoria.remove(i+1);
                    /**L'iterazione deve continuare da questo nuovo segmento*/
                    i=i-1;
                }
                else{
                    /**Il segmento successivo ï¿½ ancora referenziato da qualche
                     * processo. Converto a spazio il segmento corrente nel caso
                     * in cui non sia piï¿½ referenziato da nessun processo*/
                    if(segAux1.getIdProcesso()==idProcesso){
                        spazioResiduo+=segAux1.getDimensione();
                        memoria.set(i, new Segmento("spazio",segAux1.getDimensione(),-1));
                    }
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
     * Metodo che costruisce un Vector composto dai segmenti presenti in
     * RAM. Utile per gli algoritmi di rimpiazzo dei segmenti.
     * 
     * @return
     *      Un Vector contenente tutti i segmenti presenti in RAM
     */
    public Vector<FrameMemoria> getFrameOccupati(){
        Vector<FrameMemoria> segmenti=new Vector<FrameMemoria>();
        FrameMemoria segAux;
        for(int i=0;i<memoria.size();i++) {
            segAux=memoria.get(i);
            if(!segAux.getIndirizzo().equals("spazio")) segmenti.add(segAux);
        }
        return segmenti;
    }
    

    /**
     * Metodo che restituisce il segmento con lo spazio libero piï¿½ grande.
     * Utile per gli algoritmi di rimpiazzo dei segmenti.
     * 
     * @return
     *      Un riferimento allo spazio libero contiguo piï¿½ grande disponibile
     *      in RAM
     */
    public FrameMemoria getSpazioMaggiore(){
        FrameMemoria frameMax=new Segmento("spazio",0,-1);
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
     * Metodo che cerca se il segmento richiesto ï¿½ giï¿½ presente in RAM
     * 
     * @param seg
     *      Riferimento al segmento da controllare
     * 
     * @return
     *      TRUE se il segmento ï¿½ in RAM, FALSE altrimenti
     */
    @Override
    public boolean cerca(FrameMemoria seg){
        return memoria.contains(seg);
    }
    
    /**
     * Metodo che, dato un frame memoria, ritorna la sua posizione all'interno
     * della RAM.
     * 
     * @param seg
     *      Il riferimento al segmento di cui si vuole trovare l'indice
     * @return
     *      L'indice del segmento riferito da seg (se è presente in RAM),
     *      altrimenti ritorna -1
     * 
     */
    public int indiceDi(FrameMemoria seg){
        return memoria.indexOf(seg);
    }
}
