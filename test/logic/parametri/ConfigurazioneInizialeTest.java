/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic.parametri;

import java.util.LinkedList;
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
public class ConfigurazioneInizialeTest {

    ConfigurazioneIniziale conf;
    LinkedList list = new LinkedList();
    
    public ConfigurazioneInizialeTest() {
        try {

            conf = new ConfigurazioneIniziale(
                                                100,
                                                1,
                                                2,
                                                3,
                                                512,
                                                256,
                                                1,
                                                5,
                                                4,
                                                list);
        } catch (EccezioneConfigurazioneNonValida ex) {
            fail("Costruzione test fallita per configurazione non valida");
        }
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
     * Test of getBandaBusDati method, of class ConfigurazioneIniziale.
     */
    @Test
    public void costruzioneOggetti() {
        System.out.println("Costruzione di un range di oggetti");
        LinkedList<Processo> t = new LinkedList<Processo>();
        int numeroObj = 40 , i;
        ConfigurazioneIniziale[] instance = new ConfigurazioneIniziale[numeroObj];
        try{
        for(i=0; i<numeroObj;i++){
            instance[i] = new ConfigurazioneIniziale(
                                                  i*2+1,
                                                  i%7+1,
                                                  1,
                                                  i%7+1,
                                                  i*2+1,
                                                  i*2+1,
                                                  i*5+1,
                                                  i*5+1,
                                                  i*2+1,
                                                  t);
        }
        for(i=0; i<numeroObj;i++){
            instance[i] = new ConfigurazioneIniziale(
                                                  i*2+1,
                                                  i%5+1,
                                                  2,
                                                  i%7+1,
                                                  i*2+1,
                                                  i*2+1,
                                                  i*5+1,
                                                  i*5+1,
                                                  i*2+1,
                                                  t
                                                    );
        }
        }catch(EccezioneConfigurazioneNonValida e){
            fail("Fallimento test costruzione " + numeroObj + " oggetti. ");
        }
        
    }
    
    /**
     * Test of getBandaBusDati method, of class ConfigurazioneIniziale.
     */
    @Test
    public void getBandaBusDati() {
        System.out.println("getBandaBusDati");
        ConfigurazioneIniziale instance = conf;
        int expResult = 100;
        int result = instance.getBandaBusDati();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDimensionePagina method, of class ConfigurazioneIniziale.
     */
    @Test
    public void getDimensionePagina() {
        System.out.println("getDimensionePagina");
        ConfigurazioneIniziale instance = conf;
        int expResult = 4;
        int result = instance.getDimensionePagina();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDimensioneRAM method, of class ConfigurazioneIniziale.
     */
    @Test
    public void getDimensioneRAM() {
        System.out.println("getDimensioneRAM");
        ConfigurazioneIniziale instance = conf;
        int expResult = 512;
        int result = instance.getDimensioneRAM();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDimensioneSwap method, of class ConfigurazioneIniziale.
     */
    @Test
    public void getDimensioneSwap() {
        System.out.println("getDimensioneSwap");
        ConfigurazioneIniziale instance = conf;
        int expResult = 256;
        int result = instance.getDimensioneSwap();
        assertEquals(expResult, result);
    }

    /**
     * Test of getModalitaGestioneMemoria method, of class ConfigurazioneIniziale.
     */
    @Test
    public void getModalitaGestioneMemoria() {
        System.out.println("getModalitaGestioneMemoria");
        ConfigurazioneIniziale instance = conf;
        int expResult = 2;
        int result = instance.getModalitaGestioneMemoria();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPoliticaGestioneMemoria method, of class ConfigurazioneIniziale.
     */
    @Test
    public void getPoliticaGestioneMemoria() {
        System.out.println("getPoliticaGestioneMemoria");
        ConfigurazioneIniziale instance = conf;
        int expResult = 1;
        int result = instance.getPoliticaGestioneMemoria();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPoliticaSchedulazioneProcessi method, of class ConfigurazioneIniziale.
     */
    @Test
    public void getPoliticaSchedulazioneProcessi() {
        System.out.println("getPoliticaSchedulazioneProcessi");
        ConfigurazioneIniziale instance = conf;
        int expResult = 3;
        int result = instance.getPoliticaSchedulazioneProcessi();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTempoAccessoDisco method, of class ConfigurazioneIniziale.
     */
    @Test
    public void getTempoAccessoDisco() {
        System.out.println("getTempoAccessoDisco");
        ConfigurazioneIniziale instance = conf;
        int expResult = 5;
        int result = instance.getTempoAccessoDisco();
        assertEquals(expResult, result);

    }

    /**
     * Test of getTempoContextSwitch method, of class ConfigurazioneIniziale.
     */
    @Test
    public void getTempoContextSwitch() {
        System.out.println("getTempoContextSwitch");
        ConfigurazioneIniziale instance = conf;
        int expResult = 1;
        int result = instance.getTempoContextSwitch();
        assertEquals(expResult, result);
    }

    /**
     * Test of getListaProcessi method, of class ConfigurazioneIniziale.
     */
    @Test
    public void getListaProcessi() {
        System.out.println("getListaProcessi");
        ConfigurazioneIniziale instance = conf;
        LinkedList<Processo> expResult = list;
        LinkedList<Processo> result = instance.getListaProcessi();
        assertEquals(expResult, result);
    }

}

/*
 * Cronologia dei test effettuati:
 * 
 * public void costruzioneOggetti():
 * -.descrizione: Prova di costruzione di n oggetti diversi
 *  .input: Nuovi oggetti con valori dei parametri random
 *  .output: Oggetti costruiti correttamente
 *  .esito: Positivo
 * 
 * public void getBandaBusDati():
 * -.descrizione: Test su una data configurazione in input dei metodi di 
 *                interrogazione dei campi dati
 *  .input: Una configurazione di prova
 *  .output: Il campo dati della configurazione di prova corrispondente
 *  .esito: Positivo
 * 
 * 
 * public void getDimensionePagina():
 * -.descrizione: Test su una data configurazione in input dei metodi di 
 *                interrogazione dei campi dati
 *  .input: Una configurazione di prova
 *  .output: Il campo dati della configurazione di prova corrispondente
 *  .esito: Positivo
 * 
 * public void getDimensioneRAM():
 * -.descrizione: Test su una data configurazione in input dei metodi di 
 *                interrogazione dei campi dati
 *  .input: Una configurazione di prova
 *  .output: Il campo dati della configurazione di prova corrispondente
 *  .esito: Positivo
 * 
 * public void getDimensioneSwap():
 * -.descrizione: Test su una data configurazione in input dei metodi di 
 *                interrogazione dei campi dati
 *  .input: Una configurazione di prova
 *  .output: Il campo dati della configurazione di prova corrispondente
 *  .esito: Positivo
 * 
 * public void getModalitaGestioneMemoria():
 * -.descrizione: Test su una data configurazione in input dei metodi di 
 *                interrogazione dei campi dati
 *  .input: Una configurazione di prova
 *  .output: Il campo dati della configurazione di prova corrispondente
 *  .esito: Positivo
 * 
 * public void getPoliticaGestioneMemoria():
 * -.descrizione: Test su una data configurazione in input dei metodi di 
 *                interrogazione dei campi dati
 *  .input: Una configurazione di prova
 *  .output: Il campo dati della configurazione di prova corrispondente
 *  .esito: Positivo
 * 
 * public void getPoliticaSchedulazioneProcessi():
 * -.descrizione: Test su una data configurazione in input dei metodi di 
 *                interrogazione dei campi dati
 *  .input: Una configurazione di prova
 *  .output: Il campo dati della configurazione di prova corrispondente
 *  .esito: Positivo
 * 
 * public void getTempoAccessoDisco():
 * -.descrizione: Test su una data configurazione in input dei metodi di 
 *                interrogazione dei campi dati
 *  .input: Una configurazione di prova
 *  .output: Il campo dati della configurazione di prova corrispondente
 *  .esito: Positivo
 * 
 * public void getTempoContextSwitch():
 * -.descrizione: Test su una data configurazione in input dei metodi di 
 *                interrogazione dei campi dati
 *  .input: Una configurazione di prova
 *  .output: Il campo dati della configurazione di prova corrispondente
 *  .esito: Positivo
 * 
 * public void getListaProcessi():
 * -.descrizione: Test su una data configurazione in input dei metodi di 
 *                interrogazione dei campi dati
 *  .input: Una configurazione di prova
 *  .output: Il campo dati della configurazione di prova corrispondente
 *  .esito: Positivo
 * 
 */
