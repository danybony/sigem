/*
 * Azienda: Stylosoft
 * Nome file: ConfigurazioneAmbienteJDialog.java
 * Package: gui.dialog.java
 * Autore: Giordano Cariani
 * Data: 28/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.0 (01/03/2008): Creazione JDialog e impostazione grafica
 */

package gui.dialog;

import javax.swing.SpinnerListModel;
import gui.SiGeMv2View;

/**
 *
 * @author  Jordy
 */
public class ConfigurazioneAmbienteJDialog extends javax.swing.JDialog {
    private static final int DIMPROCESSI = 20;
    private static final int DIMRAM = 9;
    private static final int DIMSWAP = 7;
    private static final int DIMPAGINE = 3;
    
    private int numProcessi;
    private int dimensioneRAM;
    private int dimensioneAreaSWAP;
    private int dimensionePagina;
    private int tempoContextSwitch;
    private int tempoAccessoDisco;
    private int bandaBusDati;
    private SiGeMv2View view;
    

    /** Creates new form ConfigurazioneAmbienteJDialog */
    public ConfigurazioneAmbienteJDialog(java.awt.Frame parent, boolean modal, SiGeMv2View view) {
        super(parent, modal);
        this.view = view;
        initComponents();
        initJSpinnerProcessi();
        initSpinnerDimensioneRAM();
        initSpinnerDimensioneSWAP();
        initSpinnerPagine();
        initSpinnerTempi();
    }
    
    public ConfigurazioneAmbienteJDialog(ConfigurazioneAmbienteJDialog conf, SiGeMv2View view) {
        this.view = view;
        setNumProcessi(conf.getNumProcessi());
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelConfigurazioneIniziale = new javax.swing.JLabel();
        jLabelProcessi = new javax.swing.JLabel();
        jLabelDimensioneRAM = new javax.swing.JLabel();
        jLabelDimensionaAreaSWAP = new javax.swing.JLabel();
        jLabelDimensionePagina = new javax.swing.JLabel();
        jLabelTempoContextSwitch = new javax.swing.JLabel();
        jLabelTempoAccessoDisco = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSpinnerProcessi = new javax.swing.JSpinner();
        jSpinnerDimensioneRAM = new javax.swing.JSpinner();
        jSpinnerDimensioneAreaSWAP = new javax.swing.JSpinner();
        jSpinnerDimensionePagina = new javax.swing.JSpinner();
        jSpinnerTempoContextSwitch = new javax.swing.JSpinner();
        jSpinnerTempoAccessoDisco = new javax.swing.JSpinner();
        jSpinnerBandaBusDati = new javax.swing.JSpinner();
        jButtonAvanti = new javax.swing.JButton();
        jButtonAnnulla = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelConfigurazioneIniziale.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelConfigurazioneIniziale.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelConfigurazioneIniziale.setText("CONFIGURAZIONE INIZIALE");

        jLabelProcessi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelProcessi.setText("Processi (da 1 a 20)");

        jLabelDimensioneRAM.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelDimensioneRAM.setText("Dimensione RAM (kb)");

        jLabelDimensionaAreaSWAP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelDimensionaAreaSWAP.setText("Dimensione Area SWAP (kb)");

        jLabelDimensionePagina.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelDimensionePagina.setText("Dimensione Pagina (kb)");

        jLabelTempoContextSwitch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelTempoContextSwitch.setText("Tempo Context Switch (ms)");

        jLabelTempoAccessoDisco.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelTempoAccessoDisco.setText("Tempo di accesso al disco (ms)");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Banda del bus di dati");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelConfigurazioneIniziale, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelProcessi)
                                    .addComponent(jLabelDimensioneRAM)
                                    .addComponent(jLabelDimensionaAreaSWAP)
                                    .addComponent(jLabelDimensionePagina)
                                    .addComponent(jLabelTempoContextSwitch)
                                    .addComponent(jLabelTempoAccessoDisco)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSpinnerBandaBusDati)
                                    .addComponent(jSpinnerTempoAccessoDisco)
                                    .addComponent(jSpinnerTempoContextSwitch)
                                    .addComponent(jSpinnerDimensionePagina)
                                    .addComponent(jSpinnerDimensioneAreaSWAP)
                                    .addComponent(jSpinnerDimensioneRAM)
                                    .addComponent(jSpinnerProcessi, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonAvanti)
                        .addGap(29, 29, 29)
                        .addComponent(jButtonAnnulla)
                        .addGap(119, 119, 119))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelConfigurazioneIniziale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelProcessi)
                    .addComponent(jSpinnerProcessi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDimensioneRAM)
                    .addComponent(jSpinnerDimensioneRAM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDimensionaAreaSWAP)
                    .addComponent(jSpinnerDimensioneAreaSWAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDimensionePagina)
                    .addComponent(jSpinnerDimensionePagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTempoContextSwitch)
                    .addComponent(jSpinnerTempoContextSwitch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTempoAccessoDisco)
                    .addComponent(jSpinnerTempoAccessoDisco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jSpinnerBandaBusDati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAvanti)
                    .addComponent(jButtonAnnulla))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAvantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAvantiActionPerformed
       setNumProcessi(Integer.parseInt(jSpinnerProcessi.getValue().toString()));
       setDimensioneRAM(Integer.parseInt(jSpinnerDimensioneRAM.getValue().toString()));
       setBandaBusDati(Integer.parseInt(jSpinnerBandaBusDati.getValue().toString()));
       setDimensioneAreaSWAP(Integer.parseInt(jSpinnerDimensioneAreaSWAP.getValue().toString()));
       setDimensionePagina(Integer.parseInt(jSpinnerDimensionePagina.getValue().toString()));
       setTempoAccessoDisco(Integer.parseInt(jSpinnerTempoAccessoDisco.getValue().toString()));
       setTempoContextSwitch(Integer.parseInt(jSpinnerTempoContextSwitch.getValue().toString()));

       this.setVisible(false);
       
       PoliticheJDialog politicheJDialog = new PoliticheJDialog(view.getFrame(), true, this, view);
       politicheJDialog.setVisible(true);
    }//GEN-LAST:event_jButtonAvantiActionPerformed

    private void jButtonAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnullaActionPerformed
        this.setVisible(false);
}//GEN-LAST:event_jButtonAnnullaActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnnulla;
    private javax.swing.JButton jButtonAvanti;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelConfigurazioneIniziale;
    private javax.swing.JLabel jLabelDimensionaAreaSWAP;
    private javax.swing.JLabel jLabelDimensionePagina;
    private javax.swing.JLabel jLabelDimensioneRAM;
    private javax.swing.JLabel jLabelProcessi;
    private javax.swing.JLabel jLabelTempoAccessoDisco;
    private javax.swing.JLabel jLabelTempoContextSwitch;
    private javax.swing.JSpinner jSpinnerBandaBusDati;
    private javax.swing.JSpinner jSpinnerDimensioneAreaSWAP;
    private javax.swing.JSpinner jSpinnerDimensionePagina;
    private javax.swing.JSpinner jSpinnerDimensioneRAM;
    private javax.swing.JSpinner jSpinnerProcessi;
    private javax.swing.JSpinner jSpinnerTempoAccessoDisco;
    private javax.swing.JSpinner jSpinnerTempoContextSwitch;
    // End of variables declaration//GEN-END:variables

    private Integer[] impostaNumProcessi() {
        Integer[] numProcessiArray= new Integer[DIMPROCESSI];
        for (int i=0; i<DIMPROCESSI; i++)
            numProcessiArray[i]= (i+1);
        return numProcessiArray;
    }

    private Integer[] impostaDimensioniRAM() {
        Integer[] dimensioniRAM= new Integer[DIMRAM];
        Double potenza;
        for (int i=0; i<DIMRAM; i++) {
            potenza = Math.pow(2,(i+1));
            dimensioniRAM[i] = potenza.intValue();
        }
        return dimensioniRAM;
    }
    
    private Integer[] impostaDimensioniSWAP() {
        Integer[] dimensioniSWAP= new Integer[DIMSWAP];
        Double potenza;
        for (int i=0; i<DIMSWAP; i++) {
            potenza = Math.pow(2,(i+1));
            dimensioniSWAP[i]= potenza.intValue();
        }
        return dimensioniSWAP;
    }

        private Integer[] impostaPagine() {
        Integer[] dimensioniPagine= new Integer[DIMPAGINE];
        Double potenza;
        dimensioniPagine[0]=1;
        for (int i=1; i<DIMPAGINE; i++) {
            potenza = Math.pow(2,i);
            dimensioniPagine[i]= potenza.intValue();
        }
        return dimensioniPagine;
    }

    private Integer[] impostaTempi() {
        Integer[] tempo = new Integer[6];
        tempo[0]=1;
        tempo[1]=5;
        tempo[2]=50;
        tempo[3]=500;
        tempo[4]=1000;
        tempo[5]=2000;
        return tempo;
    }

    private void initJSpinnerProcessi() {
        jSpinnerProcessi.setModel(new SpinnerListModel(impostaNumProcessi()));
    }
    
    private void initSpinnerDimensioneRAM() {
        jSpinnerDimensioneRAM.setModel(new SpinnerListModel(impostaDimensioniRAM()));
    }

    private void initSpinnerDimensioneSWAP() {
        jSpinnerDimensioneAreaSWAP.setModel(new SpinnerListModel(impostaDimensioniSWAP()));
    }
       
    private void initSpinnerPagine() {
        jSpinnerDimensionePagina.setModel(new SpinnerListModel(impostaPagine()));
    }
    
    private void initSpinnerTempi() {
        jSpinnerTempoContextSwitch.setModel(new SpinnerListModel(impostaTempi()));
        jSpinnerTempoAccessoDisco.setModel(new SpinnerListModel(impostaTempi()));
    }
    
    public int getNumProcessi() {
        return numProcessi;
    }

    public void setNumProcessi(int numProcessi) {
        this.numProcessi = numProcessi;
    }    

    public int getBandaBusDati() {
        return bandaBusDati;
    }

    public void setBandaBusDati(int bandaBusDati) {
        this.bandaBusDati = bandaBusDati;
    }

    public int getDimensioneAreaSWAP() {
        return dimensioneAreaSWAP;
    }

    public void setDimensioneAreaSWAP(int dimensioneAreaSWAP) {
        this.dimensioneAreaSWAP = dimensioneAreaSWAP;
    }

    public int getDimensionePagina() {
        return dimensionePagina;
    }

    public void setDimensionePagina(int dimensionePagina) {
        this.dimensionePagina = dimensionePagina;
    }

    public int getDimensioneRAM() {
        return dimensioneRAM;
    }

    public void setDimensioneRAM(int dimensioneRAM) {
        this.dimensioneRAM = dimensioneRAM;
    }

    public int getTempoAccessoDisco() {
        return tempoAccessoDisco;
    }

    public void setTempoAccessoDisco(int tempoAccessoDisco) {
        this.tempoAccessoDisco = tempoAccessoDisco;
    }

    public int getTempoContextSwitch() {
        return tempoContextSwitch;
    }

    public void setTempoContextSwitch(int tempoContextSwitch) {
        this.tempoContextSwitch = tempoContextSwitch;
    }
  
}
