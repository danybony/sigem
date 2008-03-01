/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.LinkedList;
import logic.gestioneMemoria.FrameMemoria;
import logic.parametri.ConfigurazioneIniziale;

/**
 *
 * @author Davide
 */
public abstract class GestoreMemoria {
    
    private Memoria MemoriaRam;
    private Memoria MemoriaSwap;
    private int n_frame_fault=0;
    private int n_first_fault=0;
       
    public abstract LinkedList<Azione> Esegui( LinkedList<FrameMemoria> ListaPagine, int UT );
}
