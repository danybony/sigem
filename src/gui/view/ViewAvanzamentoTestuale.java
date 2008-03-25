/*
 * Azienda: Stylosoft
 * Nome file: ViewFrameMemoria.java
 * Package: gui.dialog
 * Autore: Alberto Zatton
 * Data: 15/03/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche: 
 *  - v.1.1 (22/03/2008): Prima realizzazione completa del metodo principale
 *                        aggiorna()
 *  - v.1.0 (15/03/2008): Impostazione dello scheletro della classe
 */

package gui.view;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
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
     * JTextArea dove si scriverà il log visualizzato a schermo
     */
    private JTextArea testo;
    
    /**
     * Numero di istanti totali della simulazione
     */
    private int istantiTotali;
    
    /**
     * Tipo di gestione della memoria<br>
     * 1: paginazione<br>
     * 2: segmentazione<br>
     * Default: paginazione
     */
    private int tipoGestioneMemoria=1;
    
    /**
     * Array che mantiene traccia di quanto accade nel log. Utile in caso la
     * simulazione torni indietro
     */
    private Vector<String> avanzamentoTestuale= new Vector<String>();
    
    /**
     * Costruttore della classe.
     */
    public ViewAvanzamentoTestuale(){
        super();
    }
    

    /**
     * Metodo che aggiorna la tabella ad ogni istante
     */
    public void aggiorna(Istante istante, int idIstanteCorrente){
        
        if(idIstanteCorrente>avanzamentoTestuale.size()-1){
                String aux = new String("");
                int idProcessoCorrente = istante.getProcessoInEsecuzione().getRifProcesso().getId();
                Vector<FrameMemoria> frameAuxRAM = new Vector<FrameMemoria>();
                Vector<FrameMemoria> frameAuxSwap = new Vector<FrameMemoria>();
                Vector<FrameMemoria> frameToltiRAM = new Vector<FrameMemoria>();
                Vector<FrameMemoria> frameToltiSwap = new Vector<FrameMemoria>();
                Vector<FrameMemoria> memoria = istante.getStatoRAM();

                aux += "La simulazione è passata all'istante " + idIstanteCorrente + " su un totale di " + istantiTotali + "\n";
                aux += "E' stato schedulato per l'esecuzione il processo " + idProcessoCorrente + "\n";

                //Scorro la memoria alla ricerca di frame memoria del processo in esecuzione
                for (int i = 0; i < memoria.size(); i++) {
                    if (memoria.get(i).getIdProcesso() == idProcessoCorrente) {
                        frameAuxRAM.add(memoria.get(i));
                    }
                }

                //Se il processo non ha richiesto nessun frame memoria, lo scrivo
                if (frameAuxRAM.isEmpty()) {
                    aux += "Il processo in questo istante non ha richiesto l'utilizzo della memoria\n";
                }
                
                aux += "Il processo in questo istante richiede ";

                if (tipoGestioneMemoria==2) {
                    aux += "i segmenti numero ";
                } else {
                    aux += "le pagine di indirizzo ";
                }
                for (int i = 0; i < frameAuxRAM.size(); i++) {
                    aux += frameAuxRAM.get(i).getIndirizzo();
                    if (i < frameAuxRAM.size() - 1) {
                        aux += ", ";
                    } else {
                        aux += "\n";
                    }
                }


                //Considero ora i cambiamenti avvenuti in RAM e in Swap
                //Prima di tutto, se un processo ha finito la sua esecuzione:
                if (istante.getProcessoPrecedenteTerminato() != null) {
                    aux += "Il processo " + istante.getProcessoPrecedenteTerminato().getRifProcesso().getId() + " ha ultimato la sua escuzione. La memoria da esso occupata è stata liberata.";
                }
                //Scorro la lista di azioni e le divido a seconda del tipo
                frameAuxRAM.clear();
                LinkedList<Azione> azioni = istante.getCambiamentiInMemoria();
                for (int i = 0; i < azioni.size(); i++) {
                    
                            
                    switch (azioni.get(i).getAzione()) {
                        case 1:
                            frameAuxRAM.add(azioni.get(i).getFrame());
                            break;
                        case 2:
                            frameAuxSwap.add(azioni.get(i).getFrame());
                            break;
                        case 3:
                            frameToltiRAM.add(azioni.get(i).getFrame());
                            break;
                        case 4:
                            frameToltiSwap.add(azioni.get(i).getFrame());
                            break;
                    }
                }
                //Se nn ci sono stati cambiamenti, lo scrivo
                if (frameAuxRAM.isEmpty() && frameAuxSwap.isEmpty()) {
                    aux += "Non ci sono stati ulteriori cambiamenti in memoria\n";
                }
                //Se invece ci sono stati cambiamenti, elenco le varie modifiche
                //Parto elencando i frame tolti dalla RAM
                if (!frameToltiRAM.isEmpty()) {
                    if (tipoGestioneMemoria==2) {
                        aux += "Sono stati rimossi dalla  RAM i segmenti numero ";
                    } else {
                        aux += "Sono state rimosse dalla RAM le pagine di indirizzo ";
                    }
                    for (int i = 0; i < frameToltiRAM.size(); i++) {
                        aux += frameToltiRAM.get(i).getIndirizzo();
                        if (i < frameToltiRAM.size() - 1) {
                            aux += ", ";
                        } else {
                            aux += "\n";
                        }
                    }
                }

                //Elenco ora gli eventuali frame aggiunti in RAM
                if (!frameAuxRAM.isEmpty()) {
                    if (tipoGestioneMemoria==2) {
                        aux += "Sono stati aggiunti alla  RAM i segmenti numero ";
                    } else {
                        aux += "Sono state aggiunte alla RAM le pagine di indirizzo ";
                    }
                    for (int i = 0; i < frameAuxRAM.size(); i++) {
                        aux += frameAuxRAM.get(i).getIndirizzo();
                        if (i < frameAuxRAM.size() - 1) {
                            aux += ", ";
                        } else {
                            aux += "\n";
                        }
                    }
                }

                //Elenco degli eventuali frame tolti dallo Swap
                if (!frameToltiSwap.isEmpty()) {
                    if (tipoGestioneMemoria==2) {
                        aux += "Sono stati rimossi dallo Swap i segmenti numero ";
                    } else {
                        aux += "Sono state rimosse dallo Swap le pagine di indirizzo ";
                    }
                    for (int i = 0; i < frameToltiSwap.size(); i++) {
                        aux += frameToltiSwap.get(i).getIndirizzo();
                        if (i < frameToltiSwap.size() - 1) {
                            aux += ", ";
                        } else {
                            aux += "\n";
                        }
                    }
                }

                //Elenco degli eventuali frame aggiunti allo Swap
                if (!frameAuxSwap.isEmpty()) {
                    if (tipoGestioneMemoria==2) {
                        aux += "Sono stati aggiunti allo Swap i segmenti numero ";
                    } else {
                        aux += "Sono state aggiunte allo Swap le pagine di indirizzo ";
                    }
                    for (int i = 0; i < frameAuxSwap.size(); i++) {
                        aux += frameAuxSwap.get(i).getIndirizzo();
                        if (i < frameAuxSwap.size() - 1) {
                            aux += ", ";
                        } else {
                            aux += "\n";
                        }
                    }
                }
                aux += "\n";
                testo.append(aux);
                avanzamentoTestuale.add(idIstanteCorrente, aux);
            
        }
        else{
            testo.append("La simulazione e' tornata all'istante "+idIstanteCorrente+"\n\n");
            testo.append(avanzamentoTestuale.get(idIstanteCorrente));
        }
    }
    
    public void configura(int istantiTotali, int tipoGestioneMemoria){
        
        testo=new JTextArea();
        this.istantiTotali=istantiTotali;
        this.tipoGestioneMemoria=tipoGestioneMemoria;
        testo.setEditable(false);
        setViewportView(testo);
        //Inizializzo il primo elemento del Vector di avanzamento testuale
        avanzamentoTestuale.add("");
    }
    

}
