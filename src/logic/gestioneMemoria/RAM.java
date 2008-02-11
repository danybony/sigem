/*
 * RAM.java
 *
 * Created on 7 febbraio 2008, 22.07
 *
 * Classe che rappresenta la memoria centrale di un elaboratore.
 * Eredita dalla classe base astratta Memoria.
 */

package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;

class RAM extends Memoria{
    
    /**Costruttore di RAM che prende in input il riferimento alla ConfigurazioneIniziale.
     *Inizializza poi la classe base Memoria con la relativa dimensione stabilita dall'
     *utente in fase di configurazione della RAM espressa in byte.
     */
    public RAM(ConfigurazioneIniziale parametri) {
        super(parametri.getDimensioneSwap());
    }
    
    /**Costruttore che prende in input direttamente la dimensione espressa in byte
     *della RAM, e inizializza con questo valore la classe base Memoria.
     */
    public RAM(int dimensione) {
        super(dimensione);
    }
    
}
