/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.LinkedList;

/**
 *
 * @author Davide
 */
public abstract class GestoreMemoria {
    
    public abstract void notificaProcessoTerminato(int id);
    //public abstract boolean getFullRAM();
    //public abstract boolean getFullSwap();
    public abstract LinkedList<Azione> esegui( LinkedList<FrameMemoria> ListaFrame, int UT );
}
