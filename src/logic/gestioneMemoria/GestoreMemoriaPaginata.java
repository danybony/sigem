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
class GestoreMemoriaPaginata extends GestoreMemoria {
    
    private int numero_frame_ram=0;
    private int numero_frame_swap=0;
    private IRimpiazzo PoliticaRimpiazzo=null;
    private RAMPaginata MemoriaRam=null;
    private SwapPaginata MemoriaSwap=null;
    
    public GestoreMemoriaPaginata( ConfigurazioneIniziale C ){
        
        numero_frame_ram=C.getDimensioneRAM()/C.getDimensionePagina();
        numero_frame_swap=C.getDimensioneSwap()/C.getDimensionePagina();
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
    
    public LinkedList<Azione> LiberaMemoria ( LinkedList<FrameMemoria> ListaPagine ) {
        
        Iterator<FrameMemoria> I=ListaPagine.iterator();
        
        while( I.hasNext() ) {
            FrameMemoria F=I.next();
            int Pos=MemoriaRam.cerca(F);
            if ( Pos>-1 ) {
                MemoriaRam.rimuovi(F);
                PoliticaRimpiazzo.LiberaEntry(Pos);
                // Azione ELIMINA(F)
            }
            Pos=MemoriaSwap.cerca(F);
            if ( Pos>-1 ) {
                MemoriaSwap.rimuovi(F);
            }
        }
        
    }
    
    public LinkedList<Azione> esegui( LinkedList<FrameMemoria> ListaPagine, int UT ) {
        
        LinkedList<Azione> ListaAzioni=null;
        
        if ( UT%3 == 0 ) {
            PoliticaRimpiazzo.AggiornaEntries();
        }
        
        Iterator<FrameMemoria> I=ListaPagine.iterator();
        
        while( I.hasNext() ) {
            
            FrameMemoria F=I.next();
            int Pos_Ram=MemoriaRam.cerca(F);
            int Pos_Swap=MemoriaSwap.cerca(F);
            
            if ( Pos_Ram<0 ) { // Pagina non in ram
                n_total_fault++;
                if ( Pos_Swap<0 ) { // Pagina neanche in swap
                    n_nonswap_fault++;
                    try {
                        int Posizione_Inserimento=MemoriaRam.aggiungi(F);
                        PoliticaRimpiazzo.InserisciEntry( F, Posizione_Inserimento, UT, F.getModifica() );
                        n_accessi_ram+=1; n_accessi_disco+=1; 
                        // crea comando inserimento INSERISCI(F,Posizione_Inserimento)
                        ListaAzioni.add( new Azione(1,F,Posizione_Inserimento) );
                    } 
                    catch ( MemoriaEsaurita RamEsaurita ) {
                        FrameMemoria Frame_Ram=PoliticaRimpiazzo.SelezionaEntry();
                        try {
                            FrameMemoria Temp=MemoriaRam.rimuovi(Frame_Ram);
                            n_accessi_ram+=1;
                            if (Temp.getModifica()==true) {
                                int Posizione_Swap=MemoriaSwap.aggiungi(Temp);
                                n_accessi_disco+=1;
                                // RAMTOSWAP(Temp,Posizione_Ram,Posizione_Swap) 
                                ListaAzioni.add( new Azione(3,Temp,Posizione_Swap) );
                            }
                            int Posizione_Ram=MemoriaRam.aggiungi(F);
                            n_accessi_ram+=1; n_accessi_disco+=1;
                            PoliticaRimpiazzo.InserisciEntry( F, Posizione_Ram, UT, F.getModifica() );
                            // INSERISCI(F,Posizione_Ram) crea comando rimpiazzo
                            ListaAzioni.add( new Azione(1,F,Posizione_Ram) );
                        }
                        catch ( MemoriaEsaurita SwapEsaurita ) {
                            // EXIT() situazione grave (memoria finita)
                            ListaAzioni.add( new Azione(0,null) );
                        }
                    }
                }
                else { // pagina in swap
                    try {
                        FrameMemoria Frame_Swap=MemoriaSwap.rimuovi(F);
                        n_accessi_disco+=1;
                        int Posizione_Ram=MemoriaRam.aggiungi(F);
                        n_accessi_ram+=1;
                        PoliticaRimpiazzo.InserisciEntry( F, Posizione_Ram, UT, F.getModifica() );
                        // SWAPTORAM(F,Posizione_Swap,Posizione_Ram)
                        ListaAzioni.add( new Azione(4,F,Posizione_Ram) );
                    }
                    catch ( MemoriaEsaurita RamEsaurita ) {
                        FrameMemoria Frame_Ram=PoliticaRimpiazzo.SelezionaEntry();
                        try {
                            FrameMemoria Temp=MemoriaRam.rimuovi(Frame_Ram);
                            if (Temp.getModifica()==true) {
                                int Posizione_Swap_Temp=MemoriaSwap.aggiungi(Temp);
                                n_accessi_ram+=1; n_accessi_disco+=1;
                                // RAMTOSWAP(Temp,Posizione_Ram,Posizione_Swap_Temp)
                                ListaAzioni.add( new Azione(3,Temp,Posizione_Swap_Temp) );                                
                            }
                            int Posizione_Ram=MemoriaRam.aggiungi(F);
                            n_accessi_ram+=1;
                            PoliticaRimpiazzo.InserisciEntry( F, Posizione_Ram, UT, F.getModifica() );                            
                            // SWAPTORAM(F,Posizione_Swap,Posizione_Ram)
                            ListaAzioni.add( new Azione(4,F,Posizione_Ram) );
                        }
                        catch ( MemoriaEsaurita SwapEsaurita ) {
                            // caso estremo
                            ListaAzioni.add( new Azione(0,null) );
                        }
                    }
                }

            }
            else { // gia in ram
                n_accessi_ram+=1;
                PoliticaRimpiazzo.AggiornaEntry(Pos_Ram, F.getModifica() );
                ListaAzioni.add( new Azione(5,F) );
            }



        }
        return ListaAzioni;
    }

}
