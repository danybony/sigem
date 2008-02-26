

package logic.test;

import java.util.LinkedList;
import logic.Processore;
import logic.parametri.Processo;
import logic.schedulazione.*;

/**
 * Classe che implementa dei test sullo scheduler utilizzando una versione incompleta
 * del processore come Stub.
 * 
 * @author Daniele Bonaldo
 */
public class TestScheduler {
    
    public static void main(String[] args) {
       PoliticaOrdinamentoProcessi politica = new FCFS();
       Processo P1 = new Processo("P1",0,5);
       Processo P2 = new Processo("P2",2,5);
       LinkedList processi = new LinkedList();
       processi.add(P1);
       processi.add(P2);
       Scheduler scheduler = new Scheduler(politica, processi);
       Processore processore = new Processore(scheduler);
       processore.creaSimulazione();
    }
   
}
