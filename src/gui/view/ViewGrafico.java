/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.view;

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

    public ViewGrafico(Player player) {
        super();
    }
    
    public void aggiornaGrafico(Player player){
        
        XYSeries series = new XYSeries("Fault");
        Iterator<Istante> I=player.ultimoIstante().iterator();
        int i=0,somma_fault=0;
        while(I.hasNext()){
            series.add(i, somma_fault);
            somma_fault+=I.next().getFault();
            i++;
        }
        XYDataset xyDataset = new XYSeriesCollection(series);
        
        JFreeChart chart = ChartFactory.createXYAreaChart("Fault in RAM","Istanti","Fault",xyDataset,PlotOrientation.VERTICAL,false,false,false);
        this.setViewportView(new ChartPanel(chart));
        setVisible(true);
    }
}
