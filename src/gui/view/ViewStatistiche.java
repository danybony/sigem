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
import logic.simulazione.Istante;
import logic.simulazione.Player;

/**
 * Questa classe implementa un JScrollPane che contiene in forma tabellare, dati relativi
 * alle statistiche sulla simulazioni. 
 * @see SiGeMv2View
 */
public class ViewStatistiche extends JScrollPane {
	/** Necessario per il Serializable. */
	private static final long serialVersionUID = 1109913847658572644L;
	/** Il secondo pannello della vista, che contiene le statistiche*/
	private JPanel p2 = null;
	/** contiene il pulsante "visualizza statistiche" */
	private JButton jButton = null;
	/** contiene il pulsante "disabilita visualizzazione statistiche" */
	private JButton jButton2 = null;
	
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
		this.setViewportView(getP2());
		p2 = getP2();
		this.stato = false;
		//abilitaVisualizzazione(false);
	}
	
	/**
	 * Crea il JPanel che contiene le statistiche.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP2() {
            p2 = new JPanel();
            p2.setLayout(new BorderLayout());
            //p2.add(jTextArea,BorderLayout.CENTER);
            //p2.add(getJButtonDisabilita(),BorderLayout.NORTH);
            return p2;
	}
	
	/**
	 * Questo metodo genera un JPanel contenente le statistiche relative all'istante
	 * corrente della simulazione, e lo visualizza nel pannello.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
        
        
	public void generaStatistiche(Player player, Istante istante) {
            
		this.stato = true;
		// creo un pannello che conterrà i JTextPane che contengono le statistiche 
		panelStat = new JPanel();
		GridLayout gridLayout = new GridLayout(-1,1,10,10);
		panelStat.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY,10));
		panelStat.setBackground(java.awt.Color.LIGHT_GRAY);
		panelStat.setLayout(gridLayout);
		
		// ottengo i valori delle statistiche
		String contenuto = "";
		contenuto += "Utilizzo RAM (KB):\t\t"
				+ ((((int) player.getStatistiche().getUtilizzoRAM()) != (-1)) ? String
						.valueOf((float) player.getStatistiche().getUtilizzoRAM()) : "n.d.");
		contenuto += "\nUtlizzo Swap (KB):\t\t"
				+ ((((int) player.getStatistiche().getUtilizzoSwap()) != (-1)) ? String
						.valueOf((float) player.getStatistiche().getUtilizzoSwap()) : "n.d.");
		contenuto += "\nNumero Istanti Rimanenti:\t"
				+ ((((int) player.getStatistiche().getNumeroIstantiRimanenti()) != (-1)) ? String
					.valueOf((float) player.getStatistiche().getNumeroIstantiRimanenti())
						: "n.d.");
		contenuto += "\nNumero Istanti totali:\t\t"
				+ ((((int) player.numeroIstanti()) != (-1)) ? String
						.valueOf((float) player.numeroIstanti()) : "n.d.");
                contenuto += "\nNumero Fault:\t\t"
                        + ((((int) player.getStatistiche().getNumeroFault()) != (-1)) ? String
						.valueOf((float) player.getStatistiche().getNumeroFault()) : "n.d.");
                
                contenuto += "\nFull RAM:\t\t\t"
                        +((istante.getFull_RAM()) ? "Si" : "No");

                contenuto += "\nFull Swap:\t\t\t"
                        +((istante.getFull_Swap()) ? "Si" : "No");
                
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
		jTextPane.setEditable(false);
		jTextPane.setText(contenuto);
		panelStat.add(jTextPane);

		p2 = getP2();
		p2.add(panelStat, BorderLayout.CENTER);
		this.setViewportView(p2);
	}

	/**
	 * Abilita o disabilita la visualizzazione delle statistiche.
	 * 
	 * @param x
	 *            true per abilitare false per disabilitare.
	 */
	public void abilitaVisualizzazione(boolean x) {
            /*
		// abilita le statistiche
		if (x) {
			jButton.setEnabled(true);
			if (stato) {
				gui.richiediStatistiche();
			}
		} else {
			this.stato = false;
			jButton.setEnabled(false);
			this.setViewportView(getP1());
		}
             */
	}        
}
