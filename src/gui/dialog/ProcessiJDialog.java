/*
 * Azienda: Stylosoft
 * Nome file: ProcessiJDialog.java
 * Package: gui.dialog
 * Autore: Giordano Cariani
 * Data: 25/03/2008
 * Versione: 1.5
 * Licenza: open-source
 * Registro delle modifiche: 
 *  - v.1.5 (25/03/2008): Inserito bottone help
 *  - v.1.4 (16/03/2008): Corretta gestione modifica configurazione
 *  - v.1.3 (15/03/2008): Inserita gestione modifica configurazione
 *  - v.1.2 (05/03/2008): Inserita gestione processi con priorita' 
 *  - v.1.1 (02/03/2008): inserita label "passo 3 di 4"
 *  - v.1.0 (01/03/2008): Creazione JDialog e impostazione grafica
 */

package gui.dialog;

import gui.SiGeMv2View;
import gui.utility.HelpHtml;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import logic.parametri.ConfigurazioneIniziale;
/**
 *
 * @author  Jordy
 */
public class ProcessiJDialog extends javax.swing.JDialog {
    private ConfigurazioneAmbienteJDialog configurazioneAmbiente;
    private PoliticheJDialog politiche;
    private ConfigurazioneIniziale confIniziale;
    private AssociazioneProcessiJDialog associazioneProcessi;
    private SiGeMv2View view;
    
    private Object[][] combinazioneProcessi;
    
    boolean modifica = false;
   
    /** Crea il nuovo form ProcessiJDialog */
    public ProcessiJDialog(java.awt.Frame parent, boolean modal, ConfigurazioneAmbienteJDialog conf, PoliticheJDialog pol, SiGeMv2View view) {
        super(parent, modal);
        configurazioneAmbiente = conf;
        politiche = pol;
        this.view = view;
        initComponents();
        initTable(); 
    }
    
    /** Crea il form ProcessiJDialog per la modifica */
    public ProcessiJDialog(java.awt.Frame parent, boolean modal, ConfigurazioneAmbienteJDialog conf, PoliticheJDialog pol, SiGeMv2View view,
            ConfigurazioneIniziale confIniziale) {
        super(parent, modal);
        configurazioneAmbiente = conf;
        politiche = pol;
        this.view = view;
        this.confIniziale=confIniziale;
        modifica = true;
        initComponents();
        initTable(); 
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelParametriProcessi = new javax.swing.JLabel();
        jScrollPaneProcessi = new javax.swing.JScrollPane();
        jButtonIndietro = new javax.swing.JButton();
        jButtonAvanti = new javax.swing.JButton();
        jButtonAnnulla = new javax.swing.JButton();
        jLabelPasso = new javax.swing.JLabel();
        jButtonHelp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelParametriProcessi.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelParametriProcessi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelParametriProcessi.setText("PARAMETRI PROCESSI");

        jButtonIndietro.setText("Indietro");
        jButtonIndietro.setMaximumSize(new java.awt.Dimension(72, 23));
        jButtonIndietro.setMinimumSize(new java.awt.Dimension(72, 23));
        jButtonIndietro.setPreferredSize(new java.awt.Dimension(72, 23));
        jButtonIndietro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIndietroActionPerformed(evt);
            }
        });

        jButtonAvanti.setText("Avanti");
        jButtonAvanti.setMaximumSize(new java.awt.Dimension(72, 23));
        jButtonAvanti.setMinimumSize(new java.awt.Dimension(72, 23));
        jButtonAvanti.setPreferredSize(new java.awt.Dimension(72, 23));
        jButtonAvanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAvantiActionPerformed(evt);
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

        jLabelPasso.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabelPasso.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPasso.setText("Passo 3 di 4");

        jButtonHelp.setText("Help");
        jButtonHelp.setMaximumSize(new java.awt.Dimension(72, 23));
        jButtonHelp.setMinimumSize(new java.awt.Dimension(72, 23));
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                            .addComponent(jLabelParametriProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabelPasso)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonHelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                        .addComponent(jButtonIndietro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAvanti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelPasso, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelParametriProcessi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonHelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAvanti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonIndietro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initTable() {      
        if (politiche.getPoliticaSchedulazione() == 6
        ||  politiche.getPoliticaSchedulazione() == 7
        ||  politiche.getPoliticaSchedulazione() == 5) {
            if (modifica)
                jTableProcessi = new javax.swing.JTable(new ModelloProcessiPriorita(confIniziale.getListaProcessi(),configurazioneAmbiente.getNumProcessi()));
            else
                jTableProcessi = new javax.swing.JTable(new ModelloProcessiPriorita(configurazioneAmbiente.getNumProcessi()));
            setPrioritaColonna(jTableProcessi.getColumnModel().getColumn(3));
        }
        else {
            if (modifica)
                jTableProcessi = new javax.swing.JTable(new ModelloProcessi(confIniziale.getListaProcessi(),configurazioneAmbiente.getNumProcessi()));
            else
                jTableProcessi = new javax.swing.JTable(new ModelloProcessi(configurazioneAmbiente.getNumProcessi()));
        }
        jTableProcessi.setName("jTableProcessi"); // NOI18N
        jTableProcessi.setCellSelectionEnabled(true);
        jTableProcessi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        /*
        jTableProcessi.addMouseListener(new MouseAdapter(){

            public void mouseClicked(MouseEvent e) {
               int riga = jTableProcessi.getSelectionModel().getLeadSelectionIndex();
               int colonna = jTableProcessi.getColumnModel().getSelectionModel().
                        getLeadSelectionIndex();
               System.out.println(jTableProcessi.getModel().getValueAt(riga, colonna));  
            }

            public void mouseReleased(MouseEvent e) {
                
            }

            public void mouseEntered(MouseEvent e) {
               
            }

            public void mouseExited(MouseEvent e) {
                
            }
            
            
        });*/
        jScrollPaneProcessi.setViewportView(jTableProcessi);
/*
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelParametriProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPaneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jButtonIndietro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAvanti)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAnnulla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonHelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelParametriProcessi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneProcessi, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIndietro)
                    .addComponent(jButtonAvanti)
                    .addComponent(jButtonAnnulla)
                    .addComponent(jButtonHelp))
                .addContainerGap()));
        pack();*/
         
    }

    private void jButtonIndietroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIndietroActionPerformed
        this.setVisible(false);
        politiche.setVisible(true);
    }//GEN-LAST:event_jButtonIndietroActionPerformed

    private void jButtonAvantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAvantiActionPerformed
        combinazioneProcessi = new Object[jTableProcessi.getRowCount()][jTableProcessi.getColumnCount()];
        
        for (int row=0; row<jTableProcessi.getRowCount(); row++) 
            for (int col=0; col<jTableProcessi.getColumnCount(); col++)
                combinazioneProcessi[row][col]= jTableProcessi.getValueAt(row, col);
                                    
        this.setVisible(false);
        if (modifica)
            associazioneProcessi = new AssociazioneProcessiJDialog(view.getFrame(), true, configurazioneAmbiente, politiche, this, view, confIniziale);
        else
            associazioneProcessi = new AssociazioneProcessiJDialog(view.getFrame(), true, configurazioneAmbiente, politiche, this, view);
        associazioneProcessi.setVisible(true);
    }//GEN-LAST:event_jButtonAvantiActionPerformed

    private void jButtonAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnullaActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonAnnullaActionPerformed

    private void jButtonHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHelpActionPerformed
        HelpHtml.openUrl("pagina.htm#Passo3");
    }//GEN-LAST:event_jButtonHelpActionPerformed
    
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnulla;
    private javax.swing.JButton jButtonAvanti;
    private javax.swing.JButton jButtonHelp;
    private javax.swing.JButton jButtonIndietro;
    private javax.swing.JLabel jLabelParametriProcessi;
    private javax.swing.JLabel jLabelPasso;
    private javax.swing.JScrollPane jScrollPaneProcessi;
    // End of variables declaration//GEN-END:variables
     private javax.swing.JTable jTableProcessi;

    public Object[][] getCombinazioneProcessi() {
        return combinazioneProcessi;
    }

    public void setCombinazioneProcessi(Object[][] combinazioneProcessi) {
        this.combinazioneProcessi = combinazioneProcessi;
    }    
   
    public void setPrioritaColonna(TableColumn associazioneColonna) {
        
        Integer [] valueItems = new Integer[5];
        for (int i=0; i<5; i++)
            valueItems[i]= i+1;
        
        JComboBox jComboBoxPriorita = new JComboBox(valueItems);
        associazioneColonna.setCellEditor(new DefaultCellEditor(jComboBoxPriorita));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Seleziona");
        associazioneColonna.setCellRenderer(renderer);
    }
}
