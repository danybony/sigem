/*
 * Azienda: Stylosoft
 * Nome file: ViewFrameMemoria.java
 * Package: gui.dialog
 * Autore: Giordano Cariani
 * Data: 03/03/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche: *  
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
import java.util.Vector;

public class ViewFrameMemoria extends JPanel {
    /** Necessario per il Serializable. */
    private static final long serialVersionUID = -4441774947632050544L;
    
    /** Pannello della vista che conterra' le pagine della MV */
    private JPanel pannello = null;
    
    /** Serve per creare le pagine della MV */
    private Vector pagineSquare = new Vector();
    
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
    public void inizializzaViewFrame(int nPagine, int dimPagine) {
        
        pagineSquare.clear();
        
        creaPagine(nPagine, dimPagine);
        
        repaint();
        
    }
    
    /**
     * Costruisce le pagine. 
     * Le pagine pari sono colorate di blue mentre quelle dispari cyan
     * 
     * @param totale numero totale di pagine da costruire
     * 
     * @param dim  dimensione della pagina impostata dall'utente
     */
    private void creaPagine(int totale, int dim) {
            int fisso = 60;
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
            for (int i=0; i<totale; i++) {
                int dimensione = dim*fisso;
                if (i==0)
                    pagineSquare.add(new SquareDraw(coordX, coordY, dimensione, dimensione, Color.lightGray));
                else if (i%5==0) {
                        indice=0;
                        coordX=0;
                        coordY+= fisso+spostamento;
                }           
                coordX = (indice*fisso)+(indice*spostamento);
                pagineSquare.add(new SquareDraw(coordX, coordY, dimensione, dimensione, Color.lightGray));
                indice++;
            }
     }
    
    /**
     * Disegna tutte le pagine. 
     * Le pagine pari sono colorate di blue mentre quelle dispari cyan
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
        }
    }

}
