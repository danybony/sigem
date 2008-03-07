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
    public void testAggiungi() throws Exception {
        
        System.out.println("aggiungi");
        //Caso 1: RAM vuota
        FrameMemoria pag = new Pagina("prova",1024,0);
        RAMPaginata instance = memoria;
        int expResult = 0;
        int result = instance.aggiungi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        
        //Caso 2: RAM che contiene già una pagina
        expResult=1;
        result=instance.aggiungi(pag);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
        
        //Caso 3: RAM piena
        try{
            instance.aggiungi(pag);
            instance.aggiungi(pag);
            instance.aggiungi(pag);
        }
        catch(MemoriaEsaurita e){System.out.println("Caso 3: esito positivo");}
        
        //Resetto la memoria
        memoria=new RAMPaginata(conf);
    }

    /**
     * Test of rimuovi method, of class RAMPaginata.
     */
    public void testRimuovi() throws Exception{
        
        System.out.println("rimuovi");
        FrameMemoria pag = p1;
        RAMPaginata instance = memoria;
        boolean expResult = true;
        boolean result = instance.rimuovi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cerca method, of class RAMPaginata.
     */
    public void testCerca() throws Exception{
        
        //Caso 1: RAM vuota
        System.out.println("cerca");
        FrameMemoria pag = p2;
        RAMPaginata instance = memoria;
        boolean expResult = false;
        boolean result = instance.cerca(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        
        //Caso 2: RAM contiene la pagina
        memoria.aggiungi(p2);
        expResult=true;
        result=instance.cerca(p2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of indiceDi method, of class RAMPaginata.
     */
    public void testIndiceDi() {
        
        //Caso 1: RAM vuota
        System.out.println("indiceDi");
        FrameMemoria pag = p3;
        RAMPaginata instance = memoria;
        int expResult = -1;
        int result = instance.indiceDi(pag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        
        //Caso 2: RAM contiene la pagina
        memoria.aggiungi(p3);
        expResult=0;
        
    }

    /**
     * Test of liberaMemoria method, of class RAMPaginata.
     */
    public void testLiberaMemoria() {
        
        System.out.println("liberaMemoria");
        int idProcesso = 0;
        RAMPaginata instance = null;
        instance.liberaMemoria(idProcesso);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
