/*
 * Azienda: Stylosoft
 * Nome file: PoliticheJDialog.java
 * Package: gui.dialog.java
 * Autore: Giordano Cariani
 * Data: 02/03/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.1 (02/03/2008): migliorata la gestione delle variabili private
 *                        inserita label "passo 2 di 4"
 *  - v.1.0 (01/03/2008): Creazione JDialog e impostazione grafica
 */

package gui.dialog;
import gui.SiGeMv2View;
/**
 *
 * @author  Jordy
 */
public class PoliticheJDialog extends javax.swing.JDialog {
    
    private ConfigurazioneAmbienteJDialog configurazioneAmbiente;
    private ProcessiJDialog processi;
    private SiGeMv2View view;
    
    private int gestioneMemoria;
    private int politica;
    private int politicaSchedulazione;
    
    /** Creates new form PoliticheJDialog */
    public PoliticheJDialog(java.awt.Frame parent, boolean modal, ConfigurazioneAmbienteJDialog conf, SiGeMv2View view) {
        super(parent, modal);
        configurazioneAmbiente = conf;
        this.view = view;
        initComponents();
        gestionePoliticaPagine((String)jComboBoxRimpiazzoPagine.getItemAt(0));
        gestioneSchedulazione((String) jComboBoxSchedulazione.getItemAt(0));
        jComboBoxRimpiazzoSegmenti.setEnabled(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelPolitiche = new javax.swing.JLabel();
        jLabelRimpiazzoPagine = new javax.swing.JLabel();
        jComboBoxRimpiazzoPagine = new javax.swing.JComboBox();
        jLabelRimpiazzoSegmenti = new javax.swing.JLabel();
        jComboBoxRimpiazzoSegmenti = new javax.swing.JComboBox();
        jLabelSchedulazione = new javax.swing.JLabel();
        jComboBoxSchedulazione = new javax.swing.JComboBox();
        jButtonIndietro = new javax.swing.JButton();
        jButtonAvanti = new javax.swing.JButton();
        jButtonAnnulla = new javax.swing.JButton();
        jLabelTecnicaGestioneMemoria = new javax.swing.JLabel();
        jComboBoxSuddivisioneMemoria = new javax.swing.JComboBox();
        jLabelPasso = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelPolitiche.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelPolitiche.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPolitiche.setText("POLITICHE");

        jLabelRimpiazzoPagine.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabelRimpiazzoPagine.setText("Politica di rimpiazzo delle pagine");

        jComboBoxRimpiazzoPagine.setFont(new java.awt.Font("Tahoma", 0, 12));
        jComboBoxRimpiazzoPagine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Not Recently Used (NRU)", "First In First Out (FIFO)", "Second Chance (SC)", "Clock (C)", "Least Recently Used (LRU)", "Not Frequently Used (NFU)", "Aging (A)" }));
        jComboBoxRimpiazzoPagine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRimpiazzoPagineActionPerformed(evt);
            }
        });

        jLabelRimpiazzoSegmenti.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabelRimpiazzoSegmenti.setText("Politica di rimpiazzo dei segmenti");

        jComboBoxRimpiazzoSegmenti.setFont(new java.awt.Font("Tahoma", 0, 12));
        jComboBoxRimpiazzoSegmenti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "First-Fit", "Next-Fit", "Best-Fit", "Worst-Fit", "Quick-Fit" }));
        jComboBoxRimpiazzoSegmenti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRimpiazzoSegmentiActionPerformed(evt);
            }
        });

        jLabelSchedulazione.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabelSchedulazione.setText("Politica di schedulazione");

        jComboBoxSchedulazione.setFont(new java.awt.Font("Tahoma", 0, 12));
        jComboBoxSchedulazione.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "First-Come-First-Served (FCFS)", "Priorità (P)", "Round Robin (RR)", "Round Robin con priorità (RRP)", "Round Robin con priorità e prerilascio (RRPP)", "Shortest Job First (SJF)", "Shortest Remaining Time Next (SRTN)" }));
        jComboBoxSchedulazione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSchedulazioneActionPerformed(evt);
            }
        });

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

        jLabelTecnicaGestioneMemoria.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabelTecnicaGestioneMemoria.setText("Tecnica di gestione memoria");

        jComboBoxSuddivisioneMemoria.setFont(new java.awt.Font("Tahoma", 0, 12));
        jComboBoxSuddivisioneMemoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pagine", "segmenti" }));
        jComboBoxSuddivisioneMemoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSuddivisioneMemoriaActionPerformed(evt);
            }
        });

        jLabelPasso.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabelPasso.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPasso.setText("Passo 2 di 4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelPolitiche, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jComboBoxSchedulazione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelSchedulazione))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelRimpiazzoSegmenti)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxRimpiazzoSegmenti, 0, 176, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelRimpiazzoPagine)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxRimpiazzoPagine, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelTecnicaGestioneMemoria)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(jComboBoxSuddivisioneMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelPasso, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
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
                .addComponent(jLabelPasso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPolitiche)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTecnicaGestioneMemoria)
                    .addComponent(jComboBoxSuddivisioneMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRimpiazzoPagine)
                    .addComponent(jComboBoxRimpiazzoPagine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRimpiazzoSegmenti)
                    .addComponent(jComboBoxRimpiazzoSegmenti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelSchedulazione)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxSchedulazione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAvanti)
                    .addComponent(jButtonAnnulla)
                    .addComponent(jButtonIndietro))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIndietroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIndietroActionPerformed
        this.setVisible(false);
        configurazioneAmbiente.setVisible(true);
}//GEN-LAST:event_jButtonIndietroActionPerformed

    private void jButtonAvantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAvantiActionPerformed
        this.setVisible(false);
        processi = new ProcessiJDialog(view.getFrame(), true, configurazioneAmbiente, this, view);
        processi.setVisible(true);
    }//GEN-LAST:event_jButtonAvantiActionPerformed

    private void jButtonAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnullaActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonAnnullaActionPerformed

    private void jComboBoxRimpiazzoPagineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxRimpiazzoPagineActionPerformed
        gestionePoliticaPagine((String)jComboBoxRimpiazzoPagine.getItemAt(jComboBoxRimpiazzoPagine.getSelectedIndex()));
    }//GEN-LAST:event_jComboBoxRimpiazzoPagineActionPerformed

    private void jComboBoxRimpiazzoSegmentiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxRimpiazzoSegmentiActionPerformed
        gestionePoliticaSegmenti((String)jComboBoxRimpiazzoSegmenti.getItemAt(jComboBoxRimpiazzoSegmenti.getSelectedIndex()));
    }//GEN-LAST:event_jComboBoxRimpiazzoSegmentiActionPerformed

    private void jComboBoxSchedulazioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSchedulazioneActionPerformed
        gestioneSchedulazione((String)jComboBoxSchedulazione.getItemAt(jComboBoxSchedulazione.getSelectedIndex()));
    }//GEN-LAST:event_jComboBoxSchedulazioneActionPerformed

    private void jComboBoxSuddivisioneMemoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSuddivisioneMemoriaActionPerformed
       if ("pagine".equalsIgnoreCase((String) jComboBoxSuddivisioneMemoria.getItemAt(jComboBoxSuddivisioneMemoria.getSelectedIndex()))) {
           jComboBoxRimpiazzoSegmenti.setEnabled(false);
           jComboBoxRimpiazzoPagine.setEnabled(true);
       }
       else if ("segmenti".equalsIgnoreCase((String) jComboBoxSuddivisioneMemoria.getItemAt(jComboBoxSuddivisioneMemoria.getSelectedIndex()))) {
           jComboBoxRimpiazzoPagine.setEnabled(false);
           jComboBoxRimpiazzoSegmenti.setEnabled(true);
       }
    }//GEN-LAST:event_jComboBoxSuddivisioneMemoriaActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnulla;
    private javax.swing.JButton jButtonAvanti;
    private javax.swing.JButton jButtonIndietro;
    private javax.swing.JComboBox jComboBoxRimpiazzoPagine;
    private javax.swing.JComboBox jComboBoxRimpiazzoSegmenti;
    private javax.swing.JComboBox jComboBoxSchedulazione;
    private javax.swing.JComboBox jComboBoxSuddivisioneMemoria;
    private javax.swing.JLabel jLabelPasso;
    private javax.swing.JLabel jLabelPolitiche;
    private javax.swing.JLabel jLabelRimpiazzoPagine;
    private javax.swing.JLabel jLabelRimpiazzoSegmenti;
    private javax.swing.JLabel jLabelSchedulazione;
    private javax.swing.JLabel jLabelTecnicaGestioneMemoria;
    // End of variables declaration//GEN-END:variables

    public int getGestioneMemoria() {
        return gestioneMemoria;
    }

    public void setGestioneMemoria(int gestioneMemoria) {
        this.gestioneMemoria = gestioneMemoria;
    }

    public int getPolitica() {
        return politica;
    }

    public void setPolitica(int politica) {
        this.politica = politica;
    }

    public int getPoliticaSchedulazione() {
        return politicaSchedulazione;
    }

    public void setPoliticaSchedulazione(int politicaSchedulazione) {
        this.politicaSchedulazione = politicaSchedulazione;
    }

    private void gestionePoliticaPagine(String politica) {
        int inizio = politica.lastIndexOf("(");
        int fine = politica.lastIndexOf(")");
        politica = politica.substring(inizio+1, fine);
        setGestioneMemoria(1);
        if (politica.equalsIgnoreCase("NRU"))
            setPolitica(1);
        else if (politica.equalsIgnoreCase("FIFO"))
            setPolitica(2);
        else if (politica.equalsIgnoreCase("SC"))
            setPolitica(3);
        else if (politica.equalsIgnoreCase("C"))
            setPolitica(4);
        else if (politica.equalsIgnoreCase("LRU"))
            setPolitica(5);
        else if (politica.equalsIgnoreCase("NFU"))
            setPolitica(6);
        else if (politica.equalsIgnoreCase("A"))
            setPolitica(7);
    }
     
    private void gestionePoliticaSegmenti(String politica) {
        setGestioneMemoria(2);
        if (politica.equalsIgnoreCase("FIRST-FIT"))
            setPolitica(1);
        else if (politica.equalsIgnoreCase("NEXT-FIT"))
            setPolitica(2);
        else if (politica.equalsIgnoreCase("BEST-FIT"))
            setPolitica(3);
        else if (politica.equalsIgnoreCase("WORKS-FIT"))
            setPolitica(4);
        else if (politica.equalsIgnoreCase("QUICK-FIT"))
            setPolitica(5);
        }
    
    private void gestioneSchedulazione(String politica) {
        int inizio = politica.lastIndexOf("(");
        int fine = politica.lastIndexOf(")");
        politica = politica.substring(inizio+1, fine);
        setGestioneMemoria(1);
        if (politica.equalsIgnoreCase("FCFS"))
            setPoliticaSchedulazione(1);
        else if (politica.equalsIgnoreCase("SJF"))
            setPoliticaSchedulazione(2);
        else if (politica.equalsIgnoreCase("SRTN"))
            setPoliticaSchedulazione(3);
        else if (politica.equalsIgnoreCase("RR"))
            setPoliticaSchedulazione(4);
        else if (politica.equalsIgnoreCase("RRP"))
            setPoliticaSchedulazione(5);
        else if (politica.equalsIgnoreCase("RRPP"))
            setPoliticaSchedulazione(6);
        else if (politica.equalsIgnoreCase("P"))
            setPoliticaSchedulazione(7);
    }
}
