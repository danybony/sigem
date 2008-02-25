package logic.schedulazione;

/* Azienda: Stylosoft
 * Nome file: Priorita.java
 * Package: scheduler
 * Autore: Daniele Bonaldo
 * Data: 22/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.0 (22/02/2008): Creazione e scrittura documentazione.
 */


import java.util.*;
import logic.parametri.*;

/**
 * Questa classe implementa la politica di ordinamento con priorita', politica che richiede 
 * che i PCB da gestire siano associati ad oggetti di tipo ProcessoConPriorita, 
 * in quanto l'ordinamento della coda dei pronti si basa sulla priorita'. La coda dei PCB
 * pronti e' ordinata per priorita' decrescente e viene estratto il PCB con
 * priorita' maggiore.
 * 
 * @author Bonaldo Daniele
 * @version 1.0 22/02/2006
 */

public class Priorita implements PoliticaOrdinamentoProcessi {

	/**
	 * Coda dei processi pronti, è ordinata per tempo priorità decrescente.
	 */
	protected LinkedList codaPronti;

	/**
	 * Riferimento allo scheduler.
	 */
	protected Scheduler scheduler = null;

	/**
	 * Costruttore, inizializza la struttura dati utilizzata per rappresentare
	 * la coda dei processi pronti per l'esecuzione. La coda sarà inizialmente
	 * vuota.
	 */
	public Priorita() {

		codaPronti = new LinkedList();

	}

	/**
	 * Questo metodo permette di impostare lo scheduler cui la politica si
	 * riferisce.
	 * 
	 * @param scheduler
	 *            Lo scheduler associato.
	 */
	public void setScheduler(Scheduler scheduler) {

		this.scheduler = scheduler;

	}

	/**
	 * Il metodo esegui, si occupa di far avanzare il tempo dello scheduler e
	 * quindi lo stato della simulazione.
	 * Prende come parametro un valore di tipo intero, che indica per quanto può
	 * avanzare l'esecuzione, senza che si verifichi qualche evento, per il
	 * quale lo scheduler deve intervenire. Ritorna un oggetto di tipo Istante,
	 * calcolato sull'intervallo di tempo eseguito.
	 * 
	 */
	public void esegui() {

	    /* 
	     * Incrementa il tempo dello scheduler e
	     * quindi lo stato della simulazione
	     */
		scheduler.incrementaTempoScheduler();

	}

	/**
	 * Metodo che effettua l'inserimento di un PCB nella coda dei processi
	 * pronti. L'inserimento mantiene la coda ordinata per priorita'
	 * decrescente.
	 * 
	 * @param pronto
	 *            E' il PCB che rappresenta il processo da inserire.
	 */

	public void inserisci(PCB pronto) {

		boolean inserito = false;

		ProcessoConPriorita proc = (ProcessoConPriorita) pronto.getRifProcesso(), tmp = null;

		/* Inserimento mantenendo l'ordine di priorita' decrescente */
		for (int i = 0; i < codaPronti.size() && !inserito; i++) {

			tmp = (ProcessoConPriorita) ((PCB) codaPronti.get(i))
					.getRifProcesso();

			if (proc.getPriorita() > tmp.getPriorita()) {

				/* Inserimento del processo */
				codaPronti.add(i, pronto);

				inserito = true;

			}
			
		}
		
		if (!inserito) {
			
			/* Il processo ha priorita' minore di quella di tutti i processi pronti.
			 * Per cui si inserisce il nuovo processo in coda. */
			codaPronti.addLast(pronto);

		}

	}

	/**
	 * Estrae il PCB che deve eseguire. In particolare questo e' il primo della
	 * coda, ossia il PCB con priorita' maggiore.
	 * 
	 * @return Ritorna, se presente, il PCB che ha priorita' maggiore,
	 *         altrimenti ritorna null.
	 */
	public PCB estrai() {

		if (codaPronti.size() > 0) {

			/* Si estrae in testa */
			return (PCB) codaPronti.removeFirst();

		} else {
			
			/* La coda e' vuota. */
			return null;

		}
		
	}

	/**
	 * Ritorna il numero di PCB presenti nella coda dei pronti.
	 * 
	 * @return Il numero di PCB nello stato di pronto.
	 */
	public int size() {
		
		return codaPronti.size();
	
	}

	/**
	 * Inserisce in un ArrayList, gli IProcesso dei PCB nella
	 * codaPronti, mantenendo l'ordine con cui dovrebbero eseguire.
	 * 
	 * @return Ritorna un ArrayList di Processi.	 
	 */
	public ArrayList getCodaPronti() {

		ArrayList pronti = new ArrayList(codaPronti.size());

		for (int i = 0; i < codaPronti.size(); i++) {

			pronti.add(((PCB) codaPronti.get(i)).getRifProcesso());

		}

		return pronti;

	}

} 
