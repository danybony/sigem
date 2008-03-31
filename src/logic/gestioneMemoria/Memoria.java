/**
 * Azienda: Stylosoft
 * Nome file: Memoria.java
 * Package: logic.gestioneMemoria
 * Autore: Alberto Zatton
 * Data: 29/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.2 (03/03/2008): Modificato il metodo rimuovi: ora ritorna un bool
 *  - v.1.1 (02/03/2008): Aggiunti i commenti sui parametri e sul tipo di ritorno
 *                        dei metodi
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.Vector;

/**
 * Classe astratta che rappresenta una generica memoria: verra' poi implementata
 * da tutte le memorie, sia paginate che segmentate.
 */
abstract class Memoria{
        
    /**
     * Struttura dati di tipo Vector incaricata di mantenere i riferimenti ai 
     * FrameMemoria contenuti in memoria.
     */
    protected Vector<FrameMemoria> memoria=new Vector<FrameMemoria>();
    
    /**
     * Metodo la cui implementazione dovra' rimuovere un FrameMemoria dalla memoria
     * @param frame
     *      Il riferimento al FrameMemoria da togliere
     * @return
     *      TRUE se la rimozione ha successo, FALSE altrimenti
     */
    public abstract boolean rimuovi(FrameMemoria frame);
    
    /**
     * Metodo la cui implementazione dovra' cercare un FrameMemoria nella memoria
     * @param frame
     *      Il riferimento al FrameMemoria da cercare
     * @return
     *      TRUE se il frame e' in memoria, FALSE altrimenti
     */
    public abstract boolean cerca(FrameMemoria frame);
    
    /**
     * Metodo la cui implementazione dovra' rimuovere tutti i FrameMemoria 
     * riferiti da un processo che ha finito la sua esecuzione
     * @param idProcesso
     *      Identificativo univoco del processo che ha finito di eseguire
     */
    public abstract void liberaMemoria(int idProcesso);
    
    /**
     * Metodo che ritorna la configurazione della memoria
     * @return
     *      Una lista di riferimenti FrameMemoria i cui oggetti formano la memoria
     */
    public Vector<FrameMemoria> getSituazione(){
        return new Vector<FrameMemoria>(memoria);
    }


}
