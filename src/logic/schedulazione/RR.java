package logic.schedulazione;

/*
 * Azienda: Stylosoft
 * Nome file: RR.java
 * Package: logic.scheduler
 * Autore: Daniele Bonaldo
 * Data: 20/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche: *
 *  - v.1.1 (20/02/2008): Correzioni in esegui().
 *  - v.1.0 (18/02/2008): Creazione e scrittura documentazione.
 */


import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Classe concreta, estende la classe astratta ConQuanti e implementa
 * l'interfaccia PoliticaOrdinamentoProcessi. Questa classe ha il compito di 
 * simulare una politica di ordinamento per sistemi Interattivi. 
 * La politica Round Robin, è una politica con ordinamento a quanti, ovvero ogni 
 * processo esegue per al più un quanto di tempo alla volta. La struttura dati 
 * su cui vengono mantenuti i processi pronti diventa quindi una lista circolare 
 * di processi. L'inserimento viene sempre fatto in coda a questa lista, mentre 
 * l'estrazione dalla testa.
 * 
 * 
 * @author Bonaldo Daniele
 * @version 1.1 20/02/2008
 */

public class RR extends ConQuanti implements PoliticaOrdinamentoProcessi {

	/**
	 * Coda dei Processi pronti.
	 */
	protected LinkedList codaPronti;

	/**
	 * Riferimento dell'ultimo PCB mandato in esecuzione. Servirà nel metodo
	 * esegui e, nel caso ultimoEseguito e il PCB in esecuzione fossero
	 * differenti, deve essere resettato il contatore.
	 */
	private PCB ultimoEseguito = null;

	/**
	 * Riferimento allo scheduler.
	 */
	protected Scheduler scheduler = null;

	/**
	 * Costruttore di default. Richiama il costruttore ad un parametro, con
	 * valore 3. Assegna di default un valore di quanto uguale a 3.
	 */
	public RR() {

		this(3);
	}

	/**
	 * Costruttore, riceve come parametro un intero, che indica il valore di
	 * time slice scelto.
	 * 
	 * @param timeSlice
	 *            indica quanto deve durare un quanto di tempo.
	 */
	public RR(int timeSlice) {

		super(timeSlice);

		codaPronti = new LinkedList();
	}

	/**
	 * 
	 * Imposta il campo dato privato di tipo scheduler.
	 * 
	 * @param scheduler
	 *            scheduler riferimento per impostare il campo dati privato
	 *            della classe.
	 */
	public void setScheduler(Scheduler scheduler) {

		this.scheduler = scheduler;
	}

	/**
	 * Inserisce un nuovo PCB nella coda dei pronti rispettando i comportamento
	 * della politica.
	 * 
	 * @param pronto
	 *            E' il PCB che rappresenta il processo appena arrivato nella
	 *            simulazione.
	 */
	public void inserisci(PCB pronto) {

		codaPronti.add(pronto); // inserimento in coda

	}

	/**
	 * Simula l'esecuzione del processo che "possiede" la CPU.         * 
	 *
	 */
	public void esegui() {
		// Ottengo il PCB in esecuzione
		PCB inEsecuzione = scheduler.getPCBCorrente();

		// Se l'ultimo PCB eseguito, è diverso da quello in esecuzione il
		// contatore deve essere resettato.
		// altrimenti avrei una inconsistenza.
		if (!(inEsecuzione.equals(ultimoEseguito))) {
			this.reset();
		}
		// Trovo il minore tra istantiSicuri e le unità di tempo ancora
		// disponibili al PCB
		int rimanenzaQuanto = getTimeSlice() - getContatore();
		int limite;
		if (rimanenzaQuanto <= 1) {
			limite = rimanenzaQuanto;
		} else {
			limite = 1;
		}

		// incremento il contatore delle unità di tempo individuate
		// precedentemente
		setContatore(getContatore() + limite);

		// istante riferito al tempo in cui il processo ha eseguito,
		// calcolato dallo scheduler.
		scheduler.incrementaTempoScheduler();

		// Se il PCB ha terminato il quanto a sua disposizione, viene tolto,
		// rimesso nella codaPronti e resettato il contatore.
		if (getContatore() == getTimeSlice()
				&& !(inEsecuzione.getIstantiDaEseguire() == 0)) {
			scheduler.rimuoviPCBCorrente();
			this.inserisci(inEsecuzione);
			this.reset();
		} else {
			// tengo traccia dell'ultimo PCB eseguito.
			ultimoEseguito = inEsecuzione;
		}

	}

	/**
	 * Estrae e ritorna il PCB che deve andare in esecuzione.
	 * 
	 * @return Ritorna il processo che deve andare ad eseguire.
	 */
	public PCB estrai() {

		if (codaPronti.size() > 0) {

			return (PCB) codaPronti.removeFirst();
		} else {

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
	 * Ritorna un ArrayList contenente Processo estratti dalla coda dei pronti.
	 * 
	 * @return ArrayList di Processo dei PCB nella codaPronti.
	 */
	public ArrayList getCodaPronti() {
		// inizializzo un ArrayList con la dimensione della coda.
		ArrayList stati = new ArrayList(codaPronti.size());
		for (int i = 0; i < codaPronti.size(); i++) {
			// aggiungo gli elementi della coda all'ArrayList.
			stati.add(((PCB) codaPronti.get(i)).getRifProcesso());
		}
		return stati;
	}

}
