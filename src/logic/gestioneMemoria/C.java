/*
 * Azienda: Stylosoft
 * Nome file: C.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.Vector;

/**
 * Classe pubblica concreta che implementa l'interfaccia IRimpiazzo. Essa
 * prende il nome di C per Clock cioè implementa l'algoritmo dell'orologio
 * @author Davide Compagnin
 */
public class C implements IRimpiazzo {
    /**
     * Campo Lancetta che mantiene la posizione
     */
    private int Lancetta=0;
    /**
     * Classe interna privata che memorizza un boolean R che indica se recentemente 
     * la pagina è stata riferita e il riferimento F alla pagina in questione.
     */
    private class Dati {
      private boolean R;
      private FrameMemoria F=null;
    }
    /**
     * Vettore privato che memorizza una sequenza di Dati come immagine
     * della memoria.
     */
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    /**
     * Costruttore 
     * @param dim
     *  Dimensione della memoria
     */
    public C( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    /**
     * Viene richiamato quando carico in memoria RAM una pagina. 
     * Inizializza i dati relativi a quella pagina, in particolare F è il 
     * riferiferimento alla pagina e 
     * imposta R a true, mentre gli altri parametri sono inutilizzati.
     * @param F
     *  Frame da Inserire
     */
    public void InserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).F=F;
    }
    /**
     * Resetta i campi dati relativi alla pagina nella Posizione specificata dal
     * parametro.
     * @param Posizione
     *  Posizione da rimuovere
     */
    public void LiberaEntry( int Posizione ) { 
        Tabella.elementAt(Posizione).R=false;
        Tabella.elementAt(Posizione).F=null;
    }
    /**
     * Implementa l'algoritmo vero e proprio. Semplicemente restituisce un riferimento 
     * alla pagina indicata dalla lancetta se essa non è stata recentemente riferita 
     * altrimenti imposta R a false e avanza la lancetta di una posizione per 
     * ricompiere il controllo.
     * @return
     *   Ritorna il Frame da rimpiazzare
     */
    public FrameMemoria SelezionaEntry() {
        boolean trovata=false;
        int dim=Tabella.size();        
        while( !trovata ) {
            if ( Tabella.elementAt(Lancetta).R==true ) {
                Tabella.elementAt(Lancetta).R=false;
                Lancetta=(Lancetta+1)%dim;
            }
            else trovata=true;
        }
        int temp=Lancetta;
        Lancetta=(Lancetta+1)%dim;
        return Tabella.elementAt(temp).F;
    }
    /**
     * NULLA
     * @param Posizione
     * @param M
     */
    public void AggiornaEntry( int Posizione, boolean M ) {}
    /**
     * NULLA
     */
    public void AggiornaEntries( ) {}
}
