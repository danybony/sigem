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

    public SquareDraw(int altezza, int lunghezza, int xCoord, int yCoord, Color color) {
        square = new Rectangle2D.Double(altezza, lunghezza, xCoord, yCoord);
        this.color = color;
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
}
        