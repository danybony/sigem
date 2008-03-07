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

    Istante instance;
    
    public IstanteTest() {
        instance = new Istante(null,null,true,3,null,false,true);
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
        PCB expResult = null;
        PCB result = instance.getProcessoInEsecuzione();
        assertEquals(expResult, result);
    }

    /**
     * Test of getProcessoPrecedenteTerminato method, of class Istante.
     */
    @Test
    public void getProcessoPrecedenteTerminato() {
        System.out.println("getProcessoPrecedenteTerminato");
        PCB expResult = null;
        PCB result = instance.getProcessoPrecedenteTerminato();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNuovoProcesso method, of class Istante.
     */
    @Test
    public void getNuovoProcesso() {
        System.out.println("getNuovoProcesso");
        boolean expResult = true;
        boolean result = instance.getNuovoProcesso();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFault method, of class Istante.
     */
    @Test
    public void getFault() {
        System.out.println("getFault");
        int expResult = 3;
        int result = instance.getFault();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCambiamentiInMemoria method, of class Istante.
     */
    @Test
    public void getCambiamentiInMemoria() {
        System.out.println("getCambiamentiInMemoria");
        LinkedList<Azione> expResult = null;
        LinkedList<Azione> result = instance.getCambiamentiInMemoria();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFull_RAM method, of class Istante.
     */
    @Test
    public void getFull_RAM() {
        System.out.println("getFull_RAM");
        boolean expResult = false;
        boolean result = instance.getFull_RAM();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFull_Swap method, of class Istante.
     */
    @Test
    public void getFull_Swap() {
        System.out.println("getFull_Swap");
        boolean expResult = true;
        boolean result = instance.getFull_Swap();
        assertEquals(expResult, result);
    }

}


/*
 * Cronologia dei test effettuati:
 * 
 * public void getProcessoInEsecuzione():
 * -.descrizione: Test su un dato istante in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Istante(null,null,true,3,null,false,true)
 *  .output: Il campo dati corrispondente al nome del metodo relativo all'istante in input
 *  .esito: Positivo
 * 
 * public void getProcessoPrecedenteTerminato():
 * -.descrizione: Test su un dato istante in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Istante(null,null,true,3,null,false,true)
 *  .output: Il campo dati corrispondente al nome del metodo relativo all'istante in input
 *  .esito: Positivo
 * 
 * public void getNuovoProcesso():
 * -.descrizione: Test su un dato istante in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Istante(null,null,true,3,null,false,true)
 *  .output: Il campo dati corrispondente al nome del metodo relativo all'istante in input
 *  .esito: Positivo
 * 
 * public void getFault():
 * -.descrizione: Test su un dato istante in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Istante(null,null,true,3,null,false,true)
 *  .output: Il campo dati corrispondente al nome del metodo relativo all'istante in input
 *  .esito: Positivo
 * 
 * public void getCambiamentiInMemoria():
 * -.descrizione: Test su un dato istante in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Istante(null,null,true,3,null,false,true)
 *  .output: Il campo dati corrispondente al nome del metodo relativo all'istante in input
 *  .esito: Positivo
 * 
 * public void getFull_RAM():
 * -.descrizione: Test su un dato istante in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Istante(null,null,true,3,null,false,true)
 *  .output: Il campo dati corrispondente al nome del metodo relativo all'istante in input
 *  .esito: Positivo
 * 
 * public void getFull_Swap():
 * -.descrizione: Test su un dato istante in input dei medoti di interrogazione dei
 *                campi dati
 *  .input: Istante(null,null,true,3,null,false,true)
 *  .output: Il campo dati corrispondente al nome del metodo relativo all'istante in input
 *  .esito: Positivo
 * 
 */