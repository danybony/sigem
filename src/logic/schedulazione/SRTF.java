package logic.schedulazione;

/*
 * Azienda: Stylosoft
 * Nome file: SRTF.java
 * Package: scheduler
 * Autore: Daniele Bonaldo
 * Data: 20/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.1 (20/02/2008):correzione nel metodo inserisci(PCB ).
 * 	- v.1.0 (18/02/2008): creazione e scrittura documentazione.
 * */


/**
 * Classe concreta che estende la classe SJF. 
 * La classe SRTF implementa la politica di ordinamento
 * Shortest Remaining Time First, ovvero una politica con prerilascio per
 * sistemi Batch. Eredita i metodi esegui(), estrai(), size() e getCodaPronti()
 * dalla classe superiore, mentre implementa il metodo minore(PCB a, PCB b)
 * Questa politica di ordnamento ha un comportamento simile alla politica SJF,
 * ma applica il prerilascio quando arriva un PCB con tempo di esecuzione minore
 * rispetto a quello che sta eseguendo. I PCB vengono mantenuti ordinati
 * rispetto al tempo di esecuzione in maniera crescente, su una struttura dati.
 * L'estrazione invece viene fatta dalla testa della struttura dati, proprio
 * perchè questa viene mantenuta ordinata.
 * 
 * @author Bonaldo Daniele
 * @version 1.1 20/02/2008
 */

public class SRTF extends SJF {

	/**
	 * Costruttore, richiama i costruttori della classe superiore (SJF), e crea
	 * la struttura dati per implementare la politica di ordinamento SRTF. La
	 * struttura sarà inizialmente vuota.
	 */
	public SRTF() {
		super();
	}

	/**
	 * Effettua un confronto tra i PCB specificati. Vengono confrontati i tempi
	 * di esecuzione dei PCB passati come parametri.
	 * 
	 * @param pronto
	 *            Primo PCB per il confronto
	 * @param inEsecuzione
	 *            Secondo PCB per il confronto
	 * @return Ritorna il PCB con tempo di esecuzione minore
	 */
	private PCB minore(PCB pronto, PCB inEsecuzione) {
		// controllo gli istanti da eseguire dei parametri e ritorno il minore
		// dei due.
		if (pronto.getIstantiDaEseguire() < inEsecuzione.getIstantiDaEseguire()) {
			return pronto;
		}
		return inEsecuzione;

	}

	/**
	 * Inserisce un nuovo PCB pronto all'interno della struttura dati. Il
	 * comportamento di questo metodo è differente da quello delle altre
	 * politiche, infatti la politica è con prerilascio, quindi prima di inserie
	 * il PCB del processo pronto nella codaPronti, viene controllato se il
	 * tempo di esecuzione di quest'ultimo è minore del PCB del processo che sta
	 * eseguendo. In questo caso il PCB del processo in esecuzione viene
	 * reinserito nella coda dei pronti e viene schedulato quello appena
	 * arrivato. Altrimenti il PCB passato come parametro viene inserito come
	 * avviene nello stesso metodo della classe SJF.
	 * 
	 * @param pronto
	 *            è il PCB da inserire nella struttura dati, come processo
	 *            pronto per eseguire.
	 */

	public void inserisci(PCB pronto) {
		PCB inEsecuzione = scheduler.getPCBCorrente();
		// Controllo che in esecuzione ci sia almeno un processo.
		if (inEsecuzione != null) {
			PCB PCBMinore = minore(pronto, inEsecuzione);
			// Se il PCB pronto ha tempo di esecuzione minore, inserisco
			// nella testa della coda il PCB inEsecuzione.
			// e subito dopo inserisco sempre nella testa della coda anche il
			// PCB pronto.
			if (pronto.equals(PCBMinore)) {

				scheduler.rimuoviPCBCorrente();
				codaPronti.addFirst(inEsecuzione);
				codaPronti.addFirst(pronto);

			} else {
				// Altrimenti inserisci normalmente.
				super.inserisci(pronto);
			}
		} else {
			// Altrimenti inserisci normalmente.
			super.inserisci(pronto);
		}
	}

}
