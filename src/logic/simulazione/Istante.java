
package logic.simulazione;

import java.util.LinkedList;
import logic.gestioneMemoria.OperazioneInMemoria;
import logic.schedulazione.PCB;


/**
 * 
 * @author 
 */
public class Istante {
    
    private PCB processoInEsecuzione;
    private PCB processoPrecedenteTerminato;
    private int numeroFault;
    private LinkedList<OperazioneInMemoria> cambiamentiInMemoria;
    
    
    /**
     * 
     * @param inEsecuzione
     * @param terminato
     * @param fault
     * @param memoria
     */
    public Istante(PCB inEsecuzione, PCB terminato, int fault, LinkedList<OperazioneInMemoria> memoria){
        this.processoInEsecuzione = inEsecuzione;
        this.processoPrecedenteTerminato = terminato;
        this.numeroFault = fault;
        this.cambiamentiInMemoria = memoria;
    }
}
