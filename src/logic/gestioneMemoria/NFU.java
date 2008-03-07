/*
 * Azienda: Stylosoft
 * Nome file: NFU.java
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
 * prende il nome di NFU per Not Frequently Used cioè rimpiazza la pagina
 * usata meno di frequente
 * @author Davide Compagnin
 */
public class NFU implements IRimpiazzo {
    
    /**
     * Classe interna privata che memorizza in un intero Contatore il numero di utilizzi 
     * della pagina, in un boolean R se è stata riferita di recente, 
     * infine il riferimento F alla pagina.
     */
    private class Dati {
      private int Contatore;
      private boolean R;
      private FrameMemoria F=null;
    }
    /**
     * Vettore privato che memorizza una sequenza di Dati come immagine
     * della memoria.
     */
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    /**
     * Costruttore che ata la dimensione intera della memoria interpretata come il numero
     * di FrameMemoria, istanzia Tabella con valori di default. 
     * @param dim
     *  Dimensione della memoria in numero framepage
     */
    public NFU( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    /**
     * Imposta F riferimento alla pagina nella Posizione indicata, imposta il
     * Contatore a zero e R a true.
     * @param F
     *  Frame da inserire
     * @param Posizione
     *  Posizione di inserimento
     * @param UT
     *  Tempo corrente
     * @param M
     *  Bit di Modifica
     */
    public void InserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M ) {
        Tabella.elementAt(Posizione).Contatore=0;
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).F=F;
    }
    /**
     * Resetta i campi dati relativi alla pagina nella Posizione specificata dal
     * parametro
     * @param Posizione
     *  Posizione di reset dei campi
     */
    public void LiberaEntry( int Posizione ) {
        Tabella.elementAt(Posizione).Contatore=0;
        Tabella.elementAt(Posizione).R=false;
        Tabella.elementAt(Posizione).F=null;
    }
    /**
     * 
     * Implementa l'algoritmo. Restituisce un riferimento alla pagina il cui 
     * valore di Contatore è minore
     * @return
     *  Ritorna la pagina ottimale da togliere
     */
    public FrameMemoria SelezionaEntry() {
        int pos=0; int C=Tabella.firstElement().Contatore;
        for (int i=0; i<Tabella.size(); i++ ) {
            if ( Tabella.elementAt(i).Contatore<C ) {
                C=Tabella.elementAt(i).Contatore;
                pos=i;
            }
        }
        return Tabella.elementAt(pos).F;
    }
   /**
    * Imposta il campo R a true significando che la pagina è stata riferita
    * @param Posizione
    *   Posizione di aggiornamento
    * @param M
    *   Bit di modifica
    */ 
   public void AggiornaEntry( int Posizione, boolean M ) { 
       Tabella.elementAt(Posizione).R=true;
   }
   /**
    * Per ogni pagina controllo se essa è stata riferita, cioè R è true, incremento il
    * Contatore e resetto R.
    */
   public void AggiornaEntries( ) { 
       for( int i=0; i<Tabella.size(); i++ ) 
           if ( Tabella.elementAt(i).R==true ) {
               Tabella.elementAt(i).Contatore+=1;
               Tabella.elementAt(i).R=false;
           }
   }
   
}
