/*
 * Azienda: Stylosoft
 * Nome file: Accesso.java
 * Package: logic.parametri
 * Autore: Daniele Bonaldo
 * Data: 20/02/2008
 * Versione: 1.06
 * Licenza: open-source
 * Registro delle modifiche:
 * v.1.06 (16/02/2006): Adeguamento del codice allo standard aziendale.
 * v.1.05 (13/02/2006): Classe resa serializzabile per il salvataggio su file.
 * v.1.04 (11/02/2006): Modifica metodo richiestaAcceso() e relativa documentazione.
 * v.1.03 (06/02/2006): Correzione del codice.
 * v.1.02 (02/02/2006): Controllo tra Specifica Tecnica e Codice.
 * v.1.01 (01/02/2006): Codifica di metodi e costruttori.
 * v.1.00 (31/01/2006): Scrittura documentazione.
 */

package logic.parametri;

import java.io.*;
import logic.gestioneMemoria.FrameMemoria;

/**
 * Classe i cui oggetti rappresentano una richiesta d'accesso ad una risorsa da
 * parte di un processo. Essi sono costituiti dalla risorsa a cui il processo
 * vuole accedere, dall'istante in cui la richiesta deve essere effettuata e
 * dalla durata per cui il processo deve poter accedere alla risorsa richiesta.
 * 
 * @author Daniele Bonaldo
 * @version 1.06
 */
public class Accesso implements Serializable {	

	/**
	 * Campo dati che contiene l'istante d'esecuzione in cui un processo deve
	 * effettuare la richiesta d'accesso ad una risorsa.
	 */
	private int istanteRichiesta;

	/**
	 * Metodo che ritorna l'istante d'esecuzione del processo in cui avviene la
	 * richiesta di accesso ad una risorsa.
	 * 
	 * @return L'istante d'esecuzione in cui si verifica una richiesta d'accesso
	 *         ad una risorsa da parte di un processo.
	 */
	public int getIstanteRichiesta() {
		return istanteRichiesta;
	}

	/**
	 * Campo dati contenente il tempo totale per cui un processo richiede di
	 * poter accedere ad una particolare risorsa.
	 */
	private int durata;

	/**
	 * Metodo che ritorna il tempo totale per cui un processo richiede l'accesso
	 * ad una particolre risorsa.
	 * 
	 * @return Il numero di istanti d'esecuzione per cui il processo ha bisogno
	 *         d'accedere ad una risorsa.
	 */
	public int getDurata() {
		return durata;
	}

	/**
	 * Campo dati contenente la risorsa a cui la richiesta d'accesso si
	 * riferisce.
	 */
	private FrameMemoria risorsa;

	/**
	 * Metodo che ritorna la risorsa a cui la richiesta d'accesso di riferisce.
	 * 
	 * @return La risorsa a cui il processo vuole accedere.
	 */
	public FrameMemoria getRisorsa() {
		return risorsa;
	}

	/**
	 * Costruttore che si occupa di creare un oggetto rappresentante tutte le
	 * informazioni che riguardano una richiesta d'accesso ad un risorsa da
	 * parte di un processo che andrà a contenerlo.
	 * 
	 * @param risorsa
	 *            La risorsa a cui il processo richiede di poter accedere
	 * @param istanteRichiesta
	 *            L'istante di tempo in cui la richiesta d'accesso deve essere
	 *            effettuata.
	 * @param durata
	 *            Il numero di istanti di tempo per cui il processo richiede di
	 *            dover accedere a risorsa
	 */
	public Accesso(FrameMemoria risorsa, int istanteRichiesta, int durata) {
		this.risorsa = risorsa;
		this.istanteRichiesta = istanteRichiesta;
		this.durata = durata;
	}
        
        /**
	 * Costruttore che si occupa di creare un oggetto rappresentante tutte le
	 * informazioni che riguardano una richiesta d'accesso ad un risorsa da
	 * parte di un processo che andrà a contenerlo.
         * Non necessita di specificare la durata della richiesta intendendola 
         * uguale a 1.
	 * 
	 * @param risorsa
	 *            La risorsa a cui il processo richiede di poter accedere
	 * @param istanteRichiesta
	 *            L'istante di tempo in cui la richiesta d'accesso deve essere
	 *            effettuata.
	 */
	public Accesso(FrameMemoria risorsa, int istanteRichiesta) {
		this(risorsa, istanteRichiesta, 1);
	}
}
