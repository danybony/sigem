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
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Compagnin Davide
 */
public class DatiGraficiTempi {

    private ConfigurazioneIniziale conf;
    
  /*  class StackedXYAreaChart { 
        
        ChartPanel StackedXYAreaChartm(Player player, LinkedList<Boolean> contextSwitchs) { 

            //super("Titolo"); 

            XYSeries series1 = new XYSeries("ContextSwitch", false, false); 
            XYSeries series2 = new XYSeries("Accesso", false, false); 
            XYSeries series3 = new XYSeries("Banda", false, false); 
            XYSeries series4 = new XYSeries("Slice", false, false); 
            series1.add(0, 0);
            series2.add(0, 0);
            series3.add(0, 0);
            series4.add(0, 0);
            int switchs = conf.getTempoContextSwitch();
            int banda = conf.getBandaBusDati();
            int accesso = conf.getTempoAccessoDisco();
                     int bandat = 0;
                     int accessot = 0;
            LinkedList<Istante> listaIstanti = player.ultimoIstante();
            Istante istante;
            System.out.println("AAA");
            for(int j = 1; j< listaIstanti.size()+1; j++){
               System.out.println("BBB");
                istante = listaIstanti.get(j-1);
                LinkedList<Azione> azioni = istante.getCambiamentiInMemoria();
                 if(azioni!=null){

                     for(int i = 0; i<azioni.size(); i++){   
                        Azione azioneCorrente = azioni.get(i);
                        switch(azioneCorrente.getAzione()){
                            case 1:
                                // spostamento di un frame in RAM
                                bandat += 1000*conf.getDimensionePagina()/banda;
                                accessot += accesso;
                                //trasferimenti += accesso + 1000*(conf.getDimensionePagina()/banda);
                                break;
                            case 2:
                                bandat += 1000*conf.getDimensionePagina()/banda;
                                accessot += accesso;
                                //trasferimenti += accesso + 1000*(conf.getDimensionePagina()/banda);
                                break;
                        }
                     }
                     System.out.println(accessot);
                     System.out.println(bandat);
                     series2.add(j, accessot);
                     series4.add(j, 10);
                     series3.add(j, bandat);
                 }
                 if(contextSwitchs.get(j).booleanValue())
                     series1.add(j, switchs+accessot+bandat);
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
            NumberAxis xAxis = new NumberAxis("Istanti"); 
            //xAxis.setTickMarkPosition(DateTickMarkPosition.START); 
            xAxis.setVerticalTickLabels(true); 
            //xAxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1)); 
            //xAxis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yy")); 
            xAxis.setLowerMargin(1.0); 
            xAxis.setUpperMargin(0.001); 
            xAxis.setAutoRangeIncludesZero(true); 

            NumberAxis yAxis = new NumberAxis("Tempo"); 
            yAxis.setAutoRangeIncludesZero(true); 
            yAxis.setLowerMargin(0.001); 
            yAxis.setUpperMargin(0.001); 
            StackedXYAreaRenderer renderer = new StackedXYAreaRenderer( 
            XYAreaRenderer.AREA, null, null ); 
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
    
    */
    
    public XYSeries[] ClacolaDatiGrafici(Player player, LinkedList<Boolean> contextSwitchs,
            ConfigurazioneIniziale conf){
        
        this.conf=conf;
        
            XYSeries series1 = new XYSeries("ContextSwitch", false, false); 
            XYSeries series2 = new XYSeries("Accesso", false, false); 
            XYSeries series3 = new XYSeries("Banda", false, false); 
            XYSeries series4 = new XYSeries("Slice", false, false); 
            XYSeries series5 = new XYSeries("Totale", false, false); 
            series1.add(0, 0);
            series2.add(0, 0);
            series3.add(0, 0);
            series4.add(0, 0);
            series5.add(0, 0);
            int switchs = conf.getTempoContextSwitch();
            int banda = conf.getBandaBusDati();
            int accesso = conf.getTempoAccessoDisco();
            int bandat = 0;
            int accessot = 0;
            int totale = 0;
            int switchTot = 0;
            
            LinkedList<Istante> listaIstanti = player.ultimoIstante();
            Istante istante;
            for(int j = 1; j< listaIstanti.size()+1; j++){
                istante = listaIstanti.get(j-1);
                LinkedList<Azione> azioni = istante.getCambiamentiInMemoria();
                 if(azioni!=null){

                     for(int i = 0; i<azioni.size(); i++){   
                        Azione azioneCorrente = azioni.get(i);
                        switch(azioneCorrente.getAzione()){
                            case 1:
                                // spostamento di un frame in RAM
                                if(conf.getModalitaGestioneMemoria()==1){
                                    bandat += 1000*conf.getDimensionePagina()/banda;
                                }
                                else if(conf.getModalitaGestioneMemoria()==2){
                                    bandat += 1000*azioneCorrente.getFrame().getDimensione()/banda;
                                }
                                
                                accessot += accesso;
                                //trasferimenti += accesso + 1000*(conf.getDimensionePagina()/banda);
                                break;
                            case 2:
                                if(conf.getModalitaGestioneMemoria()==1){
                                    bandat += 1000*conf.getDimensionePagina()/banda;
                                }
                                else if(conf.getModalitaGestioneMemoria()==2){
                                    bandat += 1000*azioneCorrente.getFrame().getDimensione()/banda;
                                }
                                accessot += accesso;
                                //trasferimenti += accesso + 1000*(conf.getDimensionePagina()/banda);
                                break;
                        }
                     }
                     series2.add(j, accessot);
                     series4.add(j, 10*j);
                     series3.add(j, bandat);
                 }
                 if(contextSwitchs.get(j).booleanValue()){
                     switchTot += switchs;
                 }
                series1.add(j, switchTot);
                series5.add(j, (switchTot+accessot+bandat+(10*j))/1000);
            }
        
 
            XYSeries[] ritorna = new XYSeries[5];
            
            ritorna[0] = series1;
            ritorna[1] = series2;
            ritorna[2] = series3;
            ritorna[3] = series4;
            ritorna[4] = series5;
            
            return ritorna;
    }
}










