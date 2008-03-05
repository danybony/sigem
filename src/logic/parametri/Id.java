/*
 * Azienda: Stylosoft
 * Nome file: Id.java
 * Package: parametri
 * Autore: Daniele Bonaldo
 * Data: 20/02/2008
 * Versione: 1.00
 * Licenza: open-source
 * Registro delle modifiche:
 * v.1.00 (20/02/2008): Definizione della classe e della documentazione.
 */

package logic.parametri;

/**
 * Classe che si occupa di generare gli identificatori univoci da asseganre ai
 * processi.
 * 
 * @author Daniele Bonaldo
 * @version 1.00
 */
public class Id {
	/**
	 * Campo dati statico che conta le istanze degli oggetti di tipo Id
	 */
	private static int counter = 0;

	/**
	 * Metodo che si occupa di ritornare un intero rappresentante un nuovo id.
	 * @return Un nuovo identificatore univoco
	 */
	public final static int returnNewId() {
		counter++;
		return counter;
	};

	/**
	 * Metodo che si occupa di resettare il campo dati counter in modo da far
	 * ripartire l'asseganzione degli id da zero.
	 */
	public final static void resetCounter() {
		counter = 0;
	}
}
