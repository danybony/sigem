/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.simulazione;

import java.util.LinkedList;
import logic.parametri.ConfigurazioneIniziale;
import logic.parametri.EccezioneConfigurazioneNonValida;
import logic.simulazione.Player.Evento;
import logic.simulazione.Player.Statistiche;
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
public class PlayerTest {

    ConfigurazioneIniziale conf;
    
    public PlayerTest() {
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
     * Test of caricaSimulazione method, of class Player.
     */
    @Test
    public void caricaSimulazione() {
        System.out.println("caricaSimulazione");
        Player instance = new Player(conf);
        boolean expResult = true;
        boolean result = instance.caricaSimulazione();
        assertEquals(expResult, result);

    }

    /**
     * Test of istantePrecedente method, of class Player.
     */
    @Test
    public void istantePrecedente() {
        System.out.println("istantePrecedente");
        Player instance = new Player(conf);
        instance.caricaSimulazione();
        Istante expResult = null;
        Istante result = instance.istantePrecedente();
        assertEquals(expResult, result);

    }

    /**
     * Test of istanteSuccessivo method, of class Player.
     */
    @Test
    public void istanteSuccessivo() {
        System.out.println("istanteSuccessivo");
        Player instance = new Player(conf);
        instance.caricaSimulazione();
        Istante expResult = null;
        Istante result = instance.istanteSuccessivo();
        assertEquals(expResult, result);

    }

    /**
     * Test of precedenteIstanteSignificativo method, of class Player.
     */
    @Test
    public void precedenteIstanteSignificativo() {
        System.out.println("precedenteIstanteSignificativo");
        Evento e = Evento.END_PROC;
        Player instance = new Player(conf);
        instance.caricaSimulazione();
        LinkedList<Istante> expResult = null;
        LinkedList<Istante> result = instance.precedenteIstanteSignificativo(e);
        assertEquals(expResult, result);

    }

    /**
     * Test of prossimoIstanteSignificativo method, of class Player.
     */
    @Test
    public void prossimoIstanteSignificativo() {
        System.out.println("prossimoIstanteSignificativo");
        Evento e = Evento.FAULT;
        Player instance = new Player(conf);
        instance.caricaSimulazione();
        LinkedList<Istante> expResult = null;
        LinkedList<Istante> result = instance.prossimoIstanteSignificativo(e);
        assertEquals(expResult, result);

    }

    /**
     * Test of primoIstante method, of class Player.
     */
    @Test
    public void primoIstante() {
        System.out.println("primoIstante");
        Player instance = new Player(conf);
        Istante expResult = null;
        Istante result = instance.primoIstante();
        assertEquals(expResult, result);

    }

    /**
     * Test of ultimoIstante method, of class Player.
     */
    @Test
    public void ultimoIstante() {
        System.out.println("ultimoIstante");
        Player instance = new Player(conf);
        instance.caricaSimulazione();
        LinkedList<Istante> expResult = new LinkedList<Istante>();
        LinkedList<Istante> result = instance.ultimoIstante();
        if(result.size()==expResult.size())
            System.out.println("Ultimo istante ok");
        else
            fail("Ultimo istante fallito");

    }

    /**
     * Test of numeroIstanti method, of class Player.
     */
    @Test
    public void numeroIstanti() {
        System.out.println("numeroIstanti");
        Player instance = new Player(conf);
        instance.caricaSimulazione();
        int expResult = 1;
        int result = instance.numeroIstanti();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStatistiche method, of class Player.
     */
    @Test
    public void getStatistiche() {
        System.out.println("getStatistiche");
        Player instance = new Player(conf);
        Statistiche expResult = null;
        Statistiche result = instance.getStatistiche();
        if(result==null)
            fail("Fallimento getStatistiche");

    }

}


/*
 * Cronologia dei test effettuati:
 * 
 * :
 * -.descrizione: 
 *  .input: 
 *  .output: 
 *  .esito: 
 * 
 * :
 * -.descrizione: 
 *  .input: 
 *  .output: 
 *  .esito: 
 * 
 * :
 * -.descrizione: 
 *  .input: 
 *  .output: 
 *  .esito: 
 * 
 * :
 * -.descrizione: 
 *  .input: 
 *  .output: 
 *  .esito: 
 * :
 * -.descrizione: 
 *  .input: 
 *  .output: 
 *  .esito: 
 * 
 * :
 * -.descrizione: 
 *  .input: 
 *  .output: 
 *  .esito: 
 * 
 * :
 * -.descrizione: 
 *  .input: 
 *  .output: 
 *  .esito: 
 * 
 * :
 * -.descrizione: 
 *  .input: 
 *  .output: 
 *  .esito: 
 * 
 * :
 * -.descrizione: 
 *  .input: 
 *  .output: 
 *  .esito: 
 * 
 * 
 */