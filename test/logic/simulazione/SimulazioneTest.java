/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.simulazione;

import java.util.LinkedList;
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

    public SimulazioneTest() {
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
        System.out.println("crea");
        Simulazione instance = null;
        LinkedList<Istante> expResult = null;
        LinkedList<Istante> result = null;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        if(result==null)fail("The test case is a prototype.");
    }

    /**
     * Test of numeroIstanti method, of class Simulazione.
     */
    @Test
    public void numeroIstanti() {
        System.out.println("numeroIstanti");
        Simulazione instance = null;
        int expResult = 0;
        int result = 0;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}