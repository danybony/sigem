/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

/**
 *
 * @author Davide
 */
public interface IRimpiazzo {

    public void InserisciEntry( FrameMemoria F, int Posizione, int UT, boolean M );
    public void LiberaEntry( int Posizione );
    public FrameMemoria SelezionaEntry();
    public void AggiornaEntry( int Posizione, boolean M );
    public void AggiornaEntries( );
    
}
