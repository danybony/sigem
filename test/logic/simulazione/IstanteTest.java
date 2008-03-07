/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.simulazione;

import java.util.LinkedList;
import logic.gestioneMemoria.Azione;
import logic.schedulazione.PCB;
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
public class IstanteTest {

    public IstanteTest() {
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
     * Test of getProcessoInEsecuzione method, of class Istante.
     */
    @Test
    public void getProcessoInEsecuzione() {
        System.out.println("getProcessoInEsecuzione");
        Istante instance = null;
        PCB expResult = null;
        PCB result = instance.getProcessoInEsecuzione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProcessoPrecedenteTerminato method, of class Istante.
     */
    @Test
    public void getProcessoPrecedenteTerminato() {
        System.out.println("getProcessoPrecedenteTerminato");
        Istante instance = null;
        PCB expResult = null;
        PCB result = instance.getProcessoPrecedenteTerminato();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNuovoProcesso method, of class Istante.
     */
    @Test
    public void getNuovoProcesso() {
        System.out.println("getNuovoProcesso");
        Istante instance = null;
        boolean expResult = false;
        boolean result = instance.getNuovoProcesso();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFault method, of class Istante.
     */
    @Test
    public void getFault() {
        System.out.println("getFault");
        Istante instance = null;
        int expResult = 0;
        int result = instance.getFault();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCambiamentiInMemoria method, of class Istante.
     */
    @Test
    public void getCambiamentiInMemoria() {
        System.out.println("getCambiamentiInMemoria");
        Istante instance = null;
        LinkedList<Azione> expResult = null;
        LinkedList<Azione> result = instance.getCambiamentiInMemoria();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFull_RAM method, of class Istante.
     */
    @Test
    public void getFull_RAM() {
        System.out.println("getFull_RAM");
        Istante instance = null;
        boolean expResult = false;
        boolean result = instance.getFull_RAM();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFull_Swap method, of class Istante.
     */
    @Test
    public void getFull_Swap() {
        System.out.println("getFull_Swap");
        Istante instance = null;
        boolean expResult = false;
        boolean result = instance.getFull_Swap();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}