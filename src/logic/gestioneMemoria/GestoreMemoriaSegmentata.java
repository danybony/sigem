/*
 * Azienda: Stylosoft
 * Nome file: GestoreMemoriaSegmentata.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 2.1
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.2.1 (14/03/2008): Corretto problema durante creazione memoria con riferimento prima
 *                        della modifica e problema di nome variabile linea 177
 *  - v.2.0 (14/03/2008): Corretto bug in rimuovi frame
 *  - v.1.9 (14/03/2008): Aggiunta la posizione in rimozione dalla RAM
 *  - v.1.8 (12/03/2008): Modifica metodo notificaProcessoTerminato, metodo Inserisci
 *                        e tolto il controllo sul caricamento massimo
 *  - v.1.7 (08/03/2008): Corretta condizione di errore sulla dimensione
 *  - v.1.6 (08/03/2008): Corretta logica di inserimento
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
     * Costruttore che imposta i dati a partire dalla ConfigurazioneIniziale
     * @param C
     *  Configurazione Iniziale
     */        
    public GestoreMemoriaSegmentata( ConfigurazioneIniziale C ){
        
        
        MemoriaRam= new RAMSegmentata(C);
        MemoriaSwap= new SwapSegmentata(C);           
        switch ( C.getPoliticaGestioneMemoria() ) {
            case 1:PoliticaAllocazione = new FirstFit(); break;
            case 2:PoliticaAllocazione = new NextFit(); break;
            case 3:PoliticaAllocazione = new BestFit(); break;
            case 4:PoliticaAllocazione = new WorstFit(); break;
            case 5:PoliticaAllocazione = new QuickFit(); break;
        }
    }
    
    
    
    public Vector<FrameMemoria> getStatoRAM() {
        return MemoriaRam.getSituazione();
    }
    
    public Vector<FrameMemoria> getStatoSwap() {
        return MemoriaSwap.getSituazione();
    }
    
    
    /**
     * Metodo pubblico chiamato dal Processore per notificare la 					terminazione di un processo. Chiama la cancellazione dalla memoria 
     * di tutte le risorse che il processo utilizzava tramite il metodo 
     * @param id
     *  Identificativo del processo terminato
     */
    public LinkedList<Azione> notificaProcessoTerminato(int id) {
        LinkedList<Azione> Azioni=new LinkedList();
        MemoriaRam.liberaMemoria(id);
        MemoriaSwap.liberaMemoria(id);
        Azioni.add( new Azione(6,null,id) ); /*Creo l'azione con la terminazione del processo*/
        return Azioni;
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
        FrameMemoria F=Frames.elementAt(0); 
        for(int i=1;i<Frames.size();i++) {
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
    private int Inserisci( MemoriaSegmentata M, FrameMemoria F ) throws MemoriaEsaurita {
        FrameMemoria FrameLibero=null;
        if ( M instanceof RAMSegmentata ) {
            //Aggiungere il vector di posizioni
            Vector<FrameMemoria> Liberi=((RAMSegmentata)M).getFrameLiberi();
            int Pos[]=null;
            if (PoliticaAllocazione instanceof NextFit) {                
                Pos=new int[Liberi.size()];
                for (int i=0; i<Liberi.size(); i++)
                    Pos[i]=((RAMSegmentata)M).indiceDi( Liberi.elementAt(i) );
            }
            FrameLibero=PoliticaAllocazione.alloca( F, Liberi, Pos );
        }
        return M.aggiungi(F, FrameLibero );
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
        
        int dimensione_totale_inserimento=0;
        int dimensione_parziale_inserimento=0;
        /* calcolo lo spazio necessario per inserite tutti i segmenti che non ho
         * già inserito in questo istante e anche lo spazio che mi servirebbe totale */
        boolean caricati=true;
        while( I.hasNext() ) {
            FrameMemoria fr=I.next();
            if( !fr.getInRAM() ) { 
                dimensione_parziale_inserimento+=fr.getDimensione();
                caricati=false;
            }
            dimensione_totale_inserimento+=fr.getDimensione();
        }
        
        if(caricati) return null; //ritorno azioni vuote se ho già caricato tutto quello che serve
        
        /* rimuovo i segmenti fino a liberare abbastanza spazio per completare 
         * gli inserimenti che mancano*/
        boolean Errore=false; 
            
        //tento di eliminare i segmenti che non mi serviranno in questo istante

        I=ListaSegmenti.iterator();
        while ( I.hasNext() && MemoriaRam.getSpazioMaggiore().getDimensione() < dimensione_parziale_inserimento && !Errore ) {
            Segmento S=((Segmento)I.next());
            if ( !S.getInRAM() ) {
                MemoriaRam.rimuovi(S);
                Azioni.add( new Azione(3, S, MemoriaRam.indiceDi(S) ));
                if ( S.getModifica()==true ) {                                                        
                    try { 
                          Inserisci( MemoriaSwap, S );
                          Azioni.add( new Azione(2, S ) );
                    }
                    catch ( MemoriaEsaurita SwapEsaurita ) {
                        Azioni.add( new Azione(-1,null) );
                        Errore=true;
                    }

                }
            }
        }

        // altrimenti tolgo anche gli altri
        if ( MemoriaRam.getSpazioMaggiore().getDimensione() < dimensione_parziale_inserimento && !Errore) {
            while ( MemoriaRam.getSpazioMaggiore().getDimensione() < dimensione_totale_inserimento && !Errore) {

                Azioni.add( new Azione(0,null) );

                FrameMemoria FrameRimosso=Rimuovi( MemoriaRam, null );
                Azioni.add( new Azione(3, FrameRimosso, MemoriaRam.indiceDi(FrameRimosso) ) );

                if ( FrameRimosso.getModifica()==true ) {                                                        
                    try { 
                          Inserisci( MemoriaSwap, FrameRimosso );
                          Azioni.add( new Azione(2, FrameRimosso ) );
                    }
                    catch ( MemoriaEsaurita SwapEsaurita ) {
                        Azioni.add( new Azione(-1,null) );
                        Errore=true;
                    }

                }

            }
        }
        
        /*Errore perchè non ho sufficiente spazio in swap*/
        if (Errore==true) return Azioni;
        
        // inserirco i segmenti
        I=ListaSegmenti.iterator();
                
        while( I.hasNext()  ) {
            
            FrameMemoria F=I.next();
            ((Segmento)F).setTempoInRAM(UT);
            
            if ( !MemoriaRam.cerca(F) ) {
                
                FrameMemoria Temp=Rimuovi( MemoriaSwap, F );
                if (Temp!=null) Azioni.add( new Azione(4, Temp) );
                               
                try {
                    int posizione=Inserisci( MemoriaRam, F );
                    Azioni.add( new Azione(1, F, posizione ) ); 

                }
                catch ( MemoriaEsaurita Impossibile ) { /* Situazione mai realizzabile 
                 perche ho gia liberato memoria a sufficienza */ }
                   
            }
            else Azioni.add( new Azione(5, F, MemoriaRam.indiceDi(F) ) );
        }
        return Azioni;
    }
    
}
