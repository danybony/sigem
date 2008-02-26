/*
 * Azienda: Stylosoft
 * Nome file: Accesso.java
 * Package: logic.parametri
 * Autore: Daniele Bonaldo
 * Data: 26/02/2008
 * Versione: 1.02
 * Licenza: open-source
 * Registro delle modifiche:
 * v.1.02 (01/02/2006): Codifica di metodi e costruttori. 
 * v.1.01 (01/02/2006): Codifica di metodi e costruttori.
 * v.1.00 (31/01/2006): Scrittura documentazione.
 */

package logic.parametri;

import java.io.*;
import logic.gestioneMemoria.FrameMemoria;

/**
 * Classe i cui oggetti rappresentano una richiesta d'accesso ad un FrameMemoria da
 * parte di un processo. Essi sono costituiti dal FrameMemoria a cui il processo
 * vuole accedere, dall'istante in cui la richiesta deve essere effettuata e
 * dalla durata per cui il processo deve poter accedere al FrameMemoria richiesto.
 * 
 * @author Daniele Bonaldo
 * @version 1.02
 */
public class Accesso implements Serializable {	

	/**
	 * Campo dati che contiene l'istante d'esecuzione in cui un processo deve
	 * effettuare la richiesta d'accesso ad un FrameMemoria.
	 */
	private int istanteRichiesta;

	/**
	 * Metodo che ritorna l'istante d'esecuzione del processo in cui avviene la
	 * richiesta di accesso ad un FrameMemoria.
	 * 
	 * @return L'istante d'esecuzione in cui si verifica una richiesta d'accesso
	 *         ad un FrameMemoria da parte di un processo.
	 */
	public int getIstanteRichiesta() {
		return istanteRichiesta;
	}

	
	/**
	 * Campo dati contenente il FrameMemoria a cui la richiesta d'accesso si
	 * riferisce.
	 */
	private FrameMemoria frame;

	/**
	 * Metodo che ritorna il FrameMemoria a cui la richiesta d'accesso di riferisce.
	 * 
	 * @return Ll FrameMemoria a cui il processo vuole accedere.
	 */
	public FrameMemoria getRisorsa() {
		return frame;
	}

	/**
	 * Costruttore che si occupa di creare un oggetto rappresentante tutte le
	 * informazioni che riguardano una richiesta d'accesso ad un FrameMemoria 
	 * da parte di un processo che andrà a contenerlo.
	 * 
	 * @param frame
	 *            Il FrameMemoria a cui il processo richiede di poter accedere
	 * @param istanteRichiesta
	 *            L'istante di tempo in cui la richiesta d'accesso deve essere
	 *            effettuata.	 
	 */
	public Accesso(FrameMemoria frame, int istanteRichiesta) {
		this.frame = frame;
		this.istanteRichiesta = istanteRichiesta;
	}
        
        
}
