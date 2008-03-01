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
public class C implements IRimpiazzo {
    private int Lancetta=0;
    
    private class Dati {
      private boolean R;
      Dati() { R=false; }
    }
    
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    
    public C( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    
    public void InserisciEntry( int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).R=true;
    }
    
    public void LiberaEntry( int Posizione ) { 
        Tabella.elementAt(Posizione).R=false;
    }
    
    public int SelezionaEntry() {
        boolean trovata=false;
        int dim=Tabella.size();        
        while( !trovata ) {
            if ( Tabella.elementAt(Lancetta).R==true ) {
                Tabella.elementAt(Lancetta).R=false;
                Lancetta=(Lancetta+1)%dim;
            }
            else trovata=true;
        }
        int temp=Lancetta;
        Lancetta=(Lancetta+1)%dim;
        return temp;
    }
    
    public void AggiornaEntry( int Posizione, boolean M ) {}
    public void AggiornaEntries( ) {}
}
