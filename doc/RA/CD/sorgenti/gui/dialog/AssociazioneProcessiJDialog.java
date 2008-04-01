package gui.dialog;

/*
 * Azienda: Stylosoft
 * Nome file: AssociazioneProcessiJDialog.java
 * Package: gui.dialog
 * Autore: Giordano Cariani
 * Data: 16/03/2008
 * Versione: 1.5
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.5 (16/03/2008): Corretta la cancellazione
 *  - v.1.4 (15/03/2008): Inseriti controlli sull'inserimento e la modifica
 *  - v.1.3 (15/03/2008): Inserita nuova modifica per la dimensione dei segmenti
 *  - v.1.2 (14/03/2008): Ridefinizione con nuova modalita' Drag&Drop
 *  - v.1.1 (02/03/2008): inserita label "passo 4 di 4"
 *                        inserita inizializzazione grafico processi
 *  - v.1.0 (01/03/2008): Creazione JDialog e impostazione grafica
 */



import gui.dialog.*;
import gui.SiGeMv2View;
import gui.utility.HelpHtml;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import logic.gestioneMemoria.FrameMemoria;
import logic.gestioneMemoria.Pagina;
import logic.gestioneMemoria.Segmento;
import logic.parametri.*;
import logic.parametri.Processo.Accesso;

/**
 *
 * @author  Jordy
 */
public class AssociazioneProcessiJDialog extends javax.swing.JDialog {
    
    /**
     * Riferimento al primo passo del wizard
     */
    private ConfigurazioneAmbienteJDialog configurazioneAmbiente;
    
    /**
     * RIferimento al secondo passo del wizard
     */
    private PoliticheJDialog politica;
    
    /**
     * Riferimento al terzo passo del wizard
     */
    private ProcessiJDialog processi;
    
    /**
     * Riferimento alla finestra principale del programma
     */
    private SiGeMv2View view;
    
    /**
     * Configurazione iniziale preesistente nel caso di modifica
     */
    private ConfigurazioneIniziale confIniziale;
    
    /**
     * Lista dei processi configurati da inserire nella configurazioneIniziale
     */
    private LinkedList<Processo> listaProcessi;
    
    /**
     * Lista degli oggetto JList rappresentanti tutti gli istanti
     */
    private Vector<JList> listaList = new Vector<JList>();
    
    /**
     * Lista dei modelli delle JList associate ai vari istanti
     */
    private Vector<DefaultListModel> listModels = new Vector<DefaultListModel>();
            
    /**
     * ArrayListTransferHandler incaricato di gestire il Drag&Drop
     */
    private ArrayListTransferHandler arrayListHandler;
    
    /**
     * Il menu che compare nelle JList degli istanti, e permette di rimuovere un
     * FrameMemoria da quell'istante, o di impostarne la modifica in quell'istante
     */
    private JPopupMenu menu;
    
    /**
     * I due componenti del menu
     */
    private JMenuItem menuItemElimina, menuItemModifica;
    
    /**
     * Modello per la lista delle pagine
     */
    DefaultListModel listaFrameModel;
    
    /**
     * Vector contenente le liste di FrameMemoria a disposizione di ogni processo
     * sotto forma di DefaultListModel
     */
    private Vector<DefaultListModel> modelliListaFrame = new Vector<DefaultListModel>();
    
    
    
    /**
     * Mouse adapter per le liste che visualizza il menu
     */
    private MouseAdapter listMouseAdapter;
    
    /**
     *  Lista della durata per ogni processo
     */
    private Vector<Integer> istantiPerProcesso=new Vector<Integer>();
            
    /** Crostruttore principale della classe.
     * Incaricato di creare tutte le liste per i vari processi e le liste dei FrameMemoria
     * a disposizione di ogni processo.
     * @param parent
     *          La classe parent di questa
     * @param modal 
     *          Booleano che indica se la finestra sarà modale o meno
     * @param configurazione
     *          Riferimento alla finestra di configurazione dell'ambiente
     * @param pol 
     *          Riferimento alla finestra di configurazione delle politiche
     * @param proc
     *          Riferimento alla finestra di configurazione dei processi
     * @param view 
     *          Riferimento alla finestra principale del programma
     */
    public AssociazioneProcessiJDialog(java.awt.Frame parent, boolean modal, 
            ConfigurazioneAmbienteJDialog configurazione, PoliticheJDialog pol,
            ProcessiJDialog proc, SiGeMv2View view) {
        super(parent, modal);
        configurazioneAmbiente = configurazione;
        politica = pol;
        processi = proc;
        this.view = view;
        
        int numProcessi = configurazioneAmbiente.getNumProcessi();
        
        /* Crea i modelli per la lista dei frame */
        for(int i=0; i<numProcessi; i++){ 
            istantiPerProcesso.add((Integer)processi.getCombinazioneProcessi()[i][2]);
            modelliListaFrame.add(new DefaultListModel());
        } 
        
        /* creo il mouse adapter per le liste */
        listMouseAdapter = new MouseAdapter() {
                @Override
                 public void mouseClicked(MouseEvent e) { 
                     /* Rende visibile il menu alle coordinate attuali del mouse */
                     JList lista =((JList)e.getComponent());
                     if (e.getButton() == MouseEvent.BUTTON3) {
                         if(!lista.isSelectionEmpty()
                         && lista.locationToIndex(e.getPoint())
                         == lista.getSelectedIndex()){
                            int index = lista.getSelectedIndex();
                            if (((DefaultListModel)lista.getModel()).get(index) instanceof FrameMemoria){
                                menuItemModifica.setText("Modificato in questo istante");
                            }
                            else{
                                menuItemModifica.setText("Non modificato in questo istante");
                            }
                            menu.show(lista, e.getX(), e.getY());
                         }                         
                      }
                 }
                @Override
                 public void mouseExited(MouseEvent e){
                     if(!menu.isVisible()){
                         JList lista =((JList)e.getComponent());
                         lista.clearSelection();
                     }                     
                 }
             };  
             
        initComponents();
        creaMenu();
        
        if (politica.getGestioneMemoria() == 1){
            jLabelAssociazioneProcessi.setText("ASSOCIAZIONE PROCESSI - PAGINE");
            jButtonNuovoFrame.setText("Nuova Pagina");
            jButtonModifica.setVisible(false);
        }
        else {
            jLabelAssociazioneProcessi.setText("ASSOCIAZIONE PROCESSI - SEGMENTI");
            jButtonNuovoFrame.setText("Nuovo segmento");
            jButtonModifica.setVisible(true);
        }
        
        arrayListHandler = new ArrayListTransferHandler();
        arrayListHandler.setDimensioneRAM(configurazioneAmbiente.getDimensioneRAM());
        
        for(int i=0; i<numProcessi; i++){
            String nomeProcesso = (String) processi.getCombinazioneProcessi()[i][0];
            jTabbedPaneProcessi.addTab(nomeProcesso, creaPannelloProcesso(i));
            modelliListaFrame.add(new DefaultListModel());
        }          
        
        /* Imposto la lista dei FrameMemoria di destra */
        listaFrameModel = modelliListaFrame.get(0);
        jListFrame.setModel(listaFrameModel);
        jListFrame.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListFrame.setTransferHandler(arrayListHandler);
        jListFrame.setDragEnabled(true);
    }
    
    /** Crostruttore invocato nel caso di modifica di una configurazione. Invoca
     * il costruttore principale della classe e successivamente carica gli accessi.
     * @param parent
     *          La classe parent di questa
     * @param modal 
     *          Booleano che indica se la finestra sarà modale o meno
     * @param configurazione
     *          Riferimento alla finestra di configurazione dell'ambiente
     * @param pol 
     *          Riferimento alla finestra di configurazione delle politiche
     * @param proc
     *          Riferimento alla finestra di configurazione dei processi
     * @param view 
     *          Riferimento alla finestra principale del programma
     * @param confIniziale 
     *          La ConfigurazioneIniziale che si sta modificando
     */
    public AssociazioneProcessiJDialog(java.awt.Frame parent, boolean modal, 
            ConfigurazioneAmbienteJDialog configurazione, PoliticheJDialog pol, 
            ProcessiJDialog proc, SiGeMv2View view, ConfigurazioneIniziale confIniziale) {
        
        this(parent, modal, configurazione, pol, proc, view);
        this.confIniziale = confIniziale;
        caricaAccessi();  
    }
    
    /**
     * Classe interna che contiene i FrameMemoria nel caso debbano essere modificati
     * durante la simulazione
     */
    static class FrameModifica{
        FrameMemoria frame;
        FrameModifica(FrameMemoria frame){
            this.frame = frame;
        }
        @Override
        public String toString() {
		return frame.toString() + " (M)";
	} 
    }

    
    
    /**
     * Metodo che carica gli accessi dalla ConfigurazioneIniziale che si sta modificando
     */
    void caricaAccessi() {
         /* Se e' stata cambiata la modalita' di gestione della memoria non carico
         gli accessi perche' pagine e segmenti non hanno nulla in comune */
        if(confIniziale.getModalitaGestioneMemoria() != politica.getGestioneMemoria()){
             return;
        }
        for(int indiceProcesso = 0; indiceProcesso < confIniziale.getListaProcessi().size()
                && indiceProcesso < configurazioneAmbiente.getNumProcessi(); 
                indiceProcesso++){
            /* Prendo la durata salvata nella ConfigurazioneIniziale */
            int numIstanti = confIniziale.getListaProcessi().get(indiceProcesso).getTempoEsecuzione();
            /* Controllo se la durata modificata e' dicversa e temgo la minore */
            if(numIstanti > ((Integer)processi.getCombinazioneProcessi()[indiceProcesso][2]).intValue()){
                numIstanti = ((Integer)processi.getCombinazioneProcessi()[indiceProcesso][2]).intValue();
            }
            
            /* Trovo il primo modello del processo proprietario del segmento */
            int primaLista = 0 ;

            for(int processo = 0; processo < indiceProcesso; processo++){
                primaLista += istantiPerProcesso.get(processo).intValue();
            }
            
            ArrayList<Accesso> accessi = confIniziale.getListaProcessi().get(indiceProcesso).getAccessi();
            
            for(int indiceAccesso = 0; indiceAccesso < accessi.size() && 
                    accessi.get(indiceAccesso).getIstanteRichiesta() < numIstanti; indiceAccesso++){
                
                FrameMemoria frame = accessi.get(indiceAccesso).getRisorsa();
                DefaultListModel modello = modelliListaFrame.get(indiceProcesso);
                
                if(accessoValido(frame,primaLista + accessi.get(indiceAccesso).getIstanteRichiesta())){
                    
                    /* Aggiungo il FrameMemoria fra quelli del processo */
                    if(!modello.contains(frame)){                    
                        modello.addElement(frame);                    
                    }

                    if(accessi.get(indiceAccesso).getModifica()){
                        listModels.get(primaLista + accessi.get(indiceAccesso).getIstanteRichiesta())
                                .addElement(new FrameModifica(frame));
                    }
                    else{
                        listModels.get(primaLista + accessi.get(indiceAccesso).getIstanteRichiesta()).addElement(frame);
                    }
                    
                }
                
            }
            
        }
    }

    void setConfigurazioneIniziale(ConfigurazioneIniziale confIniziale) {
        this.confIniziale = confIniziale;
    }
    
    /**
     * Metodo che controlla la validita' di un accesso al suo caricamento, controllando
     * se lo spazio disponibile in RAM e' sufficiente a contenere il FrameMemoria,
     * considerando i FrameMemoria gia' caricati.
     * @param frame
     *          Il FrameMemoria da inserire
     * @param indiceLista
     *          L'indice del modello della lista corrispondente all'istante in cui
     *          caricare il FrameMemoria
     * @return  Ritorna true se c'e' spazio a sufficienza per inserire il FrameMemoria,
     *          false altrimenti.
     */
    private boolean accessoValido(FrameMemoria frame, int indiceLista) {
        /* Recupero il modello in cui inserire il frame */
        DefaultListModel modello = listModels.get(indiceLista);        
        boolean valido = true;
        /* Imposto lo spazio disponibile in quell'istante */
        int spazioRimanente = configurazioneAmbiente.getDimensioneRAM() - frame.getDimensione();
        if(spazioRimanente < 0){
            return false;
        }
        for(int elemento = 0; elemento < modello.size() && valido; elemento++){
            if(modello.get(elemento) instanceof FrameMemoria){
                spazioRimanente -= ((FrameMemoria)modello.get(elemento)).getDimensione();
            }
            else{
                spazioRimanente -= ((FrameModifica)modello.get(elemento)).frame.getDimensione();
            }            
            if(spazioRimanente < 0){
                valido = false;
            }
        }
        return valido;
    }

    /**
     * Metodo incaricato di creare il menu che apparira' nelle varie JList con 
     * il click destro.
     */
    private void creaMenu() {   
        /* Crea il menu i i suoi item */
        menu = new JPopupMenu();
        menu.add(menuItemElimina= new JMenuItem("Elimina"));
        menu.add(new JPopupMenu.Separator());
        menu.add(menuItemModifica = new JMenuItem("Modifica"));
        menu.pack();
        
        /* Crea un actionListner per i due item del menu */
        ActionListener menuListner = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
               JList lista = ((JList)((JPopupMenu)((JMenuItem)e.getSource()).
                                    getParent()).getInvoker());
               DefaultListModel modello = (DefaultListModel)lista.getModel();
               
               if (e.getSource() == menuItemElimina) {                   
                     // Elimina il frameMemoria selezionato                   
                     int index = lista.getSelectedIndex();
                     modello.remove(index);                 
                }
               else {
                     // Modifica
                     int index = lista.getSelectedIndex();
                     if (modello.get(index) instanceof FrameMemoria){
                         FrameMemoria frameSelezionato = (FrameMemoria) modello.get(index);
                         modello.setElementAt(new FrameModifica(frameSelezionato), index);
                     }
                     else{
                         FrameMemoria frameSelezionato = ((FrameModifica) modello.get(index)).frame;
                         modello.setElementAt(frameSelezionato, index);
                     
                     }
                }
            }
        };
        
        /* Associa il listner agli item del menu */
        menuItemElimina.addActionListener(menuListner);
        menuItemModifica.addActionListener(menuListner);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelPasso = new javax.swing.JLabel();
        jButtonIndietro = new javax.swing.JButton();
        jButtonOk = new javax.swing.JButton();
        jButtonAnnulla = new javax.swing.JButton();
        jLabelAssociazioneProcessi = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPaneProcessi = new javax.swing.JTabbedPane();
        jPanelFrame = new javax.swing.JPanel();
        jButtonNuovoFrame = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListFrame = new javax.swing.JList();
        jButtonElimina = new javax.swing.JButton();
        jButtonModifica = new javax.swing.JButton();
        jButtonHelp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelPasso.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabelPasso.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPasso.setText("Passo 4 di 4");

        jButtonIndietro.setText("Indietro");
        jButtonIndietro.setMaximumSize(new java.awt.Dimension(72, 23));
        jButtonIndietro.setMinimumSize(new java.awt.Dimension(72, 23));
        jButtonIndietro.setPreferredSize(new java.awt.Dimension(72, 23));
        jButtonIndietro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIndietroActionPerformed(evt);
            }
        });

        jButtonOk.setText("Ok");
        jButtonOk.setMaximumSize(new java.awt.Dimension(72, 23));
        jButtonOk.setMinimumSize(new java.awt.Dimension(72, 23));
        jButtonOk.setPreferredSize(new java.awt.Dimension(72, 23));
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonAnnulla.setText("Annulla");
        jButtonAnnulla.setMaximumSize(new java.awt.Dimension(72, 23));
        jButtonAnnulla.setMinimumSize(new java.awt.Dimension(72, 23));
        jButtonAnnulla.setPreferredSize(new java.awt.Dimension(72, 23));
        jButtonAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnullaActionPerformed(evt);
            }
        });

        jLabelAssociazioneProcessi.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelAssociazioneProcessi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAssociazioneProcessi.setText("ASSOCIAZIONE PROCESSI");

        jTabbedPaneProcessi.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPaneProcessi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneProcessiStateChanged(evt);
            }
        });

        jButtonNuovoFrame.setText("Nuova");
        jButtonNuovoFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuovoFrameActionPerformed(evt);
            }
        });

        jListFrame.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jListFrame.setToolTipText("<html>Insieme delle locazioni di memoria<BR>\na disposizione del<br>\nprocesso selezionato.</html>");
        jListFrame.setName("jListFrame"); // NOI18N
        jScrollPane1.setViewportView(jListFrame);

        jButtonElimina.setText("Elimina");
        jButtonElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminaActionPerformed(evt);
            }
        });

        jButtonModifica.setText("Modifica Dimensione");
        jButtonModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelFrameLayout = new javax.swing.GroupLayout(jPanelFrame);
        jPanelFrame.setLayout(jPanelFrameLayout);
        jPanelFrameLayout.setHorizontalGroup(
            jPanelFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(jButtonNuovoFrame, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(jButtonElimina, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(jButtonModifica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelFrameLayout.setVerticalGroup(
            jPanelFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonNuovoFrame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonElimina)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonModifica)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPaneProcessi, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
            .addComponent(jPanelFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jButtonHelp.setText("Help");
        jButtonHelp.setPreferredSize(new java.awt.Dimension(72, 23));
        jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHelpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelAssociazioneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                        .addComponent(jButtonIndietro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 511, Short.MAX_VALUE)
                        .addComponent(jLabelPasso, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelPasso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelAssociazioneProcessi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonHelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonIndietro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIndietroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIndietroActionPerformed
        this.setVisible(false);
        processi.setVisible(true);        
    }//GEN-LAST:event_jButtonIndietroActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        /* Innanzitutto azzero il contatore del generatore di Id di processo*/
        Id.resetCounter();
        
        /* Creo i processi */
        listaProcessi = new LinkedList<Processo>();
        if (politica.getPoliticaSchedulazione() == 6
                || politica.getPoliticaSchedulazione() == 7
                || politica.getPoliticaSchedulazione() == 5) {
            for (int row=0; row<configurazioneAmbiente.getNumProcessi(); row++) {
                listaProcessi.add( new ProcessoConPriorita(
                        (String) processi.getCombinazioneProcessi()[row][0],
                        ((Integer)processi.getCombinazioneProcessi()[row][1]).intValue(),
                        ((Integer)processi.getCombinazioneProcessi()[row][2]).intValue(),
                        ((Integer)processi.getCombinazioneProcessi()[row][3]).intValue()
                        ));
                
            }
        } else {
            for (int row=0; row<configurazioneAmbiente.getNumProcessi(); row++) {
                listaProcessi.add( new Processo(
                        (String) processi.getCombinazioneProcessi()[row][0],
                        ((Integer)processi.getCombinazioneProcessi()[row][1]).intValue(),
                        ((Integer)processi.getCombinazioneProcessi()[row][2]).intValue()
                        ));
            }
        }
        
        creaAccessi();
        
        try {
            inizializzaConfigurazioneIniziale();
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        this.setVisible(false);
        view.abilitaTutto();
        view.setIstanteZero();
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jButtonAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnullaActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonAnnullaActionPerformed

    private void jButtonNuovoFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuovoFrameActionPerformed
        
        int nuovoIndirizzo = getNuovoIndirizzo(jTabbedPaneProcessi.getSelectedIndex());
        
        if (politica.getGestioneMemoria() == 1){
            
           // listaFrame.add(new Pagina(new Integer(contatoreFrame).toString(),configurazioneAmbiente.getDimensionePagina(),0));
            
            listaFrameModel.addElement(new Pagina(Integer.toString(nuovoIndirizzo),
                    configurazioneAmbiente.getDimensionePagina(),0));
            
        }
        else {
            
            DatiSegmentoDialog datiSegmentoDialog = new DatiSegmentoDialog(view.getFrame(), true, 
                                                        configurazioneAmbiente.getDimensioneRAM());
            datiSegmentoDialog.setVisible(true);
            
            int risultato = datiSegmentoDialog.getReturnStatus();
            
            if(risultato == DatiSegmentoDialog.RET_OK){
                
                // listaFrame.add(new Segmento(new Integer(contatoreFrame).toString(),   datiSegmentoDialog.getDimensione(),0));
            
                 listaFrameModel.addElement(new Segmento(Integer.toString(nuovoIndirizzo),
                    datiSegmentoDialog.getDimensione(),0));
            
            }
            else{
                return;
            }
            
        }
    }//GEN-LAST:event_jButtonNuovoFrameActionPerformed

    private void jTabbedPaneProcessiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneProcessiStateChanged
        aggiornaListaFrame();
    }//GEN-LAST:event_jTabbedPaneProcessiStateChanged

    private void jButtonEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminaActionPerformed
        int[] indici = jListFrame.getSelectedIndices();
        
        /* Trovo il primo modello del processo proprietario del segmento */
        int primaLista = 0 ;

        for(int processo = 0; processo < jTabbedPaneProcessi.getSelectedIndex(); processo++){
            primaLista += ((Integer)processi.getCombinazioneProcessi()[processo][2]).intValue();
        }

        int numeroIstanti = ((Integer)processi.getCombinazioneProcessi()
                        [jTabbedPaneProcessi.getSelectedIndex()][2]).intValue();
        
        for(int i = indici.length-1; i>-1 ; i--){
            String indirizzo = ((FrameMemoria)listaFrameModel.get(indici[i])).getIndirizzo();
            
            //lo elimina in tutti gli istanti del processo corrente
            for(int istante = 0; istante < numeroIstanti; istante++){
                
                DefaultListModel modello = listModels.get(istante+primaLista);
            
                for(int elemento = 0; elemento < modello.size(); elemento++){

                    if(modello.get(elemento) instanceof FrameMemoria){
                        if(((FrameMemoria)modello.get(elemento)).getIndirizzo().
                                equals(indirizzo)){
                            modello.removeElementAt(elemento);
                        }
                    }
                    else{
                        if(((FrameModifica)modello.get(elemento)).frame.getIndirizzo().
                                equals(indirizzo)){
                            modello.removeElementAt(elemento);
                        }
                    }
                    
                }
                
            }
            
            //lo elimina dalla lista dei frame a destra
            listaFrameModel.removeElementAt(indici[i]);
            
        }
       
}//GEN-LAST:event_jButtonEliminaActionPerformed

    private void jButtonModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificaActionPerformed
     
        int indiceSelezione = jListFrame.getSelectedIndex();
        
        if(indiceSelezione == -1){
            return;
        }
        
        Segmento segmentoSelezionato = (Segmento)listaFrameModel.get(indiceSelezione);
        
        int dimensioneAttuale = segmentoSelezionato.getDimensione();
        
        /* Trovo il primo modello del processo proprietario del segmento */
        int primaLista = 0 ;

        for(int processo = 0; processo < jTabbedPaneProcessi.getSelectedIndex(); processo++){
            primaLista += ((Integer)processi.getCombinazioneProcessi()[processo][2]).intValue();
        }

        int numeroIstanti = ((Integer)processi.getCombinazioneProcessi()
                        [jTabbedPaneProcessi.getSelectedIndex()][2]).intValue();
        
        int massimaDimensione = configurazioneAmbiente.getDimensioneRAM();
        
        /* Controllo in tutti gli istanti del processo in cui e' presente il segmento
         quanto e' lo spazio disponibile, e lo matto come massima dimensione */
        for(int istante = 0; istante < numeroIstanti; istante++){
             
            int spazioLibero = configurazioneAmbiente.getDimensioneRAM();
            boolean presente = false;
            DefaultListModel modello = listModels.get(istante+primaLista);
            
            for(int elemento = 0; elemento < modello.size(); elemento++){
                
                if(modello.get(elemento) instanceof Segmento){
                    if(((Segmento)modello.get(elemento)).getIndirizzo().
                            equals(segmentoSelezionato.getIndirizzo())){
                        presente = true;
                    }
                    else{
                        spazioLibero -= ((Segmento)modello.get(elemento)).getDimensione();
                    }    
                }
                else{
                    if(((FrameModifica)modello.get(elemento)).frame.getIndirizzo().
                            equals(segmentoSelezionato.getIndirizzo())){
                        presente = true;
                    }
                    else{
                        spazioLibero -= ((FrameModifica)modello.get(elemento)).frame.getDimensione();
                    } 
                }
                
                
            }
            
            if(presente && spazioLibero < massimaDimensione){
                massimaDimensione = spazioLibero;
            }
                    
         }
        
        
        DatiSegmentoDialog datiSegmentoDialog = new DatiSegmentoDialog(view.getFrame(), true, 
                                                        massimaDimensione,
                                                        dimensioneAttuale);
        
            datiSegmentoDialog.setVisible(true);
            
            int risultato = datiSegmentoDialog.getReturnStatus();
            
            if(risultato == DatiSegmentoDialog.RET_OK){                
                
                segmentoSelezionato.setDimensione(datiSegmentoDialog.getDimensione());  
                
                jListFrame.repaint();
                
                /* Lo modifica anche in tutti gli istanti in cui e' presente */
                /* Per tutti gli istanti aggiorno la lista */
                for(int istante = 0; istante < numeroIstanti; istante++){
                    
                    listaList.get(istante + primaLista).repaint();
                    
                }
                
            }
            
}//GEN-LAST:event_jButtonModificaActionPerformed

    private void jButtonHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHelpActionPerformed
       HelpHtml.openUrl("pagina.htm#Passo4");
    }//GEN-LAST:event_jButtonHelpActionPerformed
    
    private void inizializzaConfigurazioneIniziale() throws Exception {
        confIniziale = new ConfigurazioneIniziale(configurazioneAmbiente.getBandaBusDati(),
                                                  politica.getPolitica(),
                                                  politica.getGestioneMemoria(),                                                  
                                                  politica.getPoliticaSchedulazione(),
                                                  configurazioneAmbiente.getDimensioneRAM(),
                                                  configurazioneAmbiente.getDimensioneAreaSWAP(),
                                                  configurazioneAmbiente.getTempoContextSwitch(),
                                                  configurazioneAmbiente.getTempoAccessoDisco(),
                                                  configurazioneAmbiente.getDimensionePagina(),
                                                  listaProcessi,
                                                  politica.getTimeSlice());
        

        view.setConfigurazioneIniziale(confIniziale);        
        
    }
    
    void aggiornaListe(ConfigurazioneAmbienteJDialog configurazioneAmbiente, PoliticheJDialog politiche, ProcessiJDialog processi){
        this.configurazioneAmbiente = configurazioneAmbiente;
        this.politica=politiche;
        this.processi = processi;
        int numProcessi = configurazioneAmbiente.getNumProcessi();
        arrayListHandler.setDimensioneRAM(configurazioneAmbiente.getDimensioneRAM());
        
        for(int i = 0; i<listModels.size();i++){
            listModels.get(i).clear();
        }
        for(int i = 0; i<modelliListaFrame.size();i++){
            modelliListaFrame.get(i).clear();
        }
        
        
        if(numProcessi<istantiPerProcesso.size()){
            /* Elimino i processi in eccesso */
            int processiDaEliminare = istantiPerProcesso.size()-numProcessi;
            for(int i = 0; i<processiDaEliminare; i++){
                int numIstanti = istantiPerProcesso.get(istantiPerProcesso.size()-1);
                for(int istante=0; istante<numIstanti; istante++){
                    
                     rimuoviLista(istantiPerProcesso.size()-1);
                    
                }
                modelliListaFrame.remove(modelliListaFrame.size()-1);
                jTabbedPaneProcessi.remove(istantiPerProcesso.size()-1);
                istantiPerProcesso.remove(istantiPerProcesso.size()-1);
            }
            
        }
        
        if(numProcessi>istantiPerProcesso.size()){
            /* Aggiungo i processi mancanti */
            int processiDaAggiungere = numProcessi-istantiPerProcesso.size();
            System.out.println();
            for(int i = 0; i<processiDaAggiungere; i++){
                int indiceProcesso=istantiPerProcesso.size();
                int numIstanti = ((Integer)processi.getCombinazioneProcessi()[indiceProcesso][2]).intValue();
                String nomeProcesso = (String) processi.getCombinazioneProcessi()[indiceProcesso][0];
                jTabbedPaneProcessi.addTab(nomeProcesso, creaPannelloProcesso(indiceProcesso));
                modelliListaFrame.add(new DefaultListModel());
                istantiPerProcesso.add(numIstanti);
            }
        }
        
        /* per ogni processo controllo che il numero di istanti sia lo stesso */
        for(int idProcesso=0; idProcesso< istantiPerProcesso.size(); idProcesso++){
            
            int numIstanti = ((Integer)processi.getCombinazioneProcessi()[idProcesso][2]).intValue();
            
            if(numIstanti < istantiPerProcesso.get(idProcesso)){
                /* Rimuovo le liste in più */
                int listeDaRimuovere = istantiPerProcesso.get(idProcesso).intValue()-numIstanti;
                for(int istante=0; istante<listeDaRimuovere; istante++){
                    
                     rimuoviLista(idProcesso);
                    
                }
                istantiPerProcesso.set(idProcesso, numIstanti);
                
            }
            
            if(numIstanti > istantiPerProcesso.get(idProcesso)){
                /* Aggiungo le liste mancanti */
                
                for(int istante=istantiPerProcesso.get(idProcesso).intValue(); istante<numIstanti; istante++){
                    
                     /* JScrollPane del processo: 3 + indice del processo*/
                    JScrollPane pannello = (JScrollPane) jTabbedPaneProcessi.getComponents()[3+idProcesso];
                    JPanel pannelloInterno = (JPanel)pannello.getViewport().getComponents()[0];
                    pannelloInterno.add(creaPannelloLista(idProcesso,istante));
                    
                }
                istantiPerProcesso.set(idProcesso, numIstanti);
            }
            
        }
       
        jTabbedPaneProcessi.repaint();
        System.runFinalization();
        System.gc();
    }
    
    /**
     * Rimuove l'ultima lista del processo
     * 
     * @param processo
     */
    private void rimuoviLista(int processo){
        int numIstanti = istantiPerProcesso.get(processo).intValue();
        istantiPerProcesso.set(processo, numIstanti-1);
        int indiceLista=istantiPerProcesso.get(processo).intValue();
        for(int i=0; i<processo;i++){
            indiceLista+=istantiPerProcesso.get(i).intValue();
        }
        listaList.remove(indiceLista);
        listModels.remove(indiceLista);
        /* JScrollPane del processo: 3 + indice del processo*/
        JScrollPane pannello = (JScrollPane) jTabbedPaneProcessi.getComponents()[3+processo];
        JPanel pannelloInterno = (JPanel)pannello.getViewport().getComponents()[0];
        pannelloInterno.remove(istantiPerProcesso.get(processo).intValue());
    }
    
    private JPanel creaPannelloLista(int idProcesso,int istante){
        
        String commento;
        
        if(politica.getGestioneMemoria() == 1){
            commento = "<html>Trascinare qui le pagine che il processo utilizzera'" +
                    " in questo istante. <br> Fare click col tasto destro del mouse per " +
                    "visualizzare le possibili scelte per la pagina selezionata.</html>";
        }
        else{
            commento = "<html>Trascinare qui i segmenti che il processo utilizzera'" +
                    " in questo istante. <br> Fare click col tasto destro del mouse per" +
                    " visualizzare le possibili scelte per il segmento selezionata.</html>";
        }
        /*calcolo in che punto inserire la lista fra le altre*/
        int indiceLista=istante;
        for(int proc=0; proc<idProcesso; proc++){
            indiceLista+=istantiPerProcesso.get(proc).intValue();
        }
        
        /*creo un modello di default per la lista*/
        listModels.add(indiceLista, new DefaultListModel());

        /* e lo associo alla lista da costruire */
        JList list1=new JList(listModels.get(indiceLista));
        list1.setBackground(new Color(200,200,200));
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setTransferHandler(arrayListHandler);
        list1.setDragEnabled(true);
        list1.setName(Integer.toString(listaList.size()));
        list1.setToolTipText(commento);
        listaList.add(indiceLista,list1); 

        /* Per ogni lista associo il mouseListner */
        list1.addMouseListener(listMouseAdapter);
        JScrollPane list1View = new JScrollPane(list1);
        list1View.setPreferredSize(new Dimension(100, 200));
        JPanel pannelloPiccolo = new JPanel();
        pannelloPiccolo.setLayout(new BorderLayout());
        pannelloPiccolo.add(new JLabel("Istante "+istante),BorderLayout.NORTH);
        pannelloPiccolo.add(list1View,BorderLayout.SOUTH);
        
        return pannelloPiccolo;
        
    }
    
    /**
     * Metodo che crea un JScrollPane contenente tutte le liste rappresentanti 
     * gli istanti di esecuzione del processo passato per parametro.
     * Ogni lista e' contenuta in un JScrollPane indipendente in modo da poter 
     * effettuare lo scrolling se ci sono troppi FrameMemoria.
     * 
     * @param IdProcesso
     *      Intero che rappresenta l'Id del processo di cui creare il pannello.
     * @return Ritorna un JScrollPane contenente tutte le liste degli istanti del
     *         processo passato.
     */
    private JScrollPane creaPannelloProcesso(int idProcesso){
        JPanel nuovoPannelloGlobale = new JPanel();
        
        int numIstanti = ((Integer)processi.getCombinazioneProcessi()[idProcesso][2]).intValue();
        
        for(int i=0; i< numIstanti; i++){
            
            nuovoPannelloGlobale.add(creaPannelloLista(idProcesso,i));
            
        }
        
        JScrollPane nuovoScrollPane = new JScrollPane(nuovoPannelloGlobale);
        nuovoScrollPane.setPreferredSize(new Dimension(420, 270));
        
        return nuovoScrollPane;
    }
    
    /**
     * Metodo che aggiorna il modello per la lista dei FrameMemoria di un processo
     * quando viene selezionato un'altro processo nel JTabbedPane.
     * E' necessario perche' ogni processo ha i propri FrameMemoria.
     */
    private void aggiornaListaFrame(){        
        
        int indiceProcesso = jTabbedPaneProcessi.getSelectedIndex();  
        
        listaFrameModel = modelliListaFrame.get(indiceProcesso);
        
        jListFrame.setModel(listaFrameModel);
        
    }
    
    /**
     * Ritorna il primo intero rappresentante l'indirizzo del primo frameMemoria
     * disponibile per il processo indicato.
     * 
     * @param idProcesso
     *          L'id del processo che chiede un nuovo FrameMemoria
     * @return  L'indirizzo del nuovo FrameMemoria
     */
    private int getNuovoIndirizzo(int idProcesso){
        DefaultListModel modelloAttuale = modelliListaFrame.get(idProcesso);
        int min = 0;
        boolean valido = false;
        
        while(! valido){
            boolean trovato = false;
            int i = 0;
            for(; i<modelloAttuale.size() && !trovato; i++){
                  if(min == Integer.parseInt(((FrameMemoria)modelloAttuale.get(i)).getIndirizzo())){
                      trovato = true;
                  }
            }
            if(trovato){
                min++;
            }
            else{
                valido = true;
            }
        }
        
        return min;
    }
    
    /**
     * Metodo che crea ed associa gli Accessi ai vari FrameMemoria ai processi
     */
    private void creaAccessi() {
        
        int indiceModello = 0;
        
        for(int indiceProcesso = 0; indiceProcesso<listaProcessi.size(); indiceProcesso++){
            
            Processo processoAttuale = listaProcessi.get(indiceProcesso);
          
            for(int istante = 0; istante < processoAttuale.getTempoEsecuzione(); istante++){
                
                /* Per ogni FrameMemoria dell'istante crea l'Accesso */
                for (int frame = 0; frame < listModels.get(indiceModello).size(); frame++){
                    
                    boolean modificaFrame = false;
                    
                    FrameMemoria frameAttuale;
                    
                    if(listModels.get(indiceModello).get(frame) instanceof FrameModifica){
                        
                        modificaFrame = true;
                        frameAttuale = ((FrameModifica) listModels.get(indiceModello).get(frame)).frame;
                    
                    }
                    else{
                        frameAttuale = (FrameMemoria) listModels.get(indiceModello).get(frame);
                    }
                    
                    
                    if(frameAttuale != null){
                        
                        frameAttuale.setIdProcesso(processoAttuale.getId());
                        
                        processoAttuale.richiestaFrameMemoria(frameAttuale, istante, modificaFrame);
                        
                     }
                    
                }
                
                indiceModello++;
                
            }
            
        }
        
    }
       
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnulla;
    private javax.swing.JButton jButtonElimina;
    private javax.swing.JButton jButtonHelp;
    private javax.swing.JButton jButtonIndietro;
    private javax.swing.JButton jButtonModifica;
    private javax.swing.JButton jButtonNuovoFrame;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelAssociazioneProcessi;
    private javax.swing.JLabel jLabelPasso;
    private javax.swing.JList jListFrame;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelFrame;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPaneProcessi;
    // End of variables declaration//GEN-END:variables
    
}
