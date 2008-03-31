/*
 * MemoriaEsaurita.java
 *
 * Created on 5 febbraio 2008, 16.13
 *
 * Questa classe rappresenta un'eccezione che viene sollevata quando una classe
 * che implementa Memoria non puo' piu' aggiungere FrameMemoria in quanto ha esaurito
 * lo spazio a disposizione. Restituisce anche la quantita' di spazio residua
 * in termini di byte.
 */

package logic.gestioneMemoria;


class MemoriaEsaurita extends Exception{
    
    /**Intero che rappresenta lo spazio residuo rimanente in Memoria espresso in
     *byte
     */
    private int spazioResiduo;
    
    /** Costruttore dell'eccezione MemoriaEsaurita. Chiede come parametro un intero
     * che rappresenta lo spazio residuo rimasto in un'istanza concreta di Memoria.
     */
    public MemoriaEsaurita(int dimensione) {
        spazioResiduo=dimensione;
    }
    
    /** Funzione che ritorna un intero rappresentante lo spazio residuo espresso
     * in byte.
     */
    public int getSpazioResiduo(){
        return spazioResiduo;
    }
}
