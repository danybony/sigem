/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author PC
 */
interface Memoria{
        
    public void aggiungi(FrameMemoria frame);
    public FrameMemoria rimuovi(String idframe);
    public void liberaMemoria(String idProcesso);


}
