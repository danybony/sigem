/*
 * AssociazioneProcessiJDialog.java
 *
 * Created on 28 febbraio 2008, 19.43
 */

package gui.dialog;

import javax.swing.JComboBox;
import javax.swing.table.TableColumn;
import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author  Jordy
 */
public class AssociazioneProcessiJDialog extends javax.swing.JDialog {
    
    private ConfigurazioneAmbienteJDialog configurazioneAmbiente;
    private PoliticheJDialog politica;
    private ProcessiJDialog processi;
    
    /** Creates new form AssociazioneProcessiJDialog */
    public AssociazioneProcessiJDialog(java.awt.Frame parent, boolean modal, ConfigurazioneAmbienteJDialog configurazione, PoliticheJDialog pol, ProcessiJDialog proc) {
        super(parent, modal);
        configurazioneAmbiente = configurazione;
        politica = pol;
        processi = proc;
        initComponents();
        initJTable();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelAssociazioneProcessi = new javax.swing.JLabel();
        jScrollPaneAssociazioneProcessi = new javax.swing.JScrollPane();
        jButtonIndietro = new javax.swing.JButton();
        jButtonOk = new javax.swing.JButton();
        jButtonAnnulla = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(gui.SiGeMv2App.class).getContext().getResourceMap(AssociazioneProcessiJDialog.class);
        jLabelAssociazioneProcessi.setFont(resourceMap.getFont("jLabelAssociazioneProcessi.font")); // NOI18N
        jLabelAssociazioneProcessi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAssociazioneProcessi.setText(resourceMap.getString("jLabelAssociazioneProcessi.text")); // NOI18N
        jLabelAssociazioneProcessi.setToolTipText(resourceMap.getString("jLabelAssociazioneProcessi.toolTipText")); // NOI18N
        jLabelAssociazioneProcessi.setName("jLabelAssociazioneProcessi"); // NOI18N

        jScrollPaneAssociazioneProcessi.setName("jScrollPaneAssociazioneProcessi"); // NOI18N

        jButtonIndietro.setText(resourceMap.getString("jButtonIndietro.text")); // NOI18N
        jButtonIndietro.setName("jButtonIndietro"); // NOI18N
        jButtonIndietro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIndietroActionPerformed(evt);
            }
        });

        jButtonOk.setText(resourceMap.getString("jButtonOk.text")); // NOI18N
        jButtonOk.setName("jButtonOk"); // NOI18N
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonAnnulla.setText(resourceMap.getString("jButtonAnnulla.text")); // NOI18N
        jButtonAnnulla.setName("jButtonAnnulla"); // NOI18N
        jButtonAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnullaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPaneAssociazioneProcessi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                            .addComponent(jLabelAssociazioneProcessi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jButtonIndietro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAnnulla)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAssociazioneProcessi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneAssociazioneProcessi, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIndietro)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonAnnulla))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initJTable() {
        jTableAssociazioneProcessi = new javax.swing.JTable(new ModelloProcessi(processi.getCombinazioneProcessi(), 4));
        
        setAssociazioneColonna(jTableAssociazioneProcessi.getColumnModel().getColumn(3));
        
        jTableAssociazioneProcessi.setName("jTableAssociazioneProcessi"); // NOI18N
        jScrollPaneAssociazioneProcessi.setViewportView(jTableAssociazioneProcessi);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAssociazioneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPaneAssociazioneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jButtonIndietro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAnnulla)))
                .addContainerGap())
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAssociazioneProcessi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneAssociazioneProcessi, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIndietro)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonAnnulla))
                .addContainerGap()));
        pack();
    }
        
    private void jButtonAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnullaActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonAnnullaActionPerformed

    private void jButtonIndietroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIndietroActionPerformed
        this.setVisible(false);
        processi.setVisible(true);
    }//GEN-LAST:event_jButtonIndietroActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonOkActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnulla;
    private javax.swing.JButton jButtonIndietro;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelAssociazioneProcessi;
    private javax.swing.JScrollPane jScrollPaneAssociazioneProcessi;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JTable jTableAssociazioneProcessi;
    
    public void setAssociazioneColonna(TableColumn associazioneColonna) {
        String [] valueItems = new String [] { "Pagina", "Segmento"};
        
        JComboBox jComboBoxAssociazione = new JComboBox(valueItems);
        associazioneColonna.setCellEditor(new DefaultCellEditor(jComboBoxAssociazione));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Seleziona");
        associazioneColonna.setCellRenderer(renderer);
    }
}
