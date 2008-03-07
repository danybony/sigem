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
public class RAMPaginataTest extends TestCase {
    
    ConfigurazioneIniziale conf=null;
    RAMPaginata memoria=null;
    Pagina p1, p2, p3;
    {
        try{
            conf= new ConfigurazioneIniziale(1,1,1,1,4096,4096,1,1,1024,new LinkedList());
            memoria=new RAMPaginata(conf);
            p1=new Pagina("prova",1024,0);
            p2=new Pagina("prova",1024,1);
            p3=new Pagina("prova",1024,2);
        }
        catch(Exception e){}
    }
    public RAMPaginataTest(String testName) {
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
     * Test of aggiungi method, of class RAMPaginata.
     */
    
    public void testAggiungi1() throws Exception {
        
        System.out.println("aggiungi Caso 1: RAM vuota");
        FrameMemoria pag = new Pagina("prova",1024,0);
        RAMPaginata instance = memoria;
        int expResult = 0;
        int result = instance.aggiungi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");

        //Resetto la memoria
        memoria=new RAMPaginata(conf);
    }

    public void testAggiungi2() throws Exception {
        memoria.aggiungi(p1);
        System.out.println("aggiungi Caso 2: RAM che contiene già una pagina");
        FrameMemoria pag = new Pagina("prova",1024,0);
        RAMPaginata instance = memoria;
        int expResult = 1;
        int result = instance.aggiungi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMPaginata(conf);
    }
    
    public void testAggiungi3() throws Exception {
        memoria.aggiungi(p1);
        memoria.aggiungi(p1);
        memoria.aggiungi(p1);
        memoria.aggiungi(p1);
        System.out.print("aggiungi Caso 3: RAM piena");
        FrameMemoria pag = new Pagina("prova",1024,0);
        try{
            memoria.aggiungi(pag);
        }
        catch(MemoriaEsaurita e){System.out.println(" Caso 3: esito positivo");}
        finally{        
                //Resetto la memoria
                memoria=new RAMPaginata(conf);
        }
    }
    
    /**
     * Test of rimuovi method, of class RAMPaginata.
     */
    public void testRimuovi1() throws Exception{
        
        //Caso 1: RAM non contiene la pagina
        System.out.println("rimuovi Caso 1: RAM non contiene la pagina");
        FrameMemoria pag = p1;
        RAMPaginata instance = memoria;
        boolean expResult = false;
        boolean result = instance.rimuovi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMPaginata(conf);
        
    }
    
    public void testRimuovi2() throws Exception{
        memoria.aggiungi(p1);
        //Caso 2: RAM contiene la pagina
        System.out.println("rimuovi Caso 1: RAM contiene la pagina");
        FrameMemoria pag = p1;
        RAMPaginata instance = memoria;
        boolean expResult = true;
        boolean result = instance.rimuovi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMPaginata(conf);
        
    }

    /**
     * Test of cerca method, of class RAMPaginata.
     */
    public void testCerca1() throws Exception{
        
        //Caso 1: RAM vuota
        System.out.println("cerca Caso 1: RAM vuota");
        FrameMemoria pag = p2;
        RAMPaginata instance = memoria;
        boolean expResult = false;
        boolean result = instance.cerca(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMPaginata(conf);
    }
    
    public void testCerca2() throws Exception{
        memoria.aggiungi(p2);
        //Caso 2: RAM contiene la pagina
        System.out.println("cerca Caso 2: RAM contiene la pagina");
        FrameMemoria pag = p2;
        RAMPaginata instance = memoria;
        boolean expResult = true;
        boolean result = instance.cerca(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMPaginata(conf);
    }
        
    /**
     * Test of indiceDi method, of class RAMPaginata.
     */
    public void testIndiceDi1() throws Exception{
        
        //Caso 1: RAM vuota
        System.out.println("indiceDi Caso 1: RAM vuota");
        FrameMemoria pag = p3;
        RAMPaginata instance = memoria;
        int expResult = -1;
        int result = instance.indiceDi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMPaginata(conf);
    }
    
    public void testIndiceDi2() throws Exception{
        memoria.aggiungi(p3);
        //Caso 2: RAM contiene la pagina
        System.out.println("indiceDi Caso 2: RAM contiene la pagina");
        FrameMemoria pag = p3;
        RAMPaginata instance = memoria;
        int expResult = 0;
        int result = instance.indiceDi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMPaginata(conf);
    }

    /**
     * Test of liberaMemoria method, of class RAMPaginata.
     */
    public void testLiberaMemoria1() throws Exception{
        
        //Caso 1: la RAM non contiene pagine riferite al processo
        System.out.println("liberaMemoria Caso 1: la RAM non contiene pagine riferite al processo");
        int idProcesso = 4;
        RAMPaginata instance = memoria;
        instance.liberaMemoria(idProcesso);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMPaginata(conf);
    }
    
    public void testLiberaMemoria2() throws Exception{
        memoria.aggiungi(p1);
        //Caso 1: la RAM contiene pagine riferite al processo
        System.out.println("liberaMemoria Caso 1: la RAM contiene pagine riferite al processo");
        int idProcesso = 0;
        RAMPaginata instance = memoria;
        instance.liberaMemoria(idProcesso);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMPaginata(conf);
    }

}
