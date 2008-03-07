/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.simulazione;

import java.util.LinkedList;
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

    public PlayerTest() {
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
        Player instance = null;
        boolean expResult = false;
        boolean result = instance.caricaSimulazione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of istantePrecedente method, of class Player.
     */
    @Test
    public void istantePrecedente() {
        System.out.println("istantePrecedente");
        Player instance = null;
        Istante expResult = null;
        Istante result = instance.istantePrecedente();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of istanteSuccessivo method, of class Player.
     */
    @Test
    public void istanteSuccessivo() {
        System.out.println("istanteSuccessivo");
        Player instance = null;
        Istante expResult = null;
        Istante result = instance.istanteSuccessivo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of precedenteIstanteSignificativo method, of class Player.
     */
    @Test
    public void precedenteIstanteSignificativo() {
        System.out.println("precedenteIstanteSignificativo");
        Evento e = null;
        Player instance = null;
        LinkedList<Istante> expResult = null;
        LinkedList<Istante> result = instance.precedenteIstanteSignificativo(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of prossimoIstanteSignificativo method, of class Player.
     */
    @Test
    public void prossimoIstanteSignificativo() {
        System.out.println("prossimoIstanteSignificativo");
        Evento e = null;
        Player instance = null;
        LinkedList<Istante> expResult = null;
        LinkedList<Istante> result = instance.prossimoIstanteSignificativo(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of primoIstante method, of class Player.
     */
    @Test
    public void primoIstante() {
        System.out.println("primoIstante");
        Player instance = null;
        Istante expResult = null;
        Istante result = instance.primoIstante();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ultimoIstante method, of class Player.
     */
    @Test
    public void ultimoIstante() {
        System.out.println("ultimoIstante");
        Player instance = null;
        LinkedList<Istante> expResult = null;
        LinkedList<Istante> result = instance.ultimoIstante();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of numeroIstanti method, of class Player.
     */
    @Test
    public void numeroIstanti() {
        System.out.println("numeroIstanti");
        Player instance = null;
        int expResult = 0;
        int result = instance.numeroIstanti();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatistiche method, of class Player.
     */
    @Test
    public void getStatistiche() {
        System.out.println("getStatistiche");
        Player instance = null;
        Statistiche expResult = null;
        Statistiche result = instance.getStatistiche();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}