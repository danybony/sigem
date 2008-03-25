/*
 * Azienda: Stylosoft
 * Nome file: ViewFrameMemoria.java
 * Package: gui.dialog
 * Autore: Davide Compagnin
 * Data: 15/03/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche: 
 * 
 *  - v.1.0 (15/03/2008): Impostazione dello scheletro della classe
 */

package gui.view;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logic.parametri.ConfigurazioneIniziale;
/**
 *
 * @author Davide Compagnin
 */
public class ViewRiepilogo extends JScrollPane {
    /**
     * JTextArea dove si scriver� il log visualizzato a schermo
     */
    private JTextArea testo=new JTextArea(50, 1000);
    /**
     * Tipo di gestione della memoria<br>
     * FALSE: paginazione<br>
     * TRUE: segmentazione<br>
     * Default: paginazione
     */
    private boolean tipoGestioneMemoria=false;
    
    
    public ViewRiepilogo(boolean tipoGestioneMemoria) {
        this.tipoGestioneMemoria=tipoGestioneMemoria;
    }
    
    public void aggiorna( ConfigurazioneIniziale C ) {
        String CText="";
        CText+="Dimensione RAM: "+C.getDimensioneRAM()+"KB\n";
        CText+="Dimensione Swap: "+C.getDimensioneSwap()+"KB\n";
        CText+="Tempo di context switch: "+C.getTempoContextSwitch()+"\n";
        CText+="Tempo di accesso al disco: "+C.getTempoAccessoDisco()+"\n";
        CText+="Banda Bus Dati: "+C.getBandaBusDati()+"\n";
        testo.append(CText);
        this.add(testo);
    }
}
