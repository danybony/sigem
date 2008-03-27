/*
 * Azienda: Stylosoft
 * Nome file: IconStylosoft.java
 * Package: gui.utility
 * Autore: Cariani Giordano
 * Data: 01/03/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche
 *  - v.1.0 (01/03/2008): Creazione della classe. Cariani Giordano.
 * */
package gui.utility;

import javax.swing.*;
import java.net.URL;

/**
 * Questa classe fornisce delle funzionalit� per gestire icone.
 * Legge icone dal Java Look-And-Feel Graphics Respository.
 * La classe SiGeMv2View utilizza metodi di questa classe per ottenere delle icone personalizzate
 * da applicare a pulsanti e menu.
 * 
 */
public class IconStylosoft {

    private static IconStylosoft iconUtils = null;

    /**
     * Ritorna una icona tra quelle presenti nella sezione custom dell'archivio
     * personalizzato
     * 
     * @param category
     *            Una delle seguenti: general, help, navigation, panel wizard
     * @param name
     *            Il nome dell'immagine
     */
    public ImageIcon getIcon(String name) {

        // Build the URL String
        String imageName = "/images/" + name + ".png";

        // Get a URL pointing to the image
        URL iconURL = this.getClass().getResource(imageName);
        if (iconURL == null)
            return null;
        // Build and return a new ImageIcon built from this URL
        return new ImageIcon(iconURL);
    }
    
   public static ImageIcon getGeneralIcon(String name) {
        if (iconUtils == null) {
            iconUtils = new IconStylosoft();
        }

        return iconUtils.getIcon(name);
    }

}
