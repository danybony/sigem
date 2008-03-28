package gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import javax.swing.JScrollPane;
import logic.gestioneMemoria.Azione;
import logic.parametri.ConfigurazioneIniziale;
import logic.simulazione.Player;
import logic.simulazione.Istante;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Compagnin Davide
 */
public class ViewGraficoTempi extends JScrollPane {

    private ConfigurazioneIniziale conf;
    
    class StackedXYAreaChart { 
        
        ChartPanel StackedXYAreaChartm(Player player, LinkedList<Boolean> contextSwitchs) { 

            //super("Titolo"); 

            XYSeries series1 = new XYSeries("ContextSwitch", true, false); 
            XYSeries series2 = new XYSeries("Accesso", true, false); 
            XYSeries series3 = new XYSeries("Banda", true, false); 
            XYSeries series4 = new XYSeries("Slice", true, false); 
            int switchs = conf.getTempoContextSwitch();
            int banda = conf.getBandaBusDati();
            int accesso = conf.getTempoAccessoDisco();

            LinkedList<Istante> listaIstanti = player.ultimoIstante();
            Istante istante;
            System.out.println("AAA");
            for(int j = 0; j< listaIstanti.size(); j++){
               System.out.println("BBB");
                istante = listaIstanti.get(j);
                LinkedList<Azione> azioni = istante.getCambiamentiInMemoria();
                 if(azioni!=null){
                     int bandat = 0;
                     int accessot = 0;
                     for(int i = 0; i<azioni.size(); i++){   
                        Azione azioneCorrente = azioni.get(i);
                        switch(azioneCorrente.getAzione()){
                            case 1:
                                // spostamento di un frame in RAM
                                bandat += banda;
                                accessot += accesso;
                                //trasferimenti += accesso + 1000*(conf.getDimensionePagina()/banda);
                                break;
                            case 2:
                                bandat += banda;
                                accessot += accesso;
                                //trasferimenti += accesso + 1000*(conf.getDimensionePagina()/banda);
                                break;
                        }
                     }
                     System.out.println(accessot);
                     System.out.println(bandat);
                     series2.add(j, accessot);
                     if(bandat>0)
                        series3.add(j, 1000*conf.getDimensionePagina()/bandat);
                     else
                         series3.add(j, 0);
                     series4.add(j, 10);
                 }
                 if(contextSwitchs.get(j).booleanValue())
                     series1.add(j, switchs);
            }

            DefaultTableXYDataset dataset = new DefaultTableXYDataset(); 
            dataset.addSeries(series1); 
            dataset.addSeries(series2); 
            dataset.addSeries(series3); 
            dataset.addSeries(series4); 

            JFreeChart chart = createChart(dataset); 

            ChartPanel chartPanel = new ChartPanel(chart); 
            return chartPanel;
            //chartPanel.setPreferredSize(new java.awt.Dimension(600, 300)); 
            //setContentPane(chartPanel); 

        } 


        private JFreeChart createChart(TableXYDataset dataset) { 

            //SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.UK); 
            //StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator( 
            //StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, 
            //sdf, NumberFormat.getInstance()); 
            DateAxis xAxis = new DateAxis("Istanti"); 
            xAxis.setTickMarkPosition(DateTickMarkPosition.START); 
            xAxis.setVerticalTickLabels(true); 
            xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1)); 
            //xAxis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yy")); 
            xAxis.setLowerMargin(0.001); 
            xAxis.setUpperMargin(0.001); 

            NumberAxis yAxis = new NumberAxis("Tempo"); 
            yAxis.setAutoRangeIncludesZero(true); 
            StackedXYAreaRenderer renderer = new StackedXYAreaRenderer( 
            XYAreaRenderer.AREA_AND_SHAPES, null, null ); 
            renderer.setSeriesPaint(0, new Color(255, 255, 0)); 
            renderer.setSeriesPaint(1, new Color(255, 0, 255)); 
            renderer.setSeriesPaint(2, new Color(0, 255, 255)); 
            renderer.setSeriesPaint(3, new Color(0, 255, 0)); 
            renderer.setShapePaint(Color.gray); 
            renderer.setShapeStroke(new BasicStroke(0.5f)); 
            renderer.setShape(new Ellipse2D.Double(-3, -3, 6, 6)); 
            renderer.setOutline(true); 

            XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer); 
            plot.setForegroundAlpha(0.65f); 

            yAxis.configure(); 

            JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, true); 

            return chart; 
    } 

        void disegna(Player player, LinkedList<Boolean> contextSwitchs) { 
            StackedXYAreaChart demo = new StackedXYAreaChart(); 
            StackedXYAreaChartm(player,contextSwitchs); 
            //demo.pack(); 
            //RefineryUtilities.centerFrameOnScreen(demo); 
            //demo.setVisible(true); 
        } 

    } 
    
    
    
    
    public ViewGraficoTempi() {
        super();
    }
    
    public void aggiornaGrafico(Player player, LinkedList<Boolean> contextSwitchs,
            ConfigurazioneIniziale conf){
        
        this.conf=conf;
        StackedXYAreaChart demo = new StackedXYAreaChart(); 
        
        //demo.pack(); 
        //RefineryUtilities.centerFrameOnScreen(demo.StackedXYAreaChartm(player,contextSwitchs)); 
        //demo.setVisible(true);
        this.setViewportView(demo.StackedXYAreaChartm(player,contextSwitchs));
        setVisible(true);
    }
}










