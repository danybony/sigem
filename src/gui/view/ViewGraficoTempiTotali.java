/*
 * Azienda: Stylosoft
 * Nome file: ViewGraficoTempiTotali
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
 * Classe per la visualizzazione del grafico che riepiloga la stima del  tempo
 * reale necessario per eseguire la simulazione.
 */
public class ViewGraficoTempiTotali extends JScrollPane {
    
    /**
     * Crea e aggiorna il grafico.
     * 
     * @param serie
     *      la serie di coordinate X e Y per disegnare il primo grafico
     */
    public void aggiorna(XYSeries serie){
        DefaultTableXYDataset TabellaDati=new DefaultTableXYDataset();
        TabellaDati.addSeries(serie);
        
        NumberAxis xAxis = new NumberAxis("Istanti"); 
        xAxis.setLowerMargin(1); 
        xAxis.setUpperMargin(0.001); 
        xAxis.setAutoRangeIncludesZero(true); 

        NumberAxis yAxis = new NumberAxis("secondi"); 
        yAxis.setAutoRangeIncludesZero(true); 
        yAxis.setLowerMargin(0.001); 
        yAxis.setUpperMargin(0.001); 
        StackedXYAreaRenderer renderer = new StackedXYAreaRenderer( 
        XYAreaRenderer.AREA, null, null ); 
        renderer.setSeriesPaint(0, new Color(255, 255, 0)); 
        renderer.setShapePaint(Color.gray); 
        renderer.setShapeStroke(new BasicStroke(0.5f)); 
        renderer.setOutline(true); 

        XYPlot plot = new XYPlot(TabellaDati, xAxis, yAxis, renderer); 
        plot.setForegroundAlpha(0.65f); 

        yAxis.configure(); 

        JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, false); 
        
        ChartPanel p = new ChartPanel(chart);
        this.setViewportView(p);
        setVisible(true);
    }
}



