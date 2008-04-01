/*
 * Azienda: Stylosoft
 * Nome file: IAllocazione.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.0 (29/02/2008): Impostazione base dell'interfaccia
 */
package logic.gestioneMemoria;

import java.util.Vector;

/**
 * Interfaccia pubblica che definisce la struttura comune a tutte le politiche di
 * allocazione dei segmenti
 * @author Davide Compagnin
 */
public interface IAllocazione {
    /**
     * Sarà implementato da ogni politica di allocazione. Il metodo dovrà
     * restituire il FrameMemoria libero che secondo la politica rispecchia
     * meglio le caratteristiche del frame passatogli F. Liberi è un vettore il
     * quale contiene tutti i FrameMemoria liberi all'interno della memoria
     * RAM.
     * @param F
     *  Frame da allocare
     * @param Liberi
     *  Lista dei Frame liberi
     * @param Posizioni
     *  Array delle posizione dei segmenti liberi (per next-fit)
     * @return
     *  Frame sul quale si è inserito
     */
    public FrameMemoria alloca ( FrameMemoria F, Vector<FrameMemoria> Liberi, int Posizioni[] );
}
