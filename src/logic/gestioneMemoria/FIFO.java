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
public class FIFO implements IRimpiazzo {
  
    private class Dati {
      private int TArrivo;
      Dati() { TArrivo=0; }
      private boolean Minore( Dati D ) { return TArrivo<D.TArrivo; }
    }
    
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    
    FIFO( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    
    public void InserisciEntry( int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).TArrivo=UT;
    }
    
    public void LiberaEntry( int Posizione ) { 
        Tabella.elementAt(Posizione).TArrivo=0;
    }
    
    public int SelezionaEntry() {
        int pos=0; Dati D=Tabella.firstElement();
        for(int i=1; i<Tabella.size(); i++ )
            if ( Tabella.elementAt(i).Minore(D) ) { 
                pos=i; D=Tabella.elementAt(i); }
        return pos;        
    }
    
    public void AggiornaEntry( int Posizione, boolean M ) {}
    public void AggiornaEntries( ) {}
    
}
