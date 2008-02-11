/*
 * Swap.java
 *
 * Created on 7 febbraio 2008, 21.58
 *
 * Classe che rappresenta la memoria virtuale su disco fisso di un elaboratore.
 * Eredita dalla classe base astratta Memoria.
 */

package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;

class Swap extends Memoria{
    
    /**Costruttore di Swap che prende in input il riferimento alla ConfigurazioneIniziale.
     *Inizializza poi la classe base Memoria con la relativa dimensione stabilita dall'
     *utente in fase di configurazione della Swap espressa in byte.
     */
    public Swap(ConfigurazioneIniziale parametri) {
        super(parametri.getDimensioneSwap());
    }
    
    /**Costruttore che prende in input direttamente la dimensione espressa in byte
     *dello Swap, e inizializza con questo valore la classe base Memoria.
     */
    public Swap(int dimensione) {
        super(dimensione);
    }
    
}
