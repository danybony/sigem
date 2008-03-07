/*
 * Azienda: Stylosoft
 * Nome file: GestoreMemoriaPaginata.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.5
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.5 (05/03/2008): Aggiunto controllo inserimento numero maggiore di pagine 
                          rispetto a quelle disponibili
 *  - v.1.4 (04/03/2008): Aggiunto controllo rimozione pagina non esistente
 *  - v.1.3 (04/03/2008): Eliminazione ricerca in Swap ma eliminiazione a priori, 
                          con controllo e modifica del metodo rimuovi
 *  - v.1.2 (03/03/2008): Aggiunta del metodo notificaProcessoTerminato
 *  - v.1.1 (02/03/2008): Modificato il costruttore in base alla configurazione iniziale
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.LinkedList;
import java.util.Iterator;
import logic.parametri.ConfigurazioneIniziale;
/**
 * Si occupa interamente ed esclusivamente della 
 * gestione della memoria paginata, ossia quando l'utente richiede una 	simulazione
 * di un sistema a memoria virtuale paginata. Questa classe 
 * fornisce al Processore una lista di azioni, che descrive precisamente tutte le 
 * operazioni che dovranno esser eseguite in memoria nel corso 	dell'esecuzione della simulazione.
 * @author Davide Compagnin
 */
public class GestoreMemoriaPaginata extends GestoreMemoria {
    /**
     * Numero di frame totali in RAM
     */
    private int numero_frame_ram=0;
    /**
     * Riferimento alla politica di Rimpiazzo
     */
    private IRimpiazzo PoliticaRimpiazzo=null;
    /**
     * Riferimento alla memoria RAM
     */
    private RAMPaginata MemoriaRam=null;
    /**
     * Riferimanto alla Memoria Swap
     */
    private SwapPaginata MemoriaSwap=null;
    /**
     * Indicatore di dimensione pagina nulla
     */
    private boolean PaginaNulla=false;
    /**
     * Costruttore del GestoreMemoriaPaginato
     * @param C
     *  Configurazione Iniziale dalla quale impostare i valori
     */
    public GestoreMemoriaPaginata( ConfigurazioneIniziale C ){
        
        
        try { 
            
            numero_frame_ram=C.getDimensioneRAM()/C.getDimensionePagina();
            MemoriaRam= new RAMPaginata(C);
            MemoriaSwap= new SwapPaginata(C);
            switch ( C.getPoliticaGestioneMemoria() ) {
                case 1:PoliticaRimpiazzo = new NRU(numero_frame_ram); break;
                case 2:PoliticaRimpiazzo = new FIFO(numero_frame_ram); break;
                case 3:PoliticaRimpiazzo = new SC(numero_frame_ram); break;
                case 4:PoliticaRimpiazzo = new C(numero_frame_ram); break;
                case 5:PoliticaRimpiazzo = new LRU(numero_frame_ram); break;
                case 6:PoliticaRimpiazzo = new NFU(numero_frame_ram); break;
                case 7:PoliticaRimpiazzo = new A(numero_frame_ram); break;
            }
        }
        catch ( PaginaNulla paginanulla ) {
            PaginaNulla=true;
        }
        
    }
    /**
     * Rilascia la memoria del processo terminato
     * @param id
     * Identificativo del processo
     */
    public void notificaProcessoTerminato(int id) {
        MemoriaRam.liberaMemoria(id);
        MemoriaSwap.liberaMemoria(id);
    }
    /**
     * Metodo di inserimento di utilità interna
     * @param M
     *   Riferimento alla Memoria
     * @param F
     *   Pagina da Inserire
     * @param UT
     *   Unità di tempo corrente
     * @return
     *   Posizione d'inserimento
     * @throws logic.gestioneMemoria.MemoriaEsaurita
     */
    private int inserisci( MemoriaPaginata M, FrameMemoria F, int UT ) throws MemoriaEsaurita {
        int Posizione_Inserimento=M.aggiungi(F);
        if ( M instanceof RAMPaginata ) {
            PoliticaRimpiazzo.InserisciEntry( F, Posizione_Inserimento, UT, F.getModifica() );
        }
        return Posizione_Inserimento;
    }
    /**
     * Metodo di utilità interno che rimuove una pagina dalla memoria
     * @param M
     *  Riferimento alla memoria
     * @param F
     *  Pagina da rimuovere
     * @return
     *  Pagina Rimossa
     */
    private FrameMemoria rimuovi( Memoria M, FrameMemoria F ) {
        FrameMemoria Da_Rimuovere=F;
        if ( M instanceof RAMPaginata ) {
            Da_Rimuovere=PoliticaRimpiazzo.SelezionaEntry();
        }
        if ( !M.rimuovi(Da_Rimuovere) ) return null;
        return Da_Rimuovere;
    }
    /**
     * Metodo principale d'esecuzione
     * @param ListaPagine
     *  Lista di pagine da caricare
     * @param UT
     *  Unità di tempo corrente
     * @return
     *  Lista di azioni compiute
     */
    public LinkedList<Azione> esegui( LinkedList<FrameMemoria> ListaPagine, int UT ) {
        
        LinkedList<Azione> ListaAzioni=new LinkedList<Azione>();
        
        if ( PaginaNulla==true ) { 
            ListaAzioni.add( new AzionePagina(-1,null) );
            return ListaAzioni;
        }
        
        if ( UT%3 == 0 ) {
            PoliticaRimpiazzo.AggiornaEntries();
        }
        
        Iterator<FrameMemoria> I=ListaPagine.iterator();
        boolean Errore=false;
        int n_inserimenti=0;
        while( I.hasNext() && !Errore ) {
            
            FrameMemoria F=I.next();
            
            if ( !MemoriaRam.cerca(F) ) {// Pagina non in ram
                try {
                    FrameMemoria Temp=rimuovi( MemoriaSwap, F );
                    if (Temp!=null) ListaAzioni.add( new AzionePagina(4, Temp ) );
                    ListaAzioni.add( new AzionePagina(1, F, inserisci(MemoriaRam,F,UT) ) );
                    n_inserimenti++;
                    if ( n_inserimenti > numero_frame_ram ) {
                        ListaAzioni.add( new AzionePagina(-1,null) );
                        Errore=true;
                    }
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
                        n_inserimenti++;
                        if ( n_inserimenti > numero_frame_ram ) {
                            ListaAzioni.add( new AzionePagina(-1,null) );
                            Errore=true;
                        }
                    }
                    catch ( MemoriaEsaurita SwapEsaurita ) {
                        // EXIT() situazione grave (memoria finita)
                        ListaAzioni.add( new AzionePagina(-1,null) );
                        Errore=true;
                    }
                }
            }
            else { // gia in ram
                int Posizione=MemoriaRam.indiceDi(F);
                PoliticaRimpiazzo.AggiornaEntry(Posizione, F.getModifica() );
                ListaAzioni.add( new AzionePagina(5,F,Posizione) );
            }
        }
        return ListaAzioni;
    }

}
