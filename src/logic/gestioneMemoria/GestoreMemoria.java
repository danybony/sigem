/*
 * Azienda: Stylosoft
 * Nome file: GestoreMemoria.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 30/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche:
 * - v.1.1 (02/03/2008): Aggiunta del metodo notificaProcessoTerminato
 * - v.1.0 (30/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.LinkedList;

/**
 * Classe astratta che rappresenta la struttura generica del sistema di gestione
 * della memoria. Riunisce le caratteristiche comuni ed essenziali per tutte le 
 * possibili classi ereditate. Non implementa alcun metodo direttamente, poiché
 * le operazioni che dovrà compiere e le strutture dati che dovrà manipolare
 * sono logicamente discrepanti. L'obbiettivo di questa classe è fornire una lista
 * di azioni che rappresenta la sequenza logica delle operazioni che effettuo in
 * memoria.
 * @author Davide Compagnin
 */
public abstract class GestoreMemoria {
    /**
     * Metodo Astratto che notifica un processo terminato
     * @param id
     *  Identificativo del processo
     */
    public abstract void notificaProcessoTerminato(int id);
    /**
     * Metodo Principale di esecuzione del GestoreMemoria
     * @param ListaFrame
     *   Lista dei Frame da caricare
     * @param UT
     *   Unità di tempo corrente
     * @return
     *  Lista di azioni compiute
     */
    public abstract LinkedList<Azione> esegui( LinkedList<FrameMemoria> ListaFrame, int UT );
}
