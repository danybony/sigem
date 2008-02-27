/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.Vector;


abstract class Memoria{
        
    protected Vector<FrameMemoria> memoria=new Vector<FrameMemoria>();
    
    
    public abstract FrameMemoria rimuovi(FrameMemoria frame);
    public abstract boolean cerca(FrameMemoria frame);
    public abstract void liberaMemoria(int idProcesso);


}
