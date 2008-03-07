/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.schedulazione;

import java.util.ArrayList;
import java.util.LinkedList;
import logic.parametri.Processo;
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
public class SchedulerTest {
    PoliticaOrdinamentoProcessi politica = new FCFS();
       Processo P1 = new Processo("P1",0,7);
       Processo P2 = new Processo("P2",0,2);
       Processo P3 = new Processo("P3",3,3);
       LinkedList processi = new LinkedList();       
       Scheduler scheduler;
       {
       processi.add(P1);
       processi.add(P2);
       processi.add(P3);
       scheduler = new Scheduler(politica, processi);
       scheduler.eseguiAttivazione();
       }
    public SchedulerTest() {       
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
     * Test of attivaProcesso method, of class Scheduler.
     */
    @Test
    public void attivaProcesso() {
        System.out.println("attivaProcesso");
        Scheduler instance = scheduler;
        instance.attivaProcesso();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of eseguiAttivazione method, of class Scheduler.
     */
    @Test
    public void eseguiAttivazione() {
        System.out.println("eseguiAttivazione");
        Scheduler instance = scheduler;
        boolean expResult = false;
        boolean result = instance.eseguiAttivazione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of eseguiIncremento method, of class Scheduler.
     */
    @Test
    public void eseguiIncremento() {
        System.out.println("eseguiIncremento");
        Scheduler instance = scheduler;
        instance.eseguiIncremento();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getProcessiInArrivo method, of class Scheduler.
     */
    @Test
    public void getProcessiInArrivo() {
        System.out.println("getProcessiInArrivo");
        Scheduler instance = scheduler;
        LinkedList expResult = processi;
        LinkedList result = instance.getProcessiInArrivo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getProcessiTerminati method, of class Scheduler.
     */
    @Test
    public void getProcessiTerminati() {
        System.out.println("getProcessiTerminati");
        Scheduler instance = scheduler;
        ArrayList expResult = new ArrayList();
        ArrayList result = instance.getProcessiTerminati();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPCBCorrente method, of class Scheduler.
     */
    @Test
    public void getPCBCorrente() {
        System.out.println("getPCBCorrente");
        Scheduler instance = scheduler;
        PCB expResult = null;
        PCB result = instance.getPCBCorrente();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPoliticaOrdinamentoProcessi method, of class Scheduler.
     */
    @Test
    public void getPoliticaOrdinamentoProcessi() {
        System.out.println("getPoliticaOrdinamentoProcessi");
        Scheduler instance = scheduler;
        PoliticaOrdinamentoProcessi expResult = politica;
        PoliticaOrdinamentoProcessi result = instance.getPoliticaOrdinamentoProcessi();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTempoCorrente method, of class Scheduler.
     */
    @Test
    public void getTempoCorrente() {
        System.out.println("getTempoCorrente");
        Scheduler instance = scheduler;
        int expResult = 0;
        int result = instance.getTempoCorrente();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of incrementaTempo method, of class Scheduler.
     */
    @Test
    public void incrementaTempo() {
        System.out.println("incrementaTempo");
        Scheduler instance = scheduler;
        instance.incrementaTempo();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of incrementaTempoScheduler method, of class Scheduler.
     */
    @Test
    public void incrementaTempoScheduler() {
        System.out.println("incrementaTempoScheduler");
        Scheduler instance = scheduler;
        instance.incrementaTempoScheduler();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of rimuoviPCBCorrente method, of class Scheduler.
     */
    @Test
    public void rimuoviPCBCorrente() {
        System.out.println("rimuoviPCBCorrente");
        Scheduler instance = scheduler;
        instance.rimuoviPCBCorrente();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of tempoProssimoEvento method, of class Scheduler.
     */
    @Test
    public void tempoProssimoEvento() {
        System.out.println("tempoProssimoEvento");
        Scheduler instance = scheduler;
        int expResult = 0;
        int result = instance.tempoProssimoEvento();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of terminaPCBCorrente method, of class Scheduler.
     */
    @Test
    public void terminaPCBCorrente() {
        System.out.println("terminaPCBCorrente");
        Scheduler instance = scheduler;
        instance.terminaPCBCorrente();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}

/*
 * -eseguiAttivazione:
 * input: scheduler con processo in esecuzione subito
 * output: false
 * risultato: ok
 * 
 * input:scheduler senza processi in esecuzione subito
 * output: true
 * risultato: ok
 */