/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.LinkedList;
import java.util.Iterator;
import logic.parametri.ConfigurazioneIniziale;
/**
 *
 * @author Davide
 */
public class GestoreMemoriaPaginata extends GestoreMemoria {
    
    private int numero_frame_ram=0;
    private IRimpiazzo PoliticaRimpiazzo=null;
    private RAMPaginata MemoriaRam=null;
    private SwapPaginata MemoriaSwap=null;
    
    public GestoreMemoriaPaginata( ConfigurazioneIniziale C ){
        
        numero_frame_ram=C.getDimensioneRAM()/C.getDimensionePagina();
        MemoriaRam= new RAMPaginata(C);
        MemoriaSwap= new SwapPaginata(C);           
        switch ( C.getPoliticaGestioneMemoria() ) {
            case 1:PoliticaRimpiazzo = new NRU(numero_frame_ram);
            case 2:PoliticaRimpiazzo = new FIFO(numero_frame_ram);
            case 3:PoliticaRimpiazzo = new SC(numero_frame_ram);
            case 4:PoliticaRimpiazzo = new C(numero_frame_ram);
            case 5:PoliticaRimpiazzo = new LRU(numero_frame_ram);
            case 6:PoliticaRimpiazzo = new NFU(numero_frame_ram);
            case 7:PoliticaRimpiazzo = new A(numero_frame_ram);
        }
    }
    
    public void notificaProcessoTerminato(int id) {
        MemoriaRam.liberaMemoria(id);
        MemoriaSwap.liberaMemoria(id);
    }
    
    private int inserisci( MemoriaPaginata M, FrameMemoria F, int UT ) throws MemoriaEsaurita {
        int Posizione_Inserimento=M.aggiungi(F);
        if ( M instanceof RAMPaginata ) {
            PoliticaRimpiazzo.InserisciEntry( F, Posizione_Inserimento, UT, F.getModifica() );
        }
        return Posizione_Inserimento;
    }
    
    private FrameMemoria rimuovi( Memoria M, FrameMemoria F ) {
        FrameMemoria Da_Rimuovere=F;
        if ( M instanceof RAMPaginata ) {
            Da_Rimuovere=PoliticaRimpiazzo.SelezionaEntry();
        }
        if ( M.rimuovi(Da_Rimuovere) ) return Da_Rimuovere;
        else return null;
    }
    
    public LinkedList<Azione> esegui( LinkedList<FrameMemoria> ListaPagine, int UT ) {
        
        LinkedList<Azione> ListaAzioni=null;
        
        if ( UT%3 == 0 ) {
            PoliticaRimpiazzo.AggiornaEntries();
        }
        
        Iterator<FrameMemoria> I=ListaPagine.iterator();
        
        while( I.hasNext() ) {
            
            FrameMemoria F=I.next();
            
            if ( !MemoriaRam.cerca(F) ) { // Pagina non in ram
                if ( !MemoriaSwap.cerca(F) ) { // Pagina neanche in swap
                    try {
                        ListaAzioni.add( new AzionePagina(1, F, inserisci(MemoriaRam,F,UT) ) );
                    } 
                    catch ( MemoriaEsaurita RamEsaurita ) {
                        try {
                            ListaAzioni.add( new AzionePagina(0,null) );
                            FrameMemoria Frame_Rimosso=rimuovi( MemoriaRam, null );
                            ListaAzioni.add( new AzionePagina(3, Frame_Rimosso ) );
                            if ( Frame_Rimosso.getModifica()==true ) {
                                ListaAzioni.add( new AzionePagina(2, Frame_Rimosso, 
                                        inserisci(MemoriaSwap,Frame_Rimosso,UT) ) );
                            }
                            ListaAzioni.add( new AzionePagina(1, F, inserisci(MemoriaRam,F,UT) ) );
                        }
                        catch ( MemoriaEsaurita SwapEsaurita ) {
                            // EXIT() situazione grave (memoria finita)
                            ListaAzioni.add( new AzionePagina(-1,null) );
                        }
                    }
                }
                else { // pagina in swap
                    try {
                        ListaAzioni.add( new AzionePagina(4, rimuovi( MemoriaSwap, F ) ) );
                        ListaAzioni.add( new AzionePagina(1, F, inserisci(MemoriaRam,F,UT) ) );
                    }
                    catch ( MemoriaEsaurita RamEsaurita ) {
                        try {
                            ListaAzioni.add( new AzionePagina(0,null) );
                            FrameMemoria Frame_Rimosso=rimuovi( MemoriaRam, null );
                            ListaAzioni.add( new AzionePagina(3, Frame_Rimosso ) );
                            if ( Frame_Rimosso.getModifica()==true ) {
                                ListaAzioni.add( new AzionePagina(2, Frame_Rimosso, 
                                        inserisci(MemoriaSwap,Frame_Rimosso,UT) ) );
                            }
                            ListaAzioni.add( new AzionePagina(1, F, inserisci(MemoriaRam,F,UT) ) );
                        }
                        catch ( MemoriaEsaurita SwapEsaurita ) {
                            // EXIT() situazione grave (memoria finita)
                            ListaAzioni.add( new AzionePagina(-1,null) );
                        }
                    }
                }

            }
            else { // gia in ram
                PoliticaRimpiazzo.AggiornaEntry(MemoriaRam.indiceDi(F), F.getModifica() );
                ListaAzioni.add( new AzionePagina(5,F) );
            }
        }
        return ListaAzioni;
    }

}
