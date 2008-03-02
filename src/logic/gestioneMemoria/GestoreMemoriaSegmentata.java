/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.LinkedList;
import java.util.Vector;
import java.util.Iterator;
import logic.parametri.ConfigurazioneIniziale;

/**
 *
 * @author Davide
 */
public class GestoreMemoriaSegmentata extends GestoreMemoria {
    
    private IAllocazione PoliticaAllocazione=null;
    private RAMSegmentata MemoriaRam=null;
    private SwapSegmentata MemoriaSwap=null;
    
    public GestoreMemoriaSegmentata( ConfigurazioneIniziale C ){
        
        
        MemoriaRam= new RAMSegmentata(C);
        MemoriaSwap= new SwapSegmentata(C);           
        switch ( C.getPoliticaGestioneMemoria() ) {
            case 1:PoliticaAllocazione = new FirstFit();
            case 2:PoliticaAllocazione = new NextFit();
            case 3:PoliticaAllocazione = new BestFit();
            case 4:PoliticaAllocazione = new WorstFit();
            case 5:PoliticaAllocazione = new QuickFit();
        }
    }
    
    private FrameMemoria RimuoviFrame( Vector<FrameMemoria> Frames ) {
        FrameMemoria F=null; 
        for(int i=0;i<Frames.size();i++) {
            if( Frames.elementAt(i).getTArrivo() < F.getTArrivo() )
                F=Frames.elementAt(i);
        }
        return F;
    }
    
    private FrameMemoria Inserisci( MemoriaSegmentata M, FrameMemoria F ) {
        FrameMemoria FrameLibero=null;
        if ( M instanceof RAMSegmentata ) {
            M=(RAMSegmentata)M;
            FrameLibero=PoliticaAllocazione.Alloca(F,M.getFrameLiberi() ); 
        }        
        M.aggiungi(F, FrameLibero);
        return FrameLibero;
    }
    
    private FrameMemoria Rimuovi( MemoriaSegmentata M, FrameMemoria F ) {
        FrameMemoria FrameRimosso=F;
        if ( M instanceof RAMSegmentata ) {
            M=(RAMSegmentata)M;
            FrameRimosso=RimuoviFrame( M.getFrameOccupati() );
        }
        return M.rimuovi(FrameRimosso);;
    }
    
    public LinkedList<Azione> esegui( LinkedList<FrameMemoria> ListaSegmenti, int UT ){
        
        LinkedList<Azione> Azioni=new LinkedList();
        Iterator<FrameMemoria> I=ListaSegmenti.iterator();
        
        while( I.hasNext() ) {
            
            FrameMemoria F=I.next();
            F.setTArrivo=UT;
            
            if ( !MemoriaRam.cerca(F) ) {
                if ( !MemoriaSwap.cerca(F) ) { // F mai caricato
                    
                    while ( MemoriaRam.getSpazioMaggiore().getDimensione() < F.getDimensione() ) {
                        FrameMemoria FrameRimosso=Rimuovi( MemoriaRam, F );
                        Azioni.add( new AzionePagina( 3, FrameRimosso ) );
                        if ( FrameRimosso.getModifica()==true ) {                            
                            Inserisci( MemoriaSwap, FrameRimosso );
                            Azioni.add( new AzionePagina( 2, FrameRimosso ) );
                        }    
                    }
                    
                    Azioni.add( new AzionePagina( 1, Inserisci( MemoriaRam, F ) ) );
                    
                }
                else { // F in swap
                    
                    Azioni.add( new AzionePagina( 4, Rimuovi(MemoriaSwap, F) ) );
                    
                    while ( MemoriaRam.getSpazioMaggiore().getDimensione() < F.getDimensione() ) {
                        FrameMemoria FrameRimosso=Rimuovi( MemoriaRam, F );
                        Azioni.add( new AzionePagina( 3, FrameRimosso ) );
                        if ( FrameRimosso.getModifica()==true ) {                            
                            Inserisci( MemoriaSwap, FrameRimosso );
                            Azioni.add( new AzionePagina( 2, FrameRimosso );
                        }
                    }
                    
                    Azioni.add( new AzionePagina( 1, Inserisci( MemoriaRam, F ) ) );
                }
            }
            else Azioni.add( new AzionePagina( 5, F ) );
        }
        return Azioni;
    }
}
