
package logic.simulazione;

/*
 * Azienda: Stylosoft
 * Nome file: Simulazione.java
 * Package: logic.simulazione
 * Autore: Luca Rubin
 * Data: 07/03/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.3 (07/03/2008): Correzione errori rilevati in fase di test
 *  - v.1.2 (29/02/2008): Completata la documentazione
 *  - v.1.1 (26/02/2008): Aggiunta la documentazione JavaDoc.
 *  - v.1.0 (26/02/2008): Impostazione base della classe
 */

import java.util.LinkedList;
import logic.Processore;
import logic.parametri.ConfigurazioneIniziale;

/**
 * In questa classe viene effettivamente creata la simulazione.<br>
 * La simulazione puo' essere vista come una lista di istanti.<br>
 * Ogni istante racchiude le caratteristiche della simulazione di una
 * determinata unità di tempo. Per maggiori informazioni sugli istanti, vedere
 * la classe <a href="../simulazione/Istante.html">Istante</a>.<br>
 * La classe Simulazione istanzia al proprio interno un Processore il quale
 * raccolti i parametri di configurazione, procede con la generazione della
 * simulazione, generando per ogni unità di tempo un'istanza di Istante.
 */
public class Simulazione{
    
    /**
     * Istanza di processore che si occupa della generazione degli istanti
     */
    private Processore proc;
    
    /**
     * Lista di istanti generata dal processore, utilizzabile dal Player per
     * la visualizzazione della simulazione.
     */
    private LinkedList<Istante> listaIstanti;
    
    /**
     * Parametri di configurazione per il sistema di simulazione.
     */
    private ConfigurazioneIniziale conf;
    
    /**
     * Costruttore della classe.<br>
     * Esso si preoccupa di creare il processore con la configurazione passata
     * come parametro. 
     * 
     * @param conf
     *            la configurazione da utilizzare per la creazione della 
     *            processore e quindi della simulazione.
     */
    public Simulazione(ConfigurazioneIniziale conf){
        proc = new Processore(conf);
        this.conf=conf;
    }
    
    /**
     * Da il via alla creazione della simulazione: il processore può cominciare
     * ad interagire con lo scheduler e il gestore della memoria.
     * 
     * @return la lista degli istanti che compongono la simulazione
     */
    public LinkedList<Istante> crea(){
        listaIstanti = proc.creaSimulazione();
        return listaIstanti;
    }
    
    /**
     * Ritorna il numero degli istanti che compongono la simulazione.
     * Se la simulazione non è stata creata e quindi non ci sono istanti generati
     * ritorna zero.
     */
    public int numeroIstanti(){
        if(listaIstanti==null)
            return 0;
        return listaIstanti.size();
    }
}

