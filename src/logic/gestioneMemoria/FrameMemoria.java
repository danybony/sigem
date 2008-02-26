package src.logic.gestioneMemoria;


public interface FrameMemoria {

    public String getIndirizzo ();

    public int getDimensione ();

    public boolean getInRAM ();

    public boolean setInRAM (boolean nuovoStato);

    public boolean getSolaLettura ();

    public boolean setSolaLettura (boolean nuovoStato);

}

