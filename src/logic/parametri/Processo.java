/*
 * Azienda: Stylosoft
 * Nome file: Processo.java
 * Package: logic.parametri
 * Autore: Daniele Bonaldo
 * Data: 18/02/2008
 * Versione: 1.02
 * Licenza: open-source
 * Registro delle modifiche:
 * v.1.02 (29/02/2008): Innestamento della classe Accesso, prima esterna.
 * v.1.01 (21/02/2008): Correzzione del metodo equals
 * v.1.00 (20/02/2008): Scrittura della documentazione e implementazione dei metodi.
 */

package logic.parametri;

import java.io.Serializable;
import java.util.ArrayList;
import logic.gestioneMemoria.FrameMemoria;
/**
 * La classe Processo rappresenta la classe base per tutti i processi, almeno
 * per quelli che sono dati in dotazione alla consegna del prodotto. Essa
 * contiene in sè tutti i campi dati e metodi comuni ad ogni tipo di processo. 
 * 
 * @author Daniele Bonaldo
 * @version 1.03
 */
public class Processo implements Serializable {
	
        /**
         * Classe i cui oggetti rappresentano una richiesta d'accesso ad un FrameMemoria da
         * parte di un processo. Essi sono costituiti dal FrameMemoria a cui il processo
         * vuole accedere, dall'istante in cui la richiesta deve essere effettuata e
         * dalla durata per cui il processo deve poter accedere al FrameMemoria richiesto.
         * 
         * @author Daniele Bonaldo
         * @version 1.02
         */
        public class Accesso implements Serializable {	

                /**
                 * Campo dati che contiene l'istante d'esecuzione in cui un processo deve
                 * effettuare la richiesta d'accesso ad un FrameMemoria.
                 */
                private int istanteRichiesta;

                /**
                 * Metodo che ritorna l'istante d'esecuzione del processo in cui avviene la
                 * richiesta di accesso ad un FrameMemoria.
                 * 
                 * @return L'istante d'esecuzione in cui si verifica una richiesta d'accesso
                 *         ad un FrameMemoria da parte di un processo.
                 */
                public int getIstanteRichiesta() {
                        return istanteRichiesta;
                }


                /**
                 * Campo dati contenente il FrameMemoria a cui la richiesta d'accesso si
                 * riferisce.
                 */
                private FrameMemoria frame;

                /**
                 * Metodo che ritorna il FrameMemoria a cui la richiesta d'accesso di riferisce.
                 * 
                 * @return Ll FrameMemoria a cui il processo vuole accedere.
                 */
                public FrameMemoria getRisorsa() {
                        return frame;
                }

                /**
                 * Costruttore che si occupa di creare un oggetto rappresentante tutte le
                 * informazioni che riguardano una richiesta d'accesso ad un FrameMemoria 
                 * da parte di un processo che andrà a contenerlo.
                 * 
                 * @param frame
                 *            Il FrameMemoria a cui il processo richiede di poter accedere
                 * @param istanteRichiesta
                 *            L'istante di tempo in cui la richiesta d'accesso deve essere
                 *            effettuata.	 
                 */
                public Accesso(FrameMemoria frame, int istanteRichiesta) {
                        this.frame = frame;
                        this.istanteRichiesta = istanteRichiesta;
                }


        } /* fine classe interna Accesso */
        
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
	private ArrayList<Accesso> accessi = new ArrayList();

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
	 * @return true se l'inserimento ha successo, false altrimenti.
	 */
        
	public boolean richiestaFrameMemoria(FrameMemoria frame, int richiesta) {
		Accesso accesso = new Accesso(frame, richiesta);
		for (int i = 0; i < accessi.size(); i++) {
			/*
			 * Controllo di mantenere l'ArrayList ordianto per istante di
			 * richiesta crescente
			 */
			if ((accessi.get(i)).getIstanteRichiesta() > accesso
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
