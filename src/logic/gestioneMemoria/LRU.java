/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.Vector;

/**
 *
 * @author Davide
 */
public class LRU implements IRimpiazzo {
    
    private int Contatore=0;
    
    private class Dati {
      private int UltimoAccesso;
      Dati() { UltimoAccesso=0; }
    }
    
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    
    public LRU( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    
    public void InserisciEntry( int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).UltimoAccesso=Contatore;
        Contatore=Contatore+1;
    }
    
    public void LiberaEntry( int Posizione ) {
        Tabella.elementAt(Posizione).UltimoAccesso=0;
    }
    
    public int SelezionaEntry() {
        int pos=0; int T=Tabella.firstElement().UltimoAccesso;
        for (int i=0; i<Tabella.size(); i++ ) {
            if ( Tabella.elementAt(i).UltimoAccesso<T ) {
                T=Tabella.elementAt(i).UltimoAccesso;
                pos=i;
            }
        }
        return pos;
    }
    
    public void AggiornaEntry( int Posizione, boolean M ) {
        Tabella.elementAt(Posizione).UltimoAccesso=Contatore;
        Contatore=Contatore+1;
    }
    public void AggiornaEntries( ) {  }
    
}
