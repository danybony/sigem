/*
 * Memoria.java
 *
 * Created on 4 febbraio 2008, 19.47
 *
 * Classe che rappresenta un'astratta rappresentazione della memoria di un elaboratore.
 * In questo caso, viene implementata come una collezione di pagine, segmenti o 
 * segmenti paginati.
 * Questa classe verrà poi ereditata dalle due classi che rappresenteranno la RAM
 * e lo Swap; ha quindi il compito di descrivere campi dati e metodi comuni alle
 * due.
 */

package logic.gestioneMemoria;


import java.util.TreeMap;

abstract class Memoria {
    

    /**Rappresenta quanti byte mancano alla memoria prima dell'esaurimento*/
    int spazioResiduo;
    
    /**Struttura dati che contiene i FrameMemoria. E' realizzata tramite un albero rosso-nero,
     *che garantisce l'inserimento, la ricerca di una chiave e la rimozioni in O(log(n)).
     */
    TreeMap memoria=new TreeMap<String,FrameMemoria>();
    
    /**Costruttore di Memoria. Chiede un intero che rappresenta la grandezza della memoria
     *espressa in byte. Questo costruttore verrà richiamato da ogni costruttore delle classi
     *che erediteranno Memoria, per settare la dimensione della memoria stessa.
     */
    Memoria(int dimensione) {
        spazioResiduo=dimensione;
    }
    
    /**Metodo incaricato di aggiungere un FrameMemoria nella struttura dati memoria.
     *Lancia l'eccezione MemoriaEsaurita nel caso in cui lo spazio a disposizione
     *non sia sufficiente.
     */
    synchronized void Inserisci(FrameMemoria frame) throws MemoriaEsaurita{
        if (frame.getDimensione()>spazioResiduo) throw new MemoriaEsaurita(spazioResiduo);
        else {
            memoria.put(frame.getID(), frame);
            spazioResiduo -= frame.getDimensione();
        }
        
    }
    
    /**Metodo che rimuove il FrameMemoria avente identificativo ID dalla memoria
     */
    synchronized void Rimuovi(String ID) {
        
        spazioResiduo -= memoria.remove(ID).getDimensione();
    }
    
    /**Metodo che ricerca una pagina nella struttura dati. Restituisce true se la pagina
     *viene trovata; false altrimenti.
     */
    synchronized boolean Trova(String ID) {
        return memoria.containsKey(ID);
    }
    
}
