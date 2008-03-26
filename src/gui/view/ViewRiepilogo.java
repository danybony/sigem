/*
 * Azienda: Stylosoft
 * Nome file: ViewFrameMemoria.java
 * Package: gui.dialog
 * Autore: Davide Compagnin
 * Data: 14/03/2008
 * Versione: 1.2
 * Licenza: open-source
 * Registro delle modifiche: 
 *  - v.1.2 (14/03/2008): Bloccata modifica tabelle sistemato aggiornamento
 *  - v.1.1 (14/03/2008): Aggiunta Pannello Tabbed e tabelle
 *  - v.1.0 (14/03/2008): Creazione e impostazione grafica
 */

package gui.view;
import logic.parametri.*;
import logic.parametri.Processo.Accesso;
import logic.gestioneMemoria.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;
import java.util.ArrayList;
import java.util.Vector;
/**
 *
 * @author  Compagnin Davide
 */
public class ViewRiepilogo extends javax.swing.JPanel {
    
    /** Creates new form ViewRiepilogo */
    public ViewRiepilogo() {
        super();
        initComponents();
        azzeraRiepilogo();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        jPanel1.setBackground(new java.awt.Color(219, 231, 233));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setFont(new java.awt.Font("FreeMono", 0, 12));

        jLabel1.setFont(new java.awt.Font("Courier New", 0, 12));

        jLabel2.setFont(new java.awt.Font("Courier New", 0, 12));

        jLabel3.setFont(new java.awt.Font("Courier New", 0, 12));

        jLabel4.setFont(new java.awt.Font("Courier New", 0, 12));

        jLabel5.setFont(new java.awt.Font("Courier New", 0, 12));

        jLabel6.setFont(new java.awt.Font("Courier New", 0, 12));

        jLabel7.setFont(new java.awt.Font("Courier New", 0, 12));

        jLabel8.setFont(new java.awt.Font("Courier New", 0, 12));

        jLabel9.setFont(new java.awt.Font("Courier New", 0, 12));

        jLabel10.setFont(new java.awt.Font("Courier New", 0, 12));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addContainerGap(655, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
    
    
        
   
    
    public void azzeraRiepilogo() {
        jLabel1.setText("Processi: ");
        jLabel2.setText("Dimensione RAM (KB): ");
        jLabel3.setText("Dimensione area Swap (KB): ");
        jLabel4.setText("Tempo Context Switch (ms): ");
        jLabel5.setText("Tempo accesso al disco (ms): ");
        jLabel6.setText("Banda del bus dati: ");
        jLabel7.setText("Tecnica gestione memoria: ");
        jLabel8.setText("Politica schedulazione processi: ");
        jLabel9.setText(""); //Politica rimpiazzo/allocazione
        jLabel10.setText(""); //Dimensioni pagina
    }
    private void setPaginazione( ConfigurazioneIniziale C ) {
        jLabel7.setText("Tecnica gestione memoria: ...........Paginazione");
        String S="Politica rimpiazzo pagine: ..........";
        switch ( C.getPoliticaGestioneMemoria() ) {
            case 1: jLabel9.setText(S+"NRU"); break;
            case 2: jLabel9.setText(S+"FIFO"); break;
            case 3: jLabel9.setText(S+"SC"); break;
            case 4: jLabel9.setText(S+"C"); break;
            case 5: jLabel9.setText(S+"LRU"); break;
            case 6: jLabel9.setText(S+"NFU"); break;
            case 7: jLabel9.setText(S+"A"); break;        
        }
        jLabel10.setText("Dimensione pagina: .................."+C.getDimensionePagina() );
    }
    private void setSegmentazione( ConfigurazioneIniziale C ) {
        jLabel7.setText("Tecnica gestione memoria: ...........Segmentazione");
        String S="Politica allocazione segmenti: ......";
        switch ( C.getPoliticaGestioneMemoria() ) {
            case 1: jLabel9.setText(S+"First-Fit"); break;
            case 2: jLabel9.setText(S+"Next-Fit"); break;
            case 3: jLabel9.setText(S+"Best-Fit"); break;
            case 4: jLabel9.setText(S+"Worst-Fit"); break;
            case 5: jLabel9.setText(S+"Quick-Fit"); break;        
        }
    }
    
    public void aggiorna( ConfigurazioneIniziale C ) {
        // imposta i campi su
        jLabel1.setText("Processi: ..........................."+C.getListaProcessi().size());
        jLabel2.setText("Dimensione RAM (KB): ................"+C.getDimensioneRAM());
        jLabel3.setText("Dimensione area Swap (KB): .........."+C.getDimensioneSwap());
        jLabel4.setText("Tempo Context Switch (ms): .........."+C.getTempoContextSwitch());
        jLabel5.setText("Tempo accesso al disco (ms): ........"+C.getTempoAccessoDisco());
        jLabel6.setText("Banda del bus dati: ................."+C.getBandaBusDati());
        String scheduler="Politica schedulazione processi: ....";
        switch ( C.getPoliticaSchedulazioneProcessi() ) {
            case 1: jLabel8.setText(scheduler+"FCFS"); break;
            case 2: jLabel8.setText(scheduler+"SJF"); break;
            case 3: jLabel8.setText(scheduler+"SRTN"); break;
            case 4: jLabel8.setText(scheduler+"RR"); break;
            case 5: jLabel8.setText(scheduler+"RRP"); break;
            case 6: jLabel8.setText(scheduler+"RRPP"); break;
            case 7: jLabel8.setText(scheduler+"P"); break;
        }
        jLabel9.setText(""); //Politica rimpiazzo/allocazione
        jLabel10.setText(""); //Dimensioni pagina
        
        if ( C.getModalitaGestioneMemoria() == 1 ) // Paginazione
            setPaginazione(C);
        else setSegmentazione(C); // Segmentazione
        
        jTabbedPane1.removeAll();
        
        for(int i=0; i<C.getListaProcessi().size(); i++ ) {
            Processo P=C.getListaProcessi().get(i);
            JScrollPane Pannello = new JScrollPane( creaTabella(P));
            Pannello.enableInputMethods(false);
            String NomeProcesso=P.getNome();
            if (P instanceof ProcessoConPriorita ) NomeProcesso=NomeProcesso+"("+((ProcessoConPriorita)P).getPriorita();            
            jTabbedPane1.addTab(NomeProcesso, Pannello);
        }
   

    }
    
    
    private JTable creaTabella( Processo P ) {
        
        
        int colonne=P.getTempoEsecuzione();
        
        Vector<Vector<String>> Testo=new Vector<Vector<String>>();
        
        for (int i=0; i<colonne; i++)
            Testo.add( new Vector<String>() );
        
        ArrayList<Accesso> Accessi=P.getAccessi();
        
        if (Accessi==null) return new JTable(0,0);
        
        for (int i=0; i<Accessi.size(); i++ ) {
            Accesso A=Accessi.get(i);
            String S=A.getRisorsa().toString();
            if ( A.getRisorsa() instanceof Segmento ) S=S+" ("+((Segmento)A.getRisorsa()).getDimensione()+"KB)";
            if ( A.getModifica() ) S+=" (M)";
            Testo.elementAt( A.getIstanteRichiesta() ).add(S);
        }
        colonne=Testo.size();
        int righe=0;
        for ( int i=0; i<colonne; i++ )
            if ( righe < Testo.elementAt(i).size() ) righe=Testo.elementAt(i).size();
        
            
        String[] NomeColonne=new String[colonne];
        for(int c=0;c<colonne;c++)
            NomeColonne[c]="Istante "+c;
        
        Object[][] dim=new Object[righe][colonne];
        
        JTable TabellaProcesso=new JTable(dim,NomeColonne); // serve x il costruttore
        TabellaProcesso.setEnabled(false);
        
        for ( int c=0; c<colonne; c++ )
            for (int r=0; r<Testo.elementAt(c).size(); r++ ) 
                TabellaProcesso.setValueAt(Testo.elementAt(c).elementAt(r), r, c );
        
        return TabellaProcesso;
    
    }
}
