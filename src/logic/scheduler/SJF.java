package logic.scheduler;

/*
 * Azienda: Stylosoft
 * Nome file: SJF.java
 * Package: scheduler
 * Autore: Daniele Bonaldo
 * Data: 20/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: 
 *  - v.1.1 (20/02/2008): Corretti errori in alcuni commenti.
 *  - v.1.0 (18/02/2008): Creazione e scrittura documentazione.
 */

 
import java.util.ArrayList;
import java.util.LinkedList;



/**
 * Classe concreta, implementa l'interfaccia Batch. Questa classe rappresenta la
 * politica di ordinamento Shortest Job First, che viene usualmente applicata
 * nei sistemi Batch. Essa richiede la conoscenza dei tempi di
 * esecuzione infatti viene eseguito sempre il lavoro più breve. Ovviamente, non
 * è equo con i lavori non presenti all'inizio, a differenza della classe che
 * estenderà questa politica (SRTF). La struttura dati che conterrà i processi
 * pronti sarà quindi ordinata per tempi di esecuzione crescenti.
 * 
 * @author Bonaldo Daniele
 * @version 1.1 20/02/2008
 */

public class SJF implements PoliticaOrdinamentoProcessi {

	/**
	 * Coda dei processi pronti, � ordinata per tempo di esecuzione crescente.
	 */
	protected LinkedList codaPronti;

	/**
	 * Riferimento allo scheduler.
	 */
	protected Scheduler scheduler = null;

	/**
	 * Costruttore, inizializza la struttura dati utilizzata per rappresentare
	 * la coda dei processi pronti per l'esecuzione. La coda sar� inizialmente
	 * vuota.
	 */
	public SJF() {
		super();
		codaPronti = new LinkedList();
	}

	/**
	 * Setta il campo dato privato di tipo scheduler.
	 * 
	 * @param scheduler
	 *            scheduler riferimento per impostare il campo dati privato
	 *            della classe.
	 */
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * Il metodo esegui si occupa di incrementare gli istanti di tempo eseguiti
	 * da un processo e di far avanzare il tempo di utilizzo dello scheduler.
	 * Prende come parametro un valore di tipo intero, che indica per quanto pu�
	 * avanzare l'esecuzione senza che si verifichi qualche evento per il
	 * quale lo scheduler deve intervenire. Ritorna un oggetto, di tipo Istante,
	 * calcolato sull'intervallo di tempo eseguito.
	 * 
	 */
	public void esegui() {

		//Per il tempo (istantiSicuri), durante il quale il processo eseguir�
		// l'incremento del valore degli istanti eseguiti e del tempo dello
		// scheduler.

		// ritorna l'istante riferito al tempo in cui il processo ha eseguito,
		// calcolato dallo scheduler.

		scheduler.incrementaTempoScheduler();

	}

	/**
	 * Inserisce un nuovo PCB nella coda dei processi pronti. Vine effettuato,
	 * mantenendo la struttura dati ordinata per tempo di esecuzione crescente.
	 * 
	 * @param pronto
	 *            E' il PCB che rappresenta il processo appena arrivato nella
	 *            simulazione.
	 */

	public void inserisci(PCB pronto) {
		boolean inserito = false;

		PCB tmp = null;
		// scorro i PCB contenuti nella codaPronti
		for (int i = 0; i < codaPronti.size() && !inserito; i++) {
			tmp = (PCB) codaPronti.get(i);
			// confronto gli istanti da eseguire del processo da inserire e di
			// quelli già presenti nella codaPronti.
			if ((tmp.getIstantiDaEseguire()) > pronto.getIstantiDaEseguire()) {
				// Se il PCB da inserire deve eseguire per meno tempo, lo
				// inserisco all'indice i.
				codaPronti.add(i, pronto);
				inserito = true;

			}
		}
		if (!inserito) {
			// Altrimenti lo aggingo in coda.
			codaPronti.add(pronto);

		}

	}

	/**
	 * Estrae e ritorna il PCB che deve andare in esecuzione.
	 * 
	 * @return Ritorna il PCB del processo che deve andare ad eseguire. (Sar�
	 *         sempre quello nella testa della coda dei pronti)
	 */
	public PCB estrai() {
		if (codaPronti.size() > 0) {
			return (PCB) codaPronti.removeFirst();
		} else
			return null;
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
	 * Ritorna un ArrayList contenente IProcesso estratti dalla coda dei pronti.
	 * 
	 * @return ArrayList di IProcesso dei PCB nella codaPronti.
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
