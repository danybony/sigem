/*
 * Azienda: Stylosoft
 * Nome file: SC.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.1 (01/03/2008): Riscrittura completa del metodo SelezionaEntry
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.Vector;

/**
 * Classe pubblica concreta che implementa l'interfaccia IRimpiazzo. Essa 
 * prende il nome di SC per Second Chance cioè FIFO soltanto con controllo di 
 * riferimento
 * @author Davide Compagnin
 */
public class SC implements IRimpiazzo {
    /**
     * Classe interna privata che memorizza in un intero TArrivo l'unità di tempo
     * in cui la pagina è stata caricata in RAM, un boolean R che indica se la 
     * pagina è stata recentemente riferita, oltre al riferimento F alla pagina
     */
    private class Dati {
      private int TArrivo=0;
      private boolean R=false;
      private FrameMemoria F=null;
    }
    /**
     * Vettore privato che memorizza una sequenza di Dati come immagine
     * della memoria
     */
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    /**
     * Costruttore che data la dimensione intera della memoria interpretata come il numero
     * di FrameMemoria, istanzia Tabella con valori di default e inizializza il 
     * Contatore a 0
     * @param dim
     *  dimensione memoria in numero di pageframe
     */
    public SC( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    /**
     * Imposta F riferimento alla pagina nella Posizione indicata, e imposta 
     * TArrivo con il valore corrente di UT, R ovviamente sarà true.
     * @param F
     *  Frame da inserire
     * @param Posizione
     *  Posizione di inserimento
     * @param UT
     *  Tempo corrente
     * @param M
     *  Bit di Modifica
     */
    public void inserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M ) {
        Tabella.elementAt(Posizione).TArrivo=UT;
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).F=F;
    }
    /**
     * Resetta i campi dati relativi alla pagina nella Posizione specificata dal
     * parametro
     * @param Posizione
     *  Posizione di reset
     */
    public void liberaEntry( int Posizione ) {
        Tabella.elementAt(Posizione).TArrivo=-1;
        Tabella.elementAt(Posizione).R=false;
        Tabella.elementAt(Posizione).F=null;
    }
    /**
     * Implementa l'algoritmo. Restituisce un riferimento alla pagina il cui valore 
     * di TArrivo è minore e contemporaneamente il campo R sia false.
     * @return
     *  Frame da rimuovere
     */
    public FrameMemoria selezionaEntry() {
        
        int i=0,pos=0; Dati DMinore=Tabella.firstElement();       
        boolean primo=false;
        
        /*Cerco l'indice del primo elemento con R=false, cioè la prima pagina
         * non riferita */
        while( i<Tabella.size() && primo==false ) {
            if( Tabella.elementAt(i).R==false ) {
                DMinore=Tabella.elementAt(i);
                pos=i;
                primo=true;
            }
            else i++;
        }
        /* Se la trovo, cerco quella con il tempo di arrivo minore */
        if ( primo==true ) {
            for (i=pos; i<Tabella.size(); i++ ) {
                Dati D=Tabella.elementAt(i);
                if ( D.R==false && D.TArrivo < DMinore.TArrivo ) {
                    pos=i; DMinore=D; 
                }
            }
            /* Imposto R=false alle pagine con TArrivo 
             * minore della pagina selezionata */
            for (i=0; i<Tabella.size(); i++ )
                if (Tabella.elementAt(i).TArrivo<DMinore.TArrivo ) 
                    Tabella.elementAt(i).R=false;
        }
        else {
            /* Altrimenti cerco tra tutte le pagine come FIFO settando
             * R a false */
            DMinore=Tabella.firstElement();
            for(i=0; i<Tabella.size(); i++ ) {
                Tabella.elementAt(i).R=false;
                if ( Tabella.elementAt(i).TArrivo<DMinore.TArrivo ) { 
                    pos=i; DMinore=Tabella.elementAt(i); 
                }
            }
        }
        
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
