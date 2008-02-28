
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
        simulazioneEseguita = new Simulazione(conf);
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
        LinkedList<Istante> listaIstantiDaRitornare = new LinkedList();
        Istante nuovoIstante = null;
        boolean trovato = false;
        while(istanteCorrente.hasPrevious() && !trovato){
            nuovoIstante = istanteCorrente.previous();
            listaIstantiDaRitornare.add(nuovoIstante);
            if(nuovoIstante==evento) trovato=true;
        }
        if(trovato)
            return listaIstantiDaRitornare;
        else
            return null;
    }
    
    public LinkedList<Istante> prossimoIstanteSignificativo(int tipoEventoSignificativo){
        LinkedList<Istante> listaIstantiDaRitornare = new LinkedList();
        Istante nuovoIstante = null;
        boolean trovato = false;
        while(istanteCorrente.hasNext() && !trovato){
            nuovoIstante = istanteCorrente.next();
            listaIstantiDaRitornare.add(nuovoIstante);
            if(nuovoIstante==evento) trovato=true;
        }
        if(trovato)
            return listaIstantiDaRitornare;
        else
            return null;
    }
    
    public Istante primoIstante(){
        while(istanteCorrente.hasPrevious()){
            istanteCorrente.previous();
        }
        return istanteCorrente.next();
        
    }
    
    public LinkedList<Istante> ultimoIstante(){
        LinkedList<Istante> listaAllaFine = new LinkedList<Istante>();
        while(istanteCorrente.hasNext()){
            listaAllaFine.add(istanteCorrente.next());
        }
        return listaAllaFine;
    }

}