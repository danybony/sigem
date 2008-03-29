/*
 * Azienda: Stylosoft
 * Nome file: ViewGraficoTempiBandaAccesso
 * Package: gui.view
 * Autore: Rubin Luca
 * Data: 21/03/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche:
 * - v 1.1 (02/03/2008): Verificata
 * - v 1.0 (01/03/2008): Creata classe
 * */

package gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author luca
 */
public class ViewGraficoTempiBandaAccesso  extends JScrollPane {

    public void aggiorna(XYSeries serie1, XYSeries serie2){
        DefaultTableXYDataset TabellaDati1=new DefaultTableXYDataset();
        TabellaDati1.addSeries(serie1);
        
        NumberAxis xAxis = new NumberAxis("Istanti"); 
        xAxis.setLowerMargin(1); 
        xAxis.setUpperMargin(0.001); 
        xAxis.setAutoRangeIncludesZero(true); 

        NumberAxis yAxis = new NumberAxis("millisecondi"); 
        yAxis.setAutoRangeIncludesZero(true); 
        yAxis.setLowerMargin(0.001); 
        yAxis.setUpperMargin(0.001); 
        StackedXYAreaRenderer renderer = new StackedXYAreaRenderer( 
        XYAreaRenderer.AREA, null, null ); 
        renderer.setSeriesPaint(0, new Color(255, 0, 255)); 
        renderer.setShapePaint(Color.gray); 
        renderer.setShapeStroke(new BasicStroke(0.5f)); 
        renderer.setShape(new Ellipse2D.Double(-3, -3, 6, 6)); 
        renderer.setOutline(true); 

        XYPlot plot = new XYPlot(TabellaDati1, xAxis, yAxis, renderer); 
        plot.setForegroundAlpha(0.65f); 

        yAxis.configure(); 

        JFreeChart chart = new JFreeChart("Tempi del bus dati", JFreeChart.DEFAULT_TITLE_FONT, plot, false); 
        
        //JFreeChart chart = ChartFactory.createStackedXYAreaChart("Tempi context-switch", "Istanti", "millisecondi", TabellaDati, PlotOrientation.VERTICAL, false,false,false);
        
        DefaultTableXYDataset TabellaDati2=new DefaultTableXYDataset();
        TabellaDati2.addSeries(serie2);
        
        NumberAxis xAxis2 = new NumberAxis("Istanti"); 
        xAxis2.setLowerMargin(1); 
        xAxis2.setUpperMargin(0.001); 
        xAxis2.setAutoRangeIncludesZero(true); 

        NumberAxis yAxis2 = new NumberAxis("millisecondi"); 
        yAxis2.setAutoRangeIncludesZero(true); 
        yAxis2.setLowerMargin(0.001); 
        yAxis2.setUpperMargin(0.001); 
        StackedXYAreaRenderer renderer2 = new StackedXYAreaRenderer( 
        XYAreaRenderer.AREA, null, null ); 
        renderer2.setSeriesPaint(0, new Color(0, 255, 255)); 
        renderer2.setShapePaint(Color.gray); 
        renderer2.setShapeStroke(new BasicStroke(0.5f)); 
        renderer2.setShape(new Ellipse2D.Double(-3, -3, 6, 6)); 
        renderer2.setOutline(true); 

        XYPlot plot2 = new XYPlot(TabellaDati2, xAxis2, yAxis2, renderer2); 
        plot2.setForegroundAlpha(0.65f); 

        yAxis2.configure(); 

        JFreeChart chart2 = new JFreeChart("Tempi di accesso al disco", JFreeChart.DEFAULT_TITLE_FONT, plot2, false); 
        
        //JFreeChart chart = ChartFactory.createStackedXYAreaChart("Tempi context-switch", "Istanti", "millisecondi", TabellaDati, PlotOrientation.VERTICAL, false,false,false);
        ChartPanel p = new ChartPanel(chart);
        ChartPanel p2 = new ChartPanel(chart2);
        p.setPreferredSize(new java.awt.Dimension(450, 300));
        p2.setPreferredSize(new java.awt.Dimension(450, 300));
        
        JPanel jp = new JPanel();
        jp.add(p);
        jp.add(p2);
        
        
        this.setViewportView(jp);
        setVisible(true);
    }
}
