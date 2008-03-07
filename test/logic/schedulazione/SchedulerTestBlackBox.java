package logic.schedulazione;

/*
 * Azienda: Stylosoft
 * Nome file: TestSchedulerBlackBox.java
 * Package: logic.schedulazione
 * Autore: Daniele Bonaldo
 * Data: 26/02/2008
 * Versione: 1.00
 * Licenza: open-source
 * Registro delle modifiche:
 * v.1.00 (29/02/2008): Aggiornamento in seguito a correzione dello scheduler.
 * v.1.00 (26/02/2008): Creazione della classe.
 */

import java.util.LinkedList;
import logic.parametri.Processo;

/**
 * Classe che implementa dei test sullo scheduler utilizzando una versione parziale
 * del processore come Stub.
 * I risultati della schedulazione potranno venire poi confrontati con quelli 
 * di un sistema SGPEMv2 per appurarne la correttezza.
 * 
 * @author Daniele Bonaldo
 * @version 1.00
 */
public class SchedulerTestBlackBox {
    
    public static void main(String[] args) {
       PoliticaOrdinamentoProcessi politica = new SRTN();
       Processo P1 = new Processo("P1",0,7);
       Processo P2 = new Processo("P2",3,2);
       Processo P3 = new Processo("P3",3,3);
       LinkedList processi = new LinkedList();
       processi.add(P1);
       processi.add(P2);
       processi.add(P3);
       Scheduler scheduler = new Scheduler(politica, processi);
       
       //quello che segue simula la creaSimulazione() del processore
        while(!scheduler.fineSimulazione()){
            boolean stop = scheduler.eseguiAttivazione();
            PCB tmp = scheduler.getPCBCorrente();
            if (tmp != null)
                System.out.print(tmp.getRifProcesso().getNome() + " - ");
            else
                if(!scheduler.fineSimulazione())
                System.out.print("_ - ");
            if (!stop)
                
                 scheduler.eseguiIncremento();
            
        }
       
    }
   
}
