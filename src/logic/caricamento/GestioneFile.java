package logic.caricamento;
 
/*
 * Azienda: Stylosoft
 * Nome file: GestioneFile.java
 * Package: logic.caricamento
 * Autore: Luca Rubin
 * Data: 03/03/2008
 * Versione: 1.2
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.2 (03/03/2008): Implementati i metodi
 *  - v.1.1 (26/02/2008): Aggiunta la documentazione JavaDoc.
 *  - v.1.0 (26/02/2008): Impostazione base della classe
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import logic.parametri.ConfigurazioneIniziale; 

/**
 * Classe che si interfaccia con i file per la gestione di SiGeM.
 */
public class GestioneFile {

    /**
     * Percorso in cui salvare la configurazione iniziale corrente.
     */
    private String percosoFileConfigurazione;
    
    /**
     * La configurazione iniziale oggetto di salvataggi e caricamenti.
     */
    private ConfigurazioneIniziale configurazione;
    
    /**
     * Costruttore della classe.
     * 
     * @param percorsoFileConfigurazione
     *      il percorso in cui verra' salvato/caricato il file di configurazione
     * @param conf
     *      la configurazione iniziale da salvare/caricare
     */
    public GestioneFile(String percorsoFileConfigurazione,
                        ConfigurazioneIniziale conf){
        
        this.percosoFileConfigurazione=percorsoFileConfigurazione;
        this.configurazione=conf;
    }
    
    /**
     * Ritorna il percorso in cui salvare/caricare il file di configurazione.
     */
    public String getPercorsoFileConfigurazione () {
        return this.percosoFileConfigurazione;
    }

    /**
     * Imposta la directory in cui salvare/caricare il file di configurazione.
     */
    public void setPercorsoFileConfigurazione (String nuovoPercorso) {
        this.percosoFileConfigurazione=nuovoPercorso;
    }

    /**
     * Salva la configurazione iniziale su file.
     * 
     * @param conf
     *      la configurazione da salvare su file
     * 
     * @return ritorna true se l'operazione di salvataggio è andata a buon fine
     * 
     * @throws IOException
     *      se il salvataggio su file fallisce
     */
    public boolean salvaFileConfigurazione (ConfigurazioneIniziale conf) 
                                                            throws IOException {
        this.configurazione = conf;
        if(this.percosoFileConfigurazione==null) return false;
        ObjectOutputStream out = new ObjectOutputStream(
                                    new FileOutputStream(
                                    this.percosoFileConfigurazione)
                                    );
        out.writeObject(this.configurazione);
        return true;
    }

    /**
     * Carica la configurazione iniziale da file.
     * 
     * @return la configurazione iniziale caricata da file
     * 
     * @throws IOException
     *      se il caricamento da file fallisce
     * @throws ClassNotFoundException
     *      se la classe ConfigurazioneIniziale non viene trovata
     */
    public ConfigurazioneIniziale caricaFileConfigurazione () 
                                    throws IOException, ClassNotFoundException {
       
        if(this.percosoFileConfigurazione==null) return null;
        ObjectInputStream in = new ObjectInputStream(
                                new FileInputStream(
                                this.percosoFileConfigurazione)
                                );
        
        this.configurazione = (ConfigurazioneIniziale)in.readObject();
        return this.configurazione;
    }
}
