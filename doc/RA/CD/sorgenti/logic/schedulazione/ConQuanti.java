package logic.schedulazione;

/*
 * Azienda: Stylosoft
 * Nome file: ConQuanti.java
 * Package: logic.scheduler
 * Autore: Daniele Bonaldo
 * Data: 20/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.1 (20/02/2008): Aggiunta la descrizione dei parametri nella javadoc.
 *  - v.1.0 (18/02/2008): Creazione e scrittura documentazione.
 */

/**
 * Classe astratta che implementa l'interfaccia PoliticaOrdinamento. Lo scopo di
 * questa classe e' quello di definire una ulteriore caratteristica del
 * comportamento di una politica di ordinamento concreta. Una politica con
 * quanti stabilisce un intervallo di tempo (detto time slice) durante il quale
 * un processo puo' eseguire. Alla scadenza del quanto di tempo, se il processo
 * non ha finito la sua esecuzione esso potra' essere re-inserito nella coda dei
 * processi pronti oppure continuare con la sua esecuzione a seconda di cosa le
 * classi concrete che implementano la presente prevedranno di fare.
 * 
 * @author Daniele Bonaldo
 * @version 1.1 20/02/2008
 */
public abstract class ConQuanti implements PoliticaOrdinamentoProcessi {

	/**
	 * Costruttore, riceve come parametro un intero, che indica il valore di
	 * time slice della politica.
	 * 
	 * @param timeSlice
	 *            � il time slice della politica.
	 */
	public ConQuanti(int timeSlice) {
		this.timeSlice = timeSlice;
	}

	/**
	 * Campo dato di tipo intero che indica per ogni processo quante unita' di
	 * tempo del quanto a sua disposizione sono state sfruttate. Tale valore non
	 * potra' mai essere superiore al valore del timeSlice e deve essere
	 * aggiornato a seconda del numero di istanti per cui il processo ha
	 * effettivamente eseguito.
	 */
	private int contatore;

	/**
	 * Campo dato di tipo intero corrispondente al quanto impostato per la
	 * politica di ordinamento.
	 */
	private int timeSlice = 0;

	/**
	 * Azzera il valore del contatore dei quanti di tempo sfruttati dal PCB in
	 * esecuzione.
	 */
	public void reset() {
		contatore = 0;
	}

	/**
	 * Ritorna il valore del campo dato timeSlice.
	 * 
	 * @return Ritorna il valore di timeSlice.
	 */
	public int getTimeSlice() {
		return timeSlice;
	}

	/**
	 * Setta il valore di time slice.
	 * 
	 * @param timeSlice
	 *            intero che rappresenta il valore di timeSlice.
	 */
	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}

	/**
	 * Ritorna il valore del contatore che si occupa di quante unit� di tempo,
	 * rispetto al timeSlice sono state sfruttate dal processo in esecuzione.
	 * 
	 * @return Ritorna il valore del contatore.
	 */
	public int getContatore() {
		return contatore;
	}

	/**
	 * Setta il valore del contatore.
	 * 
	 * @param contatore
	 *            il numero di istanti eseguiti.
	 */
	public void setContatore(int contatore) {
		this.contatore = contatore;
	}

}
