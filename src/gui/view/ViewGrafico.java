/*
 * Azienda: Stylosoft
 * Nome file: ViewFrameMemoria.java
 * Package: gui.dialog
 * Autore: Davide Compagnin
 * Data: 14/03/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: 
 *  
 *  - v.1.0 (14/03/2008): Creazione e impostazione grafico
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
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Compagnin Davide
 */
public class ViewGrafico extends JScrollPane {

    public ViewGrafico() {
        super();
    }
    
    public void aggiornaGrafico(Player player){
        
        XYSeries series = new XYSeries("Somma Fault",false,false);
        XYSeries series2 = new XYSeries("Fault",false,false);
        Iterator<Istante> I=player.ultimoIstante().iterator();
        int i=0,somma_fault=0;
        series.add(i, somma_fault);
        series2.add(i, 0);
        while(I.hasNext()){
            int f=I.next().getFault();
            i++; somma_fault+=f; 
            series.add(i, somma_fault);
            series2.add(i, f);
        }
        
        DefaultTableXYDataset TabellaDati=new DefaultTableXYDataset();
        TabellaDati.addSeries(series);
        TabellaDati.addSeries(series2);
        
        //XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createStackedXYAreaChart("Fault in RAM", "Istanti", "Fault", TabellaDati, PlotOrientation.VERTICAL, false,false,false);
        //JFreeChart chart = ChartFactory.createXYAreaChart("Fault in RAM","Istanti","Fault",xyDataset,PlotOrientation.VERTICAL,false,false,false);
        //JScrollPane P=new JScrollPane();
        //P.add();
        //P.add
        
        this.setViewportView(new ChartPanel(chart));
        setVisible(true);
    }
}
