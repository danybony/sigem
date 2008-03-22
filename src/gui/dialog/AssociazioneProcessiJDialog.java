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

package gui.dialog;

import gui.SiGeMv2View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

/**
 *
 * @author  Jordy
 */
public class AssociazioneProcessiJDialog extends javax.swing.JDialog {
    private ConfigurazioneAmbienteJDialog configurazioneAmbiente;
    private PoliticheJDialog politica;
    private ProcessiJDialog processi;
    private SiGeMv2View view;
    private ConfigurazioneIniziale confIniziale;
    private LinkedList<Processo> listaProcessi;
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
    
        
    /** Creates new form AssociazioneProcessiJDialog
     * @param parent
     * @param modal 
     * @param configurazione
     * @param pol 
     * @param proc
     * @param view 
     */
    public AssociazioneProcessiJDialog(java.awt.Frame parent, boolean modal, ConfigurazioneAmbienteJDialog configurazione, PoliticheJDialog pol, ProcessiJDialog proc, SiGeMv2View view) {
        super(parent, modal);
        configurazioneAmbiente = configurazione;
        politica = pol;
        processi = proc;
        this.view = view;
        
        int numProcessi = configurazioneAmbiente.getNumProcessi();
        
        /* Crea i modelli per la lista dei frame */
        for(int i=0; i<numProcessi; i++){           
            modelliListaFrame.add(new DefaultListModel());
        } 
        
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
        
        arrayListHandler = new ArrayListTransferHandler(configurazioneAmbiente.getDimensioneRAM());
        
        for(int i=0; i<numProcessi; i++){
            jTabbedPaneProcessi.addTab("Processo "+i, creaPannelloProcesso(i));
            modelliListaFrame.add(new DefaultListModel());
        }          
        
        /* Imposto la lista dei FrameMemoria di destra */
        listaFrameModel = modelliListaFrame.get(0);
        jListFrame.setModel(listaFrameModel);
        jListFrame.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListFrame.setTransferHandler(arrayListHandler);
        jListFrame.setDragEnabled(true);
    }
    
    class FrameModifica{
        FrameMemoria frame;
        FrameModifica(FrameMemoria frame){
            this.frame = frame;
        }
        @Override
        public String toString() {
		return frame.toString();
	} 
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
               
               if (e.getSource() == menuItemElimina) {                   
                     // Elimina il frameMemoria selezionato                   
                     int index = lista.getSelectedIndex();
                     int idLista = Integer.parseInt(lista.getName());
                     listModels.get(idLista).remove(index);                 
                }
               else {
                     // Modifica
                     int index = lista.getSelectedIndex();
                     int idLista = Integer.parseInt(lista.getName());
                     if (listModels.get(idLista).get(index) instanceof FrameMemoria){
                         FrameMemoria frameSelezionato = (FrameMemoria) listModels.get(idLista).get(index);
                         listModels.get(idLista).setElementAt(new FrameModifica(frameSelezionato), index);
                     }
                     else{
                         FrameMemoria frameSelezionato = ((FrameModifica) listModels.get(idLista).get(index)).frame;
                         listModels.get(idLista).setElementAt(frameSelezionato, index);
                     
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelPasso.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabelPasso.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPasso.setText("Passo 4 di 4");

        jButtonIndietro.setText("Indietro");
        jButtonIndietro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIndietroActionPerformed(evt);
            }
        });

        jButtonOk.setText("Ok");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonAnnulla.setText("Annulla");
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
                    .addComponent(jButtonModifica, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(244, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabelPasso, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonIndietro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAnnulla)
                        .addGap(164, 164, 164))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabelAssociazioneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                    .addComponent(jButtonOk)
                    .addComponent(jButtonAnnulla)
                    .addComponent(jButtonIndietro))
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
    private JScrollPane creaPannelloProcesso(int IdProcesso){
        JPanel nuovoPannelloGlobale = new JPanel();
                
        int numIstanti = ((Integer)processi.getCombinazioneProcessi()[IdProcesso][2]).intValue();
        
        for(int i=0; i< numIstanti; i++){
            /*creo un modello di default per la lista*/
            listModels.add(new DefaultListModel());
            
            /* e lo associo alla lista da costruire */
            JList list1=new JList(listModels.get(listModels.size()-1));
            list1.setBackground(new Color(200,200,200));
            list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list1.setTransferHandler(arrayListHandler);
            list1.setDragEnabled(true);
            list1.setName(Integer.toString(listaList.size()));
            listaList.add(list1); 
            
            /* Per ogni lista creo un mouseListner */
            list1.addMouseListener(new MouseAdapter() {
                @Override
                 public void mouseClicked(MouseEvent e) { 
                     /* Rende visibile il menu alle coordinate attuali del mouse */
                     JList lista =((JList)e.getComponent());
                     if (e.getButton() == MouseEvent.BUTTON3) {
                         if(!lista.isSelectionEmpty()
                         && lista.locationToIndex(e.getPoint())
                         == lista.getSelectedIndex()){
                            int index = lista.getSelectedIndex();
                            int idLista = Integer.parseInt(lista.getName());
                            if (listModels.get(idLista).get(index) instanceof FrameMemoria){
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
             });   
            
            JScrollPane list1View = new JScrollPane(list1);
            list1View.setPreferredSize(new Dimension(100, 200));
            JPanel pannelloPiccolo = new JPanel();
            pannelloPiccolo.setLayout(new BorderLayout());
            pannelloPiccolo.add(new JLabel("Istante "+i),BorderLayout.NORTH);
            pannelloPiccolo.add(list1View,BorderLayout.SOUTH);
            nuovoPannelloGlobale.add(pannelloPiccolo);
        }
        
        JScrollPane nuovoScrollPane = new JScrollPane(nuovoPannelloGlobale);
        nuovoScrollPane.setPreferredSize(new Dimension(420, 270));
        
        return nuovoScrollPane;
    }
    
    /**
     * Metodo che aggiorna il modello per la lista dei FrameMemoria di un processo
     * quando viene selezionato un'altro processo nel JTabbedPane.
     * E' necessario perchè ogni processo ha i propri FrameMemoria.
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
            if(! trovato){
                valido = true;
            }
            else{
                min++;
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
                    
                    boolean modifica = false;
                    
                    FrameMemoria frameAttuale;
                    
                    if(listModels.get(indiceModello).get(frame) instanceof FrameModifica){
                        
                        modifica = true;
                        frameAttuale = ((FrameModifica) listModels.get(indiceModello).get(frame)).frame;
                    
                    }
                    else{
                        frameAttuale = (FrameMemoria) listModels.get(indiceModello).get(frame);
                    }
                    
                    
                    if(frameAttuale != null){
                        
                        frameAttuale.setIdProcesso(processoAttuale.getId());
                        
                        processoAttuale.richiestaFrameMemoria(frameAttuale, istante, modifica);
                        
                     }
                    
                }
                
                indiceModello++;
                
            }
            
        }
        
    }
       
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnulla;
    private javax.swing.JButton jButtonElimina;
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
