/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

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
public class SegmentoTest {

    public SegmentoTest() {
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
     * Test of getSolaLettura method, of class Segmento.
     */
    @Test
    public void getSolaLettura() {
        System.out.println("getSolaLettura");
        Segmento instance = new Segmento("123",300,2);
        boolean expResult = false;
        boolean result = instance.getSolaLettura();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSolaLettura method, of class Segmento.
     */
    @Test
    public void setSolaLettura() {
        System.out.println("setSolaLettura");
        boolean nuovoStato = true;
        Segmento instance = new Segmento("123",300,2);
        boolean expResult = true, verifyState = true;
        boolean result = instance.setSolaLettura(nuovoStato);
        assertEquals(expResult, result);
        assertEquals(verifyState, instance.getSolaLettura());
    }

    /**
     * Test of getDimensione method, of class Segmento.
     */
    @Test
    public void getDimensione() {
        System.out.println("getDimensione");
        Segmento instance = new Segmento("123",300,2);
        int expResult = 300;
        int result = instance.getDimensione();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIndirizzo method, of class Segmento.
     */
    @Test
    public void getIndirizzo() {
        System.out.println("getIndirizzo");
        Segmento instance = new Segmento("123",300,2);
        String expResult = "123";
        String result = instance.getIndirizzo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInRAM method, of class Segmento.
     */
    @Test
    public void getInRAM() {
        System.out.println("getInRAM");
        Segmento instance = new Segmento("123",300,2);
        boolean expResult = false;
        boolean result = instance.getInRAM();
        assertEquals(expResult, result);
    }

    /**
     * Test of setInRAM method, of class Segmento.
     */
    @Test
    public void setInRAM() {
        System.out.println("setInRAM");
        boolean nuovoStato = true;
        Segmento instance = new Segmento("123",300,2);
        boolean expResult = true, verifyState = true;
        boolean result = instance.setInRAM(nuovoStato);
        assertEquals(expResult, result);
        assertEquals(verifyState, instance.getInRAM());
    }

    /**
     * Test of getIdProcesso method, of class Segmento.
     */
    @Test
    public void getIdProcesso() {
        System.out.println("getIdProcesso");
        Segmento instance = new Segmento("123",300,2);
        int expResult = 2;
        int result = instance.getIdProcesso();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdProcesso method, of class Segmento.
     */
    @Test
    public void setIdProcesso() {
        System.out.println("setIdProcesso");
        int idProcessoPassato = 3, verifyState = 3;
        Segmento instance = new Segmento("123",300,2);
        boolean expResult = true;
        boolean result = instance.setIdProcesso(idProcessoPassato);
        assertEquals(expResult, result);
        assertEquals(verifyState, instance.getIdProcesso());
    }

    /**
     * Test of getModifica method, of class Segmento.
     */
    @Test
    public void getModifica() {
        System.out.println("getModifica");
        Segmento instance = new Segmento("123",300,2);
        boolean expResult = false;
        boolean result = instance.getModifica();
        assertEquals(expResult, result);
    }

    /**
     * Test of setModifica method, of class Segmento.
     */
    @Test
    public void setModifica() {
        System.out.println("setModifica");
        boolean nuovoStato = true;
        Segmento instance = new Segmento("123",300,2);
        boolean expResult = true, verifyState = true;
        boolean result = instance.setModifica(nuovoStato);
        assertEquals(expResult, result);
        assertEquals(verifyState, instance.getModifica());
    }

    /**
     * Test of getTempoInRam method, of class Segmento.
     */
    @Test
    public void getTempoInRam() {
        System.out.println("getTempoInRam");
        Segmento instance = new Segmento("123",300,2);
        int expResult = 0;
        int result = instance.getTempoInRam();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTempoInRAM method, of class Segmento.
     */
    @Test
    public void setTempoInRAM() {
        System.out.println("setTempoInRAM");
        int nuovoTempo = 220, verifyState = 220;
        Segmento instance = new Segmento("123",300,2);
        boolean expResult = true;
        boolean result = instance.setTempoInRAM(nuovoTempo);
        assertEquals(expResult, result);
        assertEquals(verifyState, instance.getTempoInRam());
    }

}

/*
 * Cronologia dei test effettuati:
 * 
 * :
 * -.descrizione: Test su una pagina in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: Il campo dati corrispondente
 *  .esito: Positivo
 * 
 * :
 * -.descrizione: Test su una pagina in input dei medoti di impostazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: True e l'avvenuta modifica
 *  .esito: Positivo
 */