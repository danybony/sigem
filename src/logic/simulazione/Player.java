
package logic.simulazione;

import java.util.LinkedList;
import java.util.ListIterator;
import logic.parametri.ConfigurazioneIniziale;

public class Player{

    private Simulazione simulazioneEseguita;
    private LinkedList<Istante> listaIstanti;
    private ConfigurazioneIniziale conf;
    private ListIterator<Istante> istanteCorrente;
    
    public Player(ConfigurazioneIniziale conf){
        simulazioneEseguita = Simulazione(conf);
        listaIstanti = null;
        this.conf = conf;
    }
    
    public boolean caricaSimulazione(){
        listaIstanti = simulazioneEseguita.crea();
        istanteCorrente=listaIstanti.listIterator();
        return true;
    }
    
    public Istante istantePrecedente(){
        if(istanteCorrente.hasPrevious())
            return istanteCorrente.previous();
        else
            return null;
    }
    
    public Istante istanteSuccessivo(){
        if(istanteCorrente.hasNext())
            return istanteCorrente.next();
        else
            return null;
    }
    
    public LinkedList<Istante> precedenteIstanteSignificativo(int tipoEventoSignificativo){
        LinkedList<Istante> listaIstantiDaRitornare = LinkedList();
        boolean trovato = false;
        while(istanteCorrente.hasPrevious() && !trovato){
            
        }
    }
    
    public LinkedList<Istante> prossimoIstanteSignificativo(int tipoEventoSignificativo){
    
    }
    

}