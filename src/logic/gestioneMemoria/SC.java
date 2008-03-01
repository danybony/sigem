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
public class SC implements IRimpiazzo {
  
    private class Dati {
      private int TArrivo=0;
      private boolean R=false;
      private FrameMemoria F=null;
    }
    
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    
    public SC( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    
    public void InserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M ) {
        Tabella.elementAt(Posizione).TArrivo=UT;
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).F=F;
    }
    
    public void LiberaEntry( int Posizione ) {
        Tabella.elementAt(Posizione).TArrivo=0;
        Tabella.elementAt(Posizione).R=false;
        Tabella.elementAt(Posizione).F=null;
    }
    
    public FrameMemoria SelezionaEntry() {
        
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
    
    public void AggiornaEntry( int Posizione, boolean M ) {}
    public void AggiornaEntries( ) {}
    
}
