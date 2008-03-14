/*
 * Azienda: Stylosoft
 * Nome file: SquareDraw.java
 * Package: gui.utility
 * Autore: Giordano Cariani
 * Data: 03/03/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.0 (02/03/2008): Creazione classe per disegno quadrati
 */

package gui.utility;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class SquareDraw {
	Shape square;
        Color color;
        private int xCoordText;
        private int yCoordText;
        private int xCoord;
        private int yCoord;
        int altezza;
        private String indirizzoFrame;

    public SquareDraw(int xCoord, int yCoord, int lunghezza, int altezza, Color color, String text) {
        square = new Rectangle2D.Double(xCoord, yCoord, lunghezza, altezza);
        this.color = color;
        xCoordText=xCoord+(lunghezza/2)-(text.length()*5);
        yCoordText=yCoord+(altezza/2)+6;
        this.xCoord=xCoord;
        this.yCoord=yCoord;
        indirizzoFrame=text;
        this.altezza=altezza;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    } 
        
    public Shape getSquare() {
        return square;
    }

    public void setSquare(Shape square) {
        this.square = square;
    }
    
    public int getxCoordText(){
        return xCoordText;
    }
    
    public int getyCoordText(){
        return yCoordText;
    }
    
    public int getxCoord(){
        return xCoord;
    }
    
    public int getyCoord(){
        return yCoord;
    }
    
    public String getText(){
        return indirizzoFrame;
    }
    
    public int getAltezza(){
        return altezza;
    }
}
        