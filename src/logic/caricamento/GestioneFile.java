package logic.caricamento;
 
/*
 * Azienda: Stylosoft
 * Nome file: GestioneFile.java
 * Package: logic.caricamento
 * Autore: Luca Rubin
 * Data: 29/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.1 (26/02/2008): Aggiunta la documentazione JavaDoc.
 *  - v.1.0 (26/02/2008): Impostazione base della classe
 */

import logic.parametri.ConfigurazioneIniziale; 

/**
 * Classe che si interfaccia con i file per la gestione di SiGeM.
 */
public class GestioneFile {


    private String percosoFileConfigurazione;

    private ConfigurazioneIniziale mConfigurazioneIniziale;

    
    public String getPercorsoFileConfigurazione () {
        return null;
    }


    public void setPercorsoFileConfigurazione (String val) {
        
    }


    public String getPercorsoFileLog () {
        return null;
    }


    public void setPercorsoFileLog (String val) {
    }


    public boolean salvaFileConfigurazione (ConfigurazioneIniziale val) {
        return true;
    }

 
    public boolean caricaFileConfigurazione () {
        return true;
    }


    public boolean aggiungiAlLog (String val) {
        return true;
    }

}

