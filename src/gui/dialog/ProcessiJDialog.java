/*
 * Azienda: Stylosoft
 * Nome file: ProcessiJDialog.java
 * Package: gui.dialog
 * Autore: Giordano Cariani
 * Data: 02/03/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.1 (02/03/2008): inserita label "passo 3 di 4"
 *  - v.1.0 (01/03/2008): Creazione JDialog e impostazione grafica
 */

package gui.dialog;

import gui.SiGeMv2View;
/**
 *
 * @author  Jordy
 */
public class ProcessiJDialog extends javax.swing.JDialog {
    private ConfigurazioneAmbienteJDialog configurazioneAmbiente;
    private PoliticheJDialog politiche;
    private AssociazioneProcessiJDialog associazioneProcessi;
    private SiGeMv2View view;
    
    private Object[][] combinazioneProcessi;
   
    /** Creates new form ProcessiJDialog */
    public ProcessiJDialog(java.awt.Frame parent, boolean modal, ConfigurazioneAmbienteJDialog conf, PoliticheJDialog pol, SiGeMv2View view) {
        super(parent, modal);
        configurazioneAmbiente = conf;
        politiche = pol;
        this.view = view;
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
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelParametriProcessi.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelParametriProcessi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelParametriProcessi.setText("PARAMETRI PROCESSI");

        jButtonIndietro.setText("Indietro");
        jButtonIndietro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIndietroActionPerformed(evt);
            }
        });

        jButtonAvanti.setText("Avanti");
        jButtonAvanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAvantiActionPerformed(evt);
            }
        });

        jButtonAnnulla.setText("Annulla");
        jButtonAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnullaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Passo 3 di 4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jLabelParametriProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(317, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(31, 31, 31))
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jButtonIndietro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAvanti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAnnulla)
                .addContainerGap(113, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelParametriProcessi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAvanti)
                    .addComponent(jButtonAnnulla)
                    .addComponent(jButtonIndietro))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initTable() {      
        
        jTableProcessi = new javax.swing.JTable(new ModelloProcessi(configurazioneAmbiente.getNumProcessi()));
        
        jTableProcessi.setName("jTableProcessi"); // NOI18N
        jScrollPaneProcessi.setViewportView(jTableProcessi);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelParametriProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPaneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jButtonIndietro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAvanti)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAnnulla)))
                .addContainerGap())
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelParametriProcessi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneProcessi, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIndietro)
                    .addComponent(jButtonAvanti)
                    .addComponent(jButtonAnnulla))
                .addContainerGap()));
        pack();
         
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
        associazioneProcessi = new AssociazioneProcessiJDialog(view.getFrame(), true, configurazioneAmbiente, politiche, this, view);
        associazioneProcessi.setVisible(true);
    }//GEN-LAST:event_jButtonAvantiActionPerformed

    private void jButtonAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnullaActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonAnnullaActionPerformed
    
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnulla;
    private javax.swing.JButton jButtonAvanti;
    private javax.swing.JButton jButtonIndietro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelParametriProcessi;
    private javax.swing.JScrollPane jScrollPaneProcessi;
    // End of variables declaration//GEN-END:variables
     private javax.swing.JTable jTableProcessi;

    public Object[][] getCombinazioneProcessi() {
        return combinazioneProcessi;
    }

    public void setCombinazioneProcessi(Object[][] combinazioneProcessi) {
        this.combinazioneProcessi = combinazioneProcessi;
    }    
}
