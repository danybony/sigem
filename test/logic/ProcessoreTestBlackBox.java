package logic;

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
import logic.gestioneMemoria.Pagina;
import logic.parametri.ConfigurazioneIniziale;
import logic.parametri.EccezioneConfigurazioneNonValida;
import logic.parametri.Processo;
import logic.simulazione.Istante;

/**
 * Classe che implementa dei test sullo scheduler utilizzando una versione parziale
 * del processore come Stub.
 * I risultati della schedulazione potranno venire poi confrontati con quelli 
 * di un sistema SGPEMv2 per appurarne la correttezza.
 * 
 * @author Daniele Bonaldo
 * @version 1.00
 */
public class ProcessoreTestBlackBox {
    
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        
        /* creo i processi */
        Processo processo1 = new Processo("P1", 0, 3);
        Processo processo2 = new Processo("P2", 0, 5);
        Processo processo3 = new Processo("P3", 3, 3);

        /* Creo le pagine */
        Pagina p1 = new Pagina("1", 2, 0);
        Pagina p2 = new Pagina("2", 2, 0);
        Pagina p3 = new Pagina("3", 2, 0);
        Pagina p4 = new Pagina("4", 2, 0);
        Pagina p5 = new Pagina("5", 2, 0);
        Pagina p6 = new Pagina("6", 2, 0);
        Pagina p7 = new Pagina("7", 2, 0);
        Pagina p8 = new Pagina("8", 2, 0);

        /* Creo le associazioni */
        processo1.richiestaFrameMemoria(p1, 0);
        processo1.richiestaFrameMemoria(p2, 0);
        processo1.richiestaFrameMemoria(p3, 0);
        processo1.richiestaFrameMemoria(p1, 1);
        processo1.richiestaFrameMemoria(p1, 2);
        processo1.richiestaFrameMemoria(p1, 3);
        processo2.richiestaFrameMemoria(p5, 0);
        LinkedList processi = new LinkedList();
        processi.add(processo1);
        processi.add(processo2);
        processi.add(processo3);
        ConfigurazioneIniziale conf = null;
        try {
            conf = new ConfigurazioneIniziale(1, 2, 1, 4, 4, 4, 1, 1, 1, processi);
        } catch (EccezioneConfigurazioneNonValida ex){};
        Processore processore = new Processore(conf);
        LinkedList<Istante> sim = processore.creaSimulazione();    
        for(int i=0;i<sim.size();i++){
            if(sim.get(i).getProcessoInEsecuzione() != null){
                System.out.print(sim.get(i).getProcessoInEsecuzione().getRifProcesso().getNome()+ " - ");
            }
            else{
                System.out.print(" _ - ");
            }
        }
        
       
    }
   
}
