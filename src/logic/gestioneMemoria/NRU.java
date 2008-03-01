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
public class NRU implements IRimpiazzo {
  
    private class Dati {
      private boolean M;
      private boolean R;
      Dati() { R=false; M=false; }
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
    
    
    NRU( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    
    public void InserisciEntry( int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).M=M;
    }
    
    public void LiberaEntry( int Posizione ) { 
        Tabella.elementAt(Posizione).R=false;
        Tabella.elementAt(Posizione).M=false;
    }
    
    public int SelezionaEntry() { 
      int classe=0,pos=0;
      for (int i=0; i<Tabella.size(); i++ ) {
        if ( Tabella.elementAt(i).Classe()==0 ) return i;
        else if ( Tabella.elementAt(i).Classe()<classe ) pos=i;
      }
      return pos;
    }
    
    public void AggiornaEntry( int Posizione, boolean M ) {
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).M=M;
    }
    
    public void AggiornaEntries( ) { 
        for(int i=0; i<Tabella.size(); i++)
            Tabella.elementAt(i).R=false;
    }
    
}
