package logic.schedulazione;

/*
 * Azienda: Stylosoft
 * Nome file: RRConPriorita.java
 * Package: logic.scheduler
 * Autore: Daniele Bonaldo
 * Data: 29/02/2008
 * Versione: 1.01
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.01 (29/02/2008): Modifiche alla documentazione.
 *  - v.1.00 (22/02/2008): Creazione e scrittura documentazione.
 */

import java.util.ArrayList;
import java.util.LinkedList;

import logic.parametri.ProcessoConPriorita;

/**
 * Classe concreta, estende la classe astratta ConQuanti, e implementa
 * l'interfaccia PoliticaOrdinamentoProcessi. 
 * Questa classe ha il compito di simulare una politica di ordinamento 
 * Round Robin con priorita'. Questa e' una politica con ordinamento a quanti,
 * ovvero ogni processo esegue per al più un quanto di
 * tempo alla volta. La particolarita' di questa politica, a differenza di quella
 * Round Robin, e' che i processi vengono mantenuti in una struttura dati
 * ordinata per priorita'. Ogni processo coinvolto in questa politica, ha infatti
 * un valore di priorita' proprio, che determina in quale lista della struttura
 * dati questo deve essere inserito. L'estrazione di un PCB associato ad un
 * processo, procedera' cercando quello con priorita' più alta. Al termine della
 * sua esecuzione il PCB verra' rimesso in coda alla lista da cui e' stato
 * estratto. La politica non prevede prerilascio per priorita', quindi se durante
 * l'esecuzione di un processo con priorita' bassa, ne arrivasse uno con priorita'
 * più alta, il primo rimarrebbe in esecuzione fino al termine del quanto di
 * tempo a sua disposizione, mentre l'altro verrebbe inserito nella coda dei
 * pronti. Non capitera' che un PCB con priorita' bassa vada in esecuzione prima
 * di un PCB con priorita' alta, gia' presente nella coda dei pronti.
 * 
 * 
 * @author Daniele Bonaldo
 * @version 1.01 29/02/2008
 */

public class RRConPriorita extends ConQuanti implements PoliticaOrdinamentoProcessi {

	/**
	 * Coda dei Processi pronti.
	 */
	protected ArrayList codaPronti;

	/**
	 * Riferimento dell'ultimo PCB mandato in esecuzione. Servira' nel metodo
	 * esegui e nel caso ultimoEseguito e il PCB in esecuzione fossero
	 * differenti, caso in cui deve anche essere resettato il contatore.
	 */
	private PCB ultimoEseguito = null;

	/**
	 * Riferimento allo scheduler.
	 */
	protected Scheduler scheduler = null;

	/**
	 * Costruttore di default. Richiama il costruttore ad un parametro, con
	 * valore 3, assegnando di defualt un timeslice di 3 unita' di tempo.
	 * 
	 */
	public RRConPriorita() {
		this(3);
	}

	/**
	 * Costruttore che riceve come parametro un intero il quale indica il valore
	 * di time slice scelto.
	 * 
	 * @param timeSlice
	 *            indica quanto deve durare un quanto di tempo.
	 */
	public RRConPriorita(int timeSlice) {

		super(timeSlice);
		// creo 5 code delle priorita', una per ogni possibile livello di
		// priorita'
		codaPronti = new ArrayList(5);
		for (int i = 0; i < 5; i++) {
			codaPronti.add(i, new LinkedList());
		}
	}

	/**
	 * Inserisce un nuovo PCB nella coda dei pronti. L'inserimento viene
	 * effettuato tenendo conto della priorita' di ogni PCB.
	 * 
	 * @param pronto
	 *            E' il PCB che rappresenta il processo appena arrivato nella
	 *            simulazione.
	 */
	public void inserisci(PCB pronto) {

		ProcessoConPriorita tmp = (ProcessoConPriorita) pronto.getRifProcesso();
		int priorita = tmp.getPriorita();
		((LinkedList) codaPronti.get(priorita - 1)).add(pronto);

	}

	/**
	 * Estrae e ritorna il PCB del processo che deve andare in esecuzione.
	 * L'estrazione viene fatta cercando il PCB con priorita' maggiore
	 * all'interno della codaPronti.
	 * 
	 * @return Ritorna il PCB che deve eseguire.
	 */
	public PCB estrai() {
		PCB corrente = null;
		// La ricerca parte dall'ultimo elemento della coda, quello in cui i PCB
		// hanno priorita' maggiore.
		for (int i = codaPronti.size() - 1; i >= 0; i--) {
			if (!((LinkedList) codaPronti.get(i)).isEmpty()) {
				corrente = (PCB) ((LinkedList) codaPronti.get(i)).removeFirst();
				return corrente;
			}

		}
		return corrente;
	}

	/**
	 * Ritorna il numero di PCB presenti nella coda dei pronti.
	 * 
	 * @return Il numero di PCB nello stato di pronto.
	 */
	public int size() {
		int size = 0;
		for (int i = 0; i < codaPronti.size(); i++) {
			// somma delle size delle LinkedList associate ad ogni indice
			// dell'ArrayList.
			size = size + ((LinkedList) codaPronti.get(i)).size();
		}
		return size;
	}

	/**
	 * Ritorna un ArrayList contenente Processo estratti dalla coda dei pronti.
	 * 
	 * @return ArrayList di IProcesso dei PCB nella codaPronti.
	 */
	public ArrayList getCodaPronti() {
		ArrayList stati = new ArrayList();
		// Si scorre la codaPronti dalla fine, in tale posizione si trovano
		// infatti
		// i PCB con priorita' più alta.
		for (int i = codaPronti.size() - 1; i >= 0; i--) {
			LinkedList tmp = (LinkedList) codaPronti.get(i);
			for (int j = 0; j < tmp.size(); j++) {
				// Inserimento dei PCB nell'ArrayList da ritornare.
				stati.add(((PCB) tmp.get(j)).getRifProcesso());
			}
		}
		return stati;
	}

	/**
	 * Simula l'esecuzione del processo che "possiede" la CPU per un numero
	 * definito di unita' di tempo. Metodo invocato dallo Scheduler.
	 * 
	 */
	public void esegui() {

		// Ottengo il PCB in esecuzione
		PCB inEsecuzione = scheduler.getPCBCorrente();

		// Se l'ultimo PCB eseguito, e' diverso da quello in esecuzione il
		// contatore deve essere resettato.
		// altrimenti avrei una inconsistenza.
		if (!(inEsecuzione.equals(ultimoEseguito))) {
			this.reset();
		}

		// Trovo il minore tra istantiSicuri e le unita' di tempo ancora
		// disponibili al PCB
		int rimanenzaQuanto = getTimeSlice() - getContatore();
		int limite;
		if (rimanenzaQuanto <= 1) {
			limite = rimanenzaQuanto;
		} else {
			limite = 1;
		}

		// incremento il contatore delle unita' di tempo individuate
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
			ultimoEseguito = inEsecuzione;
		}

	}

	/**
	 * Imposta il campo dato privato di tipo scheduler.
	 * 
	 * @param scheduler
	 *            scheduler riferimento per impostare il campo dati privato
	 *            della classe.
	 */
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

}
