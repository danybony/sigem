
package logic.gestioneMemoria;

import logic.parametri.ConfigurazioneIniziale;

public class Pagina implements FrameMemoria{
    
    private boolean solaLettura = false;
    private boolean bloccata = false;
    private final String INDIRIZZO;
    private static int DIMENSIONE;
    private boolean inRAM = false;
    private int idProcesso;
    
    {
        DIMENSIONE = ConfigurazioneIniziale.getDimensionePagina();
    }
    
    public Pagina(String indirizzoPagina){
        INDIRIZZO=indirizzoPagina;
    }
    
    public boolean getSolaLettura(){
        return solaLettura;
    }
    
    public boolean setSolaLettura(boolean nuovoStato){
        solaLettura = nuovoStato;
        return true;
    }
    
    public boolean getBloccata(){
        return bloccata;
    }
    
    public boolean setBloccata(boolean nuovoStato){
        bloccata = nuovoStato;
        return true;
    }
    
    public String getIndirizzo(){
        return INDIRIZZO;
    }
    
    public boolean getInRAM(){
        return inRAM;
    }
    
    public boolean setInRAM(boolean nuovoStato){
        inRAM=nuovoStato;
        return true;
    }
    
    public int getDimensione(){
        return DIMENSIONE;
    }
    
    public int getIdProcesso(){
        return idProcesso;
    }
    
    public boolean setIdProcesso(int idProcessoPassato){
        idProcesso=idProcessoPassato;
        return true;
    }
}