/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import java.util.LinkedList;
import logic.gestioneMemoria.Azione;
import logic.gestioneMemoria.AzionePagina;
import logic.gestioneMemoria.FrameMemoria;
import logic.gestioneMemoria.Pagina;
import logic.parametri.ConfigurazioneIniziale;
import logic.parametri.Processo;
import logic.schedulazione.PCB;
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
    Processore processore;
    ConfigurazioneIniziale conf;

    public ProcessoreTest() {
        try {
            conf = new ConfigurazioneIniziale(1, 1, 1, 1, 512, 256, 1, 1, 4, new LinkedList());
            processore = new Processore(conf);
        } catch (Exception ex) {}
        
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
        Processore instance = processore;
        LinkedList<Istante> expResult = null;
        LinkedList<Istante> result = instance.creaSimulazione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of calcolaFault method, of class Processore.
     */
    @Test
    public void calcolaFault() {        
        System.out.println("calcolaFault");        
        LinkedList<Azione> istruzioni = new LinkedList<Azione>();
        istruzioni.add(new AzionePagina(0,null));
        istruzioni.add(new AzionePagina(1,null));
        istruzioni.add(new AzionePagina(5,null));
        istruzioni.add(new AzionePagina(1,null));
        istruzioni.add(new AzionePagina(1,null));
        Processore instance = processore;
        int expResult = 3;
        int result = instance.calcolaFault(istruzioni);
        assertEquals(expResult, result);
    }

    /**
     * Test of controllaSwapPiena method, of class Processore.
     */
    @Test
    public void controllaSwapPiena() {
        System.out.println("controllaSwapPiena");
        LinkedList<Azione> istruzioni = new LinkedList<Azione>();
        istruzioni.add(new AzionePagina(0,null));
        istruzioni.add(new AzionePagina(1,null));
        istruzioni.add(new AzionePagina(5,null));
        istruzioni.add(new AzionePagina(1,null));
        istruzioni.add(new AzionePagina(-1,null));
        Processore instance = processore;
        boolean expResult = true;
        boolean result = instance.controllaSwapPiena(istruzioni);
        assertEquals(expResult, result);
    }

    /**
     * Test of controllaRAMPiena method, of class Processore.
     */
    @Test
    public void controllaRAMPiena() {
        System.out.println("controllaRAMPiena");
        LinkedList<Azione> istruzioni = new LinkedList<Azione>();
        istruzioni.add(new AzionePagina(0,null));
        istruzioni.add(new AzionePagina(1,null));
        istruzioni.add(new AzionePagina(5,null));
        istruzioni.add(new AzionePagina(0,null));
        istruzioni.add(new AzionePagina(1,null));
        Processore instance = processore;
        boolean expResult = true;
        boolean result = instance.controllaRAMPiena(istruzioni);
        assertEquals(expResult, result);
    }

    /**
     * Test of creaIstante method, of class Processore.
     */
    @Test
    public void creaIstante() {
        System.out.println("creaIstante");
        PCB corrente = null;
        LinkedList<Azione> istruzioni = null;
        boolean nuovoProcesso = false;
        boolean SwapPiena = false;
        Processore instance = processore;
        Istante expResult = null;
        Istante result = instance.creaIstante(corrente, istruzioni, nuovoProcesso, SwapPiena);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of estraiFrame method, of class Processore.
     */
    @Test
    public void estraiFrame() {
        System.out.println("estraiFrame");
        
        /* creo le pagine */
        Pagina p1 = new Pagina("1", 2, 0);
        Pagina p2 = new Pagina("2",2,0);
        Pagina p3 = new Pagina("3",2,0);
        Pagina p4 = new Pagina("4",2,0);
        /* Preparo il PCB che sarebbe in esecuzione */
        Processo processo=new Processo("processo",0,5);
        processo.richiestaFrameMemoria(p1, 0);
        processo.richiestaFrameMemoria(p2, 0);
        processo.richiestaFrameMemoria(p3, 0);
        processo.richiestaFrameMemoria(p4, 0);
        
        /* Preparo la lista che mi dovrebbe ritornare */
        LinkedList<FrameMemoria> risultato = new LinkedList<FrameMemoria>();
        risultato.add(p1);
        risultato.add(p2);
        risultato.add(p3);
        risultato.add(p4);
        
        PCB corrente = new PCB(processo);
        Processore instance = processore;
        LinkedList<FrameMemoria> expResult = risultato;
        LinkedList<FrameMemoria> result = instance.estraiFrame(corrente);
        assertEquals(expResult, result);
        
    }

}