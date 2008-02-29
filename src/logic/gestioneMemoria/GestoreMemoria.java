/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.LinkedList;
import logic.gestioneMemoria.FrameMemoria;
/**
 *
 * @author Davide
 */
public abstract class GestoreMemoria {
    public abstract LinkedList<Azione> Esegui( LinkedList<FrameMemoria> ListaPagine, int UT );
}
