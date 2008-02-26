package logic.test;

/*
 * Azienda: Stylosoft
 * Nome file: TestScheduler.java
 * Package: logic.test
 * Autore: Daniele Bonaldo
 * Data: 26/02/2008
 * Versione: 1.00
 * Licenza: open-source
 * Registro delle modifiche:
 * v.1.00 (03/02/2006): Creazione della classe.
 */

import java.util.LinkedList;
import logic.parametri.Processo;
import logic.schedulazione.*;

/**
 * Classe che implementa dei test sullo scheduler utilizzando una versione incompleta
 * del processore come Stub.
 * I risultati della schedulazione potranno venire poi confrontati con quelli 
 * di un sistema SGPEMv2 per appurarne la correttezza.
 * 
 * @author Daniele Bonaldo
 * @version 1.00
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
       
       //quello che segue simula la creaSimulazione() del processore
       scheduler.esegui();
        while(!scheduler.fineSimulazione()){
            PCB tmp = scheduler.getPCBCorrente();
            System.out.print(tmp.getRifProcesso().getNome() + " - ");
            scheduler.esegui();
        }
       
    }
   
}
