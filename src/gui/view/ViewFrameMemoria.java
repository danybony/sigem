/*
 * Azienda: Stylosoft
 * Nome file: ViewFrameMemoria.java
 * Package: gui.dialog
 * Autore: Giordano Cariani
 * Data: 03/03/2008
 * Versione: 1.2
 * Licenza: open-source
 * Registro delle modifiche: 
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
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.LinkedList;
import java.util.Vector;
import logic.gestioneMemoria.Azione;

public class ViewFrameMemoria extends JPanel {
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
    
    //Int che setta la grandezza dei quadrati rappresentanti le pagine
    private static final int LATO=48;
    
    //Int che imposta l'altezza totale della RAM in caso di segmentazione
    private static final int ALTEZZA=600;
        
    /** Vector che contiene le pagine/segmenti da visualizzare */
    private Vector<SquareDraw> pagineSquare = new Vector<SquareDraw>();
    
    
    
    public ViewFrameMemoria() {
        super();
        this.setLayout(new BorderLayout());
    }

    /**
    * Disegna gli elementi del grafico.
    */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        setBackground(Color.white);
        
        disegnaPagine(g);
    }
    
    /**
     * Inizializza il JPanel e crea eventualemnte le pagine
     * 
     * @param totale numero totale di pagine da costruire
     * 
     * @param dim  dimensione della pagina impostata dall'utente
     */
    /**public void inizializzaViewFrame(int nPagine, int dimPagine) {
        
        pagineSquare.clear();
        
        creaPagine(100, dimPagine);
        
        repaint();
        
    }*/
    
    /**
     * Costruisce le pagine. 
     * Le pagine pari sono colorate di blue mentre quelle dispari cyan
     * 
     * @param totale numero totale di pagine da costruire
     * 
     * @param dim  dimensione della pagina impostata dall'utente
     */
    /*private void creaPagine(int totale, int dim) {
            int fisso = 48;
            int spostamento=5;
            int coordX=0, coordY=10, indice=0;
            /*
            for (int i=0; i<totale; i++) {
                int dimensione = dim*fisso;
                if (i==0)
                    pagineSquare.add(new SquareDraw(0, coordY, dimensione, dimensione, Color.lightGray));
                else {
                    if (i%5==0) {
                        indice =0;
                        coordX=0;
                        coordY+= fisso+spostamento;
                    }           
                    coordX = (indice*fisso)+(indice*spostamento);
                    if (i%2==0)
                        pagineSquare.add(new SquareDraw(coordX, coordY, dimensione, dimensione, Color.lightGray));
                    else
                        pagineSquare.add(new SquareDraw(coordX, coordY, dimensione, dimensione, Color.GRAY));
                }
                indice++;
             */
            /*for (int i=0; i<totale; i++) {
                int dimensione = dim*fisso;
                if (i==0)
                    pagineSquare.add(new SquareDraw(coordX, coordY, dimensione, dimensione, Color.lightGray));
                else if (i%6==0) {
                        indice=0;
                        coordX=0;
                        coordY+= fisso+spostamento;
                }           
                coordX = (indice*fisso)+(indice*spostamento);
                pagineSquare.add(new SquareDraw(coordX, coordY, dimensione, dimensione, Color.lightGray));
                indice++;
            }*/
     
    
    /**
     * Disegna tutte le pagine. 
     * 
     * @param g il grafico su cui disegnare le risorse.
     */
    private void disegnaPagine(Graphics g) {
        
        Graphics2D ga = (Graphics2D)g;
        for (int i=0; i<pagineSquare.size(); i++) {
            SquareDraw pag = (SquareDraw) pagineSquare.get(i);
            ga.setColor(Color.BLACK);
            ga.draw(pag.getSquare());
            ga.setPaint(pag.getColor());
            ga.fill(pag.getSquare());
            
            ga.setColor(Color.BLACK);
            ga.setFont(new Font("Arial", 1, 20));
            ga.drawString(pag.getText(),pag.getxCoordText(),pag.getyCoordText());
            
        }
    }
    
    /**
     * Funzione che aggiorna il grafico della RAM con i cambiamenti avvenuti nel nuovo
     * istante.
     * 
     * @param cambiamentiInMemoria
     *      LinkedList di Azioni che rappresentano tutto ciò che è stato modificato
     *      in RAM
     * 
     * @throws Exception
     *      Lancia un'eccezione nel caso in cui la memoria non sia ancora stata configurata
     */
    public void aggiorna(LinkedList<Azione> cambiamentiInMemoria) throws Exception{
        if(dimMemoria==0) throw new Exception();
        
        Azione azione=null;
        SquareDraw aux=null;
        int coordX=0, coordY=0, altezza=0, larghezza=0, posizione=0;
        
        for(int i=0;i<cambiamentiInMemoria.size(); i++){
            azione=cambiamentiInMemoria.get(i);
            switch(azione.getAzione()){
                case 1: 
                       //Aggiungere pagina in RAM
                       if(pag_seg==false) {
                           posizione=azione.getPosizione();
                           coordY=((posizione/6)*LATO+5*(posizione/6))+10;
                           coordX=(posizione%6)*LATO+5*(posizione%6);
                           pagineSquare.add(
                                   posizione,
                                   new SquareDraw(coordX,
                                                  coordY,
                                                  LATO,
                                                  LATO,
                                                  ViewUtility.colorFactory(numProcessi, azione.getFrame().getIdProcesso()),
                                                  azione.getFrame().getIndirizzo())
                                   );
                           //Rimuovo l'eventuale doppione di pagina creato
                           if(pagineSquare.lastElement()!=pagineSquare.get(posizione))
                               pagineSquare.remove(posizione+1);
                       }
                       
                       if(pag_seg==true) {
                           //Inserisco il nuovo segmento nel Vector dei FrameMemoria

                           posizione=azione.getPosizione();
                           aux=(SquareDraw)pagineSquare.get(posizione);
                           
                           coordY=aux.getyCoord();
                           coordX=5;
                           larghezza=LATO*6;
                           altezza=(ALTEZZA*azione.getFrame().getDimensione())/dimMemoria;
                               
                           pagineSquare.add(
                                       posizione,
                                       new SquareDraw(coordX,
                                                      coordY,
                                                      larghezza,
                                                      altezza,
                                                      ViewUtility.colorFactory(numProcessi, azione.getFrame().getIdProcesso()),
                                                      azione.getFrame().getIndirizzo())
                                       );
                           
                           //tolgo quello vecchio che era nella stessa posizione e metto lo spazio residuo
                           
                           if (altezza!=aux.getAltezza()){
                               pagineSquare.add(
                                       posizione+1,
                                       new SquareDraw(coordX,
                                                      coordY+aux.getAltezza()-altezza,
                                                      larghezza,
                                                      aux.getAltezza()-altezza,
                                                      Color.LIGHT_GRAY,
                                                      " ")
                                       );
                           }
                           pagineSquare.remove(posizione+2);
                       }
                       break;
                case 3: 
                       //Togliere segmento in RAM
                       if(pag_seg==true){
                           aux=pagineSquare.get(azione.getPosizione());
                           
                           //Salvo i dati per il nuovo segmento spazio
                           posizione=azione.getPosizione();
                           coordX=aux.getxCoord();
                           coordY=aux.getyCoord();
                           larghezza=LATO*6;
                           altezza=aux.getAltezza();
                           
                           //Rimuovo il vecchio segmento
                           pagineSquare.remove(posizione);
                           
                           //Ora guardo se prima e dopo il vecchio segmento ci sono segmenti
                           //spazio; in caso affermativo li connetto assieme
                           
                           if(posizione<pagineSquare.size()){
                               aux=pagineSquare.elementAt(posizione);
                               if(aux.getText().equals(" ")){
                                   
                                   //Il segmento successivo è uno spazio; lo connetto al segmento corrente
                                   altezza+=aux.getAltezza();
                                   pagineSquare.remove(posizione);
                                   
                               }
                           }
                           
                           if(posizione>0){
                               aux=pagineSquare.elementAt(posizione-1);
                               if(aux.getText().equals(" ")){
                                   
                                   //Il segmento precedente è uno spazio; lo connetto al segmento corrente
                                   altezza+=aux.getAltezza();
                                   coordX=aux.getxCoord();
                                   coordY=aux.getyCoord();
                                   
                                   posizione-=1;
                                   pagineSquare.remove(posizione);
                               }
                           }
                           
                           //Infine inserisco il nuovo segmento spazio
                           pagineSquare.add(
                                       posizione,
                                       new SquareDraw(coordX,
                                                      coordY,
                                                      larghezza,
                                                      altezza,
                                                      Color.LIGHT_GRAY,
                                                      " ")
                                       );
                       }
                
                       break;
                       
                case 6:
                       //Un processo ha finito di eseguire, libero la memoria associata
                       //Prima di tutto, scorro il vettore impostando nelle pagine/segmenti
                       //associati al processo il colore grigio e lo spazio:
                           for(int j=0; j<pagineSquare.size();j++){
                               aux=pagineSquare.get(j);
                               if(aux.getColor().equals(ViewUtility.colorFactory(numProcessi, azione.getPosizione()))){
                                   aux.setColor(Color.LIGHT_GRAY);
                                   aux.setText(" ");
                               }
                           }
                       //Dopodichè, se la memoria è composta da segmenti, compatto i segmenti
                       //spazio contigui:
                       if(pag_seg==true){
                           SquareDraw aux2=null;
                           for(int j=0; j<pagineSquare.size()-1;j++){
                               aux=pagineSquare.get(j);
                               aux2=pagineSquare.get(j+1);
                               if(aux.getText().equals(" ") && aux2.getText().equals(" ")){
                                   aux.setAltezza(aux.getAltezza()+aux2.getAltezza());
                                   pagineSquare.remove(j+1);
                                   j-=1;
                               }
                           }
                       }
                       
                       break;
                default:
                       //Non faccio nulla, non è un'azione rilevante
                
                       
            }
        }
        
        repaint();
    }
    
    /*private Color assegnaColore(int processo) {
        switch(processo){
            case 0: return Color.BLUE;
            case 1:return Color.CYAN;
            case 2:return Color.GREEN;
            case 3:return Color.MAGENTA;
            case 4:return Color.ORANGE;
            case 5:return Color.PINK;
            case 6:return Color.RED;
            case 7:return Color.YELLOW;
            case 8:return Color.CYAN.brighter();
            case 9:return Color.BLUE.brighter();
            case 10:return Color.GREEN.brighter();
            case 11:return Color.MAGENTA.brighter();
            case 12:return Color.ORANGE.brighter();
            case 13:return Color.PINK.brighter();
            case 14:return Color.RED.brighter();
            case 15:return Color.YELLOW.brighter();
            case 16:return Color.BLUE;
            case 17:return Color.BLUE;
            case 18:return Color.BLUE;
            case 19:return Color.BLUE;
            case 20:return Color.BLUE;
            default: return Color.WHITE;
        }
    }*/
    
    /**Metodo per impostare il grafico come visualizzatore di pagine o segmenti<br>
     * @param sceltaGestioneMemoria
     *      FALSE se si vogliono visualizzare pagine, TRUE per i segmenti
     * @param dimMemoria
     *      dimensione totale della memoria
     */
    public void configura(boolean sceltaGestioneMemoria, int dimMemoria, int numProcessi){
        pag_seg=sceltaGestioneMemoria;
        this.dimMemoria=dimMemoria;
        this.numProcessi=numProcessi;
        if(sceltaGestioneMemoria==true){
            pagineSquare.add(
                             0,
                             new SquareDraw(5,
                                            10, 
                                            LATO*6,
                                            ALTEZZA,
                                            Color.LIGHT_GRAY,
                                            " ")
                                       );
        }
        
    }

}
