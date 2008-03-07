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
    public void testAggiungi1() {
        //Caso 1: RAM vuota
        System.out.println("aggiungi Caso 1: RAM vuota");
        FrameMemoria seg = new Segmento("prova",1024,2);
        FrameMemoria spazio = memoria.getSpazioMaggiore();
        RAMSegmentata instance = memoria;
        instance.aggiungi(seg, spazio);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }
    
    public void testAggiungi2() throws Exception {
        memoria.aggiungi(s1,memoria.getSpazioMaggiore());
        //Caso 2: RAM che contiene già un segmento
        System.out.println("aggiungi Caso 2: RAM che contiene già un segmento");
        FrameMemoria seg = new Pagina("prova",1024,0);
        RAMSegmentata instance = memoria;
        instance.aggiungi(seg,memoria.getSpazioMaggiore());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }

    /**
     * Test of rimuovi method, of class RAMSegmentata.
     */
    public void testRimuovi1() {
        //Caso 1: RAM vuota
        System.out.println("rimuovi Caso 1: RAM vuota");
        FrameMemoria seg = s1;
        RAMSegmentata instance = memoria;
        boolean expResult = false;
        boolean result = instance.rimuovi(seg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }
    
    public void testRimuovi2() {
        memoria.aggiungi(s2,memoria.getSpazioMaggiore());
        //Caso 2: La RAM contiene il segmento
        System.out.println("rimuovi Caso 2: La RAM contiene il segmento");
        FrameMemoria seg = s2;
        RAMSegmentata instance = memoria;
        boolean expResult = true;
        boolean result = instance.rimuovi(seg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }

    /**
     * Test of liberaMemoria method, of class RAMSegmentata.
     */
    public void testLiberaMemoria1() {
        //Caso 1: RAM non contiene nessun segmento riferito dal processo
        System.out.println("liberaMemoria Caso 1: RAM non contiene nessun segmento riferito dal processo");
        int idProcesso = 0;
        RAMSegmentata instance = memoria;
        instance.liberaMemoria(idProcesso);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }
    
    public void testLiberaMemoria2() {
        memoria.aggiungi(s3,memoria.getSpazioMaggiore());
        //Caso 2: RAM contiene il segmento riferito dal processo
        System.out.println("liberaMemoria Caso 2: RAM contiene il segmento riferito dal processo");
        int idProcesso = 2;
        RAMSegmentata instance = memoria;
        instance.liberaMemoria(idProcesso);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }

    /**
     * Test of getFrameLiberi method, of class RAMSegmentata.
     */
    public void testGetFrameLiberi1() {
        //Caso 1: RAM vuota
        System.out.println("getFrameLiberi Caso 1: RAM vuota");
        if(memoria.getFrameLiberi().get(0).getDimensione()!=4096)
            fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }
    
    public void testGetFrameLiberi2() {
        memoria.aggiungi(s1,memoria.getSpazioMaggiore());
        memoria.aggiungi(s1,memoria.getSpazioMaggiore());
        memoria.aggiungi(s2,memoria.getSpazioMaggiore());
        memoria.aggiungi(s3,memoria.getSpazioMaggiore());
        //Caso 2: RAM piena
        System.out.println("getFrameLiberi Caso 2: RAM piena");
        if(!memoria.getFrameLiberi().isEmpty())
            fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }

    /**
     * Test of getFrameOccupati method, of class RAMSegmentata.
     */
    public void testGetFrameOccupati1() {
        //Caso 1: RAM non contiene segmenti
        System.out.println("getFrameOccupati Caso 1: RAM non contiene segmenti");
        if(!memoria.getFrameOccupati().isEmpty())
            fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }
    
    public void testGetFrameOccupati2() {
        memoria.aggiungi(s1, memoria.getSpazioMaggiore());
        //Caso 2: RAM contiene un segmento
        System.out.println("getFrameOccupati Caso 2: RAM contiene un segmento");
        if(memoria.getFrameOccupati().size()!=1)
            fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }

    /**
     * Test of getSpazioMaggiore method, of class RAMSegmentata.
     */
    public void testGetSpazioMaggiore1() {
        //Caso 1: RAM vuota
        System.out.println("getSpazioMaggiore Caso 1: RAM vuota");
        if(memoria.getSpazioMaggiore().getDimensione()!=4096)
            fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }
    
    public void testGetSpazioMaggiore2() {
        memoria.aggiungi(s1,memoria.getSpazioMaggiore());
        memoria.aggiungi(s1,memoria.getSpazioMaggiore());
        memoria.aggiungi(s2,memoria.getSpazioMaggiore());
        memoria.aggiungi(s3,memoria.getSpazioMaggiore());
        //Caso 2: RAM piena
        System.out.println("getSpazioMaggiore Caso 2: RAM piena");
        if(memoria.getSpazioMaggiore().getDimensione()!=0)
            fail("The test case is a prototype.");
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }

    /**
     * Test of cerca method, of class RAMSegmentata.
     */
    public void testCerca1() {
        //Caso 1: RAM vuota
        System.out.println("cerca Caso 1: RAM vuota");
        FrameMemoria seg = s1;
        RAMSegmentata instance = memoria;
        boolean expResult = false;
        boolean result = instance.cerca(seg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.")
    }
    
    public void testCerca2() {
        memoria.aggiungi(s1, memoria.getSpazioMaggiore());
        //Caso 2: RAM contiene il segmento cercato
        System.out.println("cerca Caso 2: RAM contiene il segmento cercato");
        FrameMemoria seg = s1;
        RAMSegmentata instance = memoria;
        boolean expResult = true;
        boolean result = instance.cerca(seg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.")
        
        //Resetto la memoria
        memoria=new RAMSegmentata(conf);
    }

}
