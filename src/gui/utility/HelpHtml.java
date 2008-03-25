/*
 * Azienda: Stylosoft
 * Nome file: HelpHtml.java
 * Package: gui.utility
 * Autore: Giordano Cariani
 * Data: 25/03/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: 
 *  - v.1.0 (25/03/2008): Creazione e impostazione classe
 */

package gui.utility;

import com.centerkey.utils.BareBonesBrowserLaunch;
import java.io.File;

/**
 * @author Giordano Cariani
 */
public class HelpHtml {

    public static void openUrl(String url) {
        String URL="file:///"+System.getProperty("user.dir")+File.separatorChar+"help"+File.separatorChar+url;
        URL=URL.replace("\\", "/");
        URL=URL.replace(" ","%20");
        System.out.println("URL: " + URL);
        BareBonesBrowserLaunch.openURL(URL);
    }
    

}
