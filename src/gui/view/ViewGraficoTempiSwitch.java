/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import logic.gestioneMemoria.Azione;
import logic.parametri.ConfigurazioneIniziale;
import logic.simulazione.Player;
import logic.simulazione.Istante;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author luca
 */
public class ViewGraficoTempiSwitch  extends JScrollPane {
    public void aggiorna(XYSeries serie){
        DefaultTableXYDataset TabellaDati=new DefaultTableXYDataset();
        TabellaDati.addSeries(serie);

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
        renderer.setSeriesPaint(0, new Color(255, 0, 0)); 
        renderer.setShapePaint(Color.gray); 
        renderer.setShapeStroke(new BasicStroke(0.5f)); 
        renderer.setShape(new Ellipse2D.Double(-3, -3, 6, 6)); 
        renderer.setOutline(true); 

        XYPlot plot = new XYPlot(TabellaDati, xAxis, yAxis, renderer); 
        plot.setForegroundAlpha(0.65f); 

        yAxis.configure(); 

        JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, false); 
        
        //JFreeChart chart = ChartFactory.createXYAreaChart("Tempi context-switch", "Istanti", "millisecondi", TabellaDati, PlotOrientation.VERTICAL, false,false,false);
        ChartPanel p = new ChartPanel(chart);
        this.setViewportView(p);
        setVisible(true);
    }
}
