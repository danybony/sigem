/*
 * Azienda: Stylosoft
 * Nome file: A.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 29/02/2008
 * Versione: 1.2
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.2 (08/03/2008): Corretto bug sul calcolo del contatore
 *  - v.1.1 (02/03/2008): Definito il valore di incremento del contatore
 *  - v.1.0 (29/02/2008): Impostazione base della classe
 */

package logic.gestioneMemoria;

import java.util.Vector;

/**
 *
 * @author Davide Compagnin
 * Classe che implementa l'algoritmo di Anging
 */
public class A implements IRimpiazzo {
    /**
     * Classe interna privata che memorizza un intero Contatore che 
     * memorizza l'età di una pagina, un boolean R che indica se recentemente la 
     * pagina è stata riferita e il riferimento F alla pagina in questione
     */
    private class Dati {
      private int Contatore;
      private boolean R;
      private FrameMemoria F=null;
      private boolean Ultimo=false;
    }
    /**
     * Vettore privato che memorizza una sequenza di Dati come immagine della memoria.
     */
    private Vector<Dati> Tabella=new Vector<Dati>();
    
    /**
     * Costruttore che data la dimensione intera della memoria interpretata come il numero
     * di FrameMemoria, istanzia Tabella con valori di default.
     * @param dim 
     *   Dimensione della ram in numero di pageframe
     */
    public A( int dim ) {
        for( int i=0; i<dim; i++ )
            Tabella.add( new Dati() );
    }
    /**
     * Viene richiamato quando carico in memoria RAM una pagina. Inizializza i dati 
     * relativi a quella pagina, in particolare F è il riferiferimento alla pagina 
     * mentre gli altri parametri sono inutilizzati.
     * @param F 
     *   FrameMemoria da inserire
     */
    public void inserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M ) { 
        Tabella.elementAt(Posizione).Ultimo=true;
        Tabella.elementAt(Posizione).Contatore=Integer.MAX_VALUE/2;
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).F=F;
    }
    /**
     * Resetta i campi dati relativi alla pagina nella Posizione specificata dal
     * parametro
     * @param Posizione 
     *   Posizione da liberare
     */
    public void liberaEntry( int Posizione ) { 
        Tabella.elementAt(Posizione).Contatore=0;
        Tabella.elementAt(Posizione).R=false;
        Tabella.elementAt(Posizione).F=null;
        Tabella.elementAt(Posizione).Ultimo=false;
    }
    /**
     * Implementa l'algoritmo vero e proprio. Semplicemente restituisce un riferimento 
     * alla pagina il cui Contatore è il minore possibile, 
     * ossia la pagina memo utilizzata
     * @return 
     * Ritorna il riferimento alla Pagina da rimuovere
     */
    
    public FrameMemoria selezionaEntry() {
        boolean U=false; int pos=0;
        for(int i=0; i<Tabella.size() && !U; i++)
            if ( Tabella.elementAt(i).Ultimo==false ) { pos=i; U=true; }
        int C=Tabella.elementAt(pos).Contatore; 
        for (int i=pos+1; i<Tabella.size(); i++ ) {
            if ( Tabella.elementAt(i).Contatore<C && Tabella.elementAt(i).Ultimo==false ) {
                C=Tabella.elementAt(i).Contatore;
                pos=i;
            }
        }
        return Tabella.elementAt(pos).F;
    }
    /**
     * Imposta R a true quando una pagina la pagina nella posizione specificata 
     * è stata riferita. Il Parametro M è inutilizzato in questo algoritmo
     * @param Posizione 
     *   Posizione da liberare
     */
    public void aggiornaEntry( int Posizione, boolean M ) {
        Tabella.elementAt(Posizione).R=true;
        Tabella.elementAt(Posizione).Ultimo=true;
    }
    /**
     * Per ogni pagina, imposta il campo R a false e incrementa il contatore 	
     * se la pagina era recentemente riferita altrimenti lo decrementa.
     * 
     */
    public void aggiornaEntries( ) {
        for( int i=0; i<Tabella.size(); i++ )
            if ( Tabella.elementAt(i).R==true ) {
                Tabella.elementAt(i).R=false;
                Tabella.elementAt(i).Contatore/=2;
                Tabella.elementAt(i).Contatore+=Integer.MAX_VALUE/2;
            }
            else Tabella.elementAt(i).Contatore/=2;     
    }
    
    public void azzeraUltimo() {
        for( int i=0; i<Tabella.size(); i++ )
            Tabella.elementAt(i).Ultimo=false;
    }
}
