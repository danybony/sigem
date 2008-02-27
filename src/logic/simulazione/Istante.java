
package logic.simulazione;

import java.util.LinkedList;


class Istante {
    
    private PCB processoInEsecuzione;
    private PCB processoPrecedenteTerminato;
    private int numeroFault;
    private LinkedList<OperazioneInMemoria> cambiamentiInMemoria;
    
    
    Istante(PCB inEsecuzione, PCB terminato, int fault, LinkedList<OperazioneInMemoria> memoria){
        this.processoInEsecuzione = inEsecuzione;
        this.processoPrecedenteTerminato = terminato;
        this.numeroFault = fault;
        this.cambiamentiInMemoria = memoria;
    }
}
