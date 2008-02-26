
package logic.gestioneMemoria;


public class Segmento implements FrameMemoria{
    
    private boolean solaLettura = false;
    private int dimensione;
    private final String INDIRIZZO;
    private boolean inRAM;
    private int idProcesso;
    
    public Segmento(String indirizzo, int dimensione){
        this.INDIRIZZO=indirizzo;
        this.dimensione=dimensione;
    }
    
    public boolean getSolaLettura(){
        return solaLettura;
    }

    public boolean setSolaLettura(boolean nuovoStato){
        solaLettura=nuovoStato;
        return true;
    }
    
    public int getDimensione(){
        return dimensione;
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
    
    public int getIdProcesso(){
        return idProcesso;
    }
    
    public boolean setIdProcesso(int idProcessoPassato){
        idProcesso=idProcessoPassato;
        return true;
    }
    
    
}