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
                            int Posizione=MemoriaRam.aggiungi(F);
                            PoliticaRimpiazzo.InserisciEntry(Posizione);
                        } 
                        catch (MemoriaEsaurita){
                        }
                    }
                        
                }
                    
                
                
            }
        }

}
