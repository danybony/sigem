/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import java.util.LinkedList;
import logic.simulazione.Istante;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daniele
 */
public class ProcessoreTest {

    public ProcessoreTest() {
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
     * Test of creaSimulazione method, of class Processore.
     */
    @Test
    public void creaSimulazione() {
        System.out.println("creaSimulazione");
        Processore instance = null;
        LinkedList<Istante> expResult = null;
        LinkedList<Istante> result = instance.creaSimulazione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}