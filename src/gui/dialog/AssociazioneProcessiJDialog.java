/*
 * Azienda: Stylosoft
 * Nome file: AssociazioneProcessiJDialog.java
 * Package: gui.dialog
 * Autore: Giordano Cariani
 * Data: 01/03/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.1 (02/03/2008): inserita label "passo 4 di 4"
 *                        inserita inizializzazione grafico processi
 *  - v.1.0 (01/03/2008): Creazione JDialog e impostazione grafica
 */

package gui.dialog;

import gui.SiGeMv2View;
import java.util.LinkedList;
import logic.parametri.ConfigurazioneIniziale;
import logic.parametri.Processo;
import javax.swing.JComboBox;
import javax.swing.table.TableColumn;
import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableCellRenderer;
import logic.parametri.ProcessoConPriorita;
/**
 *
 * @author  Jordy
 */
public class AssociazioneProcessiJDialog extends javax.swing.JDialog {
   private ConfigurazioneAmbienteJDialog configurazioneAmbiente;
    private PoliticheJDialog politica;
    private ProcessiJDialog processi;
    private String associazione;
    private SiGeMv2View view;
    private ConfigurazioneIniziale confIniziale;
    private LinkedList<Processo> listaProcessi;
    
    /** Creates new form AssociazioneProcessiJDialog */
    public AssociazioneProcessiJDialog(java.awt.Frame parent, boolean modal, ConfigurazioneAmbienteJDialog configurazione, PoliticheJDialog pol, ProcessiJDialog proc, SiGeMv2View view) {
        super(parent, modal);
        configurazioneAmbiente = configurazione;
        politica = pol;
        processi = proc;
        this.view = view;
        initComponents();
        initJTable();
        
        if (politica.getGestioneMemoria() == 1){
            jLabelAssociazioneProcessi.setText("ASSOCIAZIONE PROCESSI - PAGINE");
        }
        else {
            jLabelAssociazioneProcessi.setText("ASSOCIAZIONE PROCESSI - SEGMENTI");
        }
        
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
        jLabelPasso = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelAssociazioneProcessi.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelAssociazioneProcessi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAssociazioneProcessi.setText("ASSOCIAZIONE PROCESSI");

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

        jLabelPasso.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabelPasso.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPasso.setText("Passo 4 di 4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneAssociazioneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                            .addComponent(jLabelAssociazioneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(510, Short.MAX_VALUE)
                        .addComponent(jLabelPasso, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(242, Short.MAX_VALUE)
                .addComponent(jButtonIndietro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAnnulla)
                .addGap(155, 155, 155))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelPasso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelAssociazioneProcessi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneAssociazioneProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonAnnulla)
                    .addComponent(jButtonIndietro))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void initJTable() {
       if (politica.getPoliticaSchedulazione() == 6
        || politica.getPoliticaSchedulazione() == 7
        || politica.getPoliticaSchedulazione() == 5) {
           jTableAssociazioneProcessi = new javax.swing.JTable(new ModelloAssociazionePriorita(processi.getCombinazioneProcessi()));
           setAssociazioneColonna(jTableAssociazioneProcessi.getColumnModel().getColumn(4));
       }
       else {
           jTableAssociazioneProcessi = new javax.swing.JTable(new ModelloAssociazione(processi.getCombinazioneProcessi()));
           setAssociazioneColonna(jTableAssociazioneProcessi.getColumnModel().getColumn(3));
       }
        
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
    }

    private void jButtonIndietroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIndietroActionPerformed
        this.setVisible(false);
        processi.setVisible(true);
    }//GEN-LAST:event_jButtonIndietroActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        listaProcessi = new LinkedList<Processo>();
        if (politica.getPoliticaSchedulazione() == 6
        || politica.getPoliticaSchedulazione() == 7
        || politica.getPoliticaSchedulazione() == 5) {
            for (int row=0; row<jTableAssociazioneProcessi.getRowCount(); row++) {
                listaProcessi.add( new ProcessoConPriorita(
                                    (String) jTableAssociazioneProcessi.getValueAt(row, 0),
                                    ((Integer)jTableAssociazioneProcessi.getValueAt(row, 1)).intValue(),
                                    ((Integer)jTableAssociazioneProcessi.getValueAt(row, 2)).intValue(),
                                    ((Integer)jTableAssociazioneProcessi.getValueAt(row, 3)).intValue()
                                    ));
       
            }
        } else {
            for (int row=0; row<jTableAssociazioneProcessi.getRowCount(); row++) {
                listaProcessi.add( new Processo (
                                    (String) jTableAssociazioneProcessi.getValueAt(row, 0),
                                    ((Integer)jTableAssociazioneProcessi.getValueAt(row, 1)).intValue(),
                                    ((Integer)jTableAssociazioneProcessi.getValueAt(row, 2)).intValue()
                                    ));
            }
        }
        
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
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnulla;
    private javax.swing.JButton jButtonIndietro;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelAssociazioneProcessi;
    private javax.swing.JLabel jLabelPasso;
    private javax.swing.JScrollPane jScrollPaneAssociazioneProcessi;
    // End of variables declaration//GEN-END:variables

   private javax.swing.JTable jTableAssociazioneProcessi;

   private void inizializzaConfigurazioneIniziale() throws Exception {
        confIniziale = new ConfigurazioneIniziale(configurazioneAmbiente.getBandaBusDati(),
                                                  politica.getGestioneMemoria(),
                                                  politica.getPolitica(),
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

    public void setAssociazioneColonna(TableColumn associazioneColonna) {
        int numPagine = configurazioneAmbiente.getDimensioneRAM() 
                        / configurazioneAmbiente.getDimensionePagina();
        
        Integer [] valueItems = new Integer [numPagine];
        for (int i=0; i<numPagine; i++)
                valueItems[i]= i+1;
        
        JComboBox jComboBoxAssociazione = new JComboBox(valueItems);
        associazioneColonna.setCellEditor(new DefaultCellEditor(jComboBoxAssociazione));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Seleziona");
        associazioneColonna.setCellRenderer(renderer);
    }

    public String getAssociazione() {
        return associazione;
    }

    public void setAssociazione(String associazione) {
        this.associazione = associazione;
    }
    
   
}
