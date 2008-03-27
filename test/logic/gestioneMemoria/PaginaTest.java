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
public class PaginaTest {

    public PaginaTest() {
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
     * Test of getSolaLettura method, of class Pagina.
     */
    @Test
    public void getSolaLettura() {
        System.out.println("getSolaLettura");
        Pagina instance = new Pagina("123",4,2);
        boolean expResult = false;
        boolean result = instance.getSolaLettura();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSolaLettura method, of class Pagina.
     */
    @Test
    public void setSolaLettura() {
        System.out.println("setSolaLettura");
        boolean nuovoStato = true;
        Pagina instance = new Pagina("123",4,2);
        boolean expResult = true, verifyState = true;
        boolean result = instance.setSolaLettura(nuovoStato);
        assertEquals(expResult, result);
        assertEquals(verifyState, instance.getSolaLettura());
    }

    /**
     * Test of getBloccata method, of class Pagina.
     */
    @Test
    public void getBloccata() {
        System.out.println("getBloccata");
        Pagina instance = new Pagina("123",4,2);
        boolean expResult = false;
        boolean result = instance.getBloccata();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBloccata method, of class Pagina.
     */
    @Test
    public void setBloccata() {
        System.out.println("setBloccata");
        boolean nuovoStato = true;
        Pagina instance = new Pagina("123",4,2);
        boolean expResult = true, verifyState = true;
        boolean result = instance.setBloccata(nuovoStato);
        assertEquals(expResult, result);
        assertEquals(verifyState, instance.getBloccata());
    }

    /**
     * Test of getIndirizzo method, of class Pagina.
     */
    @Test
    public void getIndirizzo() {
        System.out.println("getIndirizzo");
        Pagina instance = new Pagina("123",4,2);
        String expResult = "123";
        String result = instance.getIndirizzo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInRAM method, of class Pagina.
     */
    @Test
    public void getInRAM() {
        System.out.println("getInRAM");
        Pagina instance = new Pagina("123",4,2);
        boolean expResult = false;
        boolean result = instance.getInRAM();
        assertEquals(expResult, result);
    }

    /**
     * Test of setInRAM method, of class Pagina.
     */
    @Test
    public void setInRAM() {
        System.out.println("setInRAM");
        boolean nuovoStato = true;
        Pagina instance = new Pagina("123",4,2);
        boolean expResult = true, verifyState = true;
        boolean result = instance.setInRAM(nuovoStato);
        assertEquals(expResult, result);
        assertEquals(verifyState, instance.getInRAM());
    }

    /**
     * Test of getDimensione method, of class Pagina.
     */
    @Test
    public void getDimensione() {
        System.out.println("getDimensione");
        Pagina instance = new Pagina("123",4,2);
        int expResult = 4;
        int result = instance.getDimensione();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIdProcesso method, of class Pagina.
     */
    @Test
    public void getIdProcesso() {
        System.out.println("getIdProcesso");
        Pagina instance = new Pagina("123",4,2);
        int expResult = 2;
        int result = instance.getIdProcesso();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdProcesso method, of class Pagina.
     */
    @Test
    public void setIdProcesso() {
        System.out.println("setIdProcesso");
        int idProcessoPassato = 3, verifyState = 3;
        Pagina instance = new Pagina("123",4,2);
        boolean expResult = true;
        boolean result = instance.setIdProcesso(idProcessoPassato);
        assertEquals(expResult, result);
        assertEquals(verifyState, instance.getIdProcesso());
    }

    /**
     * Test of getModifica method, of class Pagina.
     */
    @Test
    public void getModifica() {
        System.out.println("getModifica");
        Pagina instance = new Pagina("123",4,2);
        boolean expResult = false;
        boolean result = instance.getModifica();
        assertEquals(expResult, result);
    }

    /**
     * Test of setModifica method, of class Pagina.
     */
    @Test
    public void setModifica() {
        System.out.println("setModifica");
        boolean nuovoStato = true;
        Pagina instance = new Pagina("123",4,2);
        boolean expResult = true, verifyState = true;
        boolean result = instance.setModifica(nuovoStato);
        assertEquals(expResult, result);
        assertEquals(verifyState, instance.getModifica());
    }

}


/*
 * Cronologia dei test effettuati:
 * 
 * public void getSolaLettura():
 * -.descrizione: Test su una pagina in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: Il campo dati corrispondente
 *  .esito: Positivo
 * 
 * public void setSolaLettura():
 * -.descrizione: Test su una pagina in input dei medoti di impostazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: True e l'avvenuta modifica
 *  .esito: Positivo
 * 
 * public void getBloccata():
 * -.descrizione: Test su una pagina in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: Il campo dati corrispondente
 *  .esito: Positivo
 * 
 * public void setBloccata():
 * -.descrizione: Test su una pagina in input dei medoti di impostazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: True e l'avvenuta modifica
 *  .esito: Positivo
 * 
 * public void getIndirizzo():
 * -.descrizione: Test su una pagina in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: Il campo dati corrispondente
 *  .esito: Positivo
 * 
 * public void getInRAM():
 * -.descrizione: Test su una pagina in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: Il campo dati corrispondente
 *  .esito: Positivo
 * 
 * public void setInRAM():
 * -.descrizione: Test su una pagina in input dei medoti di impostazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: True e l'avvenuta modifica
 *  .esito: Positivo
 * 
 * public void getDimensione():
 * -.descrizione: Test su una pagina in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: Il campo dati corrispondente
 *  .esito: Positivo
 * 
 * public void getIdProcesso():
 * -.descrizione: Test su una pagina in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: Il campo dati corrispondente
 *  .esito: Positivo
 * 
 * public void setIdProcesso():
 * -.descrizione: Test su una pagina in input dei medoti di impostazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: True e l'avvenuta modifica
 *  .esito: Positivo
 * 
 * public void getModifica():
 * -.descrizione: Test su una pagina in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: Il campo dati corrispondente
 *  .esito: Positivo
 * 
 * public void setModifica():
 * -.descrizione: Test su una pagina in input dei medoti di impostazione dei
 *                campi dati
 *  .input: Una pagina di prova
 *  .output: True e l'avvenuta modifica
 *  .esito: Positivo
 * 
 */
