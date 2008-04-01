/*
 * Azienda: Stylosoft
 * Nome file: NextFit.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 30/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.0 (30/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.Vector;

/**
 * Classe pubblica concreta che implementa l'interfaccia IRimpiazzo. Essa 
 * prende il nome di NRU per Not Recently Used cioè rimpiazza la pagina non 
 * usata di recente
 * @author Davide Compagnin
 */
public class NRU implements IRimpiazzo {
    /**
     * Classe interna privata che memorizza un boolean M che indica se la pagina
     * è stata modificata, un boolean R che indica se la pagina è stata riferita,
     * oltre al riferimento F alla pagina. 
     *   R=false	M=false	Classe=0
     *   R=false	M=true	Classe=1
     *   R=true         M=false	Classe=2
     *   R=true         M=true	Classe=3
     */
    private class Dati {
      private boolean M;
      private boolean R;
      private FrameMemoria F=null;
      private boolean Ultimo=false;
      /**
       * Metodo
       * che ritorna la classe a cui appartiene una pagina:
       * @return
       *  Classe di appartenenza
       */
      private int Classe() {
        if (R==false)
            if (M==false) return 0;
            else return 1;
        else
            if (M==false) return 2;
            else return 3;
      }
    }
    
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    /**
     * Data la dimensione intera della memoria interpretata come il numero
     * di FrameMemoria, istanzia Tabella con valori di default
     * @param dim
     *  Dimensione della memoria in numero di pageframe
     */
    public NRU( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    /**
     * Imposta F riferimento alla pagina nella Posizione indicata, imposta R a 
     * true e M con il valore del parametro passato
     * @param F
     *  Frame da inserire
     * @param Posizione
     *  Posizione di inserimento
     * @param UT
     *  Tempo Corrente
     * @param M
     *  Bit di modifica
     */
    public void inserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).Ultimo=true;
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).M=M;
        Tabella.elementAt(Posizione).F=F;
    }
    /**
     * Resetta i campi dati relativi alla pagina nella Posizione specificata dal
     * parametro
     * @param Posizione
     *  Posizione di reset
     */
    public void liberaEntry( int Posizione ) { 
        Tabella.elementAt(Posizione).R=false;
        Tabella.elementAt(Posizione).M=false;
        Tabella.elementAt(Posizione).F=null;
        Tabella.elementAt(Posizione).Ultimo=false;
    }
    /**
     * Implementa l'algoritmo. Restituisce un riferimento alla pagina di Classe minore.
     * @return
     *  Riferimento alla pagina di classe minore
     */
    public FrameMemoria selezionaEntry() { 
      boolean U=false; int pos=0;
        for(int i=0; i<Tabella.size() && !U; i++)
            if ( Tabella.elementAt(i).Ultimo==false ) { pos=i; U=true; }
      int classe=Tabella.elementAt(pos).Classe();
      for (int i=pos+1; i<Tabella.size(); i++ ) {
        if ( Tabella.elementAt(i).Classe()==0 &&  Tabella.elementAt(i).Ultimo==false ) return Tabella.elementAt(i).F;
        else if ( Tabella.elementAt(i).Classe()<classe && Tabella.elementAt(i).Ultimo==false ) pos=i;
      }
      return Tabella.elementAt(pos).F;
    }
    /**
     * Aggiorna i flag R e M della pagina in Posizione
     * @param Posizione
     *  posizione di aggiornamento
     * @param M
     *  Bit di modifica
     */
    public void aggiornaEntry( int Posizione, boolean M ) {
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).M=M;
        Tabella.elementAt(Posizione).Ultimo=true;
    }
    /**
     * Per ogni pagina resetta R a false
     */
    public void aggiornaEntries( ) { 
        for(int i=0; i<Tabella.size(); i++)
            Tabella.elementAt(i).R=false;
    }
    
    public void azzeraUltimo() {
        for( int i=0; i<Tabella.size(); i++ )
            Tabella.elementAt(i).Ultimo=false;
    }
    
}
