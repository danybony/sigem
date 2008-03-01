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
      private FrameMemoria F=null;
    }
    
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    
    public C( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    
    public void InserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).F=F;
    }
    
    public void LiberaEntry( int Posizione ) { 
        Tabella.elementAt(Posizione).R=false;
        Tabella.elementAt(Posizione).F=null;
    }
    
    public FrameMemoria SelezionaEntry() {
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
        return Tabella.elementAt(temp).F;
    }
    
    public void AggiornaEntry( int Posizione, boolean M ) {}
    public void AggiornaEntries( ) {}
}
