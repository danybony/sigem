/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.view;

import java.awt.Panel;
import java.util.Iterator;
import javax.swing.JScrollPane;
import logic.simulazione.Player;
import logic.simulazione.Istante;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author compa
 */
public class ViewGrafico extends JScrollPane {

    private int[] Fault=null;
    private int[] SumFault=null;
    public ViewGrafico(Player player) {
        super();
    }
    
    public void azzeraGrafico(Player player) {

        Fault=new int[player.numeroIstanti()+1];
        SumFault=new int[player.numeroIstanti()+1];
        Fault[0]=0;
        SumFault[0]=0;
        this.setViewportView(new Panel());
    }
    
    public void aggiornaGrafico(Player player){
        
        Iterator<Istante> I=player.ultimoIstante().iterator();
        int i=1;
        while(I.hasNext()){
            Fault[i]=I.next().getFault();
            SumFault[i]=SumFault[i-1]+Fault[i];
            i++;
        }
        
        XYSeries series = new XYSeries("Fault");
        
        for (int c=0; c<i; c++) {
            series.add(c, SumFault[c]);
        }
        XYDataset xyDataset = new XYSeriesCollection(series);
        
        JFreeChart chart = ChartFactory.createXYAreaChart("Fault in RAM","Istanti","Fault",xyDataset,PlotOrientation.VERTICAL,false,false,false);
        this.setViewportView(new ChartPanel(chart));
        setVisible(true);
    }
}
