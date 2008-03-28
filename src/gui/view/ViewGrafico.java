/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.view;

import javax.swing.JScrollPane;
import logic.simulazione.Player;
import logic.simulazione.Istante;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author compa
 */
public class ViewGrafico extends JScrollPane {
    private int istante_max=0;
    private int istante_corrente=0;
    private int[] Fault=null;
    private int[] SumFault=null;
    
    public ViewGrafico(Player player) {
        super();
    }
    
    public void azzeraGrafico(Player player) {
        istante_max=0;
        istante_corrente=0;
        
        Fault=new int[player.numeroIstanti()];
        SumFault=new int[player.numeroIstanti()];
        
            Fault[0]=0;
            SumFault[0]=0;
    }
    
    public void aggiornaGrafico(Player player,Istante istante){
        
        istante_corrente=player.getIndiceIstanteCorrente();
        if (istante_corrente>istante_max) {
            Fault[istante_corrente]=istante.getFault();
            SumFault[istante_corrente]=SumFault[istante_corrente-1]+Fault[istante_corrente];
            istante_max=istante_corrente;
        }
        
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        for (int i=0; i<istante_corrente; i++) {
            dataset.addValue(SumFault[i],"Istante"+i, "" );
        }
        JFreeChart jfc = ChartFactory.createAreaChart("Fault in RAM","Fault","",dataset,PlotOrientation.HORIZONTAL, false,false,true);
        
        this.setViewportView(new ChartPanel(jfc));
        setVisible(true);
    }
}
