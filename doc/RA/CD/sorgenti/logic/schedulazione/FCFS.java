package logic.schedulazione;

/*
 * Azienda: Stylosoft
 * Nome file: FCFS.java
 * Package: logic.scheduler
 * Autore: Daniele Bonaldo
 * Data: 20/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche: * 
 *  - v.1.1 (20/02/2008): Correzione in alcuni commenti.
 *  - v.1.0 (18/02/2008): Creazione e scrittura documentazione.
 */


import java.util.ArrayList;
import java.util.LinkedList;

/**
 * La classe FCFS rappresenta il funzionamento della politica di ordinamento 
 * First Come First Served, tipica dei sistemi Batch. 
 * Questo tipo di politica ordina la coda dei processi pronti secondo il loro 
 * tempo d'arrivo e li fa eseguire uno alla volta fino al completamento. 
 * La classe possiede quindi una struttura dati di tipo FIFO, contenente PCB, 
 * sulla quale vengono fatte le operazioni di inserimento e di estrazione. 
 * Dunque, l'estrazione del PCB da mandare in escuzione sara' fatto in testa alla 
 * strutura mentre ogni nuovo PCB che entra nello stato di pronto verra' inserito 
 * in coda.
 * 
 * @author Bonaldo Daniele
 * @version 1.1 20/02/2008
 */

public class FCFS implements PoliticaOrdinamentoProcessi {

	/**
	 * Coda dei processi pronti, si comporta come una coda FIFO.
	 */
	protected LinkedList codaPronti;

	/**
	 * Riferimento allo scheduler.
	 */
	protected Scheduler scheduler = null;

	/**
	 * Costruttore che si occupa di inizializzare la struttura dati utilizzata
	 * per rappresentare la coda dei processi pronti per l'esecuzione. Essa sara'
	 * inizialmente vuota.
	 */
	public FCFS() {
		codaPronti = new LinkedList();
	}

	/**
	 * Setta il campo dato privato di tipo scheduler.
	 * 
	 * @param scheduler
	 *            parametro per settare il riferimento allo scheduler usato.
	 */
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * Simula l'esecuzione del processo che "possiede" la CPU.
	 *
	 */
	public void esegui() {		

		scheduler.incrementaTempoScheduler();

	}

	/**
	 * Inserisce un nuovo PCB nella coda dei processi pronti. In questa
	 * politica, l'inserimento viene sempre fatto in coda.
	 * 
	 * @param pronto
	 *            E' il PCB del processo appena arrivato nella simulazione.
	 */
	public void inserisci(PCB pronto) {
		codaPronti.add(pronto);
	}

	/**
	 * Estrae e ritorna il PCB che deve andare in esecuzione.
	 * 
	 * @return Ritorna il PCB del processo che deve andare ad eseguire. (Sara'
	 *         sempre quello nella testa della coda dei pronti)
	 */
	public PCB estrai() {
		// controllo se la codaPronti e' vuota.
		if (codaPronti.size() > 0) {
			// in caso negativo: estrazione del primo elemento.
			return (PCB) codaPronti.removeFirst();
		} else
			// Se la coda e' vuota ritorno null.
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
	 * Ritorna un ArrayList contenente oggetti di tipo Processo estratti dalla
	 * coda dei pronti.
	 * 
	 * @return ArrayList di oggetti di tipo Processo associati ai PCB contenuti
	 *         nella codaPronti.
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
