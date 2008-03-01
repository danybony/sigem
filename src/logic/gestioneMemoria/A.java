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
public class A implements IRimpiazzo {
    
    private class Dati {
      private int Contatore;
      private boolean R;
      Dati() { R=false; Contatore=0; }
    }
    
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    
    public A( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    
    public void InserisciEntry( int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).Contatore=0;
        Tabella.elementAt(Posizione).R=true;
    }
    
    public void LiberaEntry( int Posizione ) { 
        Tabella.elementAt(Posizione).Contatore=0;
        Tabella.elementAt(Posizione).R=false;
    }
    
    public int SelezionaEntry() {
        int pos=0; int C=Tabella.firstElement().Contatore;
        for (int i=0; i<Tabella.size(); i++ ) {
            if ( Tabella.elementAt(i).Contatore<C ) {
                C=Tabella.elementAt(i).Contatore;
                pos=i;
            }
        }
        return pos;
    }
    
    public void AggiornaEntry( int Posizione, boolean M ) {
        Tabella.elementAt(Posizione).R=true;
    }
    public void AggiornaEntries( ) {
        for( int i=0; i<Tabella.size(); i++ )
            if ( Tabella.elementAt(i).R==true ) {
                Tabella.elementAt(i).R=false;
                Tabella.elementAt(i).Contatore+=Integer.MAX_VALUE/2;
            }
            else Tabella.elementAt(i).Contatore/=2;     
    }
}
