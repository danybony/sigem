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

import java.io.IOException;
import net.infonode.docking.*;
import net.infonode.docking.mouse.DockingWindowActionMouseButtonListener;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.*;
import net.infonode.docking.util.*;
import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.gui.laf.InfoNodeLookAndFeel;
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
import logic.caricamento.GestioneFile;
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

    /** Array delle viste statiche */
    private View[] views = new View[6];

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
    private JFrame frame = new JFrame("SiGeM - StyloSoft");

    /** Rappresenta lo stato dela Gui, vale true se la simulazione e' in avanzamento automatico,
     * false, se e' interrotta.*/
    private boolean statoGui = false;
    
    private boolean statoStop = true;

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
    private JButton jButtonSimulazioneIndietro, jButtonSimulazioneAvanti, jButtonSimulazionePausa;
    private JButton jButtonIndietroSignificativo, jButtonAvantiSignificativo;

    private JButton jButtonNuovaConfigurazione, jButtonApriConfigurazione;
    private JButton jButtonSalvaConfigurazione, jButtonModificaConfigurazione;
    private JButton jButtonHelp;
    
    JSpinner scegliVelocita;
    
    private JComboBox ComboBoxSignificativo;

    // Pulsanti Menu Simulazione
    private JMenuItem jSimulazioneItemPlay, jSimulazioneItemStop, jSimulazioneItemInizio, jSimulazioneItemFine;
    private JMenuItem jSimulazioneItemIndietro, jSimulazioneItemAvanti, jSimulazioneItemPausa;
    private JMenu jSimulazioneItemAvantiSignificativo,jSimulazioneItemIndietroSignificativo;
    private JMenuItem ItemAvantiFault, ItemAvantiSwitch, ItemAvantiFullRAM;
    private JMenuItem ItemAvantiFullSwap,ItemAvantiFineProc,ItemAvantiNuovoProc;
    private JMenuItem ItemIndietroFault, ItemIndietroSwitch, ItemIndietroFullRAM;
    private JMenuItem ItemIndietroFullSwap,ItemIndietroFineProc,ItemIndietroNuovoProc;

    // Pulsanti Menu File
    private JMenuItem jFileItemNuovaConfigurazione, jFileItemApriConfigurazione;

    private JMenuItem jFileItemSalvaConfigurazione, jFileItemSalvaConfigurazioneConNome;
    private JMenuItem jFileItemModificaConfigurazione, jFileItemEsci;

    JScrollPane scrollMemoria = new JScrollPane();

    /** Contiene finestre e funzioni per selezionare un file sul filesystem */
    private GestioneFile gestioneFile;

    /** Wizard per la configurazione dei processi */
    private ConfigurazioneAmbienteJDialog  configurazioneAmbiente;

    /** Configurazione iniziale */
    private ConfigurazioneIniziale configurazioneIniziale;

    /** Player */
    private Player player;

    /** Istante */
    private Istante istante;

    /** Processi */
    LinkedList<Processo> processiEseguiti;

    // thread per aumentare l'interattività del utente durante l'avanzamento automatico
    private Thread auto;
    
    // velocità di avanzamento nella modalità automatica
    private int velocita;
    
    GestioneFile gestione;
    
    private static final Processo PROC_VUOTO = new Processo("",0,0);
    
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
    }

    /** Disegna le statistiche sulla vista ViewStatistiche */
    public void visualizzaStatisticheProcessi(Player player, Istante istante) {
            if ((views[3]).getComponent() instanceof ViewStatistiche) {
                    ViewStatistiche currView = (ViewStatistiche) views[3].getComponent();
                    currView.generaStatistiche(player, istante,configurazioneIniziale);
            }
    };
    
    /** Disegna le statistiche sulla vista ViewStatistiche */
    public void visualizzaStatisticheSimulazione(Player player, Istante istante) {
            if ((views[2]).getComponent() instanceof ViewStatistiche) {
                    ViewStatistiche currView = (ViewStatistiche) views[2].getComponent();
                    currView.generaStatistiche(player, istante,configurazioneIniziale);
            }
    };
    
    public void visualizzaSimulazioneTestuale() {
            if ((views[2]).getComponent() instanceof ViewStatistiche) {
                    ViewStatistiche currView = (ViewStatistiche) views[2].getComponent();
                    currView.generaStatistiche(player, istante,configurazioneIniziale);
            }
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

    /*
     * Crea una nuova configurazione con l'apertura del wizard
     */
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
            views[2] = new View("Statistiche simulazione", IconStylosoft
                            .getGeneralIcon("statistiche"), new ViewStatistiche(this));
            viewMap.addView(2, views[2]);
            views[3] = new View("Statistiche Processi", IconStylosoft
                            .getGeneralIcon("statistiche"), new ViewStatistiche(this));
            viewMap.addView(3, views[3]);
            views[4] = new View("Modalita' testuale", IconStylosoft
                            .getGeneralIcon("proc"), new ViewStatistiche(this));
            viewMap.addView(4, views[4]);
            views[5] = new View("Riepilogo configurazione", IconStylosoft
                            .getGeneralIcon(""), new ViewStatistiche(this));
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
     * - aprire 
     * - salvare
     * - creare una configurazione
     * - terminare la sessione
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
                    ItemIndietroFault = new JMenuItem("Fault in memoria");
                    ItemIndietroFault.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            simulazioneSignificativoSuccessivo(0);
                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(ItemIndietroFault);

                    ItemIndietroSwitch = new JMenuItem("Context switch");
                    ItemIndietroSwitch.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(ItemIndietroSwitch);

                    ItemIndietroFullRAM = new JMenuItem("Riempimento RAM");
                    ItemIndietroFullRAM.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(ItemIndietroFullRAM);

                    ItemIndietroFullSwap = new JMenuItem("Riempimento Swap");
                    ItemIndietroFullSwap.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(ItemIndietroFullSwap);

                    ItemIndietroFineProc= new JMenuItem("Terminazione di un processo");
                    ItemIndietroFineProc.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(ItemIndietroFineProc);

                    ItemIndietroNuovoProc= new JMenuItem("Arrivo di un nuovo processo");
                    ItemIndietroNuovoProc.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemIndietroSignificativo.add(ItemIndietroNuovoProc);



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
                    ItemAvantiFault= new JMenuItem("Fault in memoria");
                    ItemAvantiFault.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(ItemAvantiFault);

                    ItemAvantiSwitch = new JMenuItem("Context switch");
                    ItemAvantiSwitch.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(ItemAvantiSwitch);

                    ItemAvantiFullRAM = new JMenuItem("Riempimento RAM");
                    ItemAvantiFullRAM.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(ItemAvantiFullRAM);

                    ItemAvantiFullSwap = new JMenuItem("Riempimento Swap");
                    ItemAvantiFullSwap.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(ItemAvantiFullSwap);

                    ItemAvantiFineProc = new JMenuItem("Terminazione di un processo");
                    ItemAvantiFineProc.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(ItemAvantiFineProc);

                    ItemAvantiNuovoProc = new JMenuItem("Arrivo nuovo processo");
                    ItemAvantiNuovoProc.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    jSimulazioneItemAvantiSignificativo.add(ItemAvantiNuovoProc);                


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
                            ComboBoxSignificativo.getSelectedIndex());
                }
            });
            jButtonIndietroSignificativo.setToolTipText("Porta la simulazione" +
                    " all'istante significativo precedente");
            
            
            ComboBoxSignificativo = new JComboBox();
            ComboBoxSignificativo.setModel(new DefaultComboBoxModel(
                    new String[] { "Fault in memoria",
                                   "Context switch",
                                   "Riempimento RAM",
                                   "Riempimento area di Swap",
                                   "Terminazione di un processo",
                                   "Arrivo di un nuovo processo"}));
            ComboBoxSignificativo.setToolTipText("Evento significativo");

            jButtonAvantiSignificativo = new JButton(IconStylosoft.getGeneralIcon("nextSign"));
            jButtonAvantiSignificativo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulazioneSignificativoSuccessivo(
                            ComboBoxSignificativo.getSelectedIndex());
                }
            });
            jButtonAvantiSignificativo.setToolTipText("Porta la simulazione" +
                    " all'istante significativo successivo");
            
            scegliVelocita = new JSpinner();
            Double[] velocitaConsentite = new Double[20];
            double t;
            for(int i=1;i<=20;i++){
                t = i;
                velocitaConsentite[i-1]=new Double(t/2);
            }
            scegliVelocita.setModel(new SpinnerListModel(velocitaConsentite));
            scegliVelocita.setToolTipText("Velocita' di avanzamento della simulazione");
            

            //jButtonHelp = new JButton(IconStylosoft.getGeneralIcon("help"));
            //jButtonHelp.addActionListener(new CSH.DisplayHelpFromSource(hb));
            //jButtonHelp.setToolTipText("Apre la finestra di aiuto");

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
            toolBar.add(ComboBoxSignificativo);
            toolBar.add(jButtonAvantiSignificativo);
            
            toolBar.addSeparator(new java.awt.Dimension(20, 12));
            
            toolBar.add(scegliVelocita);
            
            toolBar.addSeparator(new java.awt.Dimension(30, 12));

            //toolBar.add(jButtonHelp);

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
            ComboBoxSignificativo.setEnabled(false);
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
                 views[5],
                 views[4],
                 new SplitWindow(true,
                                 0.6615854f, 
                                 new SplitWindow(false,
                                                 0.68f, 
                                                 views[0],
                                                 views[2]),
                                                 //new TabWindow(new DockingWindow[] {views[3],views[2]})),
                                 views[1])
                  }));
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
            frame.setSize(1024, 768);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void abilitaTutto(){
        jButtonNuovaConfigurazione.setEnabled(true);
        jButtonApriConfigurazione.setEnabled(true);
        jButtonSalvaConfigurazione.setEnabled(true);
        jButtonModificaConfigurazione.setEnabled(true);
        
        jButtonSimulazionePlay.setEnabled(true);
        jButtonSimulazionePausa.setEnabled(false);
        jButtonSimulazioneStop.setEnabled(false);
        jButtonSimulazioneInizio.setEnabled(false);
        jButtonSimulazioneIndietro.setEnabled(false);
        jButtonSimulazioneAvanti.setEnabled(true);
        jButtonSimulazioneFine.setEnabled(true);
        
        jButtonIndietroSignificativo.setEnabled(false);
        ComboBoxSignificativo.setEnabled(true);
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
    
    public void aggiornaComandi(){
        jButtonNuovaConfigurazione.setEnabled(true);
        jButtonApriConfigurazione.setEnabled(true);
        jButtonSalvaConfigurazione.setEnabled(true);
        jButtonModificaConfigurazione.setEnabled(true);
        
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
            ComboBoxSignificativo.setEnabled(true);
        else
            ComboBoxSignificativo.setEnabled(false);
        
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

    /** inizializza le viste e prepara l'applicazione per l'esecuzione della simulazione */
    public void setIstanteZero() {
            
        statoGui = false;
        statoStop = true;
        creaPlayer();
        if(!simulazioneCarica()){
            JOptionPane.showMessageDialog(rootWindow, "Impossibile eseguire la simulazione","Errore",JOptionPane.ERROR_MESSAGE);
            return;
        }
        istante = player.primoIstante();
        abilitaTutto();

        if ((views[0]).getComponent() instanceof ViewStatoAvanzamentoProcessi) {
                ViewStatoAvanzamentoProcessi currView = (ViewStatoAvanzamentoProcessi) views[0]
                                .getComponent();
                Vector procName = new Vector();
                if(configurazioneIniziale!=null){
                    LinkedList<Processo> processi = configurazioneIniziale.getListaProcessi();
                    for (int i = 0; i < processi.size(); i++) {
                            procName.add(processi.get(i).getNome());
                    }
                    currView.initializeViewStatoAvanzamentoProcessi(procName);
                }
        }

        if ((views[1]).getComponent() instanceof ViewFrameMemoria) {
                ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                .getComponent();

                if(configurazioneIniziale!=null){

                    switch(configurazioneIniziale.getModalitaGestioneMemoria()){
                        case 1: 
                            currView.configura(false,
                                    configurazioneIniziale.getDimensioneRAM(),
                                    configurazioneIniziale.getListaProcessi().size());
                            break;
                        case 2:
                            currView.configura(true,
                                    configurazioneIniziale.getDimensioneRAM(),
                                    configurazioneIniziale.getListaProcessi().size());
                            break;
                    }
                }
        }

        processiEseguiti = new LinkedList<Processo>();
        visualizzaOrdProcessi(processiEseguiti);
        visualizzaStatisticheSimulazione(player,istante);
    };
        
    /** Istanzia una nuova simulazione */
    private void creaPlayer() {
        player = new Player(configurazioneIniziale);
    }

    /** Parte la simulazione */
    private synchronized void simulazionePlay() {
        statoGui = true;
        statoStop = false;
        
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
        ComboBoxSignificativo.setEnabled(false);
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
                ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                    .getComponent();
                while(istante!=null && statoGui){
                    Double t =((Double)scegliVelocita.getValue()).doubleValue();
                    velocita = (int) (t * 10);
                    pcbAttuale = istante.getProcessoInEsecuzione();
                    if(pcbAttuale==null){
                        processiEseguiti.add(PROC_VUOTO);
                    }    
                    else{
                        processiEseguiti.add(pcbAttuale.getRifProcesso());
                    }
                    
                    visualizzaOrdProcessi(processiEseguiti);
                    visualizzaStatisticheSimulazione(player,istante);
                    try {
                        currView.aggiorna(istante.getCambiamentiInMemoria(),player.getIndiceIstanteCorrente());
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

    /** Carica il player */
    private boolean simulazioneCarica() {
        return player.caricaSimulazione();
    }

    /** Stop della simulazione */
    private void simulazioneStop(){
        try {
            
            // interrompe l'avanzamento automatico e aspetta che il thread
            // venga terminato
            statoGui=false;
            statoStop = true;
            if(auto!=null)
                auto.join();
            
            istante = player.primoIstante();
            processiEseguiti = new LinkedList<Processo>();
            visualizzaOrdProcessi(processiEseguiti);
            visualizzaStatisticheSimulazione(player,istante);
            
            // Svuota l'interfaccia della RAM
            if ((views[1]).getComponent() instanceof ViewFrameMemoria) {
                    ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                    .getComponent();
                    
                if(configurazioneIniziale!=null){
                    switch(configurazioneIniziale.getModalitaGestioneMemoria()){
                        case 1: 
                            currView.configura(false,
                                    configurazioneIniziale.getDimensioneRAM(),
                                    configurazioneIniziale.getListaProcessi().size());
                            break;
                        case 2:
                            currView.configura(true,
                                    configurazioneIniziale.getDimensioneRAM(),
                                    configurazioneIniziale.getListaProcessi().size());
                            break;
                    }
                }
            }

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

    /** Ferma la simulazione */
    private void simulazionePausa() {
        try {
            
            // interrompe l'avanzamento automatico e aspetta che il thread
            // venga terminato
            statoGui=false;
            statoStop = false;
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

    /** Porta la simulazione allo stato iniziale */
    private void simulazioneInizio() {
        statoStop = false;
        
        istante = player.primoIstante();
        processiEseguiti = new LinkedList<Processo>();
        
        PCB pcbAttuale;
        
        pcbAttuale = istante.getProcessoInEsecuzione();

        visualizzaOrdProcessi(processiEseguiti);
        visualizzaStatisticheSimulazione(player,istante);
        // Svuota l'interfaccia della RAM
        if ((views[1]).getComponent() instanceof ViewFrameMemoria) {
                ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                .getComponent();

            if(configurazioneIniziale!=null){
                switch(configurazioneIniziale.getModalitaGestioneMemoria()){
                    case 1: 
                        currView.configura(false,
                                configurazioneIniziale.getDimensioneRAM(),
                                configurazioneIniziale.getListaProcessi().size());
                        break;
                    case 2:
                        currView.configura(true,
                                configurazioneIniziale.getDimensioneRAM(),
                                configurazioneIniziale.getListaProcessi().size());
                        break;
                }
            }
        }
        
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
    
    /** Porta la simulazione allo stato precedente */
    private void simulazioneIndietro() {

        istante = player.istantePrecedente();
        
        // Se istante e' null significa che non ho istanti precedenti e
        // quindi metto la simulazione nell'istante 0
        if(istante==null){
            simulazioneInizio();
            return;
        }
        statoStop = false;
        
        if(istante!=null){
             ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                .getComponent();
        
            processiEseguiti.removeLast();
            visualizzaOrdProcessi(processiEseguiti);
            visualizzaStatisticheSimulazione(player,istante);
            try{
                currView.aggiorna(istante.getCambiamentiInMemoria(),player.getIndiceIstanteCorrente());    
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

    /** Porta la simulazione allo stato successivo */
    private void simulazioneAvanti() { 
            
        istante = player.istanteSuccessivo();
        
        if(istante!=null){
            PCB pcbAttuale;
            ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                .getComponent();

            pcbAttuale = istante.getProcessoInEsecuzione();
            if(pcbAttuale==null){
                processiEseguiti.add(PROC_VUOTO);
            }    
            else{
                processiEseguiti.add(pcbAttuale.getRifProcesso());
            }

            visualizzaOrdProcessi(processiEseguiti);
            visualizzaStatisticheSimulazione(player,istante);
            try{
                currView.aggiorna(istante.getCambiamentiInMemoria(),player.getIndiceIstanteCorrente());    
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
    
    /** Porta la simulazione allo stato finale */
    private void simulazioneFine() {
        
        LinkedList<Istante> istantiAllaFine = player.ultimoIstante();
        
        statoStop = false;
        PCB pcbAttuale;
        ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                            .getComponent();
        
        int numeroIstanti = istantiAllaFine.size();
        int numeroIstanteCorrente;
        for(int i = 0; i < numeroIstanti; i++){
            istante = istantiAllaFine.get(i);
            pcbAttuale = istante.getProcessoInEsecuzione();
            if(pcbAttuale==null){
                processiEseguiti.add(PROC_VUOTO);
            }    
            else{
                processiEseguiti.add(pcbAttuale.getRifProcesso());
            }
            try{
                numeroIstanteCorrente = (player.getIndiceIstanteCorrente() - numeroIstanti + i + 1);
                currView.aggiorna(istante.getCambiamentiInMemoria(), numeroIstanteCorrente);    
            }catch(Exception e){}
        }
        visualizzaOrdProcessi(processiEseguiti);
        visualizzaStatisticheSimulazione(player,istante);
        
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
        
    private void simulazioneSignificativoPrecedente(int scelta) {
        statoStop = false;
        
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
        
            ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                .getComponent();
            
            int numeroIstanti = istantiAllEvento.size();
            int numeroIstanteCorrente;
            for(int i = 0 ; i < numeroIstanti; i++){
                istante = istantiAllEvento.get(i);
                processiEseguiti.removeLast();
                try{
                    numeroIstanteCorrente = (player.getIndiceIstanteCorrente() + numeroIstanti - i - 1);
                    currView.aggiorna(istante.getCambiamentiInMemoria(),numeroIstanteCorrente);    
                }catch(Exception e){}
            }
            
            visualizzaOrdProcessi(processiEseguiti);
            visualizzaStatisticheSimulazione(player,istante);

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

        statoStop = false;
        
        if(istantiAllEvento != null){ 

            PCB pcbAttuale;
            ViewFrameMemoria currView = (ViewFrameMemoria) views[1]
                                .getComponent();
            
            int numeroIstanti = istantiAllEvento.size();
            int numeroIstanteCorrente;
            for(int i = 0; i < istantiAllEvento.size(); i++){
                istante = istantiAllEvento.get(i);
                pcbAttuale = istante.getProcessoInEsecuzione();
                if(pcbAttuale==null){
                    processiEseguiti.add(PROC_VUOTO);
                }    
                else{
                    processiEseguiti.add(pcbAttuale.getRifProcesso());
                }
                try{
                    numeroIstanteCorrente = (player.getIndiceIstanteCorrente() - numeroIstanti + i + 1);
                    currView.aggiorna(istante.getCambiamentiInMemoria() , numeroIstanteCorrente);    
                }catch(Exception e){}
            }

            visualizzaOrdProcessi(processiEseguiti);
            visualizzaStatisticheSimulazione(player,istante);

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
    
    
    
    /** Aggiorna il contenuto della vista ViewStatoAvanzamentoProcessi */
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
        
    public ConfigurazioneIniziale getConfigurazioneIniziale() {
        return configurazioneIniziale;
    }

    public void setConfigurazioneIniziale(ConfigurazioneIniziale configurazioneIniziale) {
        this.configurazioneIniziale = configurazioneIniziale;
    }
    
    public void apriFileConfigurazione(){
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
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootWindow, "File di configurazione non valido","Errore",JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(rootWindow, "File di configurazione non valido","Errore",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void salvaConfigurazione(){
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
                }
                else{
                    JOptionPane.showMessageDialog(rootWindow, "Configurazione non salvata!","Errore",JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootWindow, "Impossibile salvare. \nErrore nella scrittura del file","Errore",JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    public void salvaConfigurazioneConNome(){
        final JFileChooser fc = new JFileChooser();
        File file;
        fc.addChoosableFileFilter(new ImageFilter());
        fc.setAcceptAllFileFilterUsed(false);

        int returnVal = fc.showSaveDialog(rootWindow);
        file = fc.getSelectedFile();
        String nomeFile = file.getAbsolutePath().concat(".sigem");

        gestione = new GestioneFile(nomeFile,SiGeMv2View.this.getConfigurazioneIniziale());

        try {
            if(gestione.salvaFileConfigurazione(SiGeMv2View.this.getConfigurazioneIniziale())){
                JOptionPane.showMessageDialog(rootWindow, "Configurazione salvata!","Salvata",JOptionPane.INFORMATION_MESSAGE);
                frame.setTitle(file.getName() + ".sigem" + " - SiGeM StyloSoft");
            }
            else{
                JOptionPane.showMessageDialog(rootWindow, "Configurazione non salvata!","Errore",JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootWindow, "Impossibile salvare. \nErrore nella scrittura del file","Errore",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public void modificaConfigurazione(){
        configurazioneAmbiente = new ConfigurazioneAmbienteJDialog(frame, true, this, this.configurazioneIniziale);
        configurazioneAmbiente.setVisible(true);
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
