/*
 * Azienda: Stylosoft
 * Nome file: PCB.java
 * Package: logic.scheduler
 * Autore: Daniele Bonaldo
 * Data: 21/02/2008
 * Versione: 1.01
 * Licenza: open-source
 * Registro delle modifiche: 
 * v.1.01 (01/02/2006): Codifica di metodi e costruttori.
 * v.1.00 (31/01/2006): Scrittura documentazione.
 */

package logic.schedulazione;

import logic.parametri.Processo;

/**
 * Classe che rappresenta un process control block, ovvero un particolare
 * oggetto che indicheremo con l'acronimo PCB e che si occupa di mantenere
 * memorizzati tutti i dati di un processo che evolvono col trascorrere della
 * simulazione. Istanze di questa classe percio' si occupano di memorizzare i
 * dati dinamici relativi a dei processi. Ogni processo sara' associato ad
 * un'istanza di questa classe e ogni istanza di questa classe sara' associata ad
 * uno ed un solo processo. Dunque dall'istanza di PCB potra' identificare in
 * modo univoco il processo associato, risalendo ai dati dinamici che lo
 * caratterizzano.
 * 
 * @author Daniele Bonaldo
 * @version 1.01
 */
public class PCB {
	/**
	 * Campo dati che si occupa di mantenere un riferimento al processo a cui il
	 * PCB sara' associato.
	 */
	private Processo processo;

	/**
	 * Metodo che permette d'accedere al valore del campo dati processo di un
	 * PCB.
	 * 
	 * @return un reference di tipo Processo al processo associato al PCB.
	 */
	public Processo getRifProcesso() {
		return processo;
	}

	/**
	 * Campo dati contenente per ogni unita' di tempo il numero di istanti per
	 * cui il processo associato al PCB ha eseguito. Viene inizializzato
	 * automaticamente a zero in fase di creazione dei PCB perche' un PCB appena
	 * creato non e' sicuramente mai entrato in esecuzione.
	 */
	private int istantiEseguiti = 0;

	/**
	 * Ritorna il numero di istanti eseguiti dal processo associato al PCB in
	 * questione.
	 * 
	 * @return il numero di istanti per cui il processo ha eseguito.
	 */
	public int getIstantiEseguiti() {
		return istantiEseguiti;
	}

	/**
	 * Campo dati contenente in ogni momento il numero totale di istanti
	 * d'esecuzione che mancano al processo associato al PCB per terminare la
	 * sua esecuzione.
	 */
	private int istantiDaEseguire;

	/**
	 * Metodo che ritorna il numero di istanti per cui il processo deve ancora
	 * eseguire prima di terminare.
	 * 
	 * @return il numero di istanti mancanti al termine dell'esecuzione, in
	 *         particolare ritorna zero se il processo associato al PCB ha
	 *         terminato di eseguire.
	 */
	public int getIstantiDaEseguire() {
		return istantiDaEseguire;
	}

	/**
	 * Metodo che incrementa gli istantiEseguiti e decrementa gli 
         * istantiDaEseguire di un'unita' di tempo.
	 * 	 
	 */
	public void incIstantiEseguiti() {
		istantiEseguiti ++; // eseguo l'incremento del numero di istanti
		// eseguiti
		istantiDaEseguire --; // eseguo il decremento del numero di
		// istanti che mi restano da eseguire
		
	}

	/**
	 * Costruttore si occupa di creare un'istanza della classe PCB. In esso
	 * vengono inizializzati i campi dati processo, col processo d'associare al
	 * PCB, e istantiDaEseguire, col valore totale d'istanti d'esecuzione del
	 * processo. Gli altri campi della classe PCB vengono definiti con
	 * inizializzazioni automatiche.
	 * 
	 * @param processo
	 *            il processo d'associare al PCB che sto creando e da cui
	 *            ricavare il tempo totale di esecuzione con cui inizializzare
	 *            il campo dati istantiDaEseguire
	 */
	public PCB(Processo processo) {
		this.processo = processo;
		this.istantiDaEseguire = this.processo.getTempoEsecuzione();
	}

	


	/**
	 * Metodo che permette di verificare se due oggetti di tipo PCB sono uguali.
	 * Due oggetti PCB sono uguali solo se si riferiscono allo stesso processo.
	 * Dunque il metodo al suo interno reinvoca il metodo equals definito per la
	 * classe Processo
	 * 
	 * @param o
	 *            l'oggetto con cui voglio confrontare il pcb sul quale invoco
	 *            il metodo equals.
	 * @return true se i due oggetti di tipo PCB sono uguali, false altrimenti.
	 */
	public boolean equals(Object o) {
		/*
		 * Controllo se l'oggetto passato ha TD sottotipo di PCB. Se e' così
		 * faccio RTTI a PCB e eseguo l'equals sul campo dati processo. Due PCB
		 * sono uguali solo se si riferiscono allo stesso oggetto
		 */
		if (o instanceof PCB) {
			/*
			 * casto a PCB per accedere alle funzionalit� proprie della classe
			 */
			PCB pcb = (PCB) o;
			/* invoco l'equals di processo e ritorno il risultato */
			return processo.equals(pcb.processo);
		}
		/*
		 * l'oggetto o non ha TD sottotipo di PCB. Cio' significa che esso puo'
		 * anche essere nullo. In entrambi i casi i due oggetti che voglio
		 * confrontare sono sicuramente diversi e dunque ritorno false.
		 */
		else{
			return false;
                }
	}

}
