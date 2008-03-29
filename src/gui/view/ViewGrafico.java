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


import java.awt.Panel;
import java.util.Iterator;
import javax.swing.JPanel;
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
        
        XYSeries series1 = new XYSeries("SommaFault",true,false);
        XYSeries series2 = new XYSeries("SingoliFault",true,false);
        Iterator<Istante> I=player.ultimoIstante().iterator();
        int i=0,somma_fault=0;
        series1.add(i, somma_fault);
        series2.add(i, 0);
        while(I.hasNext()){
            int f=I.next().getFault();
            i++; 
            somma_fault+=f;
            series1.add(i, somma_fault);
            series2.add(i, f);
            
            
        }
        
        //DefaultTableXYDataset TabellaDati=new DefaultTableXYDataset();
        //TabellaDati.addSeries(series2);
        //TabellaDati.addSeries(series1);
        
        
        XYDataset xyDataset1 = new XYSeriesCollection(series1);
        XYDataset xyDataset2 = new XYSeriesCollection(series2);
        //JFreeChart chart0 = ChartFactory.createStackedXYAreaChart("Fault in RAM", "Istanti", "Fault", TabellaDati, PlotOrientation.VERTICAL, false,false,false);
        JFreeChart chart1 = ChartFactory.createXYAreaChart("Andamento Fault Totali","Istanti","Fault",xyDataset1,PlotOrientation.VERTICAL,false,false,false);
        JFreeChart chart2 = ChartFactory.createXYAreaChart("Andamento Fault per Istante","Istanti","Fault",xyDataset2,PlotOrientation.VERTICAL,false,false,false);
        chart1.setTextAntiAlias(true);
        chart2.setTextAntiAlias(true);
        
        JPanel P=new JPanel();
        ChartPanel CP1=new ChartPanel(chart1);
        CP1.setPreferredSize(new java.awt.Dimension(450, 300));
        ChartPanel CP2=new ChartPanel(chart2);
        CP2.setPreferredSize(new java.awt.Dimension(450, 300));

        P.add(CP1);
        P.add(CP2);
        //P.add(new ChartPanel(chart0));
        //P.setVisible(true);
        this.setViewportView(P);
        setVisible(true);
    }
}
