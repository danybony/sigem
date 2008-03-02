/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.Vector;

/**
 *
 * @author Davide
 */
public interface IAllocazione {
    public FrameMemoria Alloca ( FrameMemoria F, Vector<FrameMemoria> Liberi );
}
