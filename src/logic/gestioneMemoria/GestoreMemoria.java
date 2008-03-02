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
    protected int n_nonswap_fault=0;
    protected int n_accessi_ram=0;
    protected int n_accessi_disco=0;
    
    public int getTotalFault() { return n_total_fault; }
    public int getFirstFault() { return n_nonswap_fault; }
    public int getAccessiRam() { return n_accessi_ram; }
    public int getAccessiDisco() { return n_accessi_disco; }
    
    public abstract LinkedList<Azione> Esegui( LinkedList<FrameMemoria> ListaFrame, int UT );
}
