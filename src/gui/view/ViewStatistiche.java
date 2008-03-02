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

/**
 * Questa classe implementa un JScrollPane che contiene in forma tabellare, dati relativi
 * alle statistiche sulla simulazioni. Quindi valori del tipo:
 * <ol>
 * <li>Occupazione della CPU del singolo processo</li>
 * <li>Occupazione delle risorse per ogni processo</li>
 * <li>Tempo di turn around</li>
 * <li>Tempo medio di turn around</li>
 * <li>Throughput</li>
 * <li>Tempo di risposta</li>
 * <li>Tempo di attesa</li>
 * <li>Tempo medio di attesa</li>
 * <li>Tempo medio di risposta</li>
 * </ol>
 * 
 * @author Sarto Carlo
 * @version 1.2
 * @see SiGeMv2View
 * @see Statistiche
 */
public class ViewStatistiche extends JScrollPane {
	/** Necessario per il Serializable. */
	private static final long serialVersionUID = 1109913847658572644L;
	/** Il primo pannello della vista */
	private JPanel p1 = null;
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
		this.setViewportView(getP1());
		p1 = getP1();
		p2 = getP2();
		this.stato = false;
		//abilitaVisualizzazione(false);
	}

	/**
	 * Crea un JPanel con un pulsante per visualizzare le statistiche.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP1() {
		if (p1 == null) {
			p1 = new JPanel();
			//p1.add(getJButtonVisualizza(), null);
		}
		return p1;
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
        
        /*
	public void generaStatistiche(Statistiche statistiche) {
            
		this.stato = true;
		// creo un pannello che conterr� i JTextPane che contengono le statistiche 
		panelStat = new JPanel();
		GridLayout gridLayout = new GridLayout(-1,1,10,10);
		panelStat.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY,10));
		panelStat.setBackground(java.awt.Color.LIGHT_GRAY);
		panelStat.setLayout(gridLayout);
		
		// ottengo i valori delle statistiche
		String contenuto = "STATISTICHE DELLA SIMULAZIONE:\n";
		contenuto += "\nThroughput:\t\t\t"
				+ ((((int) statistiche.getThroughput()) != (-1)) ? String
						.valueOf((float) statistiche.getThroughput()) : "n.d.");
		contenuto += "\nTempo medio di attesa:\t\t"
				+ ((((int) statistiche.getMediaAttesa()) != (-1)) ? String
						.valueOf((float) statistiche.getMediaAttesa()) : "n.d.");
		contenuto += "\nTempo medio di risposta:\t\t"
				+ ((((int) statistiche.getMediaRisposta()) != (-1)) ? String
						.valueOf((float) statistiche.getMediaRisposta())
						: "n.d.");
		contenuto += "\nTempo medio di turn around:\t"
				+ ((((int) statistiche.getMediaTurnAround()) != (-1)) ? String
						.valueOf((float) statistiche.getMediaTurnAround()) : "n.d.");

		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
		jTextPane.setEditable(false);
		jTextPane.setText(contenuto);
		panelStat.add(jTextPane);
		// aggiungo le statistiche dei processi
		for (int i = 0; i < statistiche.getStatisticheProcessi().size(); i++) {
			if (statistiche.getStatisticheProcessi().get(i) instanceof StatisticheProcesso) {
				StatisticheProcesso stat = (StatisticheProcesso) statistiche
						.getStatisticheProcessi().get(i);
				contenuto = "STATISTICHE PROCESSO "
						+ stat.getIProcesso().getNome() + ":\n";
				contenuto += "\nTempo di attesa:\t\t"
						+ ((((int) stat.getTempoAttesa()) != (-1)) ? String
								.valueOf((int) stat.getTempoAttesa()) : "n.d.");
				contenuto += "\nTempo di riposta:\t\t"
						+ ((((int) stat.getTempoRisposta()) != (-1)) ? String
								.valueOf((int) stat.getTempoRisposta())
								: "n.d.");
				contenuto += "\nTempo di turn around:\t\t"
						+ ((((int) stat.getTempoTurnAround()) != (-1)) ? String
								.valueOf((int) stat.getTempoTurnAround())
								: "n.d.");
				contenuto += "\nOccupazione CPU:\t\t"
						+ ((((int) stat.getOccupazioneCPU()) != (-1)) ? String
								.valueOf((int) stat.getOccupazioneCPU())
								: "n.d.");
				contenuto += "\nOccupazione CPU percentuale:\t"
						+ ((((int) stat.getPercentualeOccCPU()) != (-1)) ? String
								.valueOf((float) stat.getPercentualeOccCPU())
								+ "%"
								: "n.d.");
				jTextPane = new JTextPane();
				jTextPane.setFont(new java.awt.Font("Arial",
						java.awt.Font.PLAIN, 12));
				jTextPane.setEditable(false);
				jTextPane.setText(contenuto);
				panelStat.add(jTextPane);
			}
		}
		p2 = getP2();
		p2.add(panelStat, BorderLayout.CENTER);
		this.setViewportView(p2);
             

	}
        */
             

	/**
	 * Questo metodo crea un JButton a cui � associato un ActionListener che
	 * richiama il metodo gui.SiGeMv2View.richiediStatistiche().
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonVisualizza() {
            /*
		if (jButton == null) {
			jButton = new JButton("visualizza statistiche");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gui.richiediStatistiche();
				}
			});
		}
             */
		return jButton;
             
	}
             

	/**
	 * Questo metodo crea un JButton a cui � associato un ActionListener che
	 * sposta il ViewPort del JScrollPane sul primo JPanel.
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonDisabilita() {
            /*
		if (jButton2 == null) {
			jButton2 = new JButton("disabilita visualizzazione statistiche");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					stato = false;
					setViewportView(getP1());
				}
			});
		}
             */
		return jButton2;
             
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
