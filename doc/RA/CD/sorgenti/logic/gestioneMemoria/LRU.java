/*
 * Azienda: Stylosoft
 * Nome file: LRU.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche:
 *
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.Vector;

/**
 *Classe pubblica concreta che implementa l'interfaccia IRimpiazzo. Essa 
 * prende il nome di LRU per Least Recently Used cioè rimpiazza la pagina 
 * usata meno di recente.
 * @author Davide Compagnin
 */
public class LRU implements IRimpiazzo {
    /**
     * Memorizza un contatore incrementato ad ogni riferimento in memoria
     */
    private int Contatore=0;
    /**
     * Classe interna privata che memorizza in un intero UltimoAccesso il 
     * Contatore corrente, oltre che il riferimento F alla pagina
     */
    private class Dati {
      private int UltimoAccesso;
      private FrameMemoria F;
    }
    /**
     * Vettore privato che memorizza una sequenza di Dati come immagine
     * della memoria
     */
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    /**
     * Alloca lo spazio per le informazioni delle pagine
     * @param dim
     *  dimensione della memoria in numero di pageframe
     */
    public LRU( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    /**
     * Imposta F riferiferimento alla pagina nella Posizione indicata, e 
     * imposta UltimoAccesso con il valore corrente del Contatore, 
     * incrementandolo successivamente.
     * @param F
     *  Pagina da inserire
     * @param Posizione
     *  Posizione di inserimento
     * @param UT
     *  Tempo corrente
     * @param M
     *  Bit di Modifica
     */
    public void inserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).UltimoAccesso=Contatore;
        Tabella.elementAt(Posizione).F=F;
        Contatore=Contatore+1;
    }
    /**
     * Resetta i dati nella posizione specificata
     * @param Posizione
     *  Posizione da resettare
     */
    public void liberaEntry( int Posizione ) {
        Tabella.elementAt(Posizione).UltimoAccesso=0;
        Tabella.elementAt(Posizione).F=null;
    }
    /**
     * Implementa l'algoritmo. Restituisce un riferimento alla pagina il cui 
     * valore di UltimoAccesso è minore.
     * @return
     *  Ritorna il Frame da rimpiazzare
     */
    public FrameMemoria selezionaEntry() {
        int pos=0; int T=Tabella.firstElement().UltimoAccesso;
        for (int i=0; i<Tabella.size(); i++ ) {
            if ( Tabella.elementAt(i).UltimoAccesso<T ) {
                T=Tabella.elementAt(i).UltimoAccesso;
                pos=i;
            }
        }
        return Tabella.elementAt(pos).F;
    }
    /**
     * Aggiorna le informazioni nella posizione specificata
     * @param Posizione
     *   Posizione di aggiornamento
     * @param M
     *   Bit di modifica
     */
    public void aggiornaEntry( int Posizione, boolean M ) {
        Tabella.elementAt(Posizione).UltimoAccesso=Contatore;
        Contatore=Contatore+1;
    }
    /**
     * NULLA
     */
    public void aggiornaEntries( ) { }
    public void azzeraUltimo() {}
}
