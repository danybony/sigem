/*
 * Azienda: Stylosoft
 * Nome file: ProcessoConPriorita.java
 * Package: logic.parametri
 * Autore: Daniele Bonaldo
 * Data: 22/02/2008
 * Versione: 1.00
 * Licenza: open-source
 * Registro delle modifiche:
 * v.1.00 (03/02/2006): Scrittura della documentazione e implementazione dei metodi.
 */

package logic.parametri;

/**
 * La classe ProcessoConPriorita rappresenta i processi che, oltre ai dati
 * comuni ad ogni processo ed ereditati dunque dalla classe Processo, sono
 * caratterizzati dall'avere una certa priorità, la quale non si esclude possa
 * anche cambiare nel corso dell'evoluzione della simulazione.
 * 
 * @author 22/02/2008
 * @version 1.00
 */
public class ProcessoConPriorita extends Processo {
	/** Necessario per il Serializable.*/
	private static final long serialVersionUID = 201L;

	/** Campo dati contenente la priorità associata al processo */
	private int priorita;

	/**
	 * Metodo che ritorna il valore di priorità associato al processo.
	 * 
	 * @return La priorità associata al processo.
	 */
	public int getPriorita() {
		return priorita;
	}

	/**
	 * Metodo che si occupa di definire la priorità da associare ad un processo.
	 * Viene invocato ogni volta in cui la priorità di un processo dovesse
	 * cambiare col proseguimento della simulazione.
	 * 
	 * @param priorita
	 *            La priorità da associare al processo.
	 */
	public void setPriorita(int priorita) {
		this.priorita = priorita;
	}

	/**
	 * Costruttore di processi con priorità. Esso si occupa di settare il campo
	 * dati priorità e delega tutto il resto al costruttore della classe
	 * Processo
	 * 
	 * @param nome
	 *            Il nome da assegnare al processo
	 * @param tarrivo
	 *            Il tempo in cui un processo diventa attivo
	 * @param tesecuzione
	 *            Il tempo totale d'esecuzione di un processo
	 * @param priorita
	 *            La priorità d'associare al processo
	 */
	public ProcessoConPriorita(String nome, int tarrivo, int tesecuzione,
			int priorita) {
		super(nome, tarrivo, tesecuzione);
		this.priorita = priorita;
	}
}
