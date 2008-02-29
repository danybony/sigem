
package logic.simulazione;

import java.util.LinkedList;
import java.util.ListIterator;
import logic.parametri.ConfigurazioneIniziale;

/**
 * Classe per lo scorrimento di una simulazione.<br>
 * Potra' essere utilizzato dall'interfaccia grafica per visualizzare l'evolvere
 * della simulazione. L'avanzamento potra' avvenire avanti o indietro nel tempo.
 * L'avanzamento potra' essere fatto in unita' di tempo, o in base al verificarsi
 * di eventi.<br>
 * 
 * Sara' possibile avanzare o retrocedere rapidamente ad istanti della simulazione
 * in cui si sono verificati fault in memoria, context-switch, riempimenti della
 * memoria centrale RAM, riempimenti dell'area di swap, terminazione  di processi
 * e arrivo di processi.
 */
public class Player{
    
    /**
     * Oggetto simulazione, utilizzato per la creazione della simulazione, e
     * quindi, per la creazione degli istanti.<br>
     * La simulazione viene creata in base alla configurazione iniziale passata.
     */
    private Simulazione simulazioneEseguita;
    
    /**
     * Lista degli istanti generati dalla simulazione.
     */
    private LinkedList<Istante> listaIstanti;
    
    private int indiceElementoCorrente = 0;
    
    /**
     * Iteratore che punta all'elemento ultimo corrente restituito dal player.
     */
    private ListIterator<Istante> istanteCorrente;
    
    /**
     * Possibili eventi da ricercare nella simulazione
     */
    public enum Evento{FAULT, SWITCH, FULL_RAM, FULL_SWAP, END_PROC, NEW_PROC}
    
    /**
     * Costruttore della classe.<br>
     * Si occupa dell'istanziazione dell'oggetto simulazione, sulla base della
     * configurazione iniziale passata.
     * 
     * @param   conf
     *              configurazione iniziale per il sistema di simulazione
     */
    public Player(ConfigurazioneIniziale conf){
        simulazioneEseguita = new Simulazione(conf);
        listaIstanti = null;
    }
    
    /**
     * Si occupa di creare la simulazione, e di inizializzare l'iteratore interno
     * al player in modo che esso punti al primo istante della simulazione
     * generata.
     * 
     * @return  booleano che corrisponde all'successo o meno del caricamento
     */
    public boolean caricaSimulazione(){
        listaIstanti = simulazioneEseguita.crea();
        //Metto l'iteratore prima del primo elemento
        istanteCorrente=listaIstanti.listIterator();
        indiceElementoCorrente = 0;
        return true;
    }
    
    /**
     * Ritorna l'istante precente a quello attuale.<br>
     * Se viene ritornato un riferimento nullo significa che non ci sono istanti
     * precedenti a quello attuale, cio' significa che: o la simulazione e' vuota,
     * o siamo all'inizio della simulazione stessa (siamo cioe' all'istante zero).
     */
    public Istante istantePrecedente(){
        if(istanteCorrente.hasPrevious()){
            if(istanteCorrente.previousIndex()==indiceElementoCorrente){
                istanteCorrente.previous();
            }
        }
        if(istanteCorrente.hasPrevious()){
            indiceElementoCorrente--;
            return istanteCorrente.previous();
        }
        else
            return null;
    }
    
    /**
     * Ritorna l'istante successivo a quello attuale.<br>
     * Se viene ritornato un riferimento nullo significa che non ci sono istanti
     * successivi a quello attuale, cio' significa che: o la simulazione e' vuota,
     * o siamo alla fine della simulazione stessa.
     */
    public Istante istanteSuccessivo(){
        if(istanteCorrente.hasNext()){
            if(istanteCorrente.nextIndex()==indiceElementoCorrente){
                istanteCorrente.next();
            }
        }
        if(istanteCorrente.hasNext()){
            indiceElementoCorrente++;
            return istanteCorrente.next();
        }
        else
            return null;
    }
    
    /**
     * Ritorna il primo istante significativo che precede quello attuale.<br>
     * Il tipo dell'evento significativo da ricercare viene espresso dal
     * parametro.
     * Dato che un evento significativo, puo' distare piu' passi dallo stato
     * attuale, il tipo di ritorno e' una lista di Istante ordinati in modo che 
     * il primo istante della lista e' il piu' vicino allo stato attuale, e 
     * l'ultimo elemento e' l'istante significativo trovato.<br>
     * Se viene ritornato un riferimento nullo significa che non e' stato trovato
     * l'evento significativo.
     * 
     * @param tipoEventoSignificativo
     *              specifica il tipo di evento da ricercare
     * 
     * @return  lista di istanti che portano all'evento significativo
     */
    public LinkedList<Istante> precedenteIstanteSignificativo(Evento e, int arg0){
        LinkedList<Istante> listaIstantiDaRitornare = new LinkedList();
        Istante nuovoIstante = null;
        boolean trovato = false;
        while(istanteCorrente.hasPrevious() && !trovato){
            nuovoIstante = istanteCorrente.previous();
            listaIstantiDaRitornare.add(nuovoIstante);
            switch(e){
                case FAULT:
                    if(nuovoIstante.getFault()!=0)
                        trovato=true;
                    break;
                case SWITCH:
                    
                    break;
                case FULL_RAM:
                    
                    break;
                case FULL_SWAP:
                    
                    break;
                case END_PROC:
                    
                    break;
                /*case NEW_PROC:
                    
                    break;*/
                default:
                
                    break;
            }
                    
        }
        if(trovato)
            return listaIstantiDaRitornare;
        else
            return null;
    }
    
    /**
     * Ritorna il primo istante significativo che segue quello attuale.<br>
     * Il tipo dell'evento significativo da ricercare viene espresso dal
     * parametro.
     * Dato che un evento significativo, puo' distare piu' passi dallo stato
     * attuale, il tipo di ritorno e' una lista di Istante ordinati in modo che 
     * il primo istante della lista e' il piu' vicino allo stato attuale, e 
     * l'ultimo elemento e' l'istante significativo trovato.<br>
     * Se viene ritornato un riferimento nullo significa che non e' stato trovato
     * l'evento significativo.
     * 
     * @param tipoEventoSignificativo
     *              specifica il tipo di evento da ricercare
     * 
     * @return  lista di istanti che portano all'evento significativo
     */
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
    
    /**
     * Ritorna il primo istante della simulazione.<br>
     * Se viene ritornato un riferimento nullo, significa che la simulazione
     * è vuota.
     */
    public Istante primoIstante(){
        while(istanteCorrente.hasPrevious()){
            istanteCorrente.previous();
        }
        if(istanteCorrente.hasNext())
            return istanteCorrente.next();
        return null;
        
    }
    
    /**
     * Ritorna la lista degli istanti che portano dall'istante attuale a quello
     * finale. Il ritorno di una lista vuota, sta a significare che siamo gia'
     * all'istante finale oppure la simulazione è vuota.
     */
    public LinkedList<Istante> ultimoIstante(){
        LinkedList<Istante> listaAllaFine = new LinkedList<Istante>();
        while(istanteCorrente.hasNext()){
            listaAllaFine.add(istanteCorrente.next());
        }
        return listaAllaFine;
    }
    
    /**
     * Ritorna il numero di istanti che copongono la simulazione.
     */
    public int numeroIstanti(){
        return this.listaIstanti.size();
    }

}