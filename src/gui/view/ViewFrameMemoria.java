/*
 * Azienda: Stylosoft
 * Nome file: ViewFrameMemoria.java
 * Package: gui.dialog
 * Autore: Alberto Zatton
 * Data: 02/03/2008
 * Versione: 1.4
 * Licenza: open-source
 * Registro delle modifiche: 
 *  - v.1.4 (22/03/2008): Adattata la funzione aggiungi(): ora ai suoi parametri
 *                        viene passata direttamente la situazione della memoria.
 *  - v.1.3 (19/03/2008): Rivisto ampiamente il codice. Ora la classe estende un
 *                        JScrollPane per attivare lo scroll, completamente
 *                        cambiate le modalita' di disegno della memoria, nuova
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
import java.util.Vector;
import javax.swing.JScrollPane;
import logic.gestioneMemoria.FrameMemoria;

/**
 * Classe che e' incaricata di visualizzare sulla GUI principale l'evolvere della
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
     * 1: Pagine<br>
     * 2: Segmenti<br>
     * Di default visualizza pagine.
     */
    private int pag_seg=1;
    
    /**
     * Dimensione totale della memoria in byte
     */
    private int dimMemoria=0;
    
    /**
     * Numero di processi: utile per scegliere il colore per la pagina/segmento
     */
    private int numProcessi=0;
    
    /**
     * Numero di pagine: utile per la visualizzazione iniziale di tutta la RAM.
     * Rimane a 0 in caso di gestione memoria tramite segmentazione
     */
    private int numPagine=0;
    
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
    //Vector<Vector<Integer>> processiUltimati=null;
    
    /**
     * Riferimento alla classe interna incaricata di disegnare le pagine.
     * Il JScrollPane si occupera' dello scrolling di questo oggetto, che non
     * e' altro che un JPanel.
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
            //dopodiche' riempio il quadrato vuoto con quel colore
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
        if (pag_seg==1)setPreferredSize(new Dimension(320, (pagineSquare.size()/6)*55+55));
        else setPreferredSize(new Dimension(320, pagineSquare.lastElement().getyCoord()+pagineSquare.lastElement().getAltezza()+5));
        revalidate();
    }
    }
    
    /**
     * Funzione che aggiorna il grafico della RAM con i cambiamenti avvenuti nel nuovo
     * istante.
     * 
     * @param memoria
     *      Vector che rappresenta uno "screenshot" della memoria in un dato
     *      istante
     * 
     * @param istante
     *      intero che identifica l'istante di cui si sta disegnando i frame della
     *      memoria. Utile nel caso in cui l'utente decida di tornare indietro nella
     *      simulazione
     * 
     * @param processiUltimati
     *      Vector di Vector di interi che tengono traccia dei processi ultimati
     *      nei vari istanti
     * 
     * @throws Exception
     *      Lancia un'eccezione nel caso in cui la memoria non sia ancora stata configurata
     */
    public void aggiorna(Vector<FrameMemoria> memoria, int istante, Vector<Vector<Integer>> processiUltimati) throws Exception{
        //Se l'ambiente non e' ancora stato configurato, lancio un'eccezione
        if(dimMemoria==0) throw new Exception();
        
        
        
        
        int coordX=10;
        int larghezza=LATO*6;
        int coordY=10;
        int altezza=0;
        Color color;
        String text;
        FrameMemoria frame;
        
        
        
        pagineSquare.clear();
        
        int i=0;
        for(; i<memoria.size(); i++){
            frame=memoria.get(i);
            if (pag_seg==1){
                coordY=((i/6)*LATO+5*(i/6))+5;
                coordX=(i%6)*LATO+5*(i%6)+5;
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
        
        //In caso di memoria paginata, aggiungo le pagine residue per far vedere
        //la memoria nella sua interezza
        if(pag_seg==1){
            for (int j=i; j<numPagine; j++){
                coordY=((j/6)*LATO+5*(j/6))+5;
                coordX=(j%6)*LATO+5*(j%6)+5;
                
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
    

    
    /**
     * Metodo che inizializza il grafico. E' necessario passare per questo metodo
     * prima di usare effettivamente la classe, cioe' chiamare il metodo aggiorna()<br>
     * 
     * @param sceltaGestioneMemoria
     *      FALSE se si vogliono visualizzare pagine, TRUE per i segmenti
     * @param dimMemoria
     *      Dimensione totale della memoria
     * @param numProcessi
     *      Numero di processi della simulazione
     */
    public void configura(int sceltaGestioneMemoria, int dimMemoria, int numProcessi, int numPagine){
        pagineSquare.clear();
        pag_seg=sceltaGestioneMemoria;
        this.dimMemoria=dimMemoria;
        this.numProcessi=numProcessi;
        this.numPagine=numPagine;
        //processiUltimati=new Vector<Vector<Integer>>();
        //processiUltimati.add(0, new Vector<Integer>());
        if(sceltaGestioneMemoria==2){
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
                coordY=((i/6)*LATO+5*(i/6))+5;
                coordX=(i%6)*LATO+5*(i%6)+5;
                
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
