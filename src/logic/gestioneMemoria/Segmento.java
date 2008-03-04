
package logic.gestioneMemoria;

/*
 * Azienda: Stylosoft
 * Nome file: Segmento.java
 * Package: logic.gestioneMemoria
 * Autore: Luca Rubin
 * Data: 29/02/2008
 * Versione: 1.2
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.2 (29/02/2008): Modificato costruttore aggiungendo parametro idProcesso;
 *                        completata documentazione JavaDoc
 *  - v.1.1 (26/02/2008): Aggiunta la documentazione JavaDoc.
 *  - v.1.0 (26/02/2008): Impostazione base della classe
 */


/**
 * Classe per la modellazione della memoria come segmenti.
 */
public class Segmento implements FrameMemoria{
    
    /**
     * Indica se il segmento e' di sola lettura (esempio: segmento per le
     * istruzione di un processo).
     */
    private boolean solaLettura = false;
    
    /**
     * Specifica se un segmento e' stato modificato o meno.
     */
    private boolean modificato = false;
    
    /**
     * Specifica la dimensione del segmento(e' variabile durante l'esecuzione).
     * <br> Viene espressa in KB.
     */
    private int dimensione;
    
    /**
     * Indirizzo del segmento.<br>
     * Rimane costante per tutta l'esecuzione.
     */
    private final String INDIRIZZO;
    
    /**
     * Specifica se il segmento e' in RAM.
     */
    private boolean inRAM;
    
    /**
     * Specifica l'ID del processo che detiene il segmento.
     */
    private int idProcesso;
    
    /**
     * Tempo trascorso da quando il segmento è in RAM
     */
    private int tempoInRAM = 0;
    
    /**
     * Costruttore della classe.
     * 
     * @param indirizzo
     *      indirizzo del segmento
     * @param dimensione
     *      dimensione del segmento
     * @param idProcesso
     *      id del processo di riferimento
     */
    public Segmento(String indirizzo, int dimensione, int idProcesso){
        this.INDIRIZZO=indirizzo;
        this.dimensione=dimensione;
        this.idProcesso=idProcesso;
    }
    
    /**
     * Ritorna un booleano che indica se il segmento e' di sola lettura.
     */
    public boolean getSolaLettura(){
        return solaLettura;
    }

    /**
     * Imposta il segmento in sola lettura o meno.
     * 
     * @param nuovoStato
     *      true indica che il segmento e' di sola lettura; false indica che in 
     *      esso si possono effettuare operazioni di scrittura.
     */
    public boolean setSolaLettura(boolean nuovoStato){
        solaLettura=nuovoStato;
        return true;
    }
    
    /**
     * Ritorna la dimensione del segmento espressa in KB.
     */
    public int getDimensione(){
        return dimensione;
    }
    
    /**
     * Ritorna l'indirizzo del segmento.
     */
    public String getIndirizzo(){
        return INDIRIZZO;
    }
    
    /**
     * Ritorna un booleano che indica se il segemnto e' in RAM.
     */
    public boolean getInRAM(){
        return inRAM;
    }
    
    /**
     * Definisce se un segmento e' in RAM.
     *
     * Se un segmento viene tolto dalla RAM, il suo tempo in RAM viene posto
     * automaticamnete a zero.
     * 
     * @param nuovoStato
     *      true se e' in RAM; false altrimenti.
     */
    public boolean setInRAM(boolean nuovoStato){
        inRAM=nuovoStato;
        if(nuovoStato==false)
            this.tempoInRAM=0;
        return true;
    }
    
    /**
     * Ritorna un intero che corrisponde all'id del processo che detiene il
     * segmento.
     */
    public int getIdProcesso(){
        return idProcesso;
    }
    
    /**
     * Imposta l'id del processo che detiene il segmento.
     */
    public boolean setIdProcesso(int idProcessoPassato){
        idProcesso=idProcessoPassato;
        return true;
    }
    
    /**
     * Ritorna un booleano che indica se un segmento e' stato modificato o no.
     */
    public boolean getModifica(){
        return this.modificato;
    }
    
    /**
     * Imposta lo stato di modificato o meno su un segmento.
     * 
     * @param nuovoStato
     *      true se un segmento e' stato modificato; false altrimenti.
     */
    public boolean setModifica(boolean nuovoStato){
        this.modificato=nuovoStato;
        return true;
    }
    
    /**
     * Ritorna il tempo trascorso da quando il segmento e' in RAM.
     */
    public int getTempoInRam(){
        return this.tempoInRAM;
    }
    
    /**
     * Imposta il tempo trascorso da quando il segmento e' in RAM.
     * 
     * @param nuovoTempo
     *      il tempo da quando il segmento e' in RAM
     * 
     * @return booleano che segnala se il nuovo tempo assegnato e' corretto
     */
    public boolean setTempoInRAM(int nuovoTempo){
        if(nuovoTempo>0){
            this.tempoInRAM=nuovoTempo;
            return true;
        }
        else return false;
    }
}