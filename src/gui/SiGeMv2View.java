/*
 * Azienda: Stylosoft
 * Nome file: SiGeMv2View
 * Package: gui
 * Autore: Cariani Giordano
 * Data: 04/03/2008
 * Versione: 1.2
 * Licenza: open-source
 * Registro delle modifiche:
 * - v.1.2 (05/03/2008): implementazione di Player
 * - v.1.1 (02/03/2008): Create le 3 finestre e inizializzazione grafico processi (dopo wizard)
 * - v.1.0 (01/03/2008): Creato scheletro interfaccia grafica
 * */
package gui;

import net.infonode.docking.*;
import net.infonode.docking.mouse.DockingWindowActionMouseButtonListener;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.*;
import net.infonode.docking.util.*;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.gui.laf.InfoNodeLookAndFeel;
import net.infonode.util.Direction;

import javax.swing.*;

import java.net.URL;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Vector;

import com.jgoodies.looks.HeaderStyle;
import com.jgoodies.looks.Options;

import gui.view.*;
import gui.dialog.*;
import gui.utility.IconStylosoft;

import logic.caricamento.GestioneFile;
import logic.parametri.ConfigurazioneIniziale;
import logic.parametri.Processo;
import logic.simulazione.Player;


public class SiGeMv2View {

        // ------------------------------
	// CAMPI DATI INTERFACCIA GRAFICA
	// ------------------------------

	/**
	 * La finestra principale alla quale vengono aggiunte le View, i menu e le
	 * ToolBar.
	 **/
	private RootWindow rootWindow;

	/** Array delle viste statiche */
	private View[] views = new View[3];

	/** Contiene le viste statiche */
	private ViewMap viewMap = new ViewMap();

	/** Gli elementi del menu view */
	private JMenuItem[] viewItems = new JMenuItem[views.length];

	/** Il tema applicato all'interfaccia grafica */
	private DockingWindowsTheme currentTheme = new BlueHighlightDockingTheme();

	/**
	 * Un oggetto che contiene tutte le modifiche apportate alle caratteristiche
	 * di pulsanti, menu, grafica... Ogni volta che si cambia il tema, questo
	 * oggetto viene resettato
	 */
	private RootWindowProperties properties = new RootWindowProperties();

	/** Contiene la gestione degli eventi del menu Finestra. */
	private ActionListener lnrWindow;

	/** Il frame dell'applicazione */
	private JFrame frame = new JFrame("SiGeM");
	
	/** Rappresenta lo stato dela Gui, vale true se la simulazione e' in avanzamento automatico,
	 * false, se e' interrotta.*/
	private boolean statoGui = false;
	
	//private HelpSet hs;
	
	//private HelpBroker hb;

	// PULSANTI E MENU

	/** crea un oggetto JDialogAskSave, JDialog che richiede la conferma di salvataggio.*/
	//JDialogAskSave richiediConferma = new JDialogAskSave(frame);
	
	/** Messaggio di default visualizzato a simulazione terminata. Se si verifica 
	 * un deadlock questo messaggio viene cambiato. */ 
	String messaggioSimTerminata = new String("SIMULAZIONE TERMINATA");
	
	// Pulsanti ToolBar
	private JButton jButtonSimulazionePlay, jButtonSimulazioneStop, jButtonSimulazioneInizio, jButtonSimulazioneFine;

	private JButton jButtonNuovaConfigurazione, jButtonApriConfigurazione, jButtonSalvaConfigurazione, jButtonHelp;

	// Pulsanti Menu Simulazione
	private JMenuItem jSimulazioneItemPlay, jSimulazioneItemStop, jSimulazioneItemInizio, jSimulazioneItemFine;

	// Pulsanti Menu File
	private JMenuItem jFileItemNuovaConfigurazione, jFileItemApriConfigurazione;

	private JMenuItem jFileItemSalvaConfigurazione, jFileItemEsci;

	// Pulsanti Menu Finestre
        /*
	private JMenuItem mniOpenSProcessi, mniOpenSRisorse;

	private JMenuItem mniOpenSPronti, mniOpenSTerminati;

	private JMenuItem mniOpenSStatistiche, mniOpenSGrafRisorse;

	private JMenuItem mniOpenSGrafOrdinamento;
         */

	/** Contiene finestre e funzioni per selezionare un file sul filesystem */
	private GestioneFile gestioneFile;
        
        /** Wizard per la configurazione dei processi */
        private ConfigurazioneAmbienteJDialog  configurazioneAmbiente;
        
        /** Configurazione iniziale */
        private ConfigurazioneIniziale configurazioneIniziale;
        
        /** Player */
        private Player player;
	// ----------------------------------
	// METODI GESTIONE COMPONENTI GRAFICI
	// ----------------------------------

	/**
	 * Costruttore della classe GuiBlueThoth. Carica le politiche di ordinamento
	 * disponibili. Visualizza il frame principale.
	 */
	public SiGeMv2View() throws Exception {
		// i controlli da fare prima della chiusura dell'applicazione
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// schiacciando il pulsante "x" del frame, la finestra non si
				// chiude
				frame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        			System.exit(0);

			}
		});
		createRootWindow();
		setDefaultLayout();
		showFrame();
	}

        /** Disegna le statistiche sulla vista ViewStatistiche */
	public void visualizzaStatistiche() {
            /*
		if ((views[2]).getComponent() instanceof ViewStatistiche) {
			ViewStatistiche currView = (ViewStatistiche) views[3].getComponent();
			currView.generaStatistiche(statistiche);
		}
             */
	};
        
	/**
	 * Apre una vista statica. Questo metodo serve per aprire le viste dal menu
	 * Finestre dell'interfacci grafica.
	 * 
	 * @param _view
	 *            la vista da aprire
	 */
	private void openStaticView(View _view) {
		DockingUtil.addWindow(_view, rootWindow);
	}

        private void creaConfigurazione() {
            configurazioneAmbiente = new ConfigurazioneAmbienteJDialog(frame, true, this);
            configurazioneAmbiente.setVisible(true);
        }
	/**
	 * Crea la root window e aggiunge le view statiche.
	 */
	private void createRootWindow() {

		// Creo le viste e le aggiungo al viewMap
		views[0] = new View("Processi",
				IconStylosoft.getGeneralIcon("processi"), new ViewStatoAvanzamentoProcessi());
		viewMap.addView(0, views[0]);
                
		views[1] = new View("Frame Memoria", IconStylosoft.getGeneralIcon("mv"),
				new ViewFrameMemoria());
		viewMap.addView(1, views[1]);
		views[2] = new View("Statistiche", IconStylosoft
				.getGeneralIcon("statistiche"), new ViewStatistiche(this));
		viewMap.addView(2, views[2]);
                
		
		// Aggiungo i pulsanti help alle viste
		JButton button = new JButton(IconStylosoft.getGeneralIcon("help"));
		button.setOpaque(false);
		button.setBorder(null);
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "HELP.",
						"Visualizza help", JOptionPane.INFORMATION_MESSAGE);
			}
		});
//		views[1].getCustomTabComponents().add(button);

		rootWindow = DockingUtil.createRootWindow(viewMap, true);

		// Le view non si possono staccare ne riattaccare dalla finestra
		// principale
		properties.getDockingWindowProperties().setUndockEnabled(false);
		properties.getDockingWindowProperties().setDockEnabled(false);

		// Le view non si possono chiudere
		properties.getDockingWindowProperties().setCloseEnabled(false);

		// Abilita title bar delle viste con stile del tema
		final RootWindowProperties titleBarStyleProperties = PropertiesUtil
				.createTitleBarStyleRootWindowProperties();
		rootWindow.getRootWindowProperties().addSuperObject(
				titleBarStyleProperties);

		// Set gradient theme. The theme properties object is the super object
		// of our properties object, which
		// means our property value settings will override the theme values
		properties.addSuperObject(currentTheme.getRootWindowProperties());

		// Our properties object is the super object of the root window
		// properties object, so all property values of the
		// theme and in our property object will be used by the root window
		rootWindow.getRootWindowProperties().addSuperObject(properties);

		// Enable the bottom window bar
		rootWindow.getWindowBar(Direction.DOWN).setEnabled(true);

		// Add a listener which shows dialogs when a window is closing or
		// closed.
		rootWindow.addListener(new DockingWindowAdapter() {
			public void windowAdded(DockingWindow addedToWindow,
					DockingWindow addedWindow) {
				updateViews(addedWindow, true);
			}

			public void windowRemoved(DockingWindow removedFromWindow,
					DockingWindow removedWindow) {
				updateViews(removedWindow, false);
			}

			public void windowClosing(DockingWindow window)
					throws OperationAbortedException {
				// Confirm close operation
				if (JOptionPane.showConfirmDialog(frame,
						"Really close window '" + window + "'?") != JOptionPane.YES_OPTION)
					throw new OperationAbortedException(
							"Window close was aborted!");
			}

			public void windowDocking(DockingWindow window)
					throws OperationAbortedException {
				// Confirm dock operation
				if (JOptionPane.showConfirmDialog(frame, "Really dock window '"
						+ window + "'?") != JOptionPane.YES_OPTION)
					throw new OperationAbortedException(
							"Window dock was aborted!");
			}

			public void windowUndocking(DockingWindow window)
					throws OperationAbortedException {
				// Confirm undock operation
				if (JOptionPane.showConfirmDialog(frame,
						"Really undock window '" + window + "'?") != JOptionPane.YES_OPTION)
					throw new OperationAbortedException(
							"Window undock was aborted!");
			}

		});

		// Add a mouse button listener that closes a window when it's clicked
		// with the middle mouse button.
		rootWindow.addTabMouseButtonListener(DockingWindowActionMouseButtonListener.MIDDLE_BUTTON_CLOSE_LISTENER);

		/*
		 * Disabilito il pulsante di chiusura finestra nelle tab esterne per
		 * evitare che l'utente possa, chiudendo una finestra parent, chiudere a
		 * sua volta piu` di una finestra child in una sola azione poiche`
		 * questo invaliderebbe il listener sulle singole view.
		 */
		rootWindow.getRootWindowProperties().getTabWindowProperties()
				.getCloseButtonProperties().setVisible(false);

		/*
		 * Miglioro il look & feel del drag dei vari pannelli implementando un
		 * filtro alpha che renda visibile la posizione del pannello durante il
		 * suo drag come un rettangolo pieno semitrasparente.
		 */
		ComponentPainter alphaBlendPainter = new ComponentPainter() {

			/**
			 * @see net.infonode.gui.componentpainter
			 *      .ComponentPainter#paint(java.awt.Component,
			 *      java.awt.Graphics, int, int, int, int)
			 */
			public void paint(Component component, Graphics g, int x, int y,
					int width, int height) {
				Graphics2D g2 = (Graphics2D) g;
				Composite oldComp = g2.getComposite();

				// Imposto un alpha per rendere il rettangolo trasparente
				AlphaComposite composite = AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 0.6f);
				g2.setComposite(composite);

				// Coloro il rettangolo con un gradiente azzurro
				GradientPaint background = new GradientPaint(width, 0,
						new Color(129, 156, 231), 0, height, new Color(74, 115,
								231));
				g2.setPaint(background);
				g2.fillRect(x, y, width, height);
				g2.setColor(Color.BLACK);
				g2.setComposite(oldComp);
			}

			/**
			 * @see net.infonode.gui.componentpainter
			 *      .ComponentPainter#paint(java.awt.Component,
			 *      java.awt.Graphics, int, int, int, int,
			 *      net.infonode.util.Direction, boolean, boolean)
			 */
			public void paint(Component component, Graphics g, int x, int y,
					int width, int height, Direction direction,
					boolean horizontalFlip, boolean verticalFlip) {
				paint(component, g, x, y, width, height);
			}

			/**
			 * @see net.infonode.gui.componentpainter
			 *      .ComponentPainter#isOpaque(java.awt.Component)
			 */
			public boolean isOpaque(Component arg0) {
				return false;
			}

			/**
			 * @see net.infonode.gui.componentpainter
			 *      .ComponentPainter#getColor(java.awt.Component)
			 */
			public Color getColor(Component arg0) {
				return new Color(129, 156, 231);
			}
		};
		// Applico il filtro alla finestra principale
		rootWindow.getRootWindowProperties()
				.getDragRectangleShapedPanelProperties().setComponentPainter(
						alphaBlendPainter);
	}

        /**
	 * Aggiunge o rimuove viste dinamiche. Abilita o disabilita viste statiche
	 * 
	 * @param window
	 *            the window in which to search for views
	 * @param added
	 *            if true the window was added
	 */
	private void updateViews(DockingWindow window, boolean added) {
		if (window instanceof View) {
			for (int i = 0; i < views.length; i++)
				if (views[i] == window && viewItems[i] != null)
					viewItems[i].setEnabled(!added);
		} else {
			for (int i = 0; i < window.getChildWindowCount(); i++)
				updateViews(window.getChildWindow(i), added);
		}
	}

        /**
	 * Crea il frame MenuBar.
	 * 
	 * @return the menu bar
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menu = new JMenuBar();
		menu.add(createFileMenu());
		menu.add(createSimulazioneMenu());
		menu.add(createHelpMenu());
		return menu;
	}

        /**
	 * Crea il menu File. Usato dall'utente per:
         * - aprire 
         * - salvare
	 * - creare una configurazione
	 * - terminare la sessione
	 * 
	 * @return the layout menu
	 */
	private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");

		jFileItemNuovaConfigurazione = new JMenuItem("Nuova Configurazione");
		jFileItemNuovaConfigurazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creaConfigurazione();
			}
		});
		fileMenu.add(jFileItemNuovaConfigurazione);

		jFileItemApriConfigurazione = new JMenuItem("Apri Configurazione");
		jFileItemApriConfigurazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Apre finestra apri file
				//apriFileConfigurazione();
			}
		});
		fileMenu.add(jFileItemApriConfigurazione);

		fileMenu.addSeparator();
		jFileItemSalvaConfigurazione = new JMenuItem("Salva");
                jFileItemSalvaConfigurazione.setEnabled(false);
		jFileItemSalvaConfigurazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//salvaConfigurazione();
			}
		});
		fileMenu.add(jFileItemSalvaConfigurazione);
		jFileItemSalvaConfigurazione.setEnabled(false);

		fileMenu.addSeparator();
		jFileItemEsci = new JMenuItem("Esci");
		jFileItemEsci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
		});
		fileMenu.add(jFileItemEsci);
		return fileMenu;
	}

       /**
	 * Crea il menu Simulazione Usato dall'utente per comandare la simulazione
	 * 
	 * @return menu Simulazione
	 */
	private JMenu createSimulazioneMenu() {
		JMenu simulazioneMenu = new JMenu("Simulazione");

		jSimulazioneItemPlay = new JMenuItem(IconStylosoft.getGeneralIcon("play"));
		jSimulazioneItemPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		simulazioneMenu.add(jSimulazioneItemPlay);

		jSimulazioneItemStop = new JMenuItem(IconStylosoft.getGeneralIcon("stop"));
		jSimulazioneItemStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SimStop();
			}
		});
		simulazioneMenu.add(jSimulazioneItemStop);

		simulazioneMenu.addSeparator();

		jSimulazioneItemInizio = new JMenuItem(IconStylosoft.getGeneralIcon("prev"));
		jSimulazioneItemInizio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SimInizio();
			}
		});
		simulazioneMenu.add(jSimulazioneItemInizio);

		jSimulazioneItemFine = new JMenuItem(IconStylosoft.getGeneralIcon("next"));
		jSimulazioneItemFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SimInizio();
			}
		});
		simulazioneMenu.add(jSimulazioneItemFine);

		jSimulazioneItemFine.setEnabled(false);
		jSimulazioneItemInizio.setEnabled(false);
		jSimulazioneItemPlay.setEnabled(false);
		jSimulazioneItemStop.setEnabled(false);

		return simulazioneMenu;
	}

	/**
	 * Crea il menu Help
	 * 
	 * @return the view menu
	 */
	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu("Help");
		JMenuItem mniHelp = new JMenuItem("Help");
		//mniHelp.addActionListener(new CSH.DisplayHelpFromSource(hb));
		helpMenu.add(mniHelp);

		return helpMenu;
        }
        
        /**
	 * Crea il frame ToolBar
	 * 
	 * @return the frame tool bar
	 */
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();

		jButtonNuovaConfigurazione = new JButton(IconStylosoft.getGeneralIcon("newConf"));
		jButtonNuovaConfigurazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                                creaConfigurazione();
			}
		});
		jButtonNuovaConfigurazione.setToolTipText("Apre il pannello di configurazione per"
				+ " una nuova simulazione");

		
		jButtonApriConfigurazione = new JButton(IconStylosoft.getGeneralIcon("openFile"));
		jButtonApriConfigurazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//apriFileConfigurazione();
			}
		});
		jButtonApriConfigurazione.setToolTipText("Carica una configurazione precedentemente "
				+ "salvata");

		jButtonSalvaConfigurazione = new JButton(IconStylosoft.getGeneralIcon("save"));
		jButtonSalvaConfigurazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//salvaConfigurazione();
			}
		});
		jButtonSalvaConfigurazione.setToolTipText("Salva una simulazione su disco");

		jButtonSimulazioneStop = new JButton(IconStylosoft.getGeneralIcon("stop"));
		jButtonSimulazioneStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fermaSimulazione();
			}
		});
		jButtonSimulazioneStop.setToolTipText("Ferma l'avanzamento automatico della "
				+ "simulazione");

		jButtonSimulazionePlay = new JButton(IconStylosoft.getGeneralIcon("play"));
		jButtonSimulazionePlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				avviaSimulazione();
			}
		});
		jButtonSimulazionePlay.setToolTipText("Avvia l'avanzamento automatico della "
				+ "simulazione");

		jButtonSimulazioneInizio = new JButton(IconStylosoft.getGeneralIcon("prev"));
		jButtonSimulazioneInizio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SimInizio();
			}
		});
		jButtonSimulazioneInizio.setToolTipText("Riporta la simulazione all'istante iniziale");

		jButtonSimulazioneFine = new JButton(IconStylosoft.getGeneralIcon("next"));
		jButtonSimulazioneFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SimFine();
			}
		});
		jButtonSimulazioneFine.setToolTipText("Porta la simulazione all'istante finale");
			
		jButtonHelp = new JButton(IconStylosoft.getGeneralIcon("help"));
		//jButtonHelp.addActionListener(new CSH.DisplayHelpFromSource(hb));
		jButtonHelp.setToolTipText("Apre la finestra di aiuto");

		toolBar.setRollover(true);
		toolBar.add(jButtonNuovaConfigurazione);
		toolBar.add(jButtonApriConfigurazione);
		toolBar.add(jButtonSalvaConfigurazione);

		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.add(jButtonSimulazionePlay);
		toolBar.add(jButtonSimulazioneStop);
		toolBar.add(jButtonSimulazioneInizio);
		toolBar.add(jButtonSimulazioneFine);

		toolBar.addSeparator();
		toolBar.add(jButtonHelp);

		// Stato iniziale dei pulsanti
		jButtonSimulazionePlay.setEnabled(false);
		jButtonSimulazioneStop.setEnabled(false);
		jButtonSimulazioneInizio.setEnabled(false);
		jButtonSimulazioneFine.setEnabled(false);
		jButtonSalvaConfigurazione.setEnabled(false);
		
		toolBar.add(Box.createHorizontalGlue());
		toolBar.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
		return toolBar;
	}
    
        /**
	 * Imposta il layout di default delle viste
	 */
	private void setDefaultLayout() {
            rootWindow.setWindow(
                    new SplitWindow(true, 0.6615854f, 
                    new SplitWindow(false, 0.8f, views[0], views[2]),
                    views[1]));
		WindowBar windowBar = rootWindow.getWindowBar(Direction.DOWN);

		while (windowBar.getChildWindowCount() > 0)
			windowBar.getChildWindow(0).close();

        }
        
        /**
	 * Inizializza il frame principale e lo rende visibile
	 */
	private void showFrame() {
		frame.getContentPane().add(createToolBar(), BorderLayout.NORTH);
		frame.getContentPane().add(rootWindow, BorderLayout.CENTER);
		frame.setJMenuBar(createMenuBar());
		frame.setSize(1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

        public JFrame getFrame() {
            return frame;
        }
        
        public void abilitaTutto(){
            jSimulazioneItemFine.setEnabled(true);
            jSimulazioneItemInizio.setEnabled(true);
            jSimulazioneItemPlay.setEnabled(true);
            jSimulazioneItemStop.setEnabled(true);    
            jButtonSimulazionePlay.setEnabled(true);
            jButtonSimulazioneStop.setEnabled(true);
            jButtonSimulazioneInizio.setEnabled(true);
            jButtonSimulazioneFine.setEnabled(true);
            jButtonSalvaConfigurazione.setEnabled(true);
            jFileItemSalvaConfigurazione.setEnabled(true);
        }
        
        /** inizializza le viste e prepara l'applicazione per l'esecuzione della simulazione */
        public void setIstanteZero() {
		statoGui = false;
		if ((views[0]).getComponent() instanceof ViewStatoAvanzamentoProcessi) {
			ViewStatoAvanzamentoProcessi currView = (ViewStatoAvanzamentoProcessi) views[0]
					.getComponent();
			Vector procName = new Vector();
			LinkedList<Processo> processi = configurazioneIniziale.getListaProcessi();
			for (int i = 0; i < processi.size(); i++) {
				procName.add(processi.get(i).getNome());
			}
			currView.initializeViewStatoAvanzamentoProcessi(procName);
		}
                
                if ((views[1]).getComponent() instanceof ViewFrameMemoria) {
			ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
					.getComponent();
			currView.inizializzaViewFrame((configurazioneIniziale.getDimensioneRAM() / configurazioneIniziale.getDimensionePagina()), 
                                                        1);
		}
                
                creaPlayer();

	};
        
        /** Istanzia una nuova simulazione */
        private void creaPlayer() {
            player = new Player(configurazioneIniziale);
        }
        
        /** Parte la simulazione */
        private void avviaSimulazione() {
            statoGui = true;
            jButtonApriConfigurazione.setEnabled(false);
            jButtonNuovaConfigurazione.setEnabled(false);
            jButtonSalvaConfigurazione.setEnabled(false);
            jButtonSimulazioneFine.setEnabled(false);
            jButtonSimulazioneInizio.setEnabled(false);
            jFileItemApriConfigurazione.setEnabled(false);
            jFileItemNuovaConfigurazione.setEnabled(false);
            jFileItemSalvaConfigurazione.setEnabled(false);
            jSimulazioneItemFine.setEnabled(false);
            jSimulazioneItemInizio.setEnabled(false);
            jSimulazioneItemInizio.setEnabled(false);
            jSimulazioneItemPlay.setEnabled(false);
            
            player.caricaSimulazione();
        }
        
        /** Stoppa la simulazione */
        private void fermaSimulazione(){
            jButtonApriConfigurazione.setEnabled(true);
            jButtonNuovaConfigurazione.setEnabled(true);
            jButtonSalvaConfigurazione.setEnabled(true);
            jButtonSimulazioneFine.setEnabled(true);
            jButtonSimulazioneInizio.setEnabled(true);
            jFileItemApriConfigurazione.setEnabled(true);
            jFileItemNuovaConfigurazione.setEnabled(true);
            jFileItemSalvaConfigurazione.setEnabled(true);
            jSimulazioneItemFine.setEnabled(true);
            jSimulazioneItemInizio.setEnabled(true);
            jSimulazioneItemInizio.setEnabled(true);
            jSimulazioneItemPlay.setEnabled(true);
            // completare
        }

        /** Aggiorna il contenuto della vista ViewStatoAvanzamentoProcessi */
	public void visualizzaOrdProcessi(LinkedList<Processo> processiEseguiti) {          
            // aggiorna il grafico ordinamento dei processi
            if ((views[0]).getComponent() instanceof ViewStatoAvanzamentoProcessi) {
                    ViewStatoAvanzamentoProcessi currView = (ViewStatoAvanzamentoProcessi) views[0]
                                    .getComponent();
                    int idProcessiEseguiti[] = new int[processiEseguiti.size()];
                    for (int i = 0; i < processiEseguiti.size(); i++)
                        idProcessiEseguiti[i] = processiEseguiti.get(i).getId();

                    currView.disegnaGrafico(idProcessiEseguiti);
            }
        }
        
    public ConfigurazioneIniziale getConfigurazioneIniziale() {
        return configurazioneIniziale;
    }

    public void setConfigurazioneIniziale(ConfigurazioneIniziale configurazioneIniziale) {
        this.configurazioneIniziale = configurazioneIniziale;
    }

        

	public static void main(String[] args) throws Exception {
		// Set InfoNode Look and Feel
		UIManager.setLookAndFeel(new InfoNodeLookAndFeel());

		// Docking windwos should be run in the Swing thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new SiGeMv2View();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
