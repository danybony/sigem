/*
 * Azienda: Stylosoft
 * Nome file: ViewFrameMemoria.java
 * Package: gui.dialog
 * Autore: Alberto Zatton
 * Data: 02/03/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche: 
 *  - v.1.3 (19/03/2008): Rivisto ampiamente il codice. Ora la classe estende un
 *                        JScrollPane per attivare lo scroll, completamente
 *                        cambiate le modalità di disegno della memoria, nuova
 *                        classe interna di tipo JPanel su cui viene disegnata
 *                        la memoria.
 *  - v.1.2 (14/03/2008): Completato il metodo principale aggiungi() e corretti
 *                        i restanti
 *  - v.1.1 (03/03/2008): Inserita visualizzazione pagine all'inizializzazione
 *  - v.1.0 (02/03/2008): Creazione JDialog e impostazione grafica
 */
package gui.view;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import gui.utility.SquareDraw;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JScrollPane;
import logic.gestioneMemoria.Azione;
import logic.gestioneMemoria.FrameMemoria;

/**
 * Classe che è incaricata di visualizzare sulla GUI principale l'evolvere della
 * memoria, sia essa paginata o segmentata. La classe eredita un JScrollPane
 * per attivare gli scroll in caso di bisogno, inoltre al suo interno ha una
 * classe privata di tipo JPanel in cui viene effettivamente effettuato il diesegno
 * 
 * @author Alberto Zatton
 */
public class ViewFrameMemoria extends JScrollPane {
    /** Necessario per il Serializable. */
    private static final long serialVersionUID = -4441774947632050544L;
    
    /**
     * Boolean che indica se il grafico deve visualizzare:<br>
     * FALSE: Pagine<br>
     * TRUE: Segmenti<br>
     * Di default visualizza pagine.
     */
    private boolean pag_seg=false;
    
    /**
     * Dimensione totale della memoria in byte
     */
    private int dimMemoria=0;
    
    /**
     * Numero di processi: utile per scegliere il colore per la pagina/segmento
     */
    private int numProcessi=0;
    
    /**
     * Int che setta la grandezza dei quadrati rappresentanti le pagine
     */
    private static final int LATO=48;
    
    /**
     * Int che imposta l'altezza totale della RAM in caso di segmentazione
     */
    private static final int ALTEZZA=550;
        
    /**
     * Vector che contiene le pagine/segmenti da visualizzare
     */
    private Vector<SquareDraw> pagineSquare = new Vector<SquareDraw>();
    
    /**
     * Vector che mantiene una lista di prcessi che hanno finito di eseguire
     * una per ogni istante. Utile nel caso la simulazione "torni indietro"
     */
    Vector<Vector<Integer>> processiUltimati=null;
    
    /**
     * Riferimento alla classe interna incaricata di disegnare le pagine.
     * Il JScrollPane si occuperà dello scrolling di questo oggetto, che non
     * è altro che un JPanel.
     */
    PannelloFrame pannello=null;
    
    
    /**
     * Costruttore incaricato solo di inizializzare il grafico. Per poter utilizzare
     * le sue funzioni, bisogna prima utilizzare il metodo configure()
     */
    public ViewFrameMemoria() {
        super();
        pannello=new PannelloFrame();
        setViewportView(pannello);
    }
    
    /**
     * Classe che eredita da JPanel, incaricata di disegnare al suo interno le
     * pagine/segmenti. E' l'oggetto su cui il JScrollPane fa lo scrolling
     */
    private class PannelloFrame extends JPanel{
        
    /**
    * Overriding del metodo paintComponent: viene usato per disegnare le
    * pagine/segmenti nel JPanel attraverso il metodo disegnaPagine()
    */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        /*
	 * Necessito delle funzionalita` di Graphics2D, cast sempre sicuro
	 * poiche` paint invoca sempre paintComponent con un oggetto Graphics2D
	 */
	Graphics2D g2 = (Graphics2D) g;

	// Attivo l'antialiasing per migliorare la qualita` del rendering
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setRenderingHint(RenderingHints.KEY_RENDERING,
	                RenderingHints.VALUE_RENDER_QUALITY);
        
        setBackground(Color.white);
        
        disegnaPagine(g);
    }
    
    
    
    /**
     * Metodo incaricato di disegnare le pagine/segmenti, ognuna con il colore
     * del processo relativo e con il proprio indirizzo logico. Nel caso delle
     * pagine, se queste superano l'area visibile viene aumentata l'altezza
     * del JPanel e comunicata al JScrollPane attraverso il metodo revalidate()
     * per gli opportuni aggiustamenti grafici
     * 
     * @param g la componente grafica su cui disegnare i frame.
     */
    private void disegnaPagine(Graphics g) {
        
        Graphics2D ga = (Graphics2D)g;
        for (int i=0; i<pagineSquare.size(); i++) {
            
            SquareDraw pag = pagineSquare.get(i);
            //Disegno il quadrato (solo il contorno) con il colore nero
            ga.setColor(Color.BLACK);
            ga.draw(pag.getSquare());
            //Imposto il colore del frame a seconda dal processo di appartenenza,
            //dopodichè riempio il quadrato vuoto con quel colore
            ga.setPaint(pag.getColor());
            ga.fill(pag.getSquare());
            //Scrivo l'indirizzo del frame col colore nero e carattere Arial
            //grandezza 20 grassetto, centrando la scritta
            ga.setColor(Color.BLACK);
            ga.setFont(new Font("Arial", 1, 20));
            ga.drawString(pag.getText(),pag.getxCoordText(),pag.getyCoordText());
            
        }
        //Se il disegno dei frame ha superato l'altezza del JPanel, ridimensiono
        //lo stesso e notifico il cambiamento al JScrollPane
        if (!pag_seg)setPreferredSize(new Dimension(320, (pagineSquare.size()/6)*55));
        else setPreferredSize(new Dimension(320, pagineSquare.get(pagineSquare.size()-1).getyCoord()+pagineSquare.get(pagineSquare.size()-1).getAltezza()+5));
        revalidate();
    }
    }
    
    /**
     * Funzione che aggiorna il grafico della RAM con i cambiamenti avvenuti nel nuovo
     * istante.
     * 
     * @param cambiamentiInMemoria
     *      LinkedList di Azioni che rappresentano tutto ciï¿½ che ï¿½ stato modificato
     *      in RAM
     * 
     * @param istante
     *      intero che identifica l'istante di cui si sta disegnando i frame della
     *      memoria. Utile nel caso in cui l'utente decida di tornare indietro nella
     *      simulazione
     * 
     * @throws Exception
     *      Lancia un'eccezione nel caso in cui la memoria non sia ancora stata configurata
     */
    public void aggiorna(LinkedList<Azione> cambiamentiInMemoria, int istante) throws Exception{
        //Se l'ambiente non è ancora stato configurato, lancio un'eccezione
        if(dimMemoria==0) throw new Exception();
        
        
        
        //Aggiorno, se necessario, lo storico dei processi ultimati
        if(istante>=processiUltimati.size()){
            processiUltimati.add(new Vector(processiUltimati.get(istante-1)));
            if(cambiamentiInMemoria==null) return;
            int processoUltimato=-2;
            for(int i=0;i<cambiamentiInMemoria.size();i++){
                if (cambiamentiInMemoria.get(i).getAzione()==6){
                    processoUltimato=cambiamentiInMemoria.get(i).getPosizione();
                }
            }
            if(processoUltimato!=-2){
                processiUltimati.get(istante).add(new Integer(processoUltimato));
            }
        }
        
        int coordX=10;
        int larghezza=LATO*6;
        int coordY=10;
        int altezza=0;
        Color color;
        String text;
        FrameMemoria frame;
        try{
            Azione azione=cambiamentiInMemoria.getLast();
        
        Vector<FrameMemoria> memoria=azione.getMemoriaRAM();
        pagineSquare.clear();
        for(int i=0; i<memoria.size(); i++){
            frame=memoria.get(i);
            if (pag_seg==false){
                coordY=((i/6)*LATO+5*(i/6))+10;
                coordX=(i%6)*LATO+5*(i%6);
                boolean trovato=false;
                for(int j=0;j<processiUltimati.get(istante).size()&&!trovato;j++){
                    if(processiUltimati.get(istante).get(j).equals(new Integer(frame.getIdProcesso()))){
                        trovato=true;
                    }
                }
                if(trovato){
                        color=Color.LIGHT_GRAY;
                        text=" ";
                }
                else{
                        color=ViewUtility.colorFactory(numProcessi, frame.getIdProcesso());
                        text=frame.getIndirizzo();
                }
                pagineSquare.add(new SquareDraw(coordX,
                                                  coordY,
                                                  LATO,
                                                  LATO,
                                                  color,
                                                  text)
                                );
            
            }
            else{
                altezza=(ALTEZZA*frame.getDimensione())/dimMemoria;
                boolean trovato=false;
                for(int j=0;j<processiUltimati.get(istante).size()&&!trovato;j++){
                    if(processiUltimati.get(istante).get(j).equals(new Integer(frame.getIdProcesso()))){
                        trovato=true;
                    }
                }
                if(trovato || frame.getIdProcesso()==-1){
                    color=Color.LIGHT_GRAY;
                    text=" ";
                }
                else{
                    color=ViewUtility.colorFactory(numProcessi, frame.getIdProcesso());
                    text=frame.getIndirizzo();
                }
                pagineSquare.add(new SquareDraw(coordX,
                                                  coordY,
                                                  larghezza,
                                                  altezza,
                                                  color,
                                                  text)
            
                                                  );
                coordY+=altezza+2;
            }
        }
        
        pannello.repaint();
        }
        catch(Exception e){}
    }
    

    
    /**
     * Metodo che inizializza il grafico. E' necessario passare per questo metodo
     * prima di usare effettivamente la classe, cioè chiamare il metodo aggiorna()<br>
     * 
     * @param sceltaGestioneMemoria
     *      FALSE se si vogliono visualizzare pagine, TRUE per i segmenti
     * @param dimMemoria
     *      Dimensione totale della memoria
     * @param numProcessi
     *      Numero di processi della simulazione
     */
    public void configura(boolean sceltaGestioneMemoria, int dimMemoria, int numProcessi, int numPagine){
        pagineSquare.clear();
        pag_seg=sceltaGestioneMemoria;
        this.dimMemoria=dimMemoria;
        this.numProcessi=numProcessi;
        processiUltimati=new Vector<Vector<Integer>>();
        processiUltimati.add(0, new Vector<Integer>());
        if(sceltaGestioneMemoria==true){
            pagineSquare.add(
                             0,
                             new SquareDraw(10,
                                            10, 
                                            LATO*6,
                                            ALTEZZA,
                                            Color.LIGHT_GRAY,
                                            " ")
                                       );
        }
        else{
            int coordY=0;
            int coordX=0;
            for (int i=0; i<numPagine; i++){
                coordY=((i/6)*LATO+5*(i/6))+10;
                coordX=(i%6)*LATO+5*(i%6);
                
                pagineSquare.add(new SquareDraw(coordX,
                                                  coordY,
                                                  LATO,
                                                  LATO,
                                                  Color.LIGHT_GRAY,
                                                  " ")
                                );
            }
        }
        pannello.repaint();
        
    }

}
