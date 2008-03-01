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
      private FrameMemoria F=null;
      private boolean Minore( Dati D ) { return TArrivo<D.TArrivo; }
    }
    
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    
    public FIFO( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    
    public void InserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).TArrivo=UT;
        Tabella.elementAt(Posizione).F=F;
    }
    
    public void LiberaEntry( int Posizione ) { 
        Tabella.elementAt(Posizione).TArrivo=0;
        Tabella.elementAt(Posizione).F=null;
    }
    
    public FrameMemoria SelezionaEntry() {
        int pos=0; Dati D=Tabella.firstElement();
        for(int i=1; i<Tabella.size(); i++ )
            if ( Tabella.elementAt(i).Minore(D) ) { 
                pos=i; D=Tabella.elementAt(i); }
        return Tabella.elementAt(pos).F;        
    }
    
    public void AggiornaEntry( int Posizione, boolean M ) {}
    public void AggiornaEntries( ) {}
    
}
