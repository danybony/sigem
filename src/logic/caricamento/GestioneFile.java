package logic.caricamento;
 
import logic.parametri.ConfigurazioneIniziale; 


public class GestioneFile {


    private String percosoFileConfigurazione;


    private String percosoFileLog;


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

