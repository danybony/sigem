package logic.schedulazione;

/*
 * Azienda: Stylosoft
 * Nome file: RRConPrioritaConPrerilascio.java
 * Package: logic.scheduler
 * Autore: Daniele Bonaldo
 * Data: 22/02/2008
 * Versione: 1.00
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.00 (22/02/2008): Creazione e scrittura documentazione.
  */


import java.util.LinkedList;

import logic.parametri.ProcessoConPriorita;
/**
 * Classe concreta che estende la classe concreta RRConPriorita. 
 * Questa classe ha il compito di simulare una politica di ordinamento per 
 * sistemi Interattivi, più precisamente la politica
 * Round Robin con priorita' e con prerilascio per priorita'. Questa e' una
 * politica con ordinamento a quanti, ovvero ogni processo esegue per al più un
 * quanto di tempo alla volta. La particolarita' di questa politica, a differenza
 * di quella Round Robin, e' che i processi vengono mantenuti in una struttura
 * dati ordinata per priorita'. Ogni processo conivolto in questa politica, ha
 * infatti un valore di priorita' proprio, che determina in quale lista della
 * struttura dati questo deve essere inserito. L'estrazione di un PCB associato
 * ad un processo, procedera' cercando quello con priorita' più alta. Al termine
 * della sua esecuzione il PCB verra' rimesso in coda alla lista da cui e' stato
 * estratto. La politica, a differenza di quella da cui deriva, prevede
 * prerilascio per priorita', quindi nel caso stesse eseguendo un processo con
 * priorita' bassa, e ne arrivasse uno con priorita' più alta, il primo verrebbe
 * rimesso nella coda dei pronti, e mandato in esecuzione quello appena
 * arrivato.
 * 
 * 
 * @author 22/02/2008
 * @version 1.00
 * 
 */
public class RRConPrioritaConPrerilascio extends RRConPriorita implements
		PoliticaOrdinamentoProcessi {

	/**
	 * Costruttore di default. Richiama il costruttore ad un parametro assegnado
	 * un time slice di 3 unita' di tempo.
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
	 * Metodo di confronto tra due PCB, vengono confrontate le priorita' dei PCB
	 * passati come parametri.
	 * 
	 * @param pronto
	 *            Primo PCB per il confronto
	 * @param inEsecuzione
	 *            Secondo PCB per il confronto
	 * @return Ritorna il PCB con priorit� minore.
	 */
	public PCB minore(PCB pronto, PCB inEsecuzione) {

		ProcessoConPriorita tmp = (ProcessoConPriorita) pronto.getRifProcesso();

		// Ottengo la priorita' del PCB pronto.

		int prioritaPronto = tmp.getPriorita();
		tmp = (ProcessoConPriorita) inEsecuzione.getRifProcesso();

		// Ottengo la priorita' del PCB in esecuzione.

		int prioritaEsecuzione = tmp.getPriorita();
		if (prioritaEsecuzione < prioritaPronto) {

			// la priorita' del processo in esecuzione e' la minore
			return inEsecuzione;
		} else {

			// la priorita' del processo pronto e' la minore
			return pronto;
		}
	}

	/**
	 * Metodo per inserire un nuovo PCB pronto all'interno della struttura dati.
	 * Il comportamento di questo metodo e' differente da quello delle altre
	 * politiche, infatti la politica e' con prerilascio, quindi prima di inserie
	 * il PCB del processo pronto nella codaPronti, viene controllato se la
	 * priorita' di quest'ultimo e' maggiore del PCB del processo che sta
	 * eseguendo. In questo caso il PCB del processo in esecuzione viene
	 * reinserito nella coda dei pronti e viene schedulato quello appena
	 * arrivato. Altrimenti il PCB passato come parametro viene inserito come
	 * avviene nello stesso metodo della classe RRConPriorita.
	 * 
	 * @param pronto
	 *            e' il PCB da inserire nella struttura dati, come processo
	 *            pronto per eseguire.
	 */
	public void inserisci(PCB pronto) {
		PCB inEsecuzione = scheduler.getPCBCorrente();
		if (inEsecuzione != null) {
			PCB PCBMinore = minore(pronto, inEsecuzione);
			if (inEsecuzione.equals(PCBMinore)) {
				// in questo caso il processo in esecuzione, ha priorita' minore
				// quindi va prerilasciato.
				scheduler.rimuoviPCBCorrente();
				ProcessoConPriorita tmp = (ProcessoConPriorita) inEsecuzione
						.getRifProcesso();
				int priorita = tmp.getPriorita();
				// aggiungo il processo prerilasciato nella coda dei
				// prontiassociata alla sua priorita'.
				((LinkedList) codaPronti.get(priorita - 1))
						.addFirst(inEsecuzione);
				// aggingo il processo pronto nella coda dei pronti associata
				// alla sua priorita'.
				// In questo modo sara' il primo ad essere estratto.
				tmp = (ProcessoConPriorita) pronto.getRifProcesso();
				priorita = tmp.getPriorita();
				((LinkedList) codaPronti.get(priorita - 1)).addFirst(pronto);

			} else {
				// il processo in esecuzione ha priorita' maggiore di quello
				// pronto.
				// inserimento fatto secondo il metodo inserisci della classe
				// superiore.
				super.inserisci(pronto);
			}
		} else {
			// in esecuzione non c'e' nessun processo.
			// inserimento fatto secondo il metodo inserisci della classe
			// superiore.
			super.inserisci(pronto);
		}
	}

}
