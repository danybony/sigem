/*
 * Azienda: Stylosoft
 * Nome file: FIFO.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.Vector;

/**
 * Classe pubblica concreta che implementa l'interfaccia IRimpiazzo. 
 * Essa 	prende il nome di FIFO per FirstInFirstOut cioè rimpiazza 
 * la pagina che è 	entrata per prima in memoria sebbene questa sia 
 * stata utilizzata di recente.
 * @author Davide Compagnin
 */
public class FIFO implements IRimpiazzo {
    /**
     * Campo dati che mantiene la sequenza d'ordine dei vari inserimenti
     */
    int S=0;
    /**
     * Classe interna privata che memorizza in un intero TArrivo l'unità di tempo 
     * in cui la pagina è stata caricata in RAM, oltre che il riferimento F alla pagina.
     */
    private class Dati {
      private int TArrivo=0;
      private int Sequenza=0;
      private FrameMemoria F=null;
      /**
       * metodo private boolean Minore( Dati D ) che confronta due Dati 
       * e ne indica se uno è minore dell'altro
       * @param D
       *   Dato di confronto
       * @return
       *   true o false a seconda se è minore o no
       */
      private boolean Minore( Dati D ) { return ( TArrivo<D.TArrivo && Sequenza<D.Sequenza ); }
    }
    /**
     * Vettore privato che memorizza una sequenza di Dati come immagine
     * della memoria.
     */
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    /**
     * Costruttore
     * @param dim
     *   Dimensione della memoria
     */
    public FIFO( int dim ) {
        S=0;
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    /**
     * Imposta F riferiferimento alla pagina e imposta Tarrivo a UT, 
     * mentre gli	 altri parametri sono inutilizzati.
     * @param F
     *  Frame da inserire
     * @param Posizione
     * @param UT
     *  Istante di inserimento
     * @param M
     */
    public void inserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).TArrivo=UT;
        Tabella.elementAt(Posizione).F=F;
        Tabella.elementAt(Posizione).Sequenza=S;
        S=S+1;
    }
    /**
     * Resetta i campi dati relativi alla pagina nella Posizione specificata dal
     * parametro.
     * @param Posizione
     *   Posizione di reset
     */
    public void liberaEntry( int Posizione ) { 
        Tabella.elementAt(Posizione).TArrivo=0;
        Tabella.elementAt(Posizione).F=null;
        Tabella.elementAt(Posizione).Sequenza=0;
    }
    /**
     * Implementa l'algoritmo. Restituisce un riferimento alla pagina il cui 
     * Tarrivo è minore.
     * @return
     *  FrameMemoria con tempo di arrivo minore
     */
    public FrameMemoria selezionaEntry() {
        int pos=0; Dati D=Tabella.firstElement();
        for(int i=1; i<Tabella.size(); i++ )
            if ( Tabella.elementAt(i).Minore(D) ) { 
                pos=i; D=Tabella.elementAt(i); }
        return Tabella.elementAt(pos).F;        
    }
    /**
     * NULLA
     * @param Posizione
     * @param M
     */
    public void aggiornaEntry( int Posizione, boolean M ) {}
    /**
     * NULLA
     */
    public void aggiornaEntries( ) {}
    
}
