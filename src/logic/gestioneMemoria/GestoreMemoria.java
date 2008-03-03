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
    
    protected int n_total_fault=0;
    // protected int n_nonswap_fault=0;
    
    public int getTotalFault() { return n_total_fault; }
    // public int getFirstFault() { return n_nonswap_fault; }
    
    public abstract LinkedList<Azione> esegui( LinkedList<FrameMemoria> ListaFrame, int UT );
}
