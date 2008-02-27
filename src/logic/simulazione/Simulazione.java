
package logic.simulazione;

import java.util.LinkedList;
import logic.Processore;
import logic.parametri.ConfigurazioneIniziale;

public class Simulazione{
    
    private Processore proc;
    private LinkedList<Istante> listaIstanti;
    private ConfigurazioneIniziale conf;
    
    public Simulazione(ConfigurazioneIniziale conf){
        proc = new Processore(conf);
        this.conf=conf;
    }
    
    public LinkedList<Istante> crea(){
        listaIstanti = proc.creaSimulazione();
        return listaIstanti;
    }
    
    public int numeroIstanti(){
        return listaIstanti.size();
    }
}

