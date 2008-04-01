
package logic.gestioneMemoria;

import java.io.Serializable;

/*
 * Azienda: Stylosoft
 * Nome file: Pagina.java
 * Package: logic.gestioneMemoria
 * Autore: Luca Rubin
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.3 (26/02/2008): Completata documentazione JavaDoc
 *  - v.1.2 (27/02/2008): Aggiunto parametro idProcesso al costruttore 
 *  - v.1.1 (26/02/2008): Aggiunta la documentazione JavaDoc.
 *  - v.1.0 (26/02/2008): Impostazione base della classe
 */

/**
 * Classe per la modellazione della memoria come pagine.
 */
public class Pagina implements FrameMemoria, Serializable{
    
    /**
     * Indica se la pagina e' di sola lettura.
     */
    private boolean solaLettura = false;
    
    /**
     * Indica se la pagina e' bloccata: non puo' essere tolta dalla RAM.
     */
    private boolean bloccata = false;
    
    /**
     * Indica se una pagina e' stata modificata.
     */
    private boolean modificata = false;
    
    /**
     * Indirizzo della pagina
     */
    private final String INDIRIZZO;
    
    /**
     * Dimensione della pagina
     */
    private static int DIMENSIONE;
    
    /**
     * Indica se la pagina e' in RAM
     */
    private boolean inRAM = false;
    
    /**
     * Indica chi detiene la pagina
     */
    private int idProcesso;
    
    /**
     * Costruttore della classe
     * 
     * @param indirizzoPagina
     *      indirizzo della pagina
     * @param dimensione
     *      dimensione della pagina
     * @param idProcesso
     *      id del processo che detiene la pagina
     */
    public Pagina(String indirizzoPagina,int dimensione,int idProcesso){
        INDIRIZZO=indirizzoPagina;
        this.idProcesso=idProcesso;
        this.DIMENSIONE=dimensione;
    }
    
    /**
     * Ritorna un booleano che indica se la pagina e' di sola lettura.
     */
    public boolean getSolaLettura(){
        return solaLettura;
    }
    
    /**
     * Imposta o rimuove lo stato di sola lettura.
     * 
     * @param nuovoStato
     *      true indica che la paggina e' di sola lettura; false che non lo e'
     */
    public boolean setSolaLettura(boolean nuovoStato){
        solaLettura = nuovoStato;
        return true;
    }
    
    /**
     * Ritorna un booleano che indica se la pagina e' bloccata: non puo' essere
     * rimossa dalla RAM.
     */
    public boolean getBloccata(){
        return bloccata;
    }
    
    /**
     * Imposta la pagina come bloccata o meno.
     * 
     * @param nuovoStato
     *      true se la pagina e' bloccata; false altrimenti
     */
    public boolean setBloccata(boolean nuovoStato){
        bloccata = nuovoStato;
        return true;
    }
    
    /**
     * Ritorna l'indirizzo della pagina
     */
    public String getIndirizzo(){
        return INDIRIZZO;
    }
    
    /**
     * Ritorna un booleano che indica se la pagina e' in RAM.
     */
    public boolean getInRAM(){
        return inRAM;
    }
    
    /**
     * Specifica se la pagina e' in RAM o meno.
     * 
     * @param nuovoStato
     *      true se e' in RAM; false altrimenti.
     */
    public boolean setInRAM(boolean nuovoStato){
        inRAM=nuovoStato;
        return true;
    }
    
    /**
     * Ritorna la dimensione della pagina.
     */
    public int getDimensione(){
        return DIMENSIONE;
    }
    
    /**
     * Ritorna un intero che corrisponde all'id del processo che la detiene.
     */
    public int getIdProcesso(){
        return idProcesso;
    }
    
    /**
     * Imposta l'id del processo che detiene la pagina.
     */
    public boolean setIdProcesso(int idProcessoPassato){
        idProcesso=idProcessoPassato;
        return true;
    }
    
    /**
     * Ritorna un booleano che indica se una pagina e' stata modificata o no.
     */
    public boolean getModifica(){
        return this.modificata;
    }
    
    /**
     * Imposta lo stato di modificato o meno su una pagina.
     * 
     * @param nuovoStato
     *      true se una pagina e' stata modificata; false altrimenti.
     */
    public boolean setModifica(boolean nuovoStato){
        this.modificata=nuovoStato;
        return true;
    }
    
    /**
     * Metodo che converte a stringa una pagina ritornandone l'indirizzo.
     * 
     * @return Ritorna l'indirizzo della pagina.
     */
    public String toString() {
            return this.INDIRIZZO;
    }
    
}
