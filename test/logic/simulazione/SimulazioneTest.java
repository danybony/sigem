/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.simulazione;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.parametri.ConfigurazioneIniziale;
import logic.parametri.EccezioneConfigurazioneNonValida;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luca
 */
public class SimulazioneTest {

    ConfigurazioneIniziale conf;
    
    public SimulazioneTest() {
        try {

            conf = new ConfigurazioneIniziale(
                                                1,
                                                1,
                                                1,
                                                1,
                                                512,
                                                256,
                                                1,
                                                1,
                                                4,
                                                new LinkedList());
        } catch (EccezioneConfigurazioneNonValida ex) {
            fail("Costruzione test fallita per configurazione non valida");
        }
        
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of crea method, of class Simulazione.
     */
    @Test
    public void crea() {
        System.out.println("Creazione della simulazione");
        Simulazione instance = new Simulazione(conf);
        LinkedList<Istante> expResult = new LinkedList<Istante>();
        expResult.add(new Istante(null,null,false,0,null,false,false));
        LinkedList<Istante> result = instance.crea();
        
        if(expResult.size()==result.size())
        {
            Istante iExp, i;
            i=result.getFirst();
            iExp=expResult.getFirst();
            if(i.getFull_RAM()==iExp.getFull_RAM() &&
               i.getFull_Swap()==iExp.getFull_Swap() &&
               i.getNuovoProcesso()==iExp.getNuovoProcesso() &&
               i.getCambiamentiInMemoria()==iExp.getCambiamentiInMemoria() &&
               i.getFault()==iExp.getFault() &&
               i.getProcessoInEsecuzione()==iExp.getProcessoInEsecuzione() &&
               i.getProcessoPrecedenteTerminato()==iExp.getProcessoPrecedenteTerminato()){
                
                System.out.println("Risultato di ritorno =");
                return;
            }
        }
        fail("Gli istanti non sono =");
        

    }

    /**
     * Test of numeroIstanti method, of class Simulazione.
     */
    @Test
    public void numeroIstanti() {
        System.out.println("numeroIstanti");
        Simulazione instance = new Simulazione(conf);
        instance.crea();
        int expResult = 1;
        int result = instance.numeroIstanti();
        assertEquals(expResult, result);

    }

}


/*
 * Cronologia dei test effettuati:
 * 
 * public void crea(): 
 * -.descrizione: prova con configuarazione 
 *                    ConfigurazioneIniziale( 1,1,1,1,512,256,1,1,4,new LinkedList());
 *  .input: nuova simulazione come sopra
 *  .output: lista istanti composta da un solo istante.
 *  .esito: positivo
 * 
 * 
 * 
 * public void numeroIstanti():
 * - .descrizione: simulazione con ConfigurazioneIniziale( 1,1,1,1,512,256,1,1,4,new LinkedList());
 *                  senza invocazione di crea()
 *   .input: simulazione non creata
 *   .output: 0
 *   .esito: positivo
 * 
 * - .descrizione: simulazione con ConfigurazioneIniziale( 1,1,1,1,512,256,1,1,4,new LinkedList());
 *                  con invocazione di crea()
 *   .input: simulazione creata
 *   .output: 1
 *   .esito: positivo* 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */