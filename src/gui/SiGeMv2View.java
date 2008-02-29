/*
 * Azienda: Stylosoft
 * Nome file: Processore.java
 * Package: gui
 * Autore: Giordano Cariani
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.2 (28/02/2008): Aggiunti pannelli dei processi, delle statistiche e dei frame memoria
 *  - v.1.1 (13/02/2008): Completato il menu' e aggiunte icone
 *  - v.1.0 (12/02/2008): Creazione e stesura pannello.
 */
package gui;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import logic.caricamento.GestioneFile;
import gui.dialog.*;
import gui.view.ViewStatoAvanzamentoProcessi;

/**
 * The application's main frame.
 */
public class SiGeMv2View extends FrameView {
    
    private JProgressBar progressBar = new JProgressBar();
    private GestioneFile gestioneFile = new GestioneFile();
    private ConfigurazioneAmbienteJDialog  configurazioneAmbiente = new ConfigurazioneAmbienteJDialog(SiGeMv2App.getApplication().getMainFrame(), true, this);
    private ViewStatoAvanzamentoProcessi jPanelStatoProcessi;
    
    public SiGeMv2View(SingleFrameApplication app) {
        super(app);

        initComponents();
        //initViewProcessi();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //statusMessageLabel.setText("");
            }
        });
        
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
//                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
//        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = SiGeMv2App.getApplication().getMainFrame();
            aboutBox = new SiGeMv2AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SiGeMv2App.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jToolBarMenu = new javax.swing.JToolBar();
        jButtonNuovaConfigurazione = new javax.swing.JButton();
        jButtonApriConfigurazione = new javax.swing.JButton();
        jButtonSalvaConfigurazione = new javax.swing.JButton();
        jToolBarSimulazione = new javax.swing.JToolBar();
        jButtonSimulazionePlay = new javax.swing.JButton();
        jButtonSimulazioneStop = new javax.swing.JButton();
        jButtonSimulazioneInizio = new javax.swing.JButton();
        jButtonSimulazioneFine = new javax.swing.JButton();
        jToolBarHelp = new javax.swing.JToolBar();
        jButtonHelp = new javax.swing.JButton();
        jDesktopMV = new javax.swing.JDesktopPane();
        jPanelProcessi = new javax.swing.JPanel();
        jLabelStatoAvanzamentoProcessi = new javax.swing.JLabel();
        jPanelMemoria = new javax.swing.JPanel();
        jLabelFrameMemoria = new javax.swing.JLabel();
        jPanelStatistiche = new javax.swing.JPanel();
        jLabelStatistiche = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu jMenuFile = new javax.swing.JMenu();
        jFileItemNuovaConfigurazione = new javax.swing.JMenuItem();
        jFileItemApriConfigurazione = new javax.swing.JMenuItem();
        jFileSeparatorSalvaConfigurazione = new javax.swing.JSeparator();
        jFileItemSalvaConfigurazione = new javax.swing.JMenuItem();
        jFileSeparatorEsci = new javax.swing.JSeparator();
        javax.swing.JMenuItem jFileItemEsci = new javax.swing.JMenuItem();
        jMenuSimulazione = new javax.swing.JMenu();
        jSimulazioneItemPlay = new javax.swing.JMenuItem();
        jSimulazioneItemStop = new javax.swing.JMenuItem();
        jSimulazioneSeparator = new javax.swing.JSeparator();
        jSimulazioneItemInizio = new javax.swing.JMenuItem();
        jSimulazioneItemFine = new javax.swing.JMenuItem();
        jMenuFinestre = new javax.swing.JMenu();
        javax.swing.JMenu jMenuHelp = new javax.swing.JMenu();
        jHelpItemGuida = new javax.swing.JMenuItem();
        javax.swing.JMenuItem jHelpItemInformazioniSu = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();

        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        mainPanel.setName("mainPanel"); // NOI18N

        jToolBarMenu.setRollover(true);
        jToolBarMenu.setName("jToolBarMenu"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gui.SiGeMv2App.class).getContext().getResourceMap(SiGeMv2View.class);
        jButtonNuovaConfigurazione.setIcon(resourceMap.getIcon("jButtonNuovaConfigurazione.icon")); // NOI18N
        jButtonNuovaConfigurazione.setText(resourceMap.getString("jButtonNuovaConfigurazione.text")); // NOI18N
        jButtonNuovaConfigurazione.setBorder(null);
        jButtonNuovaConfigurazione.setFocusable(false);
        jButtonNuovaConfigurazione.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNuovaConfigurazione.setMaximumSize(new java.awt.Dimension(25, 21));
        jButtonNuovaConfigurazione.setName("jButtonNuovaConfigurazione"); // NOI18N
        jButtonNuovaConfigurazione.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNuovaConfigurazione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuovaConfigurazioneActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jButtonNuovaConfigurazione);

        jButtonApriConfigurazione.setIcon(resourceMap.getIcon("jButtonApriConfigurazione.icon")); // NOI18N
        jButtonApriConfigurazione.setText(resourceMap.getString("jButtonApriConfigurazione.text")); // NOI18N
        jButtonApriConfigurazione.setBorder(null);
        jButtonApriConfigurazione.setFocusable(false);
        jButtonApriConfigurazione.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonApriConfigurazione.setMaximumSize(new java.awt.Dimension(25, 21));
        jButtonApriConfigurazione.setName("jButtonApriConfigurazione"); // NOI18N
        jButtonApriConfigurazione.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonApriConfigurazione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApriConfigurazioneActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jButtonApriConfigurazione);

        jButtonSalvaConfigurazione.setIcon(resourceMap.getIcon("jButtonSalvaConfigurazione.icon")); // NOI18N
        jButtonSalvaConfigurazione.setText(resourceMap.getString("jButtonSalvaConfigurazione.text")); // NOI18N
        jButtonSalvaConfigurazione.setBorder(null);
        jButtonSalvaConfigurazione.setEnabled(false);
        jButtonSalvaConfigurazione.setFocusable(false);
        jButtonSalvaConfigurazione.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSalvaConfigurazione.setMaximumSize(new java.awt.Dimension(25, 21));
        jButtonSalvaConfigurazione.setName("jButtonSalvaConfigurazione"); // NOI18N
        jButtonSalvaConfigurazione.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBarMenu.add(jButtonSalvaConfigurazione);

        jToolBarSimulazione.setRollover(true);
        jToolBarSimulazione.setName("jToolBarSimulazione"); // NOI18N

        jButtonSimulazionePlay.setIcon(resourceMap.getIcon("jButtonSimulazionePlay.icon")); // NOI18N
        jButtonSimulazionePlay.setText(resourceMap.getString("jButtonSimulazionePlay.text")); // NOI18N
        jButtonSimulazionePlay.setBorder(null);
        jButtonSimulazionePlay.setEnabled(false);
        jButtonSimulazionePlay.setFocusable(false);
        jButtonSimulazionePlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSimulazionePlay.setMaximumSize(new java.awt.Dimension(25, 21));
        jButtonSimulazionePlay.setName("jButtonSimulazionePlay"); // NOI18N
        jButtonSimulazionePlay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBarSimulazione.add(jButtonSimulazionePlay);

        jButtonSimulazioneStop.setIcon(resourceMap.getIcon("jButtonSimulazioneStop.icon")); // NOI18N
        jButtonSimulazioneStop.setText(resourceMap.getString("jButtonSimulazioneStop.text")); // NOI18N
        jButtonSimulazioneStop.setBorder(null);
        jButtonSimulazioneStop.setEnabled(false);
        jButtonSimulazioneStop.setFocusable(false);
        jButtonSimulazioneStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSimulazioneStop.setMaximumSize(new java.awt.Dimension(25, 21));
        jButtonSimulazioneStop.setName("jButtonSimulazioneStop"); // NOI18N
        jButtonSimulazioneStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBarSimulazione.add(jButtonSimulazioneStop);

        jButtonSimulazioneInizio.setIcon(resourceMap.getIcon("jButtonSimulazioneInizio.icon")); // NOI18N
        jButtonSimulazioneInizio.setText(resourceMap.getString("jButtonSimulazioneInizio.text")); // NOI18N
        jButtonSimulazioneInizio.setBorder(null);
        jButtonSimulazioneInizio.setEnabled(false);
        jButtonSimulazioneInizio.setFocusable(false);
        jButtonSimulazioneInizio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSimulazioneInizio.setMaximumSize(new java.awt.Dimension(25, 21));
        jButtonSimulazioneInizio.setName("jButtonSimulazioneInizio"); // NOI18N
        jButtonSimulazioneInizio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBarSimulazione.add(jButtonSimulazioneInizio);

        jButtonSimulazioneFine.setIcon(resourceMap.getIcon("jButtonSimulazioneFine.icon")); // NOI18N
        jButtonSimulazioneFine.setText(resourceMap.getString("jButtonSimulazioneFine.text")); // NOI18N
        jButtonSimulazioneFine.setBorder(null);
        jButtonSimulazioneFine.setEnabled(false);
        jButtonSimulazioneFine.setFocusable(false);
        jButtonSimulazioneFine.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSimulazioneFine.setMaximumSize(new java.awt.Dimension(25, 21));
        jButtonSimulazioneFine.setName("jButtonSimulazioneFine"); // NOI18N
        jButtonSimulazioneFine.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBarSimulazione.add(jButtonSimulazioneFine);

        jToolBarHelp.setRollover(true);
        jToolBarHelp.setName("jToolBarHelp"); // NOI18N

        jButtonHelp.setIcon(resourceMap.getIcon("jButtonHelp.icon")); // NOI18N
        jButtonHelp.setText(resourceMap.getString("jButtonHelp.text")); // NOI18N
        jButtonHelp.setBorder(null);
        jButtonHelp.setFocusable(false);
        jButtonHelp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonHelp.setMaximumSize(new java.awt.Dimension(25, 21));
        jButtonHelp.setName("jButtonHelp"); // NOI18N
        jButtonHelp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBarHelp.add(jButtonHelp);

        jDesktopMV.setName("jDesktopMV"); // NOI18N

        jPanelProcessi.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, resourceMap.getColor("jPanelProcessi.border.highlightOuterColor"), resourceMap.getColor("jPanelProcessi.border.highlightInnerColor"), resourceMap.getColor("jPanelProcessi.border.shadowOuterColor"), resourceMap.getColor("jPanelProcessi.border.shadowInnerColor"))); // NOI18N
        jPanelProcessi.setName("jPanelProcessi"); // NOI18N

        jLabelStatoAvanzamentoProcessi.setFont(resourceMap.getFont("jLabelStatoAvanzamentoProcessi.font")); // NOI18N
        jLabelStatoAvanzamentoProcessi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelStatoAvanzamentoProcessi.setText(resourceMap.getString("jLabelStatoAvanzamentoProcessi.text")); // NOI18N
        jLabelStatoAvanzamentoProcessi.setName("jLabelStatoAvanzamentoProcessi"); // NOI18N

        javax.swing.GroupLayout jPanelProcessiLayout = new javax.swing.GroupLayout(jPanelProcessi);
        jPanelProcessi.setLayout(jPanelProcessiLayout);
        jPanelProcessiLayout.setHorizontalGroup(
            jPanelProcessiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProcessiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelStatoAvanzamentoProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelProcessiLayout.setVerticalGroup(
            jPanelProcessiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProcessiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelStatoAvanzamentoProcessi)
                .addContainerGap(306, Short.MAX_VALUE))
        );

        jPanelProcessi.setBounds(0, 0, 510, 340);
        jDesktopMV.add(jPanelProcessi, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanelMemoria.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, resourceMap.getColor("jPanelMemoria.border.highlightOuterColor"), resourceMap.getColor("jPanelMemoria.border.highlightInnerColor"), resourceMap.getColor("jPanelMemoria.border.shadowOuterColor"), resourceMap.getColor("jPanelMemoria.border.shadowInnerColor"))); // NOI18N
        jPanelMemoria.setName("jPanelMemoria"); // NOI18N

        jLabelFrameMemoria.setFont(resourceMap.getFont("jLabelFrameMemoria.font")); // NOI18N
        jLabelFrameMemoria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFrameMemoria.setText(resourceMap.getString("jLabelFrameMemoria.text")); // NOI18N
        jLabelFrameMemoria.setName("jLabelFrameMemoria"); // NOI18N

        javax.swing.GroupLayout jPanelMemoriaLayout = new javax.swing.GroupLayout(jPanelMemoria);
        jPanelMemoria.setLayout(jPanelMemoriaLayout);
        jPanelMemoriaLayout.setHorizontalGroup(
            jPanelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMemoriaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelFrameMemoria, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelMemoriaLayout.setVerticalGroup(
            jPanelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMemoriaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelFrameMemoria)
                .addContainerGap(306, Short.MAX_VALUE))
        );

        jPanelMemoria.setBounds(510, 0, 590, 340);
        jDesktopMV.add(jPanelMemoria, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanelStatistiche.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, resourceMap.getColor("jPanelStatistiche.border.highlightOuterColor"), resourceMap.getColor("jPanelStatistiche.border.highlightInnerColor"), resourceMap.getColor("jPanelStatistiche.border.shadowOuterColor"), resourceMap.getColor("jPanelStatistiche.border.shadowInnerColor"))); // NOI18N
        jPanelStatistiche.setName("jPanelStatistiche"); // NOI18N

        jLabelStatistiche.setFont(resourceMap.getFont("jLabelStatistiche.font")); // NOI18N
        jLabelStatistiche.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelStatistiche.setText(resourceMap.getString("jLabelStatistiche.text")); // NOI18N
        jLabelStatistiche.setName("jLabelStatistiche"); // NOI18N

        javax.swing.GroupLayout jPanelStatisticheLayout = new javax.swing.GroupLayout(jPanelStatistiche);
        jPanelStatistiche.setLayout(jPanelStatisticheLayout);
        jPanelStatisticheLayout.setHorizontalGroup(
            jPanelStatisticheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatisticheLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelStatistiche, javax.swing.GroupLayout.DEFAULT_SIZE, 1074, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelStatisticheLayout.setVerticalGroup(
            jPanelStatisticheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatisticheLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelStatistiche)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jPanelStatistiche.setBounds(0, 340, 1100, 150);
        jDesktopMV.add(jPanelStatistiche, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jToolBarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBarSimulazione, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBarHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(784, Short.MAX_VALUE))
            .addComponent(jDesktopMV, javax.swing.GroupLayout.DEFAULT_SIZE, 1102, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBarSimulazione, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBarHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopMV, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        jMenuFile.setText(resourceMap.getString("jMenuFile.text")); // NOI18N
        jMenuFile.setName("jMenuFile"); // NOI18N

        jFileItemNuovaConfigurazione.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jFileItemNuovaConfigurazione.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resources/new_file.png"))); // NOI18N
        jFileItemNuovaConfigurazione.setText(resourceMap.getString("jFileItemNuovaConfigurazione.text")); // NOI18N
        jFileItemNuovaConfigurazione.setName("jFileItemNuovaConfigurazione"); // NOI18N
        jFileItemNuovaConfigurazione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileItemNuovaConfigurazioneActionPerformed(evt);
            }
        });
        jMenuFile.add(jFileItemNuovaConfigurazione);

        jFileItemApriConfigurazione.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jFileItemApriConfigurazione.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resources/openFile.png"))); // NOI18N
        jFileItemApriConfigurazione.setText(resourceMap.getString("jFileItemApriConfigurazione.text")); // NOI18N
        jFileItemApriConfigurazione.setName("jFileItemApriConfigurazione"); // NOI18N
        jFileItemApriConfigurazione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileItemApriConfigurazioneActionPerformed(evt);
            }
        });
        jMenuFile.add(jFileItemApriConfigurazione);

        jFileSeparatorSalvaConfigurazione.setName("jFileSeparatorSalvaConfigurazione"); // NOI18N
        jMenuFile.add(jFileSeparatorSalvaConfigurazione);

        jFileItemSalvaConfigurazione.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jFileItemSalvaConfigurazione.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/resources/save.png"))); // NOI18N
        jFileItemSalvaConfigurazione.setText(resourceMap.getString("jFileItemSalvaConfigurazione.text")); // NOI18N
        jFileItemSalvaConfigurazione.setName("jFileItemSalvaConfigurazione"); // NOI18N
        jMenuFile.add(jFileItemSalvaConfigurazione);

        jFileSeparatorEsci.setName("jFileSeparatorEsci"); // NOI18N
        jMenuFile.add(jFileSeparatorEsci);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(gui.SiGeMv2App.class).getContext().getActionMap(SiGeMv2View.class, this);
        jFileItemEsci.setAction(actionMap.get("quit")); // NOI18N
        jFileItemEsci.setIcon(resourceMap.getIcon("jFileItemEsci.icon")); // NOI18N
        jFileItemEsci.setName("jFileItemEsci"); // NOI18N
        jMenuFile.add(jFileItemEsci);

        menuBar.add(jMenuFile);

        jMenuSimulazione.setText(resourceMap.getString("jMenuSimulazione.text")); // NOI18N
        jMenuSimulazione.setName("jMenuSimulazione"); // NOI18N

        jSimulazioneItemPlay.setIcon(resourceMap.getIcon("jSimulazioneItemPlay.icon")); // NOI18N
        jSimulazioneItemPlay.setText(resourceMap.getString("jSimulazioneItemPlay.text")); // NOI18N
        jSimulazioneItemPlay.setEnabled(false);
        jSimulazioneItemPlay.setName("jSimulazioneItemPlay"); // NOI18N
        jMenuSimulazione.add(jSimulazioneItemPlay);

        jSimulazioneItemStop.setIcon(resourceMap.getIcon("jSimulazioneItemStop.icon")); // NOI18N
        jSimulazioneItemStop.setText(resourceMap.getString("jSimulazioneItemStop.text")); // NOI18N
        jSimulazioneItemStop.setEnabled(false);
        jSimulazioneItemStop.setName("jSimulazioneItemStop"); // NOI18N
        jMenuSimulazione.add(jSimulazioneItemStop);

        jSimulazioneSeparator.setName("jSimulazioneSeparator"); // NOI18N
        jMenuSimulazione.add(jSimulazioneSeparator);

        jSimulazioneItemInizio.setIcon(resourceMap.getIcon("jSimulazioneItemInizio.icon")); // NOI18N
        jSimulazioneItemInizio.setText(resourceMap.getString("jSimulazioneItemInizio.text")); // NOI18N
        jSimulazioneItemInizio.setEnabled(false);
        jSimulazioneItemInizio.setName("jSimulazioneItemInizio"); // NOI18N
        jMenuSimulazione.add(jSimulazioneItemInizio);

        jSimulazioneItemFine.setIcon(resourceMap.getIcon("jSimulazioneItemFine.icon")); // NOI18N
        jSimulazioneItemFine.setText(resourceMap.getString("jSimulazioneItemFine.text")); // NOI18N
        jSimulazioneItemFine.setEnabled(false);
        jSimulazioneItemFine.setName("jSimulazioneItemFine"); // NOI18N
        jMenuSimulazione.add(jSimulazioneItemFine);

        menuBar.add(jMenuSimulazione);

        jMenuFinestre.setText(resourceMap.getString("jMenuFinestre.text")); // NOI18N
        jMenuFinestre.setName("jMenuFinestre"); // NOI18N
        menuBar.add(jMenuFinestre);

        jMenuHelp.setText(resourceMap.getString("jMenuHelp.text")); // NOI18N
        jMenuHelp.setName("jMenuHelp"); // NOI18N

        jHelpItemGuida.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jHelpItemGuida.setIcon(resourceMap.getIcon("jHelpItemGuida.icon")); // NOI18N
        jHelpItemGuida.setText(resourceMap.getString("jHelpItemGuida.text")); // NOI18N
        jHelpItemGuida.setName("jHelpItemGuida"); // NOI18N
        jMenuHelp.add(jHelpItemGuida);

        jHelpItemInformazioniSu.setAction(actionMap.get("showAboutBox")); // NOI18N
        jHelpItemInformazioniSu.setIcon(resourceMap.getIcon("jHelpItemInformazioniSu.icon")); // NOI18N
        jHelpItemInformazioniSu.setText(resourceMap.getString("jHelpItemInformazioniSu.text")); // NOI18N
        jHelpItemInformazioniSu.setName("jHelpItemInformazioniSu"); // NOI18N
        jMenuHelp.add(jHelpItemInformazioniSu);

        menuBar.add(jMenuHelp);

        statusPanel.setName("statusPanel"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1104, Short.MAX_VALUE)
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void initViewProcessi(){ 
        
	jPanelStatoProcessi = new ViewStatoAvanzamentoProcessi();

        javax.swing.GroupLayout jPanelStatoProcessiLayout = new javax.swing.GroupLayout(jPanelStatoProcessi);
        jPanelStatoProcessi.setLayout(jPanelStatoProcessiLayout);
        jPanelStatoProcessiLayout.setHorizontalGroup(
            jPanelStatoProcessiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );
        jPanelStatoProcessiLayout.setVerticalGroup(
            jPanelStatoProcessiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );

        jPanelStatoProcessi.setBounds(0, 0, 510, 340);
        jDesktopMV.add(jPanelStatoProcessi, javax.swing.JLayeredPane.DEFAULT_LAYER);
    }
    
    private void jFileItemApriConfigurazioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileItemApriConfigurazioneActionPerformed
        //gestioneFile.openFile(SiGeMv2App.getApplication().getMainFrame());
    }//GEN-LAST:event_jFileItemApriConfigurazioneActionPerformed

    private void jButtonApriConfigurazioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApriConfigurazioneActionPerformed
        //gestioneFile.caricaFileConfigurazione(SiGeMv2App.getApplication().getMainFrame());
    }//GEN-LAST:event_jButtonApriConfigurazioneActionPerformed

    private void jButtonNuovaConfigurazioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuovaConfigurazioneActionPerformed
       configurazioneAmbiente.setVisible(true);
    }//GEN-LAST:event_jButtonNuovaConfigurazioneActionPerformed

    private void jFileItemNuovaConfigurazioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileItemNuovaConfigurazioneActionPerformed
        configurazioneAmbiente.setVisible(true);
    }//GEN-LAST:event_jFileItemNuovaConfigurazioneActionPerformed

    public void abilitaTutto() {
        jSimulazioneItemPlay.setEnabled(true);
        jSimulazioneItemFine.setEnabled(true);
        jSimulazioneItemInizio.setEnabled(true);
        jSimulazioneItemPlay.setEnabled(true);
        jSimulazioneItemStop.setEnabled(true);
        jButtonSalvaConfigurazione.setEnabled(true);
        jButtonSimulazioneFine.setEnabled(true);
        jButtonSimulazioneInizio.setEnabled(true);
        jButtonSimulazionePlay.setEnabled(true);
        jButtonSimulazioneStop.setEnabled(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonApriConfigurazione;
    private javax.swing.JButton jButtonHelp;
    private javax.swing.JButton jButtonNuovaConfigurazione;
    private javax.swing.JButton jButtonSalvaConfigurazione;
    private javax.swing.JButton jButtonSimulazioneFine;
    private javax.swing.JButton jButtonSimulazioneInizio;
    private javax.swing.JButton jButtonSimulazionePlay;
    private javax.swing.JButton jButtonSimulazioneStop;
    private javax.swing.JDesktopPane jDesktopMV;
    private javax.swing.JMenuItem jFileItemApriConfigurazione;
    private javax.swing.JMenuItem jFileItemNuovaConfigurazione;
    private javax.swing.JMenuItem jFileItemSalvaConfigurazione;
    private javax.swing.JSeparator jFileSeparatorEsci;
    private javax.swing.JSeparator jFileSeparatorSalvaConfigurazione;
    private javax.swing.JMenuItem jHelpItemGuida;
    private javax.swing.JLabel jLabelFrameMemoria;
    private javax.swing.JLabel jLabelStatistiche;
    private javax.swing.JLabel jLabelStatoAvanzamentoProcessi;
    private javax.swing.JMenu jMenuFinestre;
    private javax.swing.JMenu jMenuSimulazione;
    private javax.swing.JPanel jPanelMemoria;
    private javax.swing.JPanel jPanelProcessi;
    private javax.swing.JPanel jPanelStatistiche;
    private javax.swing.JMenuItem jSimulazioneItemFine;
    private javax.swing.JMenuItem jSimulazioneItemInizio;
    private javax.swing.JMenuItem jSimulazioneItemPlay;
    private javax.swing.JMenuItem jSimulazioneItemStop;
    private javax.swing.JSeparator jSimulazioneSeparator;
    private javax.swing.JToolBar jToolBarHelp;
    private javax.swing.JToolBar jToolBarMenu;
    private javax.swing.JToolBar jToolBarSimulazione;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}