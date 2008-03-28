/*
 * Azienda: Stylosoft
 * Nome file: SiGeMv2View
 * Package: gui
 * Autore: Cariani Giordano
 * Data: 21/03/2008
 * Versione: 1.8
 * Licenza: open-source
 * Registro delle modifiche:
 * - v 1.11 (21/03/2008): cambiato il Look and Feel
 * - v 1.10 (19/03/2008): inserimento frame per la visualizzazione dello Swap
 * - v 1.9 (18/03/2008): creazione dei metodi per impostare una configurazione
 *                       di default
 * - v 1.8 (16/03/2008): collegamento alla guida
 * - v 1.7 (15/03/2008): preparazione dell'interfaccia per ospitare la modalita'
 *                       testuale e il riepilogo della configurazione
 * - v 1.6 (13/03/2008): interfacciamento con il frame delle statistiche
 * - v 1.5 (10/03/2008): creazione delle funzioni per l'appertura e il salvataggio
 *                       di una configurazione su file
 * - v 1.4 (08/03/2008): integrazione della visualizzazione della memoria
 * - v 1.3 (07/03/2008): ottimizzazione della gestione dei tasti del player
 * - v 1.2 (05/03/2008): integrazione del Player
 * - v 1.1 (02/03/2008): Create le 3 finestre e inizializzazione grafico processi (dopo wizard)
 * - v 1.0 (01/03/2008): Creato scheletro interfaccia grafica
 * */

package gui;

import com.centerkey.utils.BareBonesBrowserLaunch;
import java.io.IOException;
import net.infonode.docking.*;
import net.infonode.docking.mouse.DockingWindowActionMouseButtonListener;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.*;
import net.infonode.docking.util.*;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.util.Direction;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

import com.jgoodies.looks.HeaderStyle;
import com.jgoodies.looks.Options;


import gui.view.*;
import gui.dialog.*;
import gui.utility.IconStylosoft;

import gui.utility.ImageFilter;
import java.io.File;
import java.net.URL;
import logic.caricamento.GestioneFile;
import logic.gestioneMemoria.Azione;
import logic.parametri.ConfigurazioneIniziale;
import logic.parametri.Processo;
import logic.simulazione.*;
import logic.schedulazione.PCB;
import logic.simulazione.Player.Evento;


public class SiGeMv2View {

    // ------------------------------
    // CAMPI DATI INTERFACCIA GRAFICA
    // ------------------------------

    /**
     * La finestra principale alla quale vengono aggiunte le View, i menu e le
     * ToolBar.
     **/
    private RootWindow rootWindow;

    /**
     * Array delle viste statiche
     */
    private View[] views = new View[6];

    /**
     * Contiene le viste statiche
     */
    private ViewMap viewMap = new ViewMap();

    /**
     * Gli elementi del menu view 
     */
    private JMenuItem[] viewItems = new JMenuItem[views.length];

    /** 
     * Il tema applicato all'interfaccia grafica 
     */
    private DockingWindowsTheme currentTheme = new BlueHighlightDockingTheme();

    /**
     * Un oggetto che contiene tutte le modifiche apportate alle caratteristiche
     * di pulsanti, menu, grafica... Ogni volta che si cambia il tema, questo
     * oggetto viene resettato
     */
    private RootWindowProperties properties = new RootWindowProperties();

    /** 
     * Il frame dell'applicazione 
     */
    private JFrame frame = new JFrame("SiGeM - StyloSoft");

    /** 
     * Rappresenta lo stato dela Gui, vale true se la simulazione e' in 
     * avanzamento automatico, false, se e' interrotta.
     */
    private boolean statoGui = false;

    // PULSANTI E MENU

    // Pulsanti ToolBar
    private JButton jButtonSimulazionePlay, jButtonSimulazioneStop;
    private JButton jButtonSimulazioneInizio, jButtonSimulazioneFine;
    private JButton jButtonSimulazioneIndietro, jButtonSimulazioneAvanti;
    private JButton jButtonSimulazionePausa;
    private JButton jButtonIndietroSignificativo, jButtonAvantiSignificativo;

    private JButton jButtonNuovaConfigurazione, jButtonApriConfigurazione;
    private JButton jButtonSalvaConfigurazione, jButtonModificaConfigurazione;
    
    JSpinner scegliVelocita;
    
    private JComboBox comboBoxSignificativo;

    // Pulsanti Menu Simulazione
    private JMenuItem jSimulazioneItemPlay, jSimulazioneItemStop;
    private JMenuItem jSimulazioneItemInizio, jSimulazioneItemFine;
    private JMenuItem jSimulazioneItemIndietro, jSimulazioneItemAvanti;
    private JMenuItem jSimulazioneItemPausa;
    private JMenu jSimulazioneItemAvantiSignificativo;
    private JMenu jSimulazioneItemIndietroSignificativo;
    private JMenuItem itemAvantiFault, itemAvantiSwitch, itemAvantiFullRAM;
    private JMenuItem itemAvantiFullSwap,itemAvantiFineProc,itemAvantiNuovoProc;
    private JMenuItem itemIndietroFault, itemIndietroSwitch, itemIndietroFullRAM;
    private JMenuItem itemIndietroFullSwap,itemIndietroFineProc;
    private JMenuItem itemIndietroNuovoProc;

    // Pulsanti Menu File
    private JMenuItem jFileItemNuovaConfigurazione, jFileItemApriConfigurazione;

    private JMenuItem jFileItemSalvaConfigurazione;
    private JMenuItem jFileItemSalvaConfigurazioneConNome;
    private JMenuItem jFileItemModificaConfigurazione, jFileItemEsci;
    private JMenuItem jFileItemDefaultConfigurazione;

    /**
     * Wizard per la configurazione dei processi 
     */
    private ConfigurazioneAmbienteJDialog  configurazioneAmbiente;

    /**
     * Configurazione iniziale 
     */
    private ConfigurazioneIniziale configurazioneIniziale;

    /** Player */
    private Player player;

    /** Istante */
    private Istante istante;

    /** Processi */
    LinkedList<Processo> processiEseguiti;

    /** 
     * Thread per aumentare l'interattivita' del utente durante l'avanzamento 
     * automatico 
     */
    private Thread auto;
    
    /**
     * Velocita' di avanzamento nella modalità automatica
     */
    private int velocita;
    
    /**
     * La lista dei processi ultimati nei vari istanti
     */
    Vector<Vector<Integer>> processiUltimati = null;
    
    /**
     * La lista dei context-switch avvenuti nella simulazione
     */
    LinkedList<Boolean> contextSwitchs = null;
    
    /**
     * Istanza di GestioneFile per le operazioni di IO
     */
    GestioneFile gestione;
    
    /**
     * Un'istanza fittizia di processo
     */
    private static final Processo PROC_VUOTO = new Processo("",0,0);
    
    // ----------------------------------
    // METODI GESTIONE COMPONENTI GRAFICI
    // ----------------------------------

    /**
     * Costruttore della classe.
     * Crea il frame principale e tutti i suoi sotto-frame.
     */
    public SiGeMv2View() throws Exception {
            // i controlli da fare prima della chiusura dell'applicazione
            String imageName = "/images/Stylosoft2.png";

            // Get a URL pointing to the image
            URL iconURL = this.getClass().getResource(imageName);
            if(iconURL!=null){
                Image img = Toolkit.getDefaultToolkit().getImage(iconURL);
                frame.setIconImage(img);
            }
            frame.addWindowListener(new WindowAdapter() {
                    @Override
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
            caricaSimulazioneDefault();
    }
    
    /**
     * Disegna le statistiche sulla vista ViewStatistiche 
     */
    private void visualizzaStatisticheSimulazione(Player player, Istante istante, boolean avanti) {
            if ((views[2]).getComponent() instanceof ViewStatistiche) {
                    ViewStatistiche currView = (ViewStatistiche) views[2].getComponent();
                    currView.generaStatistiche(player, istante, avanti);
            }
    }
    
    /**
     * Azzera le statistiche sulla vista ViewStatistiche 
     */
    private void azzeraStatisticheSimulazione() {
            if ((views[2]).getComponent() instanceof ViewStatistiche) {
                    ViewStatistiche currView = (ViewStatistiche) views[2].getComponent();
                    currView.azzeraStatistiche(contextSwitchs,processiUltimati,configurazioneIniziale);
            }
    }
    
    /**
     * Aggiorna il frame della modalita' testuale, per la visualizzazione
     * della simulazione.
     */
    private void visualizzaSimulazioneTestuale(Istante istante, int numero) {
            if ((views[3]).getComponent() instanceof ViewAvanzamentoTestuale) {
                    ViewAvanzamentoTestuale currView = (ViewAvanzamentoTestuale) views[3].getComponent();
                    currView.aggiorna(istante,numero);
            }
    }
    
    /**
     * Azzera il frame della visualizzazione testuale.
     */
    private void azzeraSimulazioneTestuale(int tot, int modalita) {
            if ((views[3]).getComponent() instanceof ViewAvanzamentoTestuale) {
                    ViewAvanzamentoTestuale currView = (ViewAvanzamentoTestuale) views[3].getComponent();
                    currView.configura(tot, modalita);
            }
    }

    /**
     * Mostra il riepilogo della configurazione correntemente caricata.
     */
    private void visualizzaRiepilogo() {
        if ((views[4]).getComponent() instanceof ViewRiepilogo) {
                    ViewRiepilogo currView = (ViewRiepilogo) views[4].getComponent();
                    currView.azzeraRiepilogo();
                    currView.aggiorna(this.configurazioneIniziale);
            }
    }

    /**
     * Aggiorna il contenuto della vista ViewStatoAvanzamentoProcessi
     */
    public void visualizzaOrdProcessi(LinkedList<Processo> processiEseguiti) {          
        // aggiorna il grafico ordinamento dei processi
        if ((views[0]).getComponent() instanceof ViewStatoAvanzamentoProcessi) {
                ViewStatoAvanzamentoProcessi currView = (ViewStatoAvanzamentoProcessi) views[0]
                                .getComponent();
                int idProcessiEseguiti[] = new int[processiEseguiti.size()];
                for (int i = 0; i < processiEseguiti.size(); i++){
                    Processo p = processiEseguiti.get(i);
                    // Esiste un processo in esecuzione nell'istante i
                    if(p!=PROC_VUOTO){
                        idProcessiEseguiti[i] = p.getId();
                    }
                    // Nessun processo nell'istante i
                    else{
                        idProcessiEseguiti[i]=-1;
                    }
                }

                currView.disegnaGrafico(idProcessiEseguiti);
        }
    }

    /**
     * Invoca il primo wizard per la creazione di una nuova configurazione.
     */
    private void creaConfigurazione() {
        configurazioneAmbiente = new ConfigurazioneAmbienteJDialog(frame, true, this);
        configurazioneAmbiente.setVisible(true);
    }

    /**
     * Crea la root window e aggiunge le views.
     */
    private void createRootWindow() {

            // Creo le viste e le aggiungo al viewMap
            views[0] = new View("Processi",
                            IconStylosoft.getGeneralIcon("processi"), new ViewStatoAvanzamentoProcessi());
            viewMap.addView(0, views[0]);

            views[1] = new View("RAM", IconStylosoft.getGeneralIcon("mv"),
                            new ViewFrameMemoria());
            viewMap.addView(1, views[1]);
            views[2] = new View("Statistiche simulazione", IconStylosoft
                            .getGeneralIcon("statistiche"), new ViewStatistiche());
            viewMap.addView(2, views[2]);
            views[3] = new View("Modalita' testuale", IconStylosoft
                            .getGeneralIcon(""),  new ViewAvanzamentoTestuale());
            viewMap.addView(3, views[3]);
            views[4] = new View("Riepilogo configurazione", IconStylosoft
                            .getGeneralIcon(""), new ViewRiepilogo());
            viewMap.addView(4, views[4]);
            views[5] = new View("Swap", IconStylosoft
                            .getGeneralIcon("mv"), new ViewFrameMemoria());
            viewMap.addView(5, views[5]);

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
            views[1].getCustomTabComponents().add(button);

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
                    @Override
                    public void windowAdded(DockingWindow addedToWindow,
                                    DockingWindow addedWindow) {
                            updateViews(addedWindow, true);
                    }

                    @Override
                    public void windowRemoved(DockingWindow removedFromWindow,
                                    DockingWindow removedWindow) {
                            updateViews(removedWindow, false);
                    }

                    @Override
                    public void windowClosing(DockingWindow window)
                                    throws OperationAbortedException {
                            // Confirm close operation
                            if (JOptionPane.showConfirmDialog(frame,
                                            "Really close window '" + window + "'?") != JOptionPane.YES_OPTION)
                                    throw new OperationAbortedException(
                                                    "Window close was aborted!");
                    }

                    @Override
                    public void windowDocking(DockingWindow window)
                                    throws OperationAbortedException {
                            // Confirm dock operation
                            if (JOptionPane.showConfirmDialog(frame, "Really dock window '"
                                            + window + "'?") != JOptionPane.YES_OPTION)
                                    throw new OperationAbortedException(
                                                    "Window dock was aborted!");
                    }

                    @Override
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
                    public void paint(Component component, Graphics g, int x,
                                    int y, int width, int height) {
                            Graphics2D g2 = (Graphics2D) g;
                            Composite oldComp = g2.getComposite();

                            // Imposto un alpha per rendere il rettangolo trasparente
                            AlphaComposite composite = AlphaComposite.getInstance(
                                            AlphaComposite.SRC_OVER, 0.6f);
                            g2.setComposite(composite);

                            // Coloro il rettangolo con un gradiente azzurro
                            GradientPaint background = new GradientPaint(
                                                width,
                                                0,new Color(129, 156, 231),
                                                0,
                                                height,
                                                new Color(74, 115, 231));
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
            //Applico il filtro alla finestra principale
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
     * Crea la barra dei menu.
     * 
     * @return la barra dei menù
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
     * 
     * @return the layout menu
     */
    private JMenu createFileMenu() {
            JMenu fileMenu = new JMenu("File");

            jFileItemNuovaConfigurazione = new JMenuItem("Nuova Configurazione",IconStylosoft.getGeneralIcon("newConf"));
            jFileItemNuovaConfigurazione.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                            creaConfigurazione();
                    }
            });
            fileMenu.add(jFileItemNuovaConfigurazione);

            jFileItemApriConfigurazione = new JMenuItem("Apri Configurazione",IconStylosoft.getGeneralIcon("openFile"));
            jFileItemApriConfigurazione.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                            apriFileConfigurazione();
                    }
            });
            fileMenu.add(jFileItemApriConfigurazione);

            fileMenu.addSeparator();

            jFileItemSalvaConfigurazione = new JMenuItem("Salva",IconStylosoft.getGeneralIcon("save"));
            jFileItemSalvaConfigurazione.setEnabled(false);
            jFileItemSalvaConfigurazione.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                            salvaConfigurazione();
                    }
            });
            fileMenu.add(jFileItemSalvaConfigurazione);

            jFileItemSalvaConfigurazioneConNome = new JMenuItem("Salva con nome",IconStylosoft.getGeneralIcon("save"));
            jFileItemSalvaConfigurazioneConNome.setEnabled(false);
            jFileItemSalvaConfigurazioneConNome.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                            salvaConfigurazioneConNome();
                    }
            });
            fileMenu.add(jFileItemSalvaConfigurazioneConNome);

            fileMenu.addSeparator();

            jFileItemModificaConfigurazione = new JMenuItem("Modifica configurazione caricata",IconStylosoft.getGeneralIcon("mod"));
            jFileItemModificaConfigurazione.setEnabled(false);
            jFileItemModificaConfigurazione.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        modificaConfigurazione();
                    }
            });
            fileMenu.add(jFileItemModificaConfigurazione);
            
            jFileItemDefaultConfigurazione = new JMenuItem("Imposta la configurazione \"di default\"",IconStylosoft.getGeneralIcon(""));
            jFileItemDefaultConfigurazione.setEnabled(false);
            jFileItemDefaultConfigurazione.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        defaultConfigurazione();
                    }
            });
            fileMenu.add(jFileItemDefaultConfigurazione);

            fileMenu.addSeparator();

            jFileItemEsci = new JMenuItem("Esci",IconStylosoft.getGeneralIcon("exit"));
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

            jSimulazioneItemPlay = new JMenuItem("Play",IconStylosoft.getGeneralIcon("play"));
            jSimulazioneItemPlay.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        simulazionePlay();
                    }
            });
            simulazioneMenu.add(jSimulazioneItemPlay);

            jSimulazioneItemPausa = new JMenuItem("Pausa",IconStylosoft.getGeneralIcon("pause"));
            jSimulazioneItemPausa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    simulazionePausa();
                }
            });
            simulazioneMenu.add(jSimulazioneItemPausa);
            
            jSimulazioneItemStop = new JMenuItem("Stop",IconStylosoft.getGeneralIcon("stop"));
            jSimulazioneItemStop.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneStop();
                }
            });
            simulazioneMenu.add(jSimulazioneItemStop);

            simulazioneMenu.addSeparator();

            jSimulazioneItemInizio = new JMenuItem("Primo istante",IconStylosoft.getGeneralIcon("prev"));
            jSimulazioneItemInizio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneInizio();
                }
            });
            simulazioneMenu.add(jSimulazioneItemInizio);

            jSimulazioneItemIndietroSignificativo = new JMenu("Istante significativo precedente");

            simulazioneMenu.add(jSimulazioneItemIndietroSignificativo);

            // sotto-menù
                    itemIndietroFault = new JMenuItem("Fault in memoria");
                    itemIndietroFault.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoPrecedente(0);
                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(itemIndietroFault);

                    itemIndietroSwitch = new JMenuItem("Context switch");
                    itemIndietroSwitch.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoPrecedente(1);
                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(itemIndietroSwitch);

                    itemIndietroFullRAM = new JMenuItem("Riempimento RAM");
                    itemIndietroFullRAM.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoPrecedente(2);
                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(itemIndietroFullRAM);

                    itemIndietroFullSwap = new JMenuItem("Riempimento Swap");
                    itemIndietroFullSwap.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoPrecedente(3);
                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(itemIndietroFullSwap);

                    itemIndietroFineProc= new JMenuItem("Terminazione di un processo");
                    itemIndietroFineProc.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoPrecedente(4);
                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(itemIndietroFineProc);

                    itemIndietroNuovoProc= new JMenuItem("Arrivo di un nuovo processo");
                    itemIndietroNuovoProc.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoPrecedente(5);
                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(itemIndietroNuovoProc);



            jSimulazioneItemIndietro = new JMenuItem("Istante precedente",IconStylosoft.getGeneralIcon("rew"));
            jSimulazioneItemIndietro.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneIndietro();
                }
            });
            simulazioneMenu.add(jSimulazioneItemIndietro);

            jSimulazioneItemAvanti = new JMenuItem("Istante successivo",IconStylosoft.getGeneralIcon("ffwd"));
            jSimulazioneItemAvanti.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneAvanti();
                }
            });
            simulazioneMenu.add(jSimulazioneItemAvanti);

            jSimulazioneItemAvantiSignificativo = new JMenu("Istante significativo successivo");
            jSimulazioneItemAvantiSignificativo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            simulazioneMenu.add(jSimulazioneItemAvantiSignificativo);


            // sotto-menù
                    itemAvantiFault= new JMenuItem("Fault in memoria");
                    itemAvantiFault.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoSuccessivo(0);
                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(itemAvantiFault);

                    itemAvantiSwitch = new JMenuItem("Context switch");
                    itemAvantiSwitch.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoSuccessivo(1);
                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(itemAvantiSwitch);

                    itemAvantiFullRAM = new JMenuItem("Riempimento RAM");
                    itemAvantiFullRAM.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoSuccessivo(2);
                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(itemAvantiFullRAM);

                    itemAvantiFullSwap = new JMenuItem("Riempimento Swap");
                    itemAvantiFullSwap.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoSuccessivo(3);
                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(itemAvantiFullSwap);

                    itemAvantiFineProc = new JMenuItem("Terminazione di un processo");
                    itemAvantiFineProc.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoSuccessivo(4);
                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(itemAvantiFineProc);

                    itemAvantiNuovoProc = new JMenuItem("Arrivo nuovo processo");
                    itemAvantiNuovoProc.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoSuccessivo(5);
                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(itemAvantiNuovoProc);                


            jSimulazioneItemFine = new JMenuItem("Istante finale",IconStylosoft.getGeneralIcon("next"));
            jSimulazioneItemFine.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneFine();
                }
            });
            simulazioneMenu.add(jSimulazioneItemFine);


            jSimulazioneItemFine.setEnabled(false);
            jSimulazioneItemInizio.setEnabled(false);
            jSimulazioneItemIndietro.setEnabled(false);
            jSimulazioneItemAvanti.setEnabled(false);
            jSimulazioneItemPausa.setEnabled(false);
            jSimulazioneItemPlay.setEnabled(false);
            jSimulazioneItemStop.setEnabled(false);
            jSimulazioneItemAvantiSignificativo.setEnabled(false);
            jSimulazioneItemIndietroSignificativo.setEnabled(false);

            return simulazioneMenu;
    }

    /**
     * Crea il menu Help
     * 
     * @return the view menu
     */
    private JMenu createHelpMenu() {
            JMenu helpMenu = new JMenu("Help");
            JMenuItem mniHelp = new JMenuItem("Help",IconStylosoft.getGeneralIcon("help"));
            JMenuItem InfoSu = new JMenuItem("Informazioni su...",IconStylosoft.getGeneralIcon("info"));
            helpMenu.add(mniHelp);
            helpMenu.addSeparator();
            helpMenu.add(InfoSu);
            
            mniHelp.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //apre la guida
                            String URL="file:///"+System.getProperty("user.dir")+File.separatorChar+"help"+File.separatorChar+"pagina.htm";
                            URL=URL.replace("\\", "/");
                            URL=URL.replace(" ","%20");
                            BareBonesBrowserLaunch.openURL(URL);
                        }
                    });
            
            InfoSu.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            new About(frame,true).setVisible(true);
                        }
                    });
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
                            apriFileConfigurazione();
                    }
            });
            jButtonApriConfigurazione.setToolTipText("Carica una configurazione precedentemente "
                            + "salvata");

            jButtonSalvaConfigurazione = new JButton(IconStylosoft.getGeneralIcon("save"));
            jButtonSalvaConfigurazione.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                            salvaConfigurazione();
                    }
            });
            jButtonSalvaConfigurazione.setToolTipText("Salva una simulazione su disco");

            jButtonModificaConfigurazione = new JButton(IconStylosoft.getGeneralIcon("mod")); // modificare icona
            jButtonModificaConfigurazione.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        modificaConfigurazione();
                    }
            });
            jButtonModificaConfigurazione.setToolTipText("Modifica la" +
                    " simulazione correntemente caricata");


            jButtonSimulazionePlay = new JButton(IconStylosoft.getGeneralIcon("play"));
            jButtonSimulazionePlay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazionePlay();
                }
            });
            jButtonSimulazionePlay.setToolTipText("Avvia l'avanzamento automatico della "
                            + "simulazione");

            jButtonSimulazionePausa = new JButton(IconStylosoft.getGeneralIcon("pause"));
            jButtonSimulazionePausa.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazionePausa();
                }
            });
            jButtonSimulazionePausa.setToolTipText("Ferma la simulazione in questo istante");

            jButtonSimulazioneStop = new JButton(IconStylosoft.getGeneralIcon("stop"));
            jButtonSimulazioneStop.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                            simulazioneStop();
                    }
            });
            jButtonSimulazioneStop.setToolTipText("Ferma l'avanzamento automatico della "
                            + "simulazione");



            jButtonSimulazioneInizio = new JButton(IconStylosoft.getGeneralIcon("prev"));
            jButtonSimulazioneInizio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneInizio();
                }
            });
            jButtonSimulazioneInizio.setToolTipText("Riporta la simulazione all'istante iniziale");

            jButtonSimulazioneIndietro = new JButton(IconStylosoft.getGeneralIcon("rew"));
            jButtonSimulazioneIndietro.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                   simulazioneIndietro();
                }
            });
            jButtonSimulazioneIndietro.setToolTipText("Porta la simulazione all'istante precedente");

            jButtonSimulazioneAvanti = new JButton(IconStylosoft.getGeneralIcon("ffwd"));
            jButtonSimulazioneAvanti.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneAvanti();
                }
            });
            jButtonSimulazioneAvanti.setToolTipText("Porta la simulazione all'istante successivo");

            jButtonSimulazioneFine = new JButton(IconStylosoft.getGeneralIcon("next"));
            jButtonSimulazioneFine.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneFine();
                }
            });
            jButtonSimulazioneFine.setToolTipText("Porta la simulazione all'istante finale");

            
            jButtonIndietroSignificativo = new JButton(IconStylosoft.getGeneralIcon("prevSign"));
            jButtonIndietroSignificativo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneSignificativoPrecedente(
                            comboBoxSignificativo.getSelectedIndex());
                }
            });
            jButtonIndietroSignificativo.setToolTipText("Porta la simulazione" +
                    " all'istante significativo precedente");
            
            
            comboBoxSignificativo = new JComboBox();
            comboBoxSignificativo.setModel(new DefaultComboBoxModel(
                    new String[] { "Fault in memoria",
                                   "Context switch",
                                   "Riempimento RAM",
                                   "Riempimento area di Swap",
                                   "Terminazione di un processo",
                                   "Arrivo di un nuovo processo"}));
            comboBoxSignificativo.setToolTipText("Evento significativo");

            jButtonAvantiSignificativo = new JButton(IconStylosoft.getGeneralIcon("nextSign"));
            jButtonAvantiSignificativo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneSignificativoSuccessivo(
                            comboBoxSignificativo.getSelectedIndex());
                }
            });
            jButtonAvantiSignificativo.setToolTipText("Porta la simulazione" +
                    " all'istante significativo successivo");
            
            scegliVelocita = new JSpinner();
            Double[] velocitaConsentite = new Double[20];
            double t;
            for(int i=1;i<=20;i++){
                t = i;
                velocitaConsentite[i-1]=Double.valueOf(t/2);
            }
            scegliVelocita.setModel(new SpinnerListModel(velocitaConsentite));
            scegliVelocita.setToolTipText("Velocita' di avanzamento della simulazione");

            toolBar.setRollover(true);
            toolBar.add(jButtonNuovaConfigurazione);
            toolBar.add(jButtonApriConfigurazione);
            toolBar.add(jButtonSalvaConfigurazione);
            toolBar.add(jButtonModificaConfigurazione);

            toolBar.addSeparator(new java.awt.Dimension(25, 12));

            toolBar.add(jButtonSimulazionePlay);
            toolBar.add(jButtonSimulazionePausa);
            toolBar.add(jButtonSimulazioneStop);

            toolBar.addSeparator(new java.awt.Dimension(15, 12));

            toolBar.add(jButtonSimulazioneInizio);
            toolBar.add(jButtonSimulazioneIndietro);
            toolBar.add(jButtonSimulazioneAvanti);
            toolBar.add(jButtonSimulazioneFine);

            toolBar.addSeparator(new java.awt.Dimension(25, 12));
            
            toolBar.add(jButtonIndietroSignificativo);
            toolBar.add(comboBoxSignificativo);
            toolBar.add(jButtonAvantiSignificativo);
            
            toolBar.addSeparator(new java.awt.Dimension(20, 12));
            
            toolBar.add(new JLabel("Velocita': "));
            toolBar.add(scegliVelocita);
            
            toolBar.addSeparator(new java.awt.Dimension(30, 12));

            // Stato iniziale dei pulsanti
            jButtonSalvaConfigurazione.setEnabled(false);
            jButtonModificaConfigurazione.setEnabled(false);
            
            jButtonSimulazionePlay.setEnabled(false);
            jButtonSimulazionePausa.setEnabled(false);
            jButtonSimulazioneStop.setEnabled(false);
            jButtonSimulazioneInizio.setEnabled(false);
            jButtonSimulazioneIndietro.setEnabled(false);
            jButtonSimulazioneAvanti.setEnabled(false);
            jButtonSimulazioneFine.setEnabled(false);
            
            jButtonIndietroSignificativo.setEnabled(false);
            comboBoxSignificativo.setEnabled(false);
            jButtonAvantiSignificativo.setEnabled(false);

            toolBar.add(Box.createHorizontalGlue());
            toolBar.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
            return toolBar;
    }

    /**
     * Imposta il layout di default delle viste
     */
    private void setDefaultLayout() {
        rootWindow.setWindow(new TabWindow(new DockingWindow[] {
                 views[4],
                 views[3],
                 new SplitWindow(true,
                                 0.644f, 
                                 new SplitWindow(false,
                                                 0.70f, 
                                                 views[0],
                                                 views[2]),
                                 new SplitWindow(false, 0.5f, views[1], views[5])
                                 )
                  }));
            WindowBar windowBar = rootWindow.getWindowBar(Direction.DOWN);

            while (windowBar.getChildWindowCount() > 0){
                    windowBar.getChildWindow(0).close();
            }

    }

    /**
     * Inizializza il frame principale e lo rende visibile
     */
    private void showFrame() {
            frame.getContentPane().add(createToolBar(), BorderLayout.NORTH);
            frame.getContentPane().add(rootWindow, BorderLayout.CENTER);
            frame.setJMenuBar(createMenuBar());
            frame.setSize(1024, 768);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * Abilita ogni comando.
     */
    public void abilitaTutto(){
        jButtonNuovaConfigurazione.setEnabled(true);
        jButtonApriConfigurazione.setEnabled(true);
        jButtonSalvaConfigurazione.setEnabled(true);
        jButtonModificaConfigurazione.setEnabled(true);
        jFileItemDefaultConfigurazione.setEnabled(true);
        
        jButtonSimulazionePlay.setEnabled(true);
        jButtonSimulazionePausa.setEnabled(false);
        jButtonSimulazioneStop.setEnabled(false);
        jButtonSimulazioneInizio.setEnabled(false);
        jButtonSimulazioneIndietro.setEnabled(false);
        jButtonSimulazioneAvanti.setEnabled(true);
        jButtonSimulazioneFine.setEnabled(true);
        
        jButtonIndietroSignificativo.setEnabled(false);
        comboBoxSignificativo.setEnabled(true);
        jButtonAvantiSignificativo.setEnabled(true);

                
        jFileItemNuovaConfigurazione.setEnabled(true);
        jFileItemApriConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazioneConNome.setEnabled(true);
        jFileItemModificaConfigurazione.setEnabled(true);
        jFileItemEsci.setEnabled(true);
                
        jSimulazioneItemPlay.setEnabled(true);
        jSimulazioneItemPausa.setEnabled(false);
        jSimulazioneItemStop.setEnabled(false);
        jSimulazioneItemInizio.setEnabled(false);
        jSimulazioneItemIndietro.setEnabled(false);
        jSimulazioneItemAvantiSignificativo.setEnabled(true);
        jSimulazioneItemIndietroSignificativo.setEnabled(false);
        jSimulazioneItemAvanti.setEnabled(true);
        jSimulazioneItemFine.setEnabled(true);
    }
    
    /**
     * Abilita i comandi in base alla necessita'.
     */
    private void aggiornaComandi(){
        jButtonNuovaConfigurazione.setEnabled(true);
        jButtonApriConfigurazione.setEnabled(true);
        jButtonSalvaConfigurazione.setEnabled(true);
        jButtonModificaConfigurazione.setEnabled(true);
        jFileItemDefaultConfigurazione.setEnabled(true);
        
        jButtonSimulazionePlay.setEnabled(true);
        jButtonSimulazionePausa.setEnabled(true);
        jButtonSimulazioneStop.setEnabled(true);
        
        if(player.hasPrev())
            jButtonSimulazioneInizio.setEnabled(true);
        else
            jButtonSimulazioneInizio.setEnabled(false);
        
        if(player.hasPrev())
            jButtonSimulazioneIndietro.setEnabled(true);
        else
            jButtonSimulazioneIndietro.setEnabled(false);
        
        if(player.hasNext())
            jButtonSimulazioneAvanti.setEnabled(true);
        else
            jButtonSimulazioneAvanti.setEnabled(false);

        if(player.hasNext())
            jButtonSimulazioneFine.setEnabled(true);
        else
            jButtonSimulazioneFine.setEnabled(false);

        if(player.hasPrev())
            jButtonIndietroSignificativo.setEnabled(true);
        else
            jButtonIndietroSignificativo.setEnabled(false);

        if(player.hasPrev() || player.hasNext())
            comboBoxSignificativo.setEnabled(true);
        else
            comboBoxSignificativo.setEnabled(false);
        
        if(player.hasNext())
            jButtonAvantiSignificativo.setEnabled(true);
        else
            jButtonAvantiSignificativo.setEnabled(false);

                
        jFileItemNuovaConfigurazione.setEnabled(true);
        jFileItemApriConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazioneConNome.setEnabled(true);
        jFileItemModificaConfigurazione.setEnabled(true);
        jFileItemEsci.setEnabled(true);
                
        jSimulazioneItemPlay.setEnabled(true);
        jSimulazioneItemPausa.setEnabled(true);
        jSimulazioneItemStop.setEnabled(true);
        
        
        if(player.hasPrev())
            jSimulazioneItemInizio.setEnabled(true);
        else
            jSimulazioneItemInizio.setEnabled(false);
        
        if(player.hasPrev())
            jSimulazioneItemIndietro.setEnabled(true);
        else
            jSimulazioneItemIndietro.setEnabled(false);

        if(player.hasPrev())
            jSimulazioneItemIndietroSignificativo.setEnabled(true);
        else
            jSimulazioneItemIndietroSignificativo.setEnabled(false);

        if(player.hasNext())
            jSimulazioneItemAvantiSignificativo.setEnabled(true);
        else
            jSimulazioneItemAvantiSignificativo.setEnabled(false);

        if(player.hasNext())
            jSimulazioneItemAvanti.setEnabled(true);
        else
            jSimulazioneItemAvanti.setEnabled(false);
        
        if(player.hasNext())
            jSimulazioneItemFine.setEnabled(true);
        else
            jSimulazioneItemFine.setEnabled(false);
    }

    /** 
     * Inizializza le viste e prepara l'applicazione per l'esecuzione della simulazione
     */
    public synchronized void setIstanteZero() {

            statoGui = false;
            
            // viene creato il player
            creaPlayer();
            // la simulazione viene calcolata, in caso contrario l'utente viene
            // avvisato dell'errore
            if (!simulazioneCarica()) {
                JOptionPane.showMessageDialog(rootWindow, "Impossibile eseguire la simulazione", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LinkedList<Istante> listaDegliIstanti = player.ultimoIstante();
            elaboraProcessiTerminati(listaDegliIstanti);
            elaboraContextSwitch(listaDegliIstanti);

            // se nella simulazione ci sono errori l'utente viene avvisato.
            if (player.fullSwapNellaSimulazione()) {
                JOptionPane.showMessageDialog(rootWindow, "Durante la simulzione e' stata esautita la memoria (RAM e Swap).\n" + "Questa situazione viene gestita da SiGeM come un errore.\n" + "In questi casi la simulazione viene terminata prematuramente\n" + "rispetto agli effettivi istanti di cui si dovrebbe comporre.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }

            // viene caricato il primo istante
            istante = player.primoIstante();

            // creazione lista processi terminati
            //processiUltimati = new Vector<Vector<Integer>>();
            //processiUltimati.add(0, new Vector<Integer>());
            // aggiorno il grafico dei processi
            //aggiornaProcessiTerminati(0);
            // abilito tutti i controlli
            abilitaTutto();

            // aggiorno il grafico dei processi
            if ((views[0]).getComponent() instanceof ViewStatoAvanzamentoProcessi) {
                ViewStatoAvanzamentoProcessi currView = (ViewStatoAvanzamentoProcessi) views[0]
                                .getComponent();
                Vector procName = new Vector();
                if (configurazioneIniziale != null) {
                    LinkedList<Processo> processi = configurazioneIniziale.getListaProcessi();
                    for (int i = 0; i < processi.size(); i++) {
                        procName.add(processi.get(i).getNome());
                    }
                    currView.initializeViewStatoAvanzamentoProcessi(procName);
                }
            }

            // aggiorno lo schema della RAM
            if ((views[1]).getComponent() instanceof ViewFrameMemoria) {
                ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                .getComponent();

                if (configurazioneIniziale != null) {
                    currView.configura(configurazioneIniziale.getModalitaGestioneMemoria(), configurazioneIniziale.getDimensioneRAM(), configurazioneIniziale.getListaProcessi().size(), configurazioneIniziale.getDimensioneRAM() / configurazioneIniziale.getDimensionePagina());
                }
            }

            
            if ((views[5]).getComponent() instanceof ViewFrameMemoria) {
                ViewFrameMemoria currView = (ViewFrameMemoria) views[5]
                                .getComponent();

                if (configurazioneIniziale != null) {
                    currView.configura(configurazioneIniziale.getModalitaGestioneMemoria(), configurazioneIniziale.getDimensioneSwap(), configurazioneIniziale.getListaProcessi().size(), configurazioneIniziale.getDimensioneSwap() / configurazioneIniziale.getDimensionePagina());
                }
            }

            // aggiorno i frame
            processiEseguiti = new LinkedList<Processo>();
            visualizzaOrdProcessi(processiEseguiti);
            azzeraStatisticheSimulazione();
            visualizzaStatisticheSimulazione(player, istante, true);
            azzeraSimulazioneTestuale(player.numeroIstanti(), configurazioneIniziale.getModalitaGestioneMemoria());
            visualizzaSimulazioneTestuale(istante, player.getIndiceIstanteCorrente());
            visualizzaRiepilogo();
    };
        
    /**
     * Istanzia un nuovo player
     */
    private void creaPlayer() {
        player = new Player(configurazioneIniziale);
    }

    /**
     * Avanzamento automatico della simulazione
     */
    private synchronized void simulazionePlay() {
        statoGui = true;
        
        jButtonNuovaConfigurazione.setEnabled(false);
        jButtonApriConfigurazione.setEnabled(false);
        jButtonSalvaConfigurazione.setEnabled(false);
        jButtonModificaConfigurazione.setEnabled(false);
        
        jButtonSimulazionePlay.setEnabled(false);
        jButtonSimulazionePausa.setEnabled(true);
        jButtonSimulazioneStop.setEnabled(true);
        jButtonSimulazioneInizio.setEnabled(false);
        jButtonSimulazioneIndietro.setEnabled(false);
        jButtonSimulazioneAvanti.setEnabled(false);
        jButtonSimulazioneFine.setEnabled(false);
        
        jButtonIndietroSignificativo.setEnabled(false);
        comboBoxSignificativo.setEnabled(false);
        jButtonAvantiSignificativo.setEnabled(false);

        jFileItemNuovaConfigurazione.setEnabled(false);
        jFileItemApriConfigurazione.setEnabled(false);
        jFileItemSalvaConfigurazione.setEnabled(false);
        jFileItemSalvaConfigurazioneConNome.setEnabled(false);
        jFileItemModificaConfigurazione.setEnabled(false);
        jFileItemEsci.setEnabled(false);
                
        jSimulazioneItemPlay.setEnabled(false);
        jSimulazioneItemPausa.setEnabled(true);
        jSimulazioneItemStop.setEnabled(true);
        jSimulazioneItemInizio.setEnabled(false);
        jSimulazioneItemIndietro.setEnabled(false);
        jSimulazioneItemAvantiSignificativo.setEnabled(false);
        jSimulazioneItemIndietroSignificativo.setEnabled(false);
        jSimulazioneItemAvanti.setEnabled(false);
        jSimulazioneItemFine.setEnabled(false);
        
        istante = player.istanteSuccessivo();
        
        auto = new Thread(){
            @Override
            @SuppressWarnings("static-access")
            public void run(){
                PCB pcbAttuale;
                ViewFrameMemoria currView1 = (ViewFrameMemoria) views[1]
                                    .getComponent();
                ViewFrameMemoria currView5 = (ViewFrameMemoria) views[5]
                                    .getComponent();
                while(istante!=null && statoGui){
                    Double t =((Double)scegliVelocita.getValue()).doubleValue();
                    velocita = (int) (t * 10);
                    pcbAttuale = istante.getProcessoInEsecuzione();
                    //aggiornaProcessiTerminati(player.getIndiceIstanteCorrente());
                    if(pcbAttuale==null){
                        processiEseguiti.add(PROC_VUOTO);
                    }    
                    else{
                        processiEseguiti.add(pcbAttuale.getRifProcesso());
                    }
                    
                    visualizzaOrdProcessi(processiEseguiti);
                    visualizzaStatisticheSimulazione(player,istante,true);
                    visualizzaSimulazioneTestuale(istante, player.getIndiceIstanteCorrente());
                    try {
                        currView1.aggiorna(istante.getStatoRAM(),
                                           player.getIndiceIstanteCorrente(),
                                           processiUltimati);
                        currView5.aggiorna(istante.getStatoSwap(),
                                           player.getIndiceIstanteCorrente(),
                                           processiUltimati);
                    }catch (Exception e){e.printStackTrace();}
                    
                    try {
                        this.sleep(velocita*100);
                    }catch (InterruptedException e){}
                    
                    if(statoGui){
                        istante = player.istanteSuccessivo();
                    }
                }
                
                // L'esecuzione automatica e' terminata senza interruzioni
                if(statoGui){
                    aggiornaComandi();
                    jButtonNuovaConfigurazione.setEnabled(true);
                    jButtonApriConfigurazione.setEnabled(true);
                    jButtonSalvaConfigurazione.setEnabled(true);
                    jButtonModificaConfigurazione.setEnabled(true);

                    jButtonSimulazionePlay.setEnabled(true);
                    jButtonSimulazionePausa.setEnabled(false);
                    jButtonSimulazioneStop.setEnabled(true);
                    
                    jFileItemNuovaConfigurazione.setEnabled(true);
                    jFileItemApriConfigurazione.setEnabled(true);
                    jFileItemSalvaConfigurazione.setEnabled(true);
                    jFileItemSalvaConfigurazioneConNome.setEnabled(true);
                    jFileItemModificaConfigurazione.setEnabled(true);
                    jFileItemEsci.setEnabled(true);

                    jSimulazioneItemPlay.setEnabled(true);
                    jSimulazioneItemPausa.setEnabled(false);
                    jSimulazioneItemStop.setEnabled(true);
                }
                
                statoGui = false;
                }
        };
        auto.start();
    }

    /** 
     * Crea la simulazione
     */
    private boolean simulazioneCarica() {
        return player.caricaSimulazione();
    }

    /**
     * La simulazione viene fermata e portata all'istante iniziale.
     */
    private void simulazioneStop(){
        try {
            
            // interrompe l'avanzamento automatico e aspetta che il thread
            // venga terminato
            statoGui=false;
            if(auto!=null)
                auto.join();
            
            istante = player.primoIstante();
            processiEseguiti = new LinkedList<Processo>();
            visualizzaOrdProcessi(processiEseguiti);
            azzeraStatisticheSimulazione();
            visualizzaStatisticheSimulazione(player,istante,true);
            
            // Svuota l'interfaccia della RAM
            if ((views[1]).getComponent() instanceof ViewFrameMemoria) {
                    ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                    .getComponent();

                    if(configurazioneIniziale!=null){
                        currView.configura(configurazioneIniziale.getModalitaGestioneMemoria(),
                                           configurazioneIniziale.getDimensioneRAM(),
                                           configurazioneIniziale.getListaProcessi().size(),
                                           configurazioneIniziale.getDimensioneRAM()/configurazioneIniziale.getDimensionePagina());
                    }
            }
            if ((views[5]).getComponent() instanceof ViewFrameMemoria) {
                    ViewFrameMemoria currView = (ViewFrameMemoria) views[5]
                                    .getComponent();

                    if(configurazioneIniziale!=null){
                        currView.configura(configurazioneIniziale.getModalitaGestioneMemoria(),
                                           configurazioneIniziale.getDimensioneSwap(),
                                           configurazioneIniziale.getListaProcessi().size(),
                                           configurazioneIniziale.getDimensioneSwap()/configurazioneIniziale.getDimensionePagina());
                    }
            }
            
            azzeraSimulazioneTestuale(player.numeroIstanti(), 
                                  configurazioneIniziale.getModalitaGestioneMemoria());
            visualizzaSimulazioneTestuale(istante, player.getIndiceIstanteCorrente());

            aggiornaComandi();
            jButtonNuovaConfigurazione.setEnabled(true);
            jButtonApriConfigurazione.setEnabled(true);
            jButtonSalvaConfigurazione.setEnabled(true);
            jButtonModificaConfigurazione.setEnabled(true);

            jButtonSimulazionePlay.setEnabled(true);
            jButtonSimulazionePausa.setEnabled(false);
            jButtonSimulazioneStop.setEnabled(false);
            
            jFileItemNuovaConfigurazione.setEnabled(true);
            jFileItemApriConfigurazione.setEnabled(true);
            jFileItemSalvaConfigurazione.setEnabled(true);
            jFileItemSalvaConfigurazioneConNome.setEnabled(true);
            jFileItemModificaConfigurazione.setEnabled(true);
            jFileItemEsci.setEnabled(true);

            jSimulazioneItemPlay.setEnabled(true);
            jSimulazioneItemPausa.setEnabled(false);
            jSimulazioneItemStop.setEnabled(false);
            
        } catch (InterruptedException ex) {}
    }

    /**
     * Ferma la simulazione nell'istante corrente per permettere il passaggio
     * dalla modalita' di avanzamento automatico a quella manuale.
     */
    private void simulazionePausa() {
        try {
            
            // interrompe l'avanzamento automatico e aspetta che il thread
            // venga terminato
            statoGui=false;
            auto.join();

            aggiornaComandi();
            jButtonNuovaConfigurazione.setEnabled(true);
            jButtonApriConfigurazione.setEnabled(true);
            jButtonSalvaConfigurazione.setEnabled(true);
            jButtonModificaConfigurazione.setEnabled(true);

            jButtonSimulazionePlay.setEnabled(true);
            jButtonSimulazionePausa.setEnabled(false);
            jButtonSimulazioneStop.setEnabled(true);
            
            jFileItemNuovaConfigurazione.setEnabled(true);
            jFileItemApriConfigurazione.setEnabled(true);
            jFileItemSalvaConfigurazione.setEnabled(true);
            jFileItemSalvaConfigurazioneConNome.setEnabled(true);
            jFileItemModificaConfigurazione.setEnabled(true);
            jFileItemEsci.setEnabled(true);

            jSimulazioneItemPlay.setEnabled(true);
            jSimulazioneItemPausa.setEnabled(false);
            jSimulazioneItemStop.setEnabled(true);
            
        } catch (InterruptedException ex) {}
    }

    /**
     * Porta la simulazione allo stato iniziale
     */
    private void simulazioneInizio() {
        
        istante = player.primoIstante();
        //aggiornaProcessiTerminati(0);
        processiEseguiti = new LinkedList<Processo>();

        visualizzaOrdProcessi(processiEseguiti);
        azzeraStatisticheSimulazione();
        visualizzaStatisticheSimulazione(player,istante,true);
        // Svuota l'interfaccia della RAM
       if ((views[1]).getComponent() instanceof ViewFrameMemoria) {
                ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                .getComponent();

                if(configurazioneIniziale!=null){
                    currView.configura(configurazioneIniziale.getModalitaGestioneMemoria(),
                                       configurazioneIniziale.getDimensioneRAM(),
                                       configurazioneIniziale.getListaProcessi().size(),
                                       configurazioneIniziale.getDimensioneRAM()/configurazioneIniziale.getDimensionePagina());
                }
        }
        if ((views[5]).getComponent() instanceof ViewFrameMemoria) {
                ViewFrameMemoria currView = (ViewFrameMemoria) views[5]
                                .getComponent();

                if(configurazioneIniziale!=null){
                    currView.configura(configurazioneIniziale.getModalitaGestioneMemoria(),
                                       configurazioneIniziale.getDimensioneSwap(),
                                       configurazioneIniziale.getListaProcessi().size(),
                                       configurazioneIniziale.getDimensioneSwap()/configurazioneIniziale.getDimensionePagina());
                }
        }
        
        azzeraSimulazioneTestuale(player.numeroIstanti(), 
                                  configurazioneIniziale.getModalitaGestioneMemoria());
        visualizzaSimulazioneTestuale(istante, player.getIndiceIstanteCorrente());
        
        aggiornaComandi();
        jButtonNuovaConfigurazione.setEnabled(true);
        jButtonApriConfigurazione.setEnabled(true);
        jButtonSalvaConfigurazione.setEnabled(true);
        jButtonModificaConfigurazione.setEnabled(true);
        
        jButtonSimulazionePlay.setEnabled(true);
        jButtonSimulazionePausa.setEnabled(false);
        jButtonSimulazioneStop.setEnabled(false);
        
        jFileItemNuovaConfigurazione.setEnabled(true);
        jFileItemApriConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazioneConNome.setEnabled(true);
        jFileItemModificaConfigurazione.setEnabled(true);
        jFileItemEsci.setEnabled(true);
                
        jSimulazioneItemPlay.setEnabled(true);
        jSimulazioneItemPausa.setEnabled(false);
        jSimulazioneItemStop.setEnabled(false);

    }
    
    /**
     * Porta la simulazione all'istante precedente 
     */
    private void simulazioneIndietro() {

        istante = player.istantePrecedente();
        //aggiornaProcessiTerminati(player.getIndiceIstanteCorrente());
        
        // Se istante e' null significa che non ho istanti precedenti e
        // quindi metto la simulazione nell'istante 0
        if(istante==null){
            simulazioneInizio();
            return;
        }
        
         ViewFrameMemoria currView1 = (ViewFrameMemoria) views[1]
                                .getComponent();
         ViewFrameMemoria currView5 = (ViewFrameMemoria) views[5]
                                .getComponent();

        processiEseguiti.removeLast();
        visualizzaOrdProcessi(processiEseguiti);
        visualizzaStatisticheSimulazione(player,istante,false);
        visualizzaSimulazioneTestuale(istante, player.getIndiceIstanteCorrente());
        try{
            currView1.aggiorna(istante.getStatoRAM(),
                               player.getIndiceIstanteCorrente(),
                               processiUltimati);
            currView5.aggiorna(istante.getStatoSwap(),
                               player.getIndiceIstanteCorrente(),
                               processiUltimati);
        }catch(Exception e){} 

        
        aggiornaComandi();
        jButtonNuovaConfigurazione.setEnabled(true);
        jButtonApriConfigurazione.setEnabled(true);
        jButtonSalvaConfigurazione.setEnabled(true);
        jButtonModificaConfigurazione.setEnabled(true);
        
        jButtonSimulazionePlay.setEnabled(true);
        jButtonSimulazionePausa.setEnabled(false);
        jButtonSimulazioneStop.setEnabled(true);
                
        jFileItemNuovaConfigurazione.setEnabled(true);
        jFileItemApriConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazioneConNome.setEnabled(true);
        jFileItemModificaConfigurazione.setEnabled(true);
        jFileItemEsci.setEnabled(true);
                
        jSimulazioneItemPlay.setEnabled(true);
        jSimulazioneItemPausa.setEnabled(false);
        jSimulazioneItemStop.setEnabled(true);
        
    }

    /**
     * Porta la simulazione all'istante successivo
     */
    private void simulazioneAvanti() { 
            
        istante = player.istanteSuccessivo();
        //aggiornaProcessiTerminati(player.getIndiceIstanteCorrente());
        
        if(istante!=null){
            PCB pcbAttuale;
            ViewFrameMemoria currView1 = (ViewFrameMemoria) views[1]
                                    .getComponent();
            ViewFrameMemoria currView5 = (ViewFrameMemoria) views[5]
                                    .getComponent();

            pcbAttuale = istante.getProcessoInEsecuzione();
            if(pcbAttuale==null){
                processiEseguiti.add(PROC_VUOTO);
            }    
            else{
                processiEseguiti.add(pcbAttuale.getRifProcesso());
            }

            visualizzaOrdProcessi(processiEseguiti);
            visualizzaStatisticheSimulazione(player,istante,true);
            visualizzaSimulazioneTestuale(istante, player.getIndiceIstanteCorrente());
            try{
                currView1.aggiorna(istante.getStatoRAM(),
                                  player.getIndiceIstanteCorrente(),
                                  processiUltimati);
                currView5.aggiorna(istante.getStatoSwap(),
                                  player.getIndiceIstanteCorrente(),
                                  processiUltimati);
            }catch(Exception e){}
        }
        
        aggiornaComandi();
        jButtonNuovaConfigurazione.setEnabled(true);
        jButtonApriConfigurazione.setEnabled(true);
        jButtonSalvaConfigurazione.setEnabled(true);
        jButtonModificaConfigurazione.setEnabled(true);
        
        jButtonSimulazionePlay.setEnabled(true);
        jButtonSimulazionePausa.setEnabled(false);
        jButtonSimulazioneStop.setEnabled(true);
                
        jFileItemNuovaConfigurazione.setEnabled(true);
        jFileItemApriConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazioneConNome.setEnabled(true);
        jFileItemModificaConfigurazione.setEnabled(true);
        jFileItemEsci.setEnabled(true);
                
        jSimulazioneItemPlay.setEnabled(true);
        jSimulazioneItemPausa.setEnabled(false);
        jSimulazioneItemStop.setEnabled(true);
        
    }
    
    /**
     * Porta la simulazione allistante finale
     */
    private void simulazioneFine() {
        
        LinkedList<Istante> istantiAllaFine = player.ultimoIstante();
        
        PCB pcbAttuale;
        ViewFrameMemoria currView1 = (ViewFrameMemoria) views[1]
                                    .getComponent();
        ViewFrameMemoria currView5 = (ViewFrameMemoria) views[5]
                                    .getComponent();
        
        int numeroIstanti = istantiAllaFine.size();
        int numeroIstanteCorrente;
        for(int i = 0; i < numeroIstanti; i++){
            istante = istantiAllaFine.get(i);
            //aggiornaProcessiTerminati(player.getIndiceIstanteCorrente() - numeroIstanti + i + 1);
            pcbAttuale = istante.getProcessoInEsecuzione();
            if(pcbAttuale==null){
                processiEseguiti.add(PROC_VUOTO);
            }    
            else{
                processiEseguiti.add(pcbAttuale.getRifProcesso());
            }
            try{
                numeroIstanteCorrente = (player.getIndiceIstanteCorrente() - numeroIstanti + i + 1);
                currView1.aggiorna(istante.getStatoRAM(),
                                  numeroIstanteCorrente,
                                  processiUltimati);
                currView5.aggiorna(istante.getStatoSwap(),
                                  numeroIstanteCorrente,
                                  processiUltimati);
                visualizzaSimulazioneTestuale(istante, numeroIstanteCorrente);
            }catch(Exception e){}
        }
        visualizzaOrdProcessi(processiEseguiti);
        for(int i = 0; i < numeroIstanti; i++){
            visualizzaStatisticheSimulazione(player,istantiAllaFine.get(i),true);
        }
        aggiornaComandi();
        jButtonNuovaConfigurazione.setEnabled(true);
        jButtonApriConfigurazione.setEnabled(true);
        jButtonSalvaConfigurazione.setEnabled(true);
        jButtonModificaConfigurazione.setEnabled(true);
        
        jButtonSimulazionePlay.setEnabled(true);
        jButtonSimulazionePausa.setEnabled(false);
        jButtonSimulazioneStop.setEnabled(true);
        
        jFileItemNuovaConfigurazione.setEnabled(true);
        jFileItemApriConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazione.setEnabled(true);
        jFileItemSalvaConfigurazioneConNome.setEnabled(true);
        jFileItemModificaConfigurazione.setEnabled(true);
        jFileItemEsci.setEnabled(true);
                
        jSimulazioneItemPlay.setEnabled(true);
        jSimulazioneItemPausa.setEnabled(false);
        jSimulazioneItemStop.setEnabled(true);
    }

    /**
     * Porta la simulazione all'istante significativo precedente
     */
    private void simulazioneSignificativoPrecedente(int scelta) {
        
        LinkedList<Istante> istantiAllEvento;
        
        switch(scelta){
            case 0:
                istantiAllEvento = player.precedenteIstanteSignificativo(Evento.FAULT);
                break;
            case 1:
                istantiAllEvento = player.precedenteIstanteSignificativo(Evento.SWITCH);
                break;
            case 2:
                istantiAllEvento = player.precedenteIstanteSignificativo(Evento.FULL_RAM);
                break;
            case 3:
                istantiAllEvento = player.precedenteIstanteSignificativo(Evento.FULL_SWAP);
                break;
            case 4:
                istantiAllEvento = player.precedenteIstanteSignificativo(Evento.END_PROC);
                break;
            case 5:
                istantiAllEvento = player.precedenteIstanteSignificativo(Evento.NEW_PROC);
                break;
            default:
                istantiAllEvento = null;
        }
        
        if(istantiAllEvento != null){
        
            ViewFrameMemoria currView1 = (ViewFrameMemoria) views[1]
                                    .getComponent();
            ViewFrameMemoria currView5 = (ViewFrameMemoria) views[5]
                                    .getComponent();
            
            int numeroIstanti = istantiAllEvento.size();
            int numeroIstanteCorrente;
            for(int i = 0 ; i < numeroIstanti; i++){
                istante = istantiAllEvento.get(i);
                //aggiornaProcessiTerminati(player.getIndiceIstanteCorrente() + numeroIstanti - i - 1);
                processiEseguiti.removeLast();
                try{
                    numeroIstanteCorrente = (player.getIndiceIstanteCorrente() + numeroIstanti - i - 1);
                    currView1.aggiorna(istante.getStatoRAM(),
                                      numeroIstanteCorrente,
                                      processiUltimati);
                    currView5.aggiorna(istante.getStatoSwap(),
                                      numeroIstanteCorrente,
                                      processiUltimati);
                    visualizzaSimulazioneTestuale(istante, numeroIstanteCorrente);
                }catch(Exception e){}
            }
            
            visualizzaOrdProcessi(processiEseguiti);
            for(int i = 0 ; i < numeroIstanti; i++){
                visualizzaStatisticheSimulazione(player,istantiAllEvento.get(i),false);
            }
            
            aggiornaComandi();
            jButtonNuovaConfigurazione.setEnabled(true);
            jButtonApriConfigurazione.setEnabled(true);
            jButtonSalvaConfigurazione.setEnabled(true);
            jButtonModificaConfigurazione.setEnabled(true);

            jButtonSimulazionePlay.setEnabled(true);
            jButtonSimulazionePausa.setEnabled(false);
            jButtonSimulazioneStop.setEnabled(true);

            jFileItemNuovaConfigurazione.setEnabled(true);
            jFileItemApriConfigurazione.setEnabled(true);
            jFileItemSalvaConfigurazione.setEnabled(true);
            jFileItemSalvaConfigurazioneConNome.setEnabled(true);
            jFileItemModificaConfigurazione.setEnabled(true);
            jFileItemEsci.setEnabled(true);

            jSimulazioneItemPlay.setEnabled(true);
            jSimulazioneItemPausa.setEnabled(false);
            jSimulazioneItemStop.setEnabled(true);
        }
    }
    
    /**
     * Porta la simulazione all'istante significativo successivo.
     */
    private void simulazioneSignificativoSuccessivo(int scelta) {

        LinkedList<Istante> istantiAllEvento;
        
        switch(scelta){
            case 0:
                istantiAllEvento = player.prossimoIstanteSignificativo(Evento.FAULT);
                break;
            case 1:
                istantiAllEvento = player.prossimoIstanteSignificativo(Evento.SWITCH);
                break;
            case 2:
                istantiAllEvento = player.prossimoIstanteSignificativo(Evento.FULL_RAM);
                break;
            case 3:
                istantiAllEvento = player.prossimoIstanteSignificativo(Evento.FULL_SWAP);
                break;
            case 4:
                istantiAllEvento = player.prossimoIstanteSignificativo(Evento.END_PROC);
                break;
            case 5:
                istantiAllEvento = player.prossimoIstanteSignificativo(Evento.NEW_PROC);
                break;
            default:
                istantiAllEvento = null;
        }
        
        if(istantiAllEvento != null){ 

            PCB pcbAttuale;
            ViewFrameMemoria currView1 = (ViewFrameMemoria) views[1]
                                    .getComponent();
            ViewFrameMemoria currView5 = (ViewFrameMemoria) views[5]
                                    .getComponent();
            
            int numeroIstanti = istantiAllEvento.size();
            int numeroIstanteCorrente;
            for(int i = 0; i < istantiAllEvento.size(); i++){
                istante = istantiAllEvento.get(i);
                //aggiornaProcessiTerminati(player.getIndiceIstanteCorrente() - numeroIstanti + i + 1);
                
                pcbAttuale = istante.getProcessoInEsecuzione();
                if(pcbAttuale==null){
                    processiEseguiti.add(PROC_VUOTO);
                }    
                else{
                    processiEseguiti.add(pcbAttuale.getRifProcesso());
                }
                try{
                    numeroIstanteCorrente = (player.getIndiceIstanteCorrente() - numeroIstanti + i + 1);
                    currView1.aggiorna(istante.getStatoRAM(),
                                      numeroIstanteCorrente,
                                      processiUltimati);
                    currView5.aggiorna(istante.getStatoSwap(),
                                      numeroIstanteCorrente,
                                      processiUltimati);
                    visualizzaSimulazioneTestuale(istante, numeroIstanteCorrente);
                }catch(Exception e){}
            }

            visualizzaOrdProcessi(processiEseguiti);
            for(int i = 0; i < istantiAllEvento.size(); i++){
                visualizzaStatisticheSimulazione(player,istantiAllEvento.get(i),true);
            }

            aggiornaComandi();
            jButtonNuovaConfigurazione.setEnabled(true);
            jButtonApriConfigurazione.setEnabled(true);
            jButtonSalvaConfigurazione.setEnabled(true);
            jButtonModificaConfigurazione.setEnabled(true);

            jButtonSimulazionePlay.setEnabled(true);
            jButtonSimulazionePausa.setEnabled(false);
            jButtonSimulazioneStop.setEnabled(true);

            jFileItemNuovaConfigurazione.setEnabled(true);
            jFileItemApriConfigurazione.setEnabled(true);
            jFileItemSalvaConfigurazione.setEnabled(true);
            jFileItemSalvaConfigurazioneConNome.setEnabled(true);
            jFileItemModificaConfigurazione.setEnabled(true);
            jFileItemEsci.setEnabled(true);

            jSimulazioneItemPlay.setEnabled(true);
            jSimulazioneItemPausa.setEnabled(false);
            jSimulazioneItemStop.setEnabled(true);
            jSimulazioneItemInizio.setEnabled(true);
        }
    }
        
    /**
     * Ritorna la configurazione iniziale corrente.
     */
    public ConfigurazioneIniziale getConfigurazioneIniziale() {
        return configurazioneIniziale;
    }

    /**
     * Imposta la configurazione iniziale corrente
     */
    public void setConfigurazioneIniziale(ConfigurazioneIniziale configurazioneIniziale) {
        this.configurazioneIniziale = configurazioneIniziale;
    }
    
    /**
     * Apertura di un file di configurazione e successivo caricamento della
     * simulazione.
     */
    private void apriFileConfigurazione(){
        //Handle open button action.
        JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new ImageFilter());
        fc.setAcceptAllFileFilterUsed(false);

        int returnVal = fc.showOpenDialog(rootWindow);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                gestione = new GestioneFile(file.getAbsolutePath(), null);
                ConfigurazioneIniziale conf = gestione.caricaFileConfigurazione();
                setConfigurazioneIniziale(conf);
                this.setIstanteZero();
                frame.setTitle(file.getName() + " - SiGeM StyloSoft");
                System.out.println("Configurazione " + file.getName() + " caricata con successo");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootWindow, "File di configurazione non valido","Errore",JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(rootWindow, "File di configurazione non valido","Errore",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Salva una configurazione su file.
     */
    private void salvaConfigurazione(){
        if(gestione==null){
            gestione = new GestioneFile(null,null);
        }
        if(gestione.getPercorsoFileConfigurazione()==null){
            salvaConfigurazioneConNome();
        }
        else{
            try {
                if(gestione.salvaFileConfigurazione(this.getConfigurazioneIniziale())){
                    JOptionPane.showMessageDialog(rootWindow, "Configurazione salvata!","Salvata",JOptionPane.INFORMATION_MESSAGE);
                    String nomeFile = gestione.getPercorsoFileConfigurazione();
                    char[] c = nomeFile.toCharArray();
                    int i = c.length-1;
                    while(c[i]!='\\' && c[i]!='/'){
                        i--;
                    }
                    frame.setTitle(nomeFile.substring(i+1) + " - SiGeM StyloSoft");
                    System.out.println("Configurazione salvata in " + nomeFile.substring(i+1));
                }
                else{
                    JOptionPane.showMessageDialog(rootWindow, "Configurazione non salvata!","Errore",JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootWindow, "Impossibile salvare. \nErrore nella scrittura del file","Errore",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Salva con nome una configurazione su file.
     */
    private void salvaConfigurazioneConNome(){
        JFileChooser fc = new JFileChooser();
        File file;
        fc.addChoosableFileFilter(new ImageFilter());
        fc.setAcceptAllFileFilterUsed(false);
        
        int returnVal = fc.showSaveDialog(rootWindow);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            if(file!=null){
                String nomeFile = file.getAbsolutePath().concat(".sigem");

                gestione = new GestioneFile(nomeFile,SiGeMv2View.this.getConfigurazioneIniziale());

                try {
                    if(gestione.salvaFileConfigurazione(SiGeMv2View.this.getConfigurazioneIniziale())){
                        JOptionPane.showMessageDialog(rootWindow, "Configurazione salvata!","Salvata",JOptionPane.INFORMATION_MESSAGE);
                        frame.setTitle(file.getName() + ".sigem" + " - SiGeM StyloSoft");
                        System.out.println("Configurazione salvata in " + file.getName() + ".sigem");
                    }
                    else{
                        JOptionPane.showMessageDialog(rootWindow, "Configurazione non salvata!","Errore",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootWindow, "Impossibile salvare. \nErrore nella scrittura del file","Errore",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    /**
     * Imposta la configurazione di default.
     */
    private void defaultConfigurazione(){
        String nomeFile=System.getProperty("user.dir")+File.separatorChar+"default.sigem";
        nomeFile=nomeFile.replace("\\", "/");
        nomeFile=nomeFile.replace(" ","%20");
                            
        gestione = new GestioneFile(nomeFile,SiGeMv2View.this.getConfigurazioneIniziale());
        System.out.println("Imposto la configurazione di default in " + nomeFile);
        try {
            if(gestione.salvaFileConfigurazione(SiGeMv2View.this.getConfigurazioneIniziale())){
                JOptionPane.showMessageDialog(rootWindow, "La configurazione di default e' stata impostata!","Configurazione di default",JOptionPane.INFORMATION_MESSAGE);
                frame.setTitle("SiGeM StyloSoft");
                System.out.println("Configurazione di default impostata");
            }
            else{
                JOptionPane.showMessageDialog(rootWindow, "Impossibile impostare la configurazione di default","Errore",JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootWindow, "Impossibile impostare la configurazione di default. \nErrore nella scrittura del file","Errore",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Caricamento della configurazione di default.
     */
    private boolean caricaSimulazioneDefault(){
        String nomeFile=System.getProperty("user.dir")+File.separatorChar+"default.sigem";
        nomeFile=nomeFile.replace("\\", "/");
        nomeFile=nomeFile.replace(" ","%20");

        try {
            gestione = new GestioneFile(nomeFile, null);
            ConfigurazioneIniziale conf = gestione.caricaFileConfigurazione();
            setConfigurazioneIniziale(conf);
            this.setIstanteZero();
            frame.setTitle("SiGeM StyloSoft");
        } catch (IOException ex) {
            gestione = null;
            return false;
        } 
        catch (ClassNotFoundException ex) {
            gestione = null;
            return false;
        }
        
        return true;
    }
    
    /**
     * Procedura per la modifica della configurazione correntemente caricata.
     */
    private void modificaConfigurazione(){
        configurazioneAmbiente = new ConfigurazioneAmbienteJDialog(frame, true, this, this.configurazioneIniziale);
        configurazioneAmbiente.setVisible(true);
    }
    
    /*private void aggiornaProcessiTerminati(int numeroIstante){
        //Aggiorno, se necessario, lo storico dei processi ultimati
        if(istante!=null){
            LinkedList<Azione> cambiamentiInMemoria = istante.getCambiamentiInMemoria();
            if(numeroIstante >= processiUltimati.size()){
                processiUltimati.add(new Vector(processiUltimati.get(numeroIstante-1)));
                if(cambiamentiInMemoria==null) return;
                int processoUltimato=-2;
                for(int i=0;i<cambiamentiInMemoria.size();i++){
                    if (cambiamentiInMemoria.get(i).getAzione()==6){
                        processoUltimato=cambiamentiInMemoria.get(i).getPosizione();
                    }
                }
                if(processoUltimato!=-2){
                    processiUltimati.get(numeroIstante).add(Integer.valueOf(processoUltimato));
                }
            }
        }
    }*/

   private void elaboraProcessiTerminati(LinkedList<Istante> listaIstanti){
        //Aggiorno, se necessario, lo storico dei processi ultimati
       int numeroIstanti = listaIstanti.size();
       processiUltimati = new Vector<Vector<Integer>>();
       processiUltimati.add(0, new Vector<Integer>());
       for(int j=0; j<numeroIstanti; j++){
        Istante ist = listaIstanti.get(j);
        LinkedList<Azione> cambiamentiInMemoria = ist.getCambiamentiInMemoria();
        processiUltimati.add(new Vector(processiUltimati.get(j)));
        if(cambiamentiInMemoria==null) continue;
        int processoUltimato=-2;
        for(int i=0;i<cambiamentiInMemoria.size();i++){
            if (cambiamentiInMemoria.get(i).getAzione()==6){
                processoUltimato=cambiamentiInMemoria.get(i).getPosizione();
            }
        }
        if(processoUltimato!=-2){
            processiUltimati.get(j+1).add(Integer.valueOf(processoUltimato));
        }
       }
    }
   
   private void elaboraContextSwitch(LinkedList<Istante> listaIstanti){
    contextSwitchs = new LinkedList<Boolean>();
    contextSwitchs.add(Boolean.valueOf(false));
    contextSwitchs.add(Boolean.valueOf(true));
    PCB processoScorso = listaIstanti.getFirst().getProcessoInEsecuzione();
    for(int i = 1; i<listaIstanti.size(); i++){
        if(processoScorso!=listaIstanti.get(i).getProcessoInEsecuzione()){
            contextSwitchs.add(Boolean.valueOf(true));    
        }
        else{
            contextSwitchs.add(Boolean.valueOf(false));
        }
        processoScorso = listaIstanti.get(i).getProcessoInEsecuzione();
    }
   }

    public static void main(String[] args) throws Exception {
            // Set InfoNode Look and Feel
        
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
