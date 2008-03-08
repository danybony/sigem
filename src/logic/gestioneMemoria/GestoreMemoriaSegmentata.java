/*
 * Azienda: Stylosoft
 * Nome file: GestoreMemoriaSegmentata.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.5
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.5 (08/03/2008): Corretto controllo sulla dimensione segmento e possibilità di allocazione
 *  - v.1.4 (08/03/2008): Corretto bug azione inserimento
 *  - v.1.3 (04/03/2008): Aggiunto controllo dimensione sui segmenti e sulla somma delle dimensioni
 *  - v.1.2 (03/03/2008): Aggiustata la rimozione iniziale
 *  - v.1.1 (02/03/2008): Aggiunta del metodo di notifica processo terminato
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */
package logic.gestioneMemoria;

import java.util.LinkedList;
import java.util.Vector;
import java.util.Iterator;
import logic.parametri.ConfigurazioneIniziale;

/**
 * Classe pubblica e concreta. Questa classe eredita direttamente dalla classe
 * GestoreMemoria e quindi ne implementa i metodi astratti. Manipola la
 * memoria secondo la tecnica della segmentazione. Restituisce le operazioni
 * che ad ogni istante della simulazione dovranno essere eseguite nel sistema
 * a segmentazione affinchè ogni processo possa eseguire
 * @author Davide Compagnin
 */
public class GestoreMemoriaSegmentata extends GestoreMemoria {
    /**
     * Riferimento alla politica di allocazione
     */
    private IAllocazione PoliticaAllocazione=null;
    /**
     * Riferimento alla RAM segmentata
     */
    private RAMSegmentata MemoriaRam=null;
    /**
     * Riferimento alla Swap segmentata
     */
    private SwapSegmentata MemoriaSwap=null;
    /**
     * Dimensione RAM in KB
     */
    private int dimensione_ram=0;
    /**
     * Costruttore che imposta i dati a partire dalla ConfigurazioneIniziale
     * @param C
     *  Configurazione Iniziale
     */        
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
    /**
     * Metodo pubblico chiamato dal Processore per notificare la 					terminazione di un processo. Chiama la cancellazione dalla memoria 
     * di tutte le risorse che il processo utilizzava tramite il metodo 
     * @param id
     *  Identificativo del processo terminato
     */
    public void notificaProcessoTerminato(int id) {
        MemoriaRam.liberaMemoria(id);
        MemoriaSwap.liberaMemoria(id);
    }
    /**
     * Metodo privato di utilità interna alla classe che rimuove un segmento 	dalla RAM quando essa è piena per far posto ad un altro segmento.
     * La rimozione avviene secondo una politica FIFO.
     * @param Frames
     *   Lista di Segmenti presenti in memoria
     * @return
     *   Segmento da rimuovere
     */
    private FrameMemoria RimuoviFrame( Vector<FrameMemoria> Frames ) {
        FrameMemoria F=null; 
        for(int i=0;i<Frames.size();i++) {
            if( ((Segmento)Frames.elementAt(i)).getTempoInRam() < ((Segmento)F).getTempoInRam() )
                F=Frames.elementAt(i);
        }
        return F;
    }
    /**
     * Metodo privato di utilità interna alla classe che inserisce il Segmento F
     * nella memoria M. Può rilanciare l'eccezione MemoriaEsaurita nel caso 
     * in cui l'aggiunta del segmento in memoria Swap preveda il
     * superamento della quantità di memoria Swap disponibile
     * @param M
     *  Riferimento alla memoria
     * @param F
     *  Frame da inserire
     * @return
     *  Frame nel quale ho inserito
     * @throws logic.gestioneMemoria.MemoriaEsaurita
     */
    private FrameMemoria Inserisci( MemoriaSegmentata M, FrameMemoria F ) throws MemoriaEsaurita {
        FrameMemoria FrameLibero=null;
        if ( M instanceof RAMSegmentata ) {
            FrameLibero=PoliticaAllocazione.Alloca( F,((RAMSegmentata)M).getFrameLiberi() );
        }
        M.aggiungi(F, FrameLibero);
        return FrameLibero;
    }
    /**
     * Metodo privato di utilità interna alla classe che rimuove il segmento F
     * dalla memoria M nel caso essa sia Swap, in caso contrario richiama il 
     * metodo RimuoviFrame. Ritorna il frame effettivamente rimosso.
     * @param M
     *  Riferimento alla memoria
     * @param F
     *  Frame da rimuovere
     * @return
     *  Frame rimosso
     */
    private FrameMemoria Rimuovi( MemoriaSegmentata M, FrameMemoria F ) {
        FrameMemoria FrameRimosso=F;
        if ( M instanceof RAMSegmentata ) {
            FrameRimosso=RimuoviFrame( ((RAMSegmentata)M).getFrameOccupati() );
        }
        if ( M.rimuovi(FrameRimosso) ) return FrameRimosso;
        else return null;
    }
    /**
     * Metodo pubblico di fondamentale importanza. E' il metodo principale
     * per tutto il sistema. E' chiamato dal Processore passandogli la lista di 
     * segmenti da caricare in quell'istante. Restituisce una LinkedList di
     * AzioneSegmento che rappresenta la sequenza ordinata di azioni 
     * effettuate nella memoria
     * @param ListaSegmenti
     *   Lista dei segmenti da caricare in un certo istante
     * @param UT
     *   Istante corrente
     * @return
     *   Lista di azioni effettuate in memoria
     */
    public LinkedList<Azione> esegui( LinkedList<FrameMemoria> ListaSegmenti, int UT ){
        
        LinkedList<Azione> Azioni=new LinkedList();
        Iterator<FrameMemoria> I=ListaSegmenti.iterator();
        
        boolean Errore=false;
        int spazio_a_disposizione=dimensione_ram;
        
        while( I.hasNext() && !Errore ) {
            
            FrameMemoria F=I.next();
            ((Segmento)F).setTempoInRAM(UT);
            
            if ( !MemoriaRam.cerca(F) ) {
                if ( ((Segmento)F).getDimensione() > dimensione_ram || spazio_a_disposizione<=0 ) {
                    Errore=true;
                    Azioni.add( new AzioneSegmento(-1,null) );
                }
                else {
                    FrameMemoria Temp=Rimuovi( MemoriaSwap, F );
                    if (Temp!=null) Azioni.add( new AzioneSegmento(4, Temp ) );

                    while ( MemoriaRam.getSpazioMaggiore().getDimensione() < F.getDimensione() && !Errore ) {
                        Azioni.add( new AzioneSegmento(0,null) );
                        FrameMemoria FrameRimosso=Rimuovi( MemoriaRam, null );
                        Azioni.add( new AzioneSegmento( 3, FrameRimosso ) );
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
                            Azioni.add( new AzioneSegmento( 1, F, Inserisci( MemoriaRam, F ) ) ); 
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
