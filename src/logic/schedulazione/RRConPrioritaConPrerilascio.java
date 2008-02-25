package logic.schedulazione;

/*
 * Azienda: Stylosoft
 * Nome file: RRConPrioritaConPrerilascio.java
 * Package: scheduler
 * Autore: Daniele Bonaldo
 * Data: 06/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.0 (06/02/2008): Creazione e scrittura documentazione.
  */


import java.util.LinkedList;

import parametri.IProcessoConPriorita;
import parametri.PCB;

/**
 * Classe concreta che estende la classe concreta RRConPriorita e implementa
 * l'interfaccia ConPrerilascio. Questa classe ha il compito di simulare una
 * politica di ordinamento per sistemi Interattivi, pi� precisamente la politica
 * Round Robin con priorit� e con prerilascio per priorit�. Questa � una
 * politica con ordinamento a quanti, ovvero ogni processo esegue per al pi� un
 * quanto di tempo alla volta. La particolarit� di questa politica, a differenza
 * di quella Round Robin, � che i processi vengono mantenuti in una struttura
 * dati ordinata per priorit�. Ogni processo conivolto in questa politica, ha
 * infatti un valore di priorit� proprio, che determina in quale lista della
 * struttura dati questo deve essere inserito. L'estrazione di un PCB associato
 * ad un processo, proceder� cercando quello con priorit� pi� alta. Al termine
 * della sua esecuzione il PCB verr� rimesso in coda alla lista da cui � stato
 * estratto. La politica, a differenza di quella da cui deriva, prevede
 * prerilascio per priorit�, quindi nel caso stesse eseguendo un processo con
 * priorit� bassa, e ne arrivasse uno con priorit� pi� alta, il primo verrebbe
 * rimesso nella coda dei pronti, e mandato in esecuzione quello appena
 * arrivato.
 * 
 * 
 * @author Marin Pier Giorgio
 * @version 1.0
 * 
 */
public class RRConPrioritaConPrerilascio extends RRConPriorita implements
		PoliticaOrdinamentoProcessi {

	/**
	 * Costruttore di default. Richiama il costruttore ad un parametro assegnado
	 * un time slice di 3 unit� di tempo.
	 */
	public RRConPrioritaConPrerilascio() {
		this(3);

	}

	/**
	 * Costruttore, riceve come parametro un intero, che indica il valore di
	 * time slice scelto. Richiama il costruttore della classe superiore.
	 * 
	 * @param timeSlice
	 *            indica quanto deve durare un quanto di tempo.
	 * 
	 */
	public RRConPrioritaConPrerilascio(int timeSlice) {
		super(timeSlice);

	}

	/**
	 * Metodo di confronto tra due PCB, vengono confrontate le priorit� dei PCB
	 * passati come parametri.
	 * 
	 * @param pronto
	 *            Primo PCB per il confronto
	 * @param inEsecuzione
	 *            Secondo PCB per il confronto
	 * @return Ritorna il PCB con priorit� minore.
	 */
	public PCB minore(PCB pronto, PCB inEsecuzione) {

		IProcessoConPriorita tmp = (IProcessoConPriorita) pronto.getIProcesso();

		// Ottengo la priorit� del PCB pronto.

		int prioritaPronto = tmp.getPriorita();
		tmp = (IProcessoConPriorita) inEsecuzione.getIProcesso();

		// Ottengo la priorit� del PCB in esecuzione.

		int prioritaEsecuzione = tmp.getPriorita();
		if (prioritaEsecuzione < prioritaPronto) {

			// la priorit� del processo in esecuzione � la minore
			return inEsecuzione;
		} else {

			// la priorit� del processo pronto � la minore
			return pronto;
		}
	}

	/**
	 * Metodo per inserire un nuovo PCB pronto all'interno della struttura dati.
	 * Il comportamento di questo metodo � differente da quello delle altre
	 * politiche, infatti la politica � con prerilascio, quindi prima di inserie
	 * il PCB del processo pronto nella codaPronti, viene controllato se la
	 * priorit� di quest'ultimo � maggiore del PCB del processo che sta
	 * eseguendo. In questo caso il PCB del processo in esecuzione viene
	 * reinserito nella coda dei pronti e viene schedulato quello appena
	 * arrivato. Altrimenti il PCB passato come parametro viene inserito come
	 * avviene nello stesso metodo della classe RRConPriorita..
	 * 
	 * @param pronto
	 *            � il PCB da inserire nella struttura dati, come processo
	 *            pronto per eseguire.
	 */
	public void inserisci(PCB pronto) {
		PCB inEsecuzione = scheduler.getPCBCorrente();
		if (inEsecuzione != null) {
			PCB PCBMinore = minore(pronto, inEsecuzione);
			if (inEsecuzione.equals(PCBMinore)) {
				// in questo caso il processo in esecuzione, ha priorit� minore
				// quindi va prerilasciato.
				scheduler.rimuoviPCBCorrente();
				IProcessoConPriorita tmp = (IProcessoConPriorita) inEsecuzione
						.getIProcesso();
				int priorita = tmp.getPriorita();
				// aggiungo il processo prerilasciato nella coda dei
				// prontiassociata alla sua priorit�.
				((LinkedList) codaPronti.get(priorita - 1))
						.addFirst(inEsecuzione);
				// aggingo il processo pronto nella coda dei pronti associata
				// alla sua priorit�.
				// In questo modo sar� il primo ad essere estratto.
				tmp = (IProcessoConPriorita) pronto.getIProcesso();
				priorita = tmp.getPriorita();
				((LinkedList) codaPronti.get(priorita - 1)).addFirst(pronto);

			} else {
				// il processo in esecuzione ha priorit� maggiore di quello
				// pronto.
				// inserimento fatto secondo il metodo inserisci della classe
				// superiore.
				super.inserisci(pronto);
			}
		} else {
			// in esecuzione non c'� nessun processo.
			// inserimento fatto secondo il metodo inserisci della classe
			// superiore.
			super.inserisci(pronto);
		}
	}

}
