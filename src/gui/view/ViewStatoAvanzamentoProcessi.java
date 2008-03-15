/*
 * Azienda: Stylosoft
 * Nome file: ViewStatoAvanzamentoProcessi.java
 * Package: gui
 * Autore: Giordano Cariani
 * Data: 29/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.0 (29/02/2008): Creazione e stesura grafico.
 */

package gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

/**
 * Questa classe modella un JPanel con funzionalit� che permettono di disegnare e aggiornare
 * un grafico che rappresenta l'avanzamento temporale dell'esecuzione dei processi.
 * 
 * @author Cariani Giordano
 * @version 1.0
 */
public class ViewStatoAvanzamentoProcessi extends JPanel {
	/** Necessario per il Serializable. */
	private static final long serialVersionUID = -3021217566431305374L;
	/** Implementa tutti i metodi per il disegno del grafico. */
	private class JPanelGrafico extends JPanel {
		/** Necessario per il Serializable. */
		private static final long serialVersionUID = -8491593576327143055L;
		
		/** Costruttore di default per la classe JPanelGrafico */
		public JPanelGrafico(){
			super();
		}
		
		/** Dimensione corrente del lato orizzontale della griglia. */
	    private int xOffset = 10;
	    /** Dimensione corrente del lato verticale della griglia. */
	    private int yOffset = 10;

	    /** Dimensione minima del lato orizzontale della griglia. */
	    private int gridX = 10;
	    /** Dimensione minima del lato verticale della griglia. */
	    private int gridY = 10;

	    /** Una stringa con il nome (di processo) pi� lungo. */
	    private String currMaxProcString = "o";
	    /** Definisce quanti caratteri possono essere visualizzati del nome di un processo. */
	    private int maxProcStringSize = 10;

	    /** Vettore che contiene i nomi dei processi. */
	    Vector process = new Vector();
	    
	    /** Dimensione del font di scrittura. */
	    int fontSize = 6;

	    Vector procName = new Vector();
	    
	    /**
	     * Vettore che contiene gli id dei processi che hanno eseguito.
	     * Nella posizione i-esima del vattore ci sar� l'id del processo
	     * che ha eseguito nell'istante i-esimo. 
	     */
	    Vector instants = new Vector();
	    
	    /**
	     * Disegna un insieme di istanti sul grafico.
	     * Se l'id del processo da disegnare � uguale a zero, non verra disegnata
	     * una barra sul grafico, ma verr� solo incrementato l'istante.
	     * 
	     * @param idProcessi array ordinato di id dei processi che eseguono negli istanti
	     * relativi definiti dalla posizione nell'array.
	     */
	    public void disegnaJPanelGrafico(int[] idProcessi) {
	    	instants = new Vector();
	    	// Aggiungo l'istante al vettore
	        for (int i = 0; i < idProcessi.length; i++) {
	            instants.add(new Integer(idProcessi[i]));
	        }
	        // Aggiorno il grafico ridisegnando gli istanti necessari
	        repaint();
	        
	        // Aggiorno la lunghezza del grafico.
	        resizeControl();
	    }
	    
	    /**
	     * Inizializza il grafico. Questo metodo viene chiamato quando viene creata una
	     * simulazione, e imposta il grafico all'istante zero della simulazione.
	     * 
	     * @param procNameRef	procNameRef � un vettore che contiene il nome dei processi
	     * della ConfigurazioneIniziale associata alla simulazione in corso.
	     */
	    public void initializeJPanelGrafico(Vector procNameRef) {
	    	instants = new Vector();
	    	procName = procNameRef;
	        for (int i = 0; i < procName.size(); i++)
	            if (procName.elementAt(i) instanceof String) {
	                String name = (String) procName.elementAt(i);
	                if (currMaxProcString.length() < name.length())
	                    currMaxProcString = name;
	            }
	        // Aggiorna il grafico disegnando i nomi dei processi
	        repaint();
	    }
	    
	    /**
	     * Modifica la dimensione del grafico in base a quanti istanti devono essere disegnati.
	     */
	    private void resizeControl() {

	        /*
	         * Calcolo la larghezza del controllo in base agli istanti che deve
	         * rappresentare
	         */
	        int newWidth = (instants.size() + 4) * gridX + xOffset;
			
	        // imposto la nuova larghezza del grafico mantenendo invec invariata l'altezza
	        setPreferredSize(new Dimension(newWidth, gridY * procName.size()
	                + yOffset));

	        // Notifico il cambiamento di dimensione all'eventuale JScrollPane
	        revalidate();

	        /*
	         * Sposto la visualizzazione di un eventuale JScrollPane in prossimita`
	         * dell'ultimo aggiornamento del grafo in modo che lo scrollPane segua
	         * l'andamento temporale del grafo. Per farlo basta tentare di
	         * visualizzare il rettangolo rappresentante della colonna piu` a destra
	         * del controllo.
	         */
	        scrollRectToVisible(new Rectangle(newWidth + 10, 0, newWidth + 10, 1));
	        revalidate();
	    }

	    /**
	     * Disegna gli elementi (etichette, barre, linee) del grafico.
	     */
	    public void paintComponent(Graphics g) {
	    	super.paintComponent(g);

	        // Larghezza del nuovo grafico
	        int currWidth;
	        // Altezza (costante) del grafico
	        int currHeight = getHeight();
	        // Vecchia griglia orizzontale
	        int oldXGrid;

	        /*
	         * Necessito delle funzionalita` di Graphics2D, cast sempre sicuro
	         * poiche` paint invoca sempre paintComponent con un oggetto Graphics2D
	         */
	        Graphics2D g2 = (Graphics2D) g;

	        // Attivo l'antialiasing per migliorare la qualita` del rendering
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
	                RenderingHints.VALUE_RENDER_QUALITY);

	        // Imposto il colore dello sfondo
	        setBackground(Color.white);

	        /*
	         * Imposto gli offsets orizzontali degli assi in base al processo col
	         * nome piu` lungo, se supera i maxProcStringSize caratteri lo tronco per
	         * evitare che il grafo risulti poco leggibile
	         */
	        if (maxProcStringSize > currMaxProcString.length())
	            xOffset = (int) (new TextLayout(currMaxProcString, this.getFont(),
	                    g2.getFontRenderContext())).getAdvance();
	        else
	            xOffset = (int) (new TextLayout(currMaxProcString.substring(0,
	                    maxProcStringSize - 2)
	                    + "..", this.getFont(), g2.getFontRenderContext()))
	                    .getAdvance();
	        xOffset += (int) (new TextLayout("O", this.getFont(), g2
	                .getFontRenderContext())).getAdvance();

	        /*
	         * l'offsets verticale e' fissato univocamente poiche` vi vengono
	         * disegnati solamente numeri progressivi
	         */
	        yOffset = (int) (1.5f * (new TextLayout("O", this.getFont(), g2
	                .getFontRenderContext())).getAscent());

	        /*
	         * L'altezza della griglia, dato il font e` fissata, la rendo uguale
	         * all'offset poiche` e` una soluzione comoda ed efficace
	         */
	        gridY = yOffset;

	        /*
	         * Numero di caratteri dell'istante piu` grande: serve per calcolare
	         * quando spazio ci deve essere tra i numeri e quindi la dimensione
	         * della griglia orizzontale
	         */
	        int maxInstantDigits = (new Integer(instants.size())).toString()
	                .length();

	        oldXGrid = gridX;

	        /*
	         * La larghezza della griglia deve tenere conto del numero piu` largo
	         * che vado a scriverci nelle etichette, per rendere il grafico piu`
	         * leggibile rendo la griglia quadrata nel caso in cui il numero di
	         * istanti sia piccolo
	         */
	        if (maxInstantDigits < 3)
	            gridX = gridY;
	        else
	            gridX = maxInstantDigits
	                    * (int) (new TextLayout("O", this.getFont(), g2
	                            .getFontRenderContext())).getAdvance();

	        // Imposto la nuova larghezza in accordo con la griglia calcolata
	        currWidth = gridX * instants.size() + xOffset + 2;

	        int axisLength = currWidth;
	        if (currWidth < getWidth())
	            axisLength = getWidth();

	        /*
	         * Disegno l'asse delle X sforando di un istante per evitare che
	         * l'ultimo quadratino sia disegnato sul bordo del controllo
	         */
	        g2.fillRect(xOffset, currHeight - yOffset, axisLength, 2);

	        // Disegno l'asse delle Y
	        g2.fillRect(xOffset, 0, 2, currHeight - yOffset);

	        /*
	         * Disegno le etichette dei processi secondo la griglia procedendo dal
	         * basso verso l'alto
	         */
	        for (int i = procName.size(); i > 0; i--) {

	            // Nome reale del processo
	            String text = ((String) procName.get(i - 1));

	            /*
	             * Se il nome del processo supera i maxProcStringSize caratteri lo
	             * troco per evitare che il grafo risulti poco leggibile
	             */
	            if (text.length() > maxProcStringSize)
	                text = text.substring(0, maxProcStringSize - 2) + "..";

	            /*
	             * Calcolo la posizione in cui vado effettivamente a scrivere
	             * l'etichetta
	             */
	            TextLayout label = new TextLayout(text, this.getFont(), g2
	                    .getFontRenderContext());

	            // Centro la scritta nell'offset
	            int x = (xOffset - (int) label.getAdvance()) / 2;
	            // Seguo la griglia per il verticale
	            int y = currHeight - yOffset - gridY * (i - 1);

	            label.draw(g2, x, y - (int) label.getAscent() / 2);

	            // Disegno anche le linee orizzontali della griglia
				g.setColor(Color.LIGHT_GRAY);
	            g.drawLine(xOffset, y, axisLength, y);
	            g.setColor(Color.BLACK);
	        }

	        //Disegno anche la riga superiore della griglia
	        g.setColor(Color.LIGHT_GRAY);
	        g.drawLine(xOffset, currHeight - yOffset - gridY * procName.size(),
	                axisLength, currHeight - yOffset - gridY * procName.size());
	               
	        // disegno le etichette degli istanti di tempo secondo la griglia
	        g.setColor(Color.BLACK);
	        for (int i = 0; i <= instants.size(); i++) {

	            /*
	             * Lo 0 e` in corrispondenza dell'asse delle y, gli altri seguono da
	             * grid
	             */
	            String text = String.valueOf(i);
	            TextLayout label = new TextLayout(text, this.getFont(), g2
	                    .getFontRenderContext());

	            int x = xOffset + gridX * i - (int) label.getAdvance() / 2;
	            int y = currHeight - yOffset + (int) label.getAscent() * 3 / 2;

	            label.draw(g2, x, y);

	        }

	        // Ridisegno gli istanti
	        for (int i = 0; i < instants.size(); i++) {
	            int procIdx = ((Integer) instants.get(i)).intValue();
	            if (procIdx == -1)
	                continue;
	            int y = 1 + currHeight - yOffset - gridY * procIdx;
	            int x = 2 + xOffset + gridX * i;

	            /*
	             * Imposto un gradiente per dare un effetto 3d, piu` piacevole al
	             * grafo
	             */
	            GradientPaint gradient = new GradientPaint(x, y, ViewUtility
	                    .colorFactory(procName.size(), procIdx), x,
	                    y + (gridY / 2), Color.WHITE, true);
	            g2.setPaint(gradient);

	            g.fillRect(x, y, gridX, gridY - 1);

	            g.setColor(Color.BLACK);

	            /*
	             * Disegno le righe verticali che terminano gli istanti ma solo nel
	             * caso in cui ci sia una separazione dei processi: in questo modo
	             * il grafico risultera` a barre piuttosto che a quadretti
	             */
	            if (i < instants.size() - 1
	                    && ((Integer) instants.get(i + 1)).intValue() != procIdx)
	                g.drawLine(gridX + x, y, gridX + x, this.getHeight() - yOffset);
	            else if (i == instants.size() - 1)
	                g.drawLine(gridX + x, y, gridX + x, this.getHeight() - yOffset);

	            if (i > 0 && ((Integer) instants.get(i - 1)).intValue() != procIdx)
	                g.drawLine(x, y, x, this.getHeight() - yOffset);

	        }

	        /*
	         * Se la griglia e` cambiata in dimensioni, lo notifico al controllo in
	         * modo che esso venga ridisegnato correttamente
	         */
	        if (gridX != oldXGrid)
	            resizeControl();
	    }
	}
	
	/** Componente JPanelGrafico su cui viene disegnato il grafico.*/	
	JPanelGrafico x = new JPanelGrafico(); 
	/** Contiene il JPanelGrafico fornendo la funzioanlit� della scrollbar.*/
	JScrollPane base = new JScrollPane();
	/** Etichetta che contiene una stringa che descrive lo stato della simulazione. */
	JLabel jLabel= new JLabel();
	
	/** Imposta il testo contenuto nella jLabel */
	public void setLabelText(String text) {
		jLabel.setText(text);
	}
	
	/** Svuota il contenuto della jLabel */
	private void resetLabelText() {
		jLabel.setText("");
	}
	
    /** Chiama il costruttore della superclasse JPanel. */
    public ViewStatoAvanzamentoProcessi() {
        super();
        jLabel.setText("");
        jLabel.setPreferredSize(new java.awt.Dimension(31,15));
        jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        base.setViewportView(x); 
        this.add(base,BorderLayout.CENTER);
        this.add(jLabel,BorderLayout.NORTH);
        
    }
    
    /**
     * Richiama il metodo initializeJPanelGrafico(procNameRef) della classe interna
     * JPanelGrafico, per inizializzare il Grafico.
     * Inizializza il grafico. Questo metodo viene chiamato quando viene creata una
     * simulazione, e imposta il grafico all'istante zero della simulazione.
     * 
     * @param procNameRef	procNameRef � un vettore che contiene il nome dei processi
     * della ConfigurazioneIniziale associata alla simulazione in corso.
     */
    public void initializeViewStatoAvanzamentoProcessi(Vector procNameRef) {
    	x.initializeJPanelGrafico(procNameRef);
    }
    
    /**
     * Richiama il metodo disegnaJPanelGrafico(int[] idProcessi) della classe interna
     * JPanelGrafico, che disegna il grafico.
     * Disegna un insieme di istanti sul grafico.
     * Se l'id del processo da disegnare � uguale a zero, non verra disegnata
     * una barra sul grafico, ma verr� solo incrementato l'istante.
     * 
     * @param idProcessi array ordinato di id dei processi che eseguono negli istanti
     * relativi definiti dalla posizione nell'array.
     */
    public void disegnaGrafico(int[] idProcessi) {
    	resetLabelText();
    	x.disegnaJPanelGrafico(idProcessi);
    }
}