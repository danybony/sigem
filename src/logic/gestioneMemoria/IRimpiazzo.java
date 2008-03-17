/*
 * Azienda: Stylosoft
 * Nome file: IRimpiazzo.java
 * Package: logic.gestioneMemoria
 * Autore: DavideCompagnin
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.3 (04/03/2008): Aggiornati i parametri del metodo InserisciEntry
 *  - v.1.2 (03/03/2008): Definiti i metodi AggiornaEntry e AggiornaEntries
 *  - v.1.1 (02/03/2008): Aggiunto il metodo InserisciEntry
 *  - v.1.0 (29/02/2008): Impostazione base dell'interfaccia
 */

package logic.gestioneMemoria;

/**
 * Interfaccia pubblica che definisce la struttura comune a tutte le politiche di 
 * rimpiazzo delle pagine.
 * @author Davide Compagnin
 */
public interface IRimpiazzo {
    /**
     * Implementato dalle politiche di rimpiazzo servirà per aggiornare i dati
     * derivati dall'inserimento in RAM di una pagina.
     * @param F
     *  Frame da inserire
     * @param Posizione
     *  Posizione di inserimento
     * @param UT
     *  Tempo corrente
     * @param M
     *  Bit di Modifica
     */
    public void inserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M );
    /**
     * Resetta i dati relativi alla pagina in posizione specificata 
     * @param Posizione
     *  Posizione di reset
     */
    public void liberaEntry( int Posizione );
    /**
     * Implementerà l'algritmo di rimpiazzo
     * @return
     *  Ritorna il riferimento alla pagina da rimpiazzare
     */
    public FrameMemoria selezionaEntry();
    /**
     * Aggiorna le informazioni relative alla pagina nella posizione desiderata
     * @param Posizione
     *  Posizione di aggiornamento
     * @param M
     *  Bit di Modifica
     */
    public void aggiornaEntry( int Posizione, boolean M );
    /**
     * Aggiorna i dati relativi a tutte le pagine in memoria
     */
    public void aggiornaEntries( );
    
}
