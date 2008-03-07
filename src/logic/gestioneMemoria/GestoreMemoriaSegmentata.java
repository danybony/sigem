/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * Aggiustata la rimozione iniziale
 * Aggiunto controllo dimensione sui segmenti e sulla somma
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
    private int dimensione_ram=0;
            
    public GestoreMemoriaSegmentata( ConfigurazioneIniziale C ){
        
        dimensione_ram=C.getDimensioneRAM();
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
    
    public void notificaProcessoTerminato(int id) {
        MemoriaRam.liberaMemoria(id);
        MemoriaSwap.liberaMemoria(id);
    }
    
    private FrameMemoria RimuoviFrame( Vector<FrameMemoria> Frames ) {
        FrameMemoria F=null; 
        for(int i=0;i<Frames.size();i++) {
            if( ((Segmento)Frames.elementAt(i)).getTempoInRam() < ((Segmento)F).getTempoInRam() )
                F=Frames.elementAt(i);
        }
        return F;
    }
    
    private FrameMemoria Inserisci( MemoriaSegmentata M, FrameMemoria F ) throws MemoriaEsaurita {
        FrameMemoria FrameLibero=null;
        if ( M instanceof RAMSegmentata ) {
            FrameLibero=PoliticaAllocazione.Alloca( F,((RAMSegmentata)M).getFrameLiberi() );
        }
        M.aggiungi(F, FrameLibero);
        return FrameLibero;
    }
    
    private FrameMemoria Rimuovi( MemoriaSegmentata M, FrameMemoria F ) {
        FrameMemoria FrameRimosso=F;
        if ( M instanceof RAMSegmentata ) {
            FrameRimosso=RimuoviFrame( ((RAMSegmentata)M).getFrameOccupati() );
        }
        if ( M.rimuovi(FrameRimosso) ) return FrameRimosso;
        else return null;
    }
    
    public LinkedList<Azione> esegui( LinkedList<FrameMemoria> ListaSegmenti, int UT ){
        
        LinkedList<Azione> Azioni=new LinkedList();
        Iterator<FrameMemoria> I=ListaSegmenti.iterator();
        
        boolean Errore=false;
        int spazio_a_disposizione=dimensione_ram;
        
        while( I.hasNext() && !Errore ) {
            
            FrameMemoria F=I.next();
            ((Segmento)F).setTempoInRAM(UT);
            
            if ( !MemoriaRam.cerca(F) ) {
                if ( ((Segmento)F).getDimensione() > dimensione_ram && spazio_a_disposizione>0 ) {
                    Errore=true;
                    Azioni.add( new AzioneSegmento(-1,null) );
                }
                else {
                    FrameMemoria Temp=Rimuovi( MemoriaSwap, F );
                    if (Temp!=null) Azioni.add( new AzioneSegmento(4, Temp ) );

                    while ( MemoriaRam.getSpazioMaggiore().getDimensione() < F.getDimensione() && !Errore ) {
                        Azioni.add( new AzioneSegmento(0,null) );
                        FrameMemoria FrameRimosso=Rimuovi( MemoriaRam, null );
                        Azioni.add( new AzionePagina( 3, FrameRimosso ) );
                        if ( FrameRimosso.getModifica()==true ) {                                                        
                            try { 
                                  Inserisci( MemoriaSwap, FrameRimosso );
                                  Azioni.add( new AzioneSegmento( 2, FrameRimosso ) );
                            }
                            catch ( MemoriaEsaurita SwapEsaurita ) {
                                Azioni.add( new AzioneSegmento(-1,null) );
                                Errore=true;
                            }

                        }

                    }
                    if ( Errore==false ) 
                        try { 
                            Azioni.add( new AzioneSegmento( 1, Inserisci( MemoriaRam, F ) ) ); 
                            spazio_a_disposizione-=F.getDimensione();
                        }
                        catch ( MemoriaEsaurita Impossibile ) { }
                }
   
            }
            else Azioni.add( new AzioneSegmento( 5, F ) );
        }
        return Azioni;
    }
    
}
