/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.parametri;

import java.util.ArrayList;
import logic.gestioneMemoria.FrameMemoria;
import logic.gestioneMemoria.Pagina;
import logic.parametri.Processo.Accesso;
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
public class ProcessoTest {
    Processo processo = new Processo("P1",0,10);
    /* creo le pagine */
        Pagina p1 = new Pagina("1", 2, 0);
        Pagina p2 = new Pagina("2",2,0);
        Pagina p3 = new Pagina("3",2,0);
        Pagina p4 = new Pagina("4",2,0);
    public ProcessoTest() {
        processo.richiestaFrameMemoria(p1, 0);
        processo.richiestaFrameMemoria(p2, 0);
        processo.richiestaFrameMemoria(p3, 0);
        processo.richiestaFrameMemoria(p4, 0);
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
     * Test of getId method, of class Processo.
     */
    @Test
    public void getId() {
        System.out.println("getId");
        Processo instance = processo;
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAccessi method, of class Processo.
     */
    @Test
    public void getAccessi() {
        System.out.println("getAccessi");
        ArrayList risultato = new ArrayList();
        risultato.add(p1);
        risultato.add(p2);
        risultato.add(p3);
        risultato.add(p4);
        Processo instance = processo;
        ArrayList expResult = risultato;
        ArrayList result = instance.getAccessi();
        ArrayList result2 = new ArrayList();
        for(int i=0; i<result.size();i++){
            result2.add(((Accesso)result.get(i)).getRisorsa());
        }
        assertEquals(expResult, result2);
    }
    

    /**
     * Test of getTempoArrivo method, of class Processo.
     */
    @Test
    public void getTempoArrivo() {
        System.out.println("getTempoArrivo");
        Processo instance = processo;
        int expResult = 0;
        int result = instance.getTempoArrivo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNome method, of class Processo.
     */
    @Test
    public void getNome() {
        System.out.println("getNome");
        Processo instance = processo;
        String expResult = "P1";
        String result = instance.getNome();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTempoEsecuzione method, of class Processo.
     */
    @Test
    public void getTempoEsecuzione() {
        System.out.println("getTempoEsecuzione");
        Processo instance = processo;
        int expResult = 10;
        int result = instance.getTempoEsecuzione();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Processo.
     */
    @Test
    public void equals() {
        System.out.println("equals");
        Object processoProva = processo;
        Processo instance = processo;
        boolean expResult = true;
        boolean result = instance.equals(processoProva);
        assertEquals(expResult, result);
    }

}