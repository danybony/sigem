/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.utility;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 *
 * @author luca
 */
public class ImageFilter extends FileFilter {

    //Accept all directories and .sigem files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.sigem)) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "SiGeM configuration file";
    }

}