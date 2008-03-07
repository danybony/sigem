/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.LinkedList;
import junit.framework.TestCase;
import logic.parametri.ConfigurazioneIniziale;

/**
 *
 * @author PC
 */
public class SwapPaginataTest extends TestCase {
    
    ConfigurazioneIniziale conf=null;
    SwapPaginata memoria=null;
    Pagina p1, p2, p3;
    {
        try{
            conf= new ConfigurazioneIniziale(1,1,1,1,4096,4096,1,1,1024,new LinkedList());
            memoria=new SwapPaginata(conf);
            p1=new Pagina("prova",1024,0);
            p2=new Pagina("prova",1024,1);
            p3=new Pagina("prova",1024,2);
        }
        catch(Exception e){}
    }
    public SwapPaginataTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of aggiungi method, of class SwapPaginata.
     */
    public void testAggiungi1() throws Exception {
        //Caso 1: Swap vuota
        System.out.println("aggiungi Caso 1: Swap vuota");
        FrameMemoria pag = p1;
        SwapPaginata instance = memoria;
        int expResult = 0;
        int result = instance.aggiungi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new SwapPaginata(conf);
    }

    public void testAggiungi2() throws Exception {
        memoria.aggiungi(p1);
        memoria.aggiungi(p1);
        memoria.aggiungi(p1);
        memoria.aggiungi(p1);
        //Caso 2: Swap piena
        System.out.println("aggiungi Caso 2: Swap piena");
        try{
            memoria.aggiungi(p2);
        }
        catch(MemoriaEsaurita e){}
        catch(Exception e){fail("The test case is a prototype.");}
        // TODO review the generated test code and remove the default call to fail.
        
        
        //Resetto la memoria
        memoria=new SwapPaginata(conf);
    }
    /**
     * Test of rimuovi method, of class SwapPaginata.
     */
    public void testRimuovi1() throws Exception{
        // Caso 1: La pagina non è in Swap
        System.out.println("rimuovi Caso 1: La pagina non è in Swap");
        FrameMemoria pag = p3;
        SwapPaginata instance = memoria;
        boolean expResult = false;
        boolean result = instance.rimuovi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new SwapPaginata(conf);
    }
    
    public void testRimuovi2() throws Exception{
        memoria.aggiungi(p3);
        // Caso 2: La pagina è in Swap
        System.out.println("rimuovi Caso 2: La pagina è in Swap");
        FrameMemoria pag = p3;
        SwapPaginata instance = memoria;
        boolean expResult = true;
        boolean result = instance.rimuovi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new SwapPaginata(conf);
    }

    /**
     * Test of liberaMemoria method, of class SwapPaginata.
     */
    public void testLiberaMemoria1() {
        //Caso 1: Nessuna pagina riferita dal processo
        System.out.println("liberaMemoria Caso 1: Nessuna pagina riferita dal processo");
        int idProcesso = 0;
        SwapPaginata instance = memoria;
        instance.liberaMemoria(idProcesso);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    public void testLiberaMemoria2() throws Exception{
        memoria.aggiungi(p1);
        //Caso 2: Swap contiene pagina riferita dal processo
        System.out.println("liberaMemoria Caso 2: Swap contiene pagina riferita dal processo");
        int idProcesso = 0;
        SwapPaginata instance = memoria;
        instance.liberaMemoria(idProcesso);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new SwapPaginata(conf);
    }

    /**
     * Test of cerca method, of class SwapPaginata.
     */
    public void testCerca1() {
        //Caso 1: La pagina non è in Swap
        System.out.println("cerca Caso 1: La pagina non è in Swap");
        FrameMemoria pag = p1;
        SwapPaginata instance = memoria;
        boolean expResult = false;
        boolean result = instance.cerca(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    public void testCerca2() throws Exception{
        memoria.aggiungi(p1);
        //Caso 2: La pagina è in Swap
        System.out.println("cerca Caso 2: La pagina è in Swap");
        FrameMemoria pag = p1;
        SwapPaginata instance = memoria;
        boolean expResult = true;
        boolean result = instance.cerca(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new SwapPaginata(conf);
    }

}
