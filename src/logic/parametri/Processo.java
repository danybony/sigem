/*
 * Azienda: Stylosoft
 * Nome file: Processo.java
 * Package: logic.parametri
 * Autore: Daniele Bonaldo
 * Data: 18/02/2008
 * Versione: 1.01
 * Licenza: open-source
 * Registro delle modifiche:
 * v.1.01 (06/02/2006): Correzzione del metodo equals
 * v.1.00 (03/02/2006): Scrittura della documentazione e implementazione dei metodi.
 */

package logic.parametri;

import java.util.ArrayList;
import logic.gestioneMemoria.FrameMemoria;
/**
 * La classe Processo rappresenta la classe base per tutti i processi, almeno
 * per quelli che sono dati in dotazione alla consegna del prodotto. Essa
 * contiene in sè tutti i campi dati e metodi comuni ad ogni tipo di processo. 
 * 
 * @author Daniele Bonaldo
 * @version 1.04
 */
public class Processo {
	

	/** Campo dati che identifica univocamente ogni istanza della classe 
	 * Da inizializzare con un'apposita chiamata ad un metodo della classe Id.*/
	private int id;

	/**
	 * Metodo che permette di visualizzare l'id di un oggetto che istanzia la
	 * classe
	 * 
	 * @return l'identificatore univoco dell'oggetto che istanzia la classe.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Campo dati che contiene la lista degli accessi a FrameMemoria che un processo
	 * andrà a richiedere durante il suo ciclo di vita. Tale lista di accessi è
	 * ordinata per istante di esecuzione crescente e alla creazione viene posta
	 * inizialmete uguale a una lista vuota.
	 */
	private ArrayList accessi = new ArrayList();

	/**
	 * Metodo che ritorna la lista degli accessi ordinata per istante di
	 * esecuzione crescente.
	 * 
	 * @return La lista di richieste d'accesso a FrameMemoria che saranno fatte da un
	 *         particolare processo.
	 */
	public ArrayList getAccessi() {
		return accessi;
	}

	/**
	 * Metodo che si occupa di aggiungere una richiesta d'accesso ad un FrameMemoria
	 * nel campo dati accessi. Il metodo garantisce che l'inserimento mantenga
	 * ordinata per istante di richiesta crescenta la lista degli accessi.
	 * Ritorna true se l'inserimento ha successo, false altrimenti.
	 * 
	 * @param frame
	 *            FrameMemoria a cui il processo vuole accedere
	 * @param richiesta
	 *            istante di esecuzione in cui avviene al richiesta di accesso
	 *            al FrameMemoria
	 * @param durata
	 *            numero di istanti di esecuzione per cui il processo deve poter
	 *            accedere al FrameMemoria
	 * @return true se l'inserimento ha successo, false altrimenti.
	 */
        
	public boolean richiestaFrameMemoria(FrameMemoria frame, int richiesta, int durata) {
		Accesso accesso = new Accesso(frame, richiesta, durata);
		for (int i = 0; i < accessi.size(); i++) {
			/*
			 * Controllo di mantenere l'ArrayList ordianto per istante di
			 * richiesta crescente
			 */
			if (((Accesso) accessi.get(i)).getIstanteRichiesta() > accesso
					.getIstanteRichiesta()) {
				accessi.add(i, accesso);
				return true;
			}
		}// esco dal for perchè l'oggetto va inserito in fondo all'ArrayList
		return accessi.add(accesso);
	}
        
	/**
	 * Campo dati contentente l'istante in cui un processo diventa attivo, cio
	 * è l'istante a partire dal quale un processo può concorrere per andare 
	 * in esecuzione.
	 */
	private int tempoArrivo;

	/**
	 * Metodo che ritorna l'istante di attivazione di un processo.
	 * 
	 * @return L'istante a partire dal quale un processo inizia a concorrere con
	 *         gli altri per andare in esecuzione.
	 */
	public int getTempoArrivo() {
		return tempoArrivo;
	}

	/** Campo dati contenente il nome associato al processo */
	private String nome = "";

	/**
	 * Metodo che ritorna il nome associato ad un processo
	 * 
	 * @return Il nome associato ad un processo.
	 */
	public String getNome() {
		return nome;
	}

	/** Campo dati che contiene il tempo totale d'esecuzione di un processo. */
	private int tempoEsecuzione;

	/**
	 * Metodo che ritorna il tempo totale d'esecuzione di un processo.
	 * 
	 * @return Il tempo totale d'esecuzione di un processo.
	 */
	public int getTempoEsecuzione() {
		return tempoEsecuzione;
	}

	/**
	 * Costrutture di un'istanza della classe Processo.
	 * 
	 * @param nome
	 *            Nome da assegnare al processo creato
	 * @param tarrivo
	 *            tempo in cui il processo diventa attivo
	 * @param tesecuzione
	 *            tempo totale d'esecuzione del processo
	 * @see Id#returnNewId()
	 */
	public Processo(String nome, int tarrivo, int tesecuzione) {
		id = Id.returnNewId(); // creo l'id per il nuovo processo creato
		this.nome = nome;
		this.tempoArrivo = tarrivo;
		this.tempoEsecuzione = tesecuzione;
	}

	/**
	 * Metodo che esegue l'uguaglianza fra due processi
	 * 
	 * @param processo
	 *            il processo da confrontare.
	 * @return Ritorna true se due processi sono uguali false altrimenti.
	 */
	public boolean equals(Object processo) {
		Processo proc;
		/*
		 * se processo ha TD un sottotipo della classe Processo allora eseguo il
		 * cast al tipo dinamico
		 */
		if (processo instanceof Processo) {
			proc = (Processo) processo;
			/*
			 * se i due oggetti di tipo processo hanno lo stesso id allora sono
			 * uguali.
			 */
			if (this.id == proc.getId())
				return true;
		}
		/*
		 * In questo punto ho che o i due oggetti di tipo processo hanno diverso
		 * id, oppure l'oggetto processo non ha TD sottotipo di Processo che può
		 * significare che processo==null o processo non ha TD sottotipo di
		 * processo. Comunque sia so con certezza che this!=processo perciò
		 * ritorno false.
		 */
		return false;
	}

	/**
	 * Metodo che converte a stringa un processo ritornandone il nome.
	 * 
	 * @return Ritorna il nome del processo.
	 */
	public String toString() {
		return this.nome;
	}
}
