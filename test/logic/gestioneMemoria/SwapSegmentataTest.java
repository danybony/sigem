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
public class SwapSegmentataTest extends TestCase {
    
    ConfigurazioneIniziale conf=null;
    SwapSegmentata memoria=null;
    Segmento s1, s2, s3;
    {
        try{
            conf= new ConfigurazioneIniziale(1,1,1,1,4096,4096,1,1,1024,new LinkedList());
            memoria=new SwapSegmentata(conf);
            s1=new Segmento("prova",1024,0);
            s2=new Segmento("prova",1024,1);
            s3=new Segmento("prova",1024,2);
        }
        catch(Exception e){}
    }
    public SwapSegmentataTest(String testName) {
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
     * Test of aggiungi method, of class SwapSegmentata.
     */
    public void testAggiungi1() throws Exception {
        //Caso 1: Spazio disponibile in memoria
        System.out.println("aggiungi Caso 1: Spazio disponibile in memoria");
        FrameMemoria seg = s1;
        FrameMemoria spazio = null;
        SwapSegmentata instance = memoria;
        instance.aggiungi(seg, spazio);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    public void testAggiungi2() throws Exception {
        memoria.aggiungi(s1,null);
        memoria.aggiungi(s2,null);
        memoria.aggiungi(s1,null);
        memoria.aggiungi(s1,null);
        //Caso 2: Memoria piena
        System.out.println("aggiungi Caso 2: Memoria piena");
        try{
            memoria.aggiungi(s1, null);
        }
        catch(MemoriaEsaurita e){}
        catch(Exception e){fail("The test case is a prototype.");}
        // TODO review the generated test code and remove the default call to fail.
        
        //Resetto la memoria
        memoria=new SwapSegmentata(conf);

    }

    /**
     * Test of rimuovi method, of class SwapSegmentata.
     */
    public void testRimuovi1() {
        //Caso 1: Segmento non presente in Swap
        System.out.println("rimuovi Caso 1: Segmento non presente in Swap");
        FrameMemoria seg = s1;
        SwapSegmentata instance = memoria;
        boolean expResult = false;
        boolean result = instance.rimuovi(seg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    public void testRimuovi2() throws Exception{
        memoria.aggiungi(s1, null);
        //Caso 2: Segmento presente in Swap
        System.out.println("rimuovi Caso 2: Segmento presente in Swap");
        FrameMemoria seg = s1;
        SwapSegmentata instance = memoria;
        boolean expResult = true;
        boolean result = instance.rimuovi(seg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new SwapSegmentata(conf);
    }

    /**
     * Test of liberaMemoria method, of class SwapSegmentata.
     */
    public void testLiberaMemoria1() {
        //Caso 1: Swap non contiene segmenti riferiti dal processo
        System.out.println("liberaMemoria Caso 1: Swap non contiene segmenti riferiti dal processo");
        int idProcesso = 0;
        SwapSegmentata instance = memoria;
        instance.liberaMemoria(idProcesso);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    public void testLiberaMemoria2() throws Exception{
        memoria.aggiungi(s3, null);
        //Caso 2: Swap contiene segmenti riferiti dal processo
        System.out.println("liberaMemoria Caso 2: Swap contiene segmenti riferiti dal processo");
        int idProcesso = 2;
        SwapSegmentata instance = memoria;
        instance.liberaMemoria(idProcesso);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new SwapSegmentata(conf);
    }

    /**
     * Test of cerca method, of class SwapSegmentata.
     */
    public void testCerca1() {
        //Caso 1: Swap non contiene il segmento cercato
        System.out.println("cerca Caso 1: Swap non contiene il segmento cercato");
        FrameMemoria seg = s2;
        SwapSegmentata instance = memoria;
        boolean expResult = false;
        boolean result = instance.cerca(seg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    public void testCerca2() throws Exception{
        memoria.aggiungi(s2, null);
        //Caso 2: Swap contiene il segmento cercato
        System.out.println("cerca Caso 2: Swap contiene il segmento cercato");
        FrameMemoria seg = s2;
        SwapSegmentata instance = memoria;
        boolean expResult = true;
        boolean result = instance.cerca(seg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new SwapSegmentata(conf);
    }

}
