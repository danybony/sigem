/*
 * Azienda: Stylosoft
 * Nome file: ViewFrameMemoria.java
 * Package: gui.dialog
 * Autore: Alberto Zatton
 * Data: 15/03/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: 
 *
 *  - v.1.0 (15/03/2008): Impostazione dello scheletro della classe
 */

package gui.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import logic.gestioneMemoria.Azione;
import logic.gestioneMemoria.FrameMemoria;
import logic.simulazione.Istante;

/**
 * Classe che permette la visualizzazione testuale dell'avanzamento della simulazione
 * 
 * @author Alberto Zatton
 */
public class ViewAvanzamentoTestuale extends JScrollPane{
    
    /**
     * JTextArea dove si scriver� il log visualizzato a schermo
     */
    private JTextArea testo=new JTextArea(50, 1000);
    
    /**
     * Numero di istanti totali della simulazione
     */
    private final int istantiTotali;
    
    /**
     * Tipo di gestione della memoria<br>
     * FALSE: paginazione<br>
     * TRUE: segmentazione<br>
     * Default: paginazione
     */
    private boolean tipoGestioneMemoria=false;
    
    /**
     * Array che mantiene traccia di quanto accade nel log. Utile in caso la
     * simulazione torni indietro
     */
    private Vector<String> avanzamentoTestuale= new Vector<String>();
    
    /**
     * Costruttore della classe.
     */
    public ViewAvanzamentoTestuale(int istantiTotali, boolean tipoGestioneMemoria){
        super();
        this.istantiTotali=istantiTotali;
        this.tipoGestioneMemoria=tipoGestioneMemoria;
        testo.setPreferredSize(new Dimension(50, 1000));
        setViewportView(testo);
    }
    

    /**
     * Metodo che aggiorna la tabella ad ogni istante
     */
    public void aggiorna(Istante istante, int idIstanteCorrente, int idProcessoCorrente){
        if(idIstanteCorrente>avanzamentoTestuale.size()-1){
            String aux="";
            Vector<FrameMemoria>frameAuxRAM=new Vector<FrameMemoria>();
            Vector<FrameMemoria>frameAuxSwap=new Vector<FrameMemoria>();
            Vector<FrameMemoria>frameToltiRAM=new Vector<FrameMemoria>();
            Vector<FrameMemoria>frameToltiSwap=new Vector<FrameMemoria>();
            Vector<FrameMemoria>memoria=new Vector<FrameMemoria>();
            aux+="La simulazione � passata all'istante "+idIstanteCorrente+" su un totale di "+istantiTotali+"\n";
            aux+="E' stato schedulato per l'esecuzione il processo "+idProcessoCorrente+"\n" ;
        
            //Scorro la memoria alla ricerca di frame memoria del processo in esecuzione
            for(int i=0;i<memoria.size();i++){
                if(memoria.get(i).getIdProcesso()==idProcessoCorrente) frameAuxRAM.add(memoria.get(i));
            }
            
            //Se il processo non ha richiesto nessun frame memoria, lo scrivo
            if(frameAuxRAM.isEmpty()) aux+="Il processo in questo istante non ha richiesto l'utilizzo della memoria\n";
            
            //Il processo richiede frame memoria, passo quindi ad elencarli
                aux+="Il processo in questo istante richiede ";
        
                if(tipoGestioneMemoria){
                    aux+="i segmenti numero ";
                }
                else aux+="le pagine di indirizzo ";
                
                for(int i=0; i<frameAuxRAM.size(); i++){
                    aux+=frameAuxRAM.get(i).getIndirizzo();
                    if(i<frameAuxRAM.size()-1){
                        aux+=", ";
                    }
                    else aux+="\n";
                }
            
            
            //Considero ora i cambiamenti avvenuti in RAM e in Swap
            //Prima di tutto, se un processo ha finito la sua esecuzione:
            if(istante.getProcessoPrecedenteTerminato()!=null)
                aux+="Il processo "+istante.getProcessoPrecedenteTerminato().getRifProcesso().getId()+" ha ultimato la sua escuzione. La memoria da esso occupata � stata liberata.";
                
            //Scorro la lista di azioni e le divido a seconda del tipo
            frameAuxRAM.clear();
            LinkedList<Azione> azioni=new LinkedList<Azione>();
            for(int i=0; i<azioni.size(); i++){
                switch(azioni.get(i).getAzione()){
                    case 1: frameAuxRAM.add(azioni.get(i).getFrame());
                            break;
                            
                    case 2: frameAuxSwap.add(azioni.get(i).getFrame());
                            break;
                            
                    case 3: frameToltiRAM.add(azioni.get(i).getFrame());
                            break;
                            
                    case 4: frameToltiSwap.add(azioni.get(i).getFrame());
                            break;
                }
            }
            //Se nn ci sono stati cambiamenti, lo scrivo
            
            testo.append(aux);
            avanzamentoTestuale.add(idIstanteCorrente, aux);
        }
        else{
            testo.append("La simulazione � tornata all'istante "+idIstanteCorrente+"\n\n");
            testo.append(avanzamentoTestuale.get(idIstanteCorrente));
        }
    }
    

}
