/*
 * ViewStatistiche2.java
 *
 * Created on 21 marzo 2008, 10.26
 */

package gui.view;

import gui.SiGeMv2View;
import java.util.NoSuchElementException;
import java.util.Vector;
import logic.gestioneMemoria.FrameMemoria;
import logic.parametri.ConfigurazioneIniziale;
import logic.simulazione.Istante;
import logic.simulazione.Player;

/**
 *
 * @author  luca
 */
public class ViewStatistiche extends javax.swing.JPanel {
    
    private int numeroFault;
    
    /** Creates new form ViewStatistiche2 */
    public ViewStatistiche(SiGeMv2View v) {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jProgressBar2 = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        jProgressBar3 = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(219, 231, 233));
        jPanel1.setPreferredSize(new java.awt.Dimension(605, 120));

        jLabel1.setText("Utilizzo RAM (KB):");

        jLabel2.setText("Utilizzo Swap (KB):");

        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        jProgressBar2.setString("");
        jProgressBar2.setStringPainted(true);

        jLabel3.setText("Simulazione: ");

        jProgressBar3.setString("");
        jProgressBar3.setStringPainted(true);

        jLabel4.setText("Numero Falult in RAM:");

        jLabel5.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jProgressBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                            .addComponent(jProgressBar2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    public void azzeraStatistiche(){
        this.numeroFault = 0;
        System.out.println("C");
        this.jProgressBar1.setValue(0);
        this.jProgressBar1.setString("0%");
        this.jProgressBar2.setValue(0);
        this.jProgressBar2.setString("0%");
        this.jProgressBar3.setValue(0);
        this.jProgressBar3.setString("");
        
    }
    
    public void generaStatistiche(Player player,
                                  Istante istante, ConfigurazioneIniziale conf,
                                  Vector<Vector<Integer>> processiUltimati,
                                  boolean avanti) {
    
        if(player.getIndiceIstanteCorrente()==0){
            this.numeroFault=0;
            jLabel5.setText("0");
            System.out.println("A");
            this.jProgressBar1.setValue(0);
            this.jProgressBar1.setString("0%  (0 di " + conf.getDimensioneRAM() + " KB)");
            this.jProgressBar2.setValue(0);
            this.jProgressBar2.setString("0%  (0 di " + conf.getDimensioneSwap() + " KB)");
        }
        else{
            aggiornaUtilizzoRAM(player,istante,conf,processiUltimati);
            aggiornaUtilizzoSwap(player,istante,conf,processiUltimati);
            aggiornaFault(istante, avanti, player);
        }
        aggiornaNumeroIstanti(player);
    }
    
    void aggiornaNumeroIstanti(Player player){
        int tot = player.numeroIstanti();
        int curr = player.getIndiceIstanteCorrente();
        this.jProgressBar3.setValue(curr*100/tot);
        this.jProgressBar3.setString("Istante " + curr + " di " + tot);
    }
    
    void aggiornaFault(Istante istante, boolean avanti, Player player){
        if(avanti){
            this.numeroFault += istante.getFault();
        }
        else{
            istante = player.istanteSuccessivo();
            this.numeroFault -= istante.getFault();
            player.istantePrecedente();
        }
        jLabel5.setText(new Integer(this.numeroFault).toString());
        System.out.println("B"+ this.numeroFault);
    }
    
    void aggiornaUtilizzoRAM(Player player,
                             Istante istante,
                             ConfigurazioneIniziale conf,
                             Vector<Vector<Integer>> processiUltimati){
        
        int tot = conf.getDimensioneRAM();
        int ultimo=0;
        
        // screen-shot memoria
        try{
            Vector<FrameMemoria> shot = istante.getStatoRAM();
            // controllo per ogni slot della RAM se il frame corrisponde ad
            // un processo terminato
            for(int j=0; j < shot.size(); j++){

                // il processo a cui appartiene il frame in memoria
                int proc = shot.get(j).getIdProcesso();
                //prendo i processi terminati nell'istante corrente
                Vector<Integer> procCorrenti = processiUltimati.get(player.getIndiceIstanteCorrente());

                boolean trovato = false;

                // cerco se proc e' uno dei processi terminati
                for(int i = 0; i < procCorrenti.size(); i++){
                    if(proc==procCorrenti.get(i).intValue())
                        trovato=true;
                }

                // se proc non e' terminato, aggiungo o tolgo mem
                if(!trovato){
                    if(conf.getModalitaGestioneMemoria()==1){
                        ultimo += conf.getDimensionePagina();
                    }
                    else if(conf.getModalitaGestioneMemoria()==2){
                        if(shot.get(j).getIdProcesso()!=-1){
                            ultimo += shot.get(j).getDimensione();
                            
                        }
                    }
                }
            }

            this.jProgressBar1.setValue(ultimo*100/tot);
            this.jProgressBar1.setString(ultimo*100/tot + "%  (" + ultimo + " di " + tot + " KB)");
        }
        catch(NullPointerException e){}
        catch(NoSuchElementException e){}
    }
    
     void aggiornaUtilizzoSwap(Player player,
                             Istante istante,
                             ConfigurazioneIniziale conf,
                             Vector<Vector<Integer>> processiUltimati){
     
        int tot = conf.getDimensioneSwap();
        int ultimo=0;
        
        // screen-shot memoria
        try{
            Vector<FrameMemoria> shot = istante.getStatoSwap();
            // controllo per ogni slot della RAM se il frame corrisponde ad
            // un processo terminato
            for(int j=0; j < shot.size(); j++){

                // il processo a cui appartiene il frame in memoria
                int proc = shot.get(j).getIdProcesso();
                //prendo i processi terminati nell'istante corrente
                Vector<Integer> procCorrenti = processiUltimati.get(player.getIndiceIstanteCorrente());

                boolean trovato = false;

                // cerco se proc e' uno dei processi terminati
                for(int i = 0; i < procCorrenti.size(); i++){
                    if(proc==procCorrenti.get(i).intValue())
                        trovato=true;
                }

                // se proc non e' terminato, aggiungo o tolgo mem
                if(!trovato){
                    if(conf.getModalitaGestioneMemoria()==1){
                        ultimo += conf.getDimensionePagina();
                    }
                    else if(conf.getModalitaGestioneMemoria()==2){
                        if(shot.get(j).getIdProcesso()!=-1){
                            ultimo += shot.get(j).getDimensione();
                            
                        }
                    }
                }
            }

            this.jProgressBar1.setValue(ultimo*100/tot);
            this.jProgressBar1.setString(ultimo*100/tot + "%  (" + ultimo + " di " + tot + " KB)");
        }
        catch(NullPointerException e){}
        catch(NoSuchElementException e){}
     }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JProgressBar jProgressBar3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
