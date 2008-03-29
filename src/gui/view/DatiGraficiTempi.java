package gui.view;

import java.util.LinkedList;
import logic.gestioneMemoria.Azione;
import logic.parametri.ConfigurazioneIniziale;
import logic.simulazione.Player;
import logic.simulazione.Istante;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Compagnin Davide
 */
public class DatiGraficiTempi {

    private ConfigurazioneIniziale conf;

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










