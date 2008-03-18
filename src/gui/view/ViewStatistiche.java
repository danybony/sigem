/*
 * Azienda: Stylosoft
 * Nome file: ViewStatistiche.java
 * Package: gui.view
 * Autore: Cariani Giordano
 * Data: 02/03/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche:
 * - v.1.0 (02/03/2008): Creazione della classe. 
 * */
package gui.view;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;

//import simulazione.Statistiche;
//import simulazione.StatisticheProcesso;
import gui.SiGeMv2View;
import logic.parametri.ConfigurazioneIniziale;
import logic.simulazione.Istante;
import logic.simulazione.Player;

/**
 * Questa classe implementa un JScrollPane che contiene in forma tabellare, dati relativi
 * alle statistiche sulla simulazioni. 
 * @see SiGeMv2View
 */
public class ViewStatistiche extends JScrollPane {
	/** Il secondo pannello della vista, che contiene le statistiche*/
	private JPanel panel = null;
	
	JPanel panelStat = null; 
	/** Il possessore di questo JScrollPane.*/
	private SiGeMv2View gui= null;
	/** Vale true solo se il ViewPort del JScrollPane � impostato sul secondo pannello */
	private boolean stato; 
	
	/**
	 * Chiama il costruttore della superclasse JScrollPane.
	 * Imposta l'origine dati per il viewport della JScrollPane con il metodo 
	 * setViewportView(), sul JPanel p1.
	 */
	public ViewStatistiche(SiGeMv2View gui) {
		super();
		this.gui = gui;
		this.setViewportView(getpanel());
		panel = getpanel();
		this.stato = false;
	}
	
	/**
	 * Crea il JPanel che contiene le statistiche.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getpanel() {
            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            return panel;
	}
	
	/**
	 * Questo metodo genera un JPanel contenente le statistiche relative all'istante
	 * corrente della simulazione, e lo visualizza nel pannello.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
        
        
	public void generaStatistiche(Player player, Istante istante, ConfigurazioneIniziale conf) {
            
		this.stato = true;
		// creo un pannello che conterrà i JTextPane che contengono le statistiche 
		panelStat = new JPanel();
		GridLayout gridLayout = new GridLayout(-1,1,10,10);
		panelStat.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY,6));
		panelStat.setBackground(java.awt.Color.LIGHT_GRAY);
		panelStat.setLayout(gridLayout);
		String contenuto = "";
                if(player.getIndiceIstanteCorrente() > 0){
                    // ottengo i valori delle statistiche

                    contenuto += "Utilizzo RAM (KB): \t\t"
                                    + (player.getStatistiche().getUtilizzoRAM())
                                    + " di "
                                    + conf.getDimensioneRAM();


                    contenuto += "\nUtlizzo Swap (KB): \t\t"
                                    + ( player.getStatistiche().getUtilizzoSwap())
                                    + " di "
                                    + conf.getDimensioneSwap();


                    contenuto += "\nIstante: \t\t\t"
                                    + ( player.getIndiceIstanteCorrente())
                                    + " di "
                                    + player.numeroIstanti();

                    contenuto += "\nNumero Fault in memoria: \t"
                            + ( player.getStatistiche().getNumeroFault());

                    contenuto += "\nMemoria RAM riempita completamente:"
                            +((istante.getFull_RAM()) ? "Si" : "No");

                    contenuto += "\nArea di Swap riempita completamente: \t"
                            +((istante.getFull_Swap()) ? "Si" : "No");
                }
                else{
                    contenuto += "Utilizzo RAM (KB): \t\tn.d.";

                    contenuto += "\nUtlizzo Swap (KB): \t\tn.d.";

                    contenuto += "\nIstante: \t\t\tn.d.";

                    contenuto += "\nNumero Fault in memoria: \tn.d.";

                    contenuto += "\nMemoria RAM riempita completamente: n.d.";

                    contenuto += "\nArea di Swap riempita completamente: \tn.d.";
                }
                
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
		jTextPane.setEditable(false);
		jTextPane.setText(contenuto);
		panelStat.add(jTextPane);

		panel = getpanel();
		panel.add(panelStat, BorderLayout.CENTER);
		this.setViewportView(panel);
	}

}
