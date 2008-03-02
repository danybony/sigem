/*
 * Azienda: Stylosoft
 * Nome file: ViewUtility.java
 * Package: gui.view
 * Autore: Giordano Cariani
 * Data: 29/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.0 (29/02/2008): Creazione 
 */

package gui.view;

import java.awt.*;

/**
 * Questa classe fornisce delle funzionalit� per il disegno di grafici.
 * Queste utilit� sono usate da istanze di oggetti di tipo ViewGrafAttribuzioneRisorse,
 * ViewGrafEsecuzione.
 * 
 * @author Sarto Carlo
 * @version 1.1
 */
public class ViewUtility {
	
	/**
	 * Fornisce una funzionalit� per il disegno di freccie. Questo metodo � utilizzato
	 * da istanze di ViewGrafAttribuzioneRisorse per disegnare le attribuzioni processo-risorsa.
	 * Disegna una freccia usando il colore corrente, tra i punti con coordinata (xCenter,yCenter)
	 * e (x,y).
	 * 
	 * @param g2d � un oggetto di tipo Graphics2D, � il grafico sul quale vengono
	 * 		  disegnate le freccie.
	 * @param xCenter la coordinata x del primo punto
	 * @param yCenter la coordinata y del primo punto
	 * @param x		  la coordinata x del secondo punto
	 * @param y		  la coordinata y del secondo punto
	 * @param stroke  spessore della freccia
	 */ 
    public static void drawArrow(Graphics2D g2d, int xCenter, int yCenter,
            int x, int y, float stroke) {
        double aDir = Math.atan2(xCenter - x, yCenter - y);
        g2d.drawLine(x, y, xCenter, yCenter);
        g2d.setStroke(new BasicStroke(1f));

        // make the arrow head solid even if dash pattern has been specified
        Polygon tmpPoly = new Polygon();
        int i1 = 12 + (int) (stroke * 2);
        int i2 = 6 + (int) stroke;

        // make the arrow head the same size regardless of the length length
        //      arrow tip
        tmpPoly.addPoint(x, y);
        tmpPoly.addPoint(x + xCor(i1, aDir + .3), y + yCor(i1, aDir + .3));
        tmpPoly.addPoint(x + xCor(i2, aDir), y + yCor(i2, aDir));
        tmpPoly.addPoint(x + xCor(i1, aDir - .3), y + yCor(i1, aDir - .3));
        //      arrow tip
        tmpPoly.addPoint(x, y);

        g2d.drawPolygon(tmpPoly);
        //      remove this line to leave arrow head unpainted
        g2d.fillPolygon(tmpPoly);

    }

    private static int yCor(int len, double dir) {
        return (int) (len * Math.cos(dir));
    }

    private static int xCor(int len, double dir) {
        return (int) (len * Math.sin(dir));
    }
    
    /**
     * Genera un colore con una tonalit� che si discosta da un colore di riferimento "index",
     * di un intervallo cromatico "steps".
     * 
     * @param steps		offset cromatico (rispetto una indice) del colore generato. 
     * @param index		indice del colore di riferimento.
     * @return Color	il colore generato
     */
    public static Color colorFactory(int steps, int index) {
        /*
         * Scelgo un numero massimo di colori, in questo caso 256*6, dividendo
         * per il numero di processi conosco l'intervallo cromatico tra i colori
         * di due processi diversi.
         */
        int step = 1536 / steps;

        // Calcolo l'indice del colore associato al processo specificato
        int procColorIndex = step * (index - 1);

        int red = 0;
        int green = 0;
        int blue = 0;

        int stepTot = procColorIndex / 256;
        int stepModule = (procColorIndex % 256);

        switch (stepTot) {
        case 0:
            red = 255;
            green = stepModule;
            break;
        case 1:
            red = 255 - stepModule;
            green = 255;
            break;
        case 2:
            green = 255;
            blue = stepModule;
            break;
        case 3:
            blue = 255;
            green = 255 - stepModule;
            break;
        case 4:
            blue = 255;
            red = stepModule;
            break;
        case 5:
            red = 255;
            blue = 255 - stepModule;
            break;
        default:
            break;
        }
        return new Color(red, green, blue);
    }
}