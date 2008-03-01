/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * @author Davide
 */
class GestoreMemoriaPaginata extends GestoreMemoria {
    
    private int numero_frame_ram;
    private int numero_frame_swap;
    private IRimpiazzo PoliticaRimpiazzo;
    private RAMPaginata MemoriaRam=null;
    private SwapPaginata MemoriaSwap=null;
    
    public GestoreMemoriaPaginata(IRimpiazzo p, int n_frame_ram, int n_frame_swap){
        
        PoliticaRimpiazzo=p;
        numero_frame_ram=n_frame_ram;
        numero_frame_swap=n_frame_swap;
        MemoriaRam= new RAMPaginata(n_frame_ram);
        MemoriaSwap= new SwapPaginata(n_frame_swap);
    
    }
    
        public LinkedList<Azione> Esegui( LinkedList<FrameMemoria> ListaPagine, int UT ) {
            if ( UT%3 == 0 ) {
                PoliticaRimpiazzo.AggiornaEntries();
            }
            Iterator<FrameMemoria> I=ListaPagine.iterator();
            while( I.hasNext() ) {
                FrameMemoria F=I.next();
                if ( !MemoriaRam.cerca(F) ) { // Pagina non in ram
                    n_total_fault++;
                    if ( !MemoriaSwap.cerca(F) ) { // Pagina neanche in swap
                        n_first_fault++;
                        try {
                            int Posizione_Inserimento=MemoriaRam.aggiungi(F);
                            PoliticaRimpiazzo.InserisciEntry( Posizione_Inserimento, UT, F.getModifica() );
                            n_accessi_ram+=1; n_accessi_disco+=1; 
                            // crea comando inserimento
                        } 
                        catch ( MemoriaEsaurita RamEsaurita ) {
                            int Posizione_Ram=PoliticaRimpiazzo.SelezionaEntry();
                            try {
                                FrameMemoria Temp=MemoriaRam.rimuovi(Posizione_Ram);
                                n_accessi_ram+=1;
                                int Posizione_Swap=MemoriaSwap.aggiungi(Temp);
                                n_accessi_disco+=1;
                                Posizione_Ram=MemoriaRam.aggiungi(F);
                                n_accessi_ram+=1; n_accessi_disco+=1;
                                PoliticaRimpiazzo.InserisciEntry( Posizione_Ram, UT, F.getModifica() );
                                // crea comando rimpiazzo
                            }
                            catch ( MemoriaEsaurita SwapEsaurita ) {
                                // situazione grave (memoria finita)
                            }
                        }
                    }
                    else { // pagina in swap
                        try {
                            int Posizione_Swap=MemoriaSwap.rimuovi(F);
                            n_accessi_disco+=1;
                            int Posizione_Ram=MemoriaRam.aggiungi(F);
                            n_accessi_ram+=1;
                            PoliticaRimpiazzo.InserisciEntry( Posizione_Ram, UT, false );
                            // crea comando from swap to ram
                        }
                        catch ( MemoriaEsaurita RamEsaurita ) {
                            int Posizione_Ram=PoliticaRimpiazzo.SelezionaEntry();
                            try {
                                FrameMemoria Temp=MemoriaRam.rimuovi(Posizione_Ram);
                                int Posizione_Swap=MemoriaSwap.aggiungi(Temp);
                                n_accessi_ram+=1; n_accessi_disco+=1;
                                Posizione_Ram=MemoriaRam.aggiungi(F);
                                n_accessi_ram+=1;
                                PoliticaRimpiazzo.InserisciEntry( Posizione_Ram, UT, F.getModifica() );
                                // crea comando swapping doppio
                            }
                            catch ( MemoriaEsaurita SwapEsaurita ) {
                                // caso impossibile
                            }
                        }
                    }
                        
                }
                else { // gia in ram
                    int Posizione_Ram=MemoriaRam.cerca(F);
                    n_accessi_ram+=1;
                    PoliticaRimpiazzo.AggiornaEntry(Posizione_Ram, F.getModifica() );
                }
                    
                
                
            }
        }

}
