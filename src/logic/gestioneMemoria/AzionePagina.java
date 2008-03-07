/*
 * Azienda: Stylosoft
 * Nome file: AzionePagina.java
 * Package: logic.gestioneMemoria
 * Autore: Davide Compagnin
 * Data: 30/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche:
 *  
 *  - v.1.0 (30/02/2008): Impostazione base della classe in seguito ad estensione da classe astratta
 */
package logic.gestioneMemoria;

/**
 *
 * @author Davide Compagnin
 * Classe pubblica, concreta. Questa classe eredita da Azione generica 	
 * effettuata in memoria e ne specializza l'uso per una memoria paginata.
 */
public class AzionePagina extends Azione {
    
    /* -1    SWAP PIENA
     *  0    RAMP IENA
     *  1    INSERT RAM
     *  2    INSERT SWAP
     *  3    REMOVE RAM
     *  4    REMOVE SWAP
     *  5    LEGGI
     */
    /**
     * Posizione di inserimento in memoria
     */
    private int Pos;
    /**
     * Costruttore a tre parametri
     * @param T
     *   Tipo di Azione
     * @param F
     *   Frame coinvolto
     * @param P
     *   Posizione
     */
    public AzionePagina( int T, FrameMemoria F, int P ) {
        super(T,F); Pos=P;
    }
    /**
     * Costruttore a due parametri
     * @param T
     *   Tipo di Azione
     * @param F
     *   Frame coinvolto
     * 
     */
    public AzionePagina( int T, FrameMemoria F) {
        super(T,F); Pos=-1;
    }
    /**
     * Ritorna la posizione
     * @return
     *   Posizione
     */
    public int getPosizione() { return Pos; }
    
}
