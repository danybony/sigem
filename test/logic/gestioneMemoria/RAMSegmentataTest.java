/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.gestioneMemoria;

import java.util.LinkedList;
import java.util.Vector;
import junit.framework.TestCase;
import logic.parametri.ConfigurazioneIniziale;

/**
 *
 * @author PC
 */
public class RAMSegmentataTest extends TestCase {
    ConfigurazioneIniziale conf=null;
    RAMSegmentata memoria=null;
    Segmento s1, s2, s3;
    {
        try{
            conf= new ConfigurazioneIniziale(1,1,1,1,4096,4096,1,1,1024,new LinkedList());
            memoria=new RAMSegmentata(conf);
            s1=new Segmento("prova",1024,0);
            s2=new Segmento("prova",1024,1);
            s3=new Segmento("prova",1024,2);
        }
        catch(Exception e){}
    }
    public RAMSegmentataTest(String testName) {
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
     * Test of aggiungi method, of class RAMSegmentata.
     */
    public void testAggiungi() {
        System.out.println("aggiungi");
        FrameMemoria seg = new Segmento("prova",1024,2);
        FrameMemoria spazio = memoria.getSpazioMaggiore();
        RAMSegmentata instance = null;
        instance.aggiungi(seg, spazio);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rimuovi method, of class RAMSegmentata.
     */
    public void testRimuovi() {
        System.out.println("rimuovi");
        FrameMemoria seg = s1;
        RAMSegmentata instance = memoria;
        boolean expResult = true;
        boolean result = instance.rimuovi(seg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of liberaMemoria method, of class RAMSegmentata.
     */
    public void testLiberaMemoria() {
        System.out.println("liberaMemoria");
        int idProcesso = 0;
        RAMSegmentata instance = null;
        instance.liberaMemoria(idProcesso);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFrameLiberi method, of class RAMSegmentata.
     */
    public void testGetFrameLiberi() {
        System.out.println("getFrameLiberi");
        RAMSegmentata instance = null;
        Vector<FrameMemoria> expResult = null;
        Vector<FrameMemoria> result = instance.getFrameLiberi();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFrameOccupati method, of class RAMSegmentata.
     */
    public void testGetFrameOccupati() {
        System.out.println("getFrameOccupati");
        RAMSegmentata instance = null;
        Vector<FrameMemoria> expResult = null;
        Vector<FrameMemoria> result = instance.getFrameOccupati();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpazioMaggiore method, of class RAMSegmentata.
     */
    public void testGetSpazioMaggiore() {
        System.out.println("getSpazioMaggiore");
        RAMSegmentata instance = null;
        FrameMemoria expResult = null;
        FrameMemoria result = instance.getSpazioMaggiore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cerca method, of class RAMSegmentata.
     */
    public void testCerca() {
        System.out.println("cerca");
        FrameMemoria seg = null;
        RAMSegmentata instance = null;
        boolean expResult = false;
        boolean result = instance.cerca(seg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
