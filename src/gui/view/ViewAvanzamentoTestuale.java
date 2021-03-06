/*
 * Azienda: Stylosoft
 * Nome file: ViewFrameMemoria.java
 * Package: gui.dialog
 * Autore: Alberto Zatton
 * Data: 15/03/2008
 * Versione: 1.2
 * Licenza: open-source
 * Registro delle modifiche: 
 *  - v.1.2 (29/03/2008): Tolto il Vector che salvava le informazioni testuali
 *                        per ogni istante. Modificato di conseguenza il metodo
 *                        aggiorna(). Inseriti i commenti mancanti
 *  - v.1.1 (22/03/2008): Prima realizzazione completa del metodo principale
 *                        aggiorna()
 *  - v.1.0 (15/03/2008): Impostazione dello scheletro della classe
 */

package gui.view;
import java.util.GregorianCalendar;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
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
     * JTextArea dove si scrivera' il log visualizzato a schermo
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
     * Costruttore della classe.
     */
    public ViewAvanzamentoTestuale(){
        super();
    }
    

    /**
     * Metodo che aggiorna la tabella ad ogni istante
     */
    public void aggiorna(Istante istante, int idIstanteCorrente){
        
        if(idIstanteCorrente==0){

            testo.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            testo.append("~~~~~~~~~~~~~~~~~~~~~~~~ INIZIO DELLA SIMULAZIONE ~~~~~~~~~~~~~~~~~~~~~~~~\n");
            testo.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            return;
        }
        
        //Dichiarazione delle varibili locali
        String aux = new String("");
        int idProcessoCorrente=0;
        String ProcessoCorrente="";
        Vector<FrameMemoria> frameAuxRAM = new Vector<FrameMemoria>();
        Vector<FrameMemoria> frameAuxSwap = new Vector<FrameMemoria>();
        Vector<FrameMemoria> frameToltiRAM = new Vector<FrameMemoria>();
        Vector<FrameMemoria> frameToltiSwap = new Vector<FrameMemoria>();
        Vector<FrameMemoria> memoria = istante.getStatoRAM();
                
        //Per prima cosa, visualizzo a che istante e' arrivata la simulazione
        aux += GregorianCalendar.getInstance().getTime()+": La simulazione e' passata all'istante " + idIstanteCorrente + " su un totale di " + istantiTotali + "\n";
                        
        //Se e' stato schedulato il processo, continuo con le altre informazioni
        if (istante.getProcessoInEsecuzione()!=null){
                idProcessoCorrente = istante.getProcessoInEsecuzione().getRifProcesso().getId();
                ProcessoCorrente=istante.getProcessoInEsecuzione().getRifProcesso().getNome();
               
                aux += GregorianCalendar.getInstance().getTime()+": E' stato schedulato per l'esecuzione il processo " + ProcessoCorrente + "(id="+idProcessoCorrente + ")\n";

                //Scorro la memoria alla ricerca di frame memoria del processo in esecuzione
                for (int i = 0; i < memoria.size(); i++) {
                    if (memoria.get(i).getIdProcesso() == idProcessoCorrente) {
                        frameAuxRAM.add(memoria.get(i));
                    }
                }

                //Considero ora i cambiamenti avvenuti in RAM e in Swap
                //Prima di tutto, se un processo ha finito la sua esecuzione:
                if (istante.getProcessoPrecedenteTerminato() != null) {
                    aux += GregorianCalendar.getInstance().getTime()+": Il processo " + istante.getProcessoPrecedenteTerminato().getRifProcesso().getId() + " ha ultimato la sua escuzione. La memoria da esso occupata e' stata liberata.\n";
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
                    aux += GregorianCalendar.getInstance().getTime()+": Non ci sono stati ulteriori cambiamenti in memoria\n";
                }
                //Se invece ci sono stati cambiamenti, elenco le varie modifiche
                //Parto elencando i frame tolti dalla RAM
                if (!frameToltiRAM.isEmpty()) {
                    if (tipoGestioneMemoria==2) {
                        aux += GregorianCalendar.getInstance().getTime()+": Sono stati rimossi dalla  RAM i segmenti numero ";
                    } else {
                        aux += GregorianCalendar.getInstance().getTime()+": Sono state rimosse dalla RAM le pagine di indirizzo ";
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
                        aux += GregorianCalendar.getInstance().getTime()+": Sono stati aggiunti alla  RAM i segmenti numero ";
                    } else {
                        aux += GregorianCalendar.getInstance().getTime()+": Sono state aggiunte alla RAM le pagine di indirizzo ";
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
                        aux += GregorianCalendar.getInstance().getTime()+": Sono stati rimossi dallo Swap i segmenti numero ";
                    } else {
                        aux += GregorianCalendar.getInstance().getTime()+": Sono state rimosse dallo Swap le pagine di indirizzo ";
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
                        aux += GregorianCalendar.getInstance().getTime()+": Sono stati aggiunti allo Swap i segmenti numero ";
                    } else {
                        aux += GregorianCalendar.getInstance().getTime()+": Sono state aggiunte allo Swap le pagine di indirizzo ";
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
        }
        //Non ci sono processi schedulabili
        else aux+=GregorianCalendar.getInstance().getTime()+": Non e' stato possibile schedulare un processo perche' la coda dei pronti e' vuota\n";
        aux += "\n";
        
        //Infine, aggiungo il risultato raccolto nella variabile aux al log, facendolo comparire a video
        testo.append(aux);
        
    }
    
    
    /**
     * Metodo di configurazione della classe, deve essere chiamato ad ogni nuovo
     * inizio di simulazione per preparare la classe a visualizzare correttamente
     * le informazioni
     * 
     * @param istantiTotali
     *          Intero che rappresenta il numero di istanti totali della simulazione
     * @param tipoGestioneMemoria
     *          Intero che rappresenta il tipo di gestione della memoria<br>
     *          1: Paginazione<br>
     *          2: Segmentazione
     */
    public void configura(int istantiTotali, int tipoGestioneMemoria){
        
        setBackground(Color.WHITE);
        testo=new JTextArea();
        testo.setBorder(new EmptyBorder(10,10,10,10));
        this.istantiTotali=istantiTotali;
        this.tipoGestioneMemoria=tipoGestioneMemoria;
        testo.setEditable(false);
        setViewportView(testo);
    }
    

}
