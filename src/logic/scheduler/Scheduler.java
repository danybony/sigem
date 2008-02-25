package logic.scheduler;

/*
 * Azienda: BlueThoth
 * Nome file: Scheduler.java
 * Package: scheduler
 * Autore: Piero Dalle Pezze
 * Data: 29/01/2006
 * Versione: 1.10
 * Licenza: open-source
 * 
 * Registro delle modifiche:
 * v1.10 (22/02/2006): Aggiunta documentazione. Piero Dalle Pezze.
 * v1.9 (19/02/2006): Apportato standard di documentazione di BlueThoth. Piero Dalle Pezze.
 * v1.8 (11/02/2006):  Effettuata ottimizzazione nel caso in cui non ci sono processi da eseguire
 * 		       finche' un nuovo processo non arriva. In questo modo si avanza direttamente
 * 		       fino al prossimo arrivo di processo. Piero Dalle Pezze.
 * v1.7 (10/02/2006):  Effettuata ottimizzazione sull'incremento del tempo dello scheduler
 * 		       mediante il passaggio di un parametro. Piero Dalle Pezze.
 * v1.6 (09/02/2006):  Revisione del codice e della documentazione. Modifica di rendere il
 * 		       metodo esegui() automatico, in modo da creare tutta la simulazione al
 * 		       principio con una sola chiamata. Reso parametrico il metodo 
 * 		       incrementaTempoScheduler. Piero Dalle Pezze.
 * v1.5 (06/02/2006):  Studio sugli specificatori d'accesso. Revisione della documentazione
 * 		       con le modifiche apportate in data 3,4,5 febbraio 2006. Piero Dalle Pezze.
 * v1.4 (05/02/2006):  Aggiunti i metodi terminaPCBCorrente, incrementaTempoScheduler e aggiunta
 * 		       struttura per i processi terminati. Modificato il rilascio delle risorse.
 * 		       Adottata tecnica lazy. Piero Dalle Pezze.
 * v1.3 (04/02/2006):  Realizzazione simulazione discreta. Aggiunt� struttura tempoEvento e 
 * 		       tipi di eventi, metodo tempoProssimoEvento(). Adeguato il metodo esegui()
 * 		       facendo riferimento ai tipi di eventi e il metodo attivaProcesso. 
 * 		       Piero Dalle Pezze.
 * v1.2 (03/02/2006):  Analisi e progettazione della simulazione discreta anziche' continua. 
 * 		       Stabiliti gli eventi per i quali lo stato interno cambia. Definizione 
 * 		       delle funzionalita' necessarie che devono essere disponibili in PCB 
 * 		       (metodi per calcolare gli eventi). Valutazione delle attivit� svolte dal 
 * 		       metodo specializzato esegui() nelle politiche di ordinamento, in quanto 
 * 		       invocato dal metodo esegui() di Scheduler.
 * v1.1 (30/01/2006):  Codifica di tutti i metodi e costruttori dichiarati nella specifica tecnica. 
 * 		       Piero Dalle Pezze
 * v1.0 (29/01/2006):  Scrittura documentazione. Piero Dalle Pezze
 */

import java.util.*;

/**
 * Questa classe implementa i meccanismi necessari per realizzare una
 * simulazione discreta di processi in un elaboratore multi-programmato. Uno
 * scheduler di questo tipo deve ordinare e gestire gli eventi che possono
 * sorgere durante la schedulazione e calcolare tutti gli istanti continui nei
 * quali lo stato interno dello scheduler stesso rimane inalterato. Lo spazio
 * degli eventi gestiti dallo scheduler sono: </br>
 * <ol>
 * <li>Arrivo di un nuovo processo.</li>
 * <li>Richiesta di una nuova risorsa.</li>
 * <li>Rilascio di una risorsa.</li>
 * <li>Terminazione di un PCB.</li>
 * </ol>
 * Lo scheduler deve prevenire, rispetto al tempo attuale della schedulazione,
 * quale sar� il prossimo evento. Una volta stabilito l'istante futuro nel quale
 * avverr� tale evento, lo scheduler � in grado di avanzare l'esecuzione del PCB
 * attualmente in esecuzione con la certezza che lo stato interno non pu�
 * mutare. </br> La politica di ordinamento scelta per un'istanza di questa
 * classe, pu� essere caratterizzata da ulteriori propri eventi, i quali possono
 * anticipare l'evento scelto dallo scheduler. Per esempio, la politica di
 * ordinamento Round Robin, � caratterizzata dall'evento di fine del quanto di
 * tempo, che pu� spostare il PCB in esecuzione nella coda dei PCB pronti. </br>
 * Per quanto riguarda la gestione dei processi, tale classe simula il
 * comportamento dello scheduler interno ad un sistema operativo. Quindi si
 * occupa dell'attivazione di un nuovo processo, che si simula arrivato nel
 * tempo attuale interno dello scheduler e della sua evoluzione all'interno di
 * un contesto multiprogrammato. La struttura della coda di ordinamento e di
 * assegnazione delle risorse e' interna alle classi che implementano le
 * interfacce PoliticaOrdinamento e PoliticaAssegnazione. Ci� consente allo
 * scheduler di operare il pi� possibile in autonomia dalla scelta della
 * politica e dal modo in cui questa politica considera il PCB in stato di
 * esecuzione. Nel momento dell'estrazione di un PCB dalla coda dei processi
 * pronti, lo scheduler stabilisce se tale PCB estratto pu� andare in esecuzione
 * oppure deve attendere il rilascio di una risorsa attualmente non disponibile.
 * In caso affermativo, lo scheduler manda in esecuzione il PCB con il criterio
 * stabilito dalla classe PoliticaOrdinamento, altrimenti lo inserisce in una
 * specifica struttura dati chiamata PoliticaAssegnazione che, analogamente alla
 * PoliticaOrdinamento, definisce una modalit� di accodamento per i processi
 * bloccati.
 * 
 * @author Piero Dalle Pezze
 * @version 1.10 22/02/2006
 * @see <a href="../parametri/IProcesso.html">IProcesso</a>
 * @see <a href="../simulazione/Istante.html">Istante</a>
 * @see <a href="../parametri/PCB.html">PCB</a>
 * @see <a href="PoliticaOrdinamento.html">PoliticaOrdinamento</a>
 * @see <a
 *      href="politicheAssegnazione/PoliticaAssegnazione.html">PoliticaAssegnazione</a>
 * @see <a href="../parametri/Risorsa.html">Risorsa</a>
 */

public class Scheduler {

	

	/**
	 * Il PCBCorrente rappresenta il PCB che, all'istante definito nella
	 * variabile tempoCorrente, � nello stato di esecuzione. Il caso in cui tale
	 * campo dati fosse null, rappresenta la situazione nella quale la CPU non
	 * sta eseguendo nessun processo.
	 */
	PCB PCBCorrente = null;

	/**
	 * Questo campo dati specifica la politica di schedulazione che si intende
	 * utilizzare per questo scheduler. All'interno di questa � definita la coda
	 * dei PCB nello stato di pronto.
	 */
	PoliticaOrdinamentoProcessi politicaOrdinamento = null;

	/**
	 * Questo campo dati contiene la lista dei processi da attivare, ordinata
	 * per tempo di arrivo.
	 * 
	 */
	LinkedList processiInArrivo = new LinkedList();

	/**
	 * Questo campo dati contiene la coda dei processi terminati in ordine di
	 * terminazione.
	 * 
	 */
	ArrayList processiTerminati;

	/**
	 * Questo campo dati rappresenta la tabella di tutti i PCB (processi attivi)
	 * nell'istante temporale definito dalla variabile tempoCorrente.
	 * 
	 */
	LinkedList tabellaPCB = new LinkedList();

	/**
	 * Questa variabile è il contatore interno della classe Scheduler. Esso è
	 * una rappresentazione del clock della CPU.
	 * 
	 */
	int tempoCorrente = 0;
        
        /**
         * Questa variabile indica se la simulazione è giunta al termine o meno.
         */
        boolean fineSimulazione = false;

	/**
	 * Questa struttura serve per memorizzare i tempi dei quattro eventi che
	 * possono ricorrere in questa simulazione di schedulazione. Tali eventi
	 * specificano lo spazio degli eventi, noto allo scheduler. Il minore dei
	 * tempi di questi eventi rappresenta quanto tempo il PCBCorrente puo'
	 * eseguire, senza che lo stato interno dello scheduler sia alterato.
	 */
	int tempoEvento[] = new int[2];

	/* Spazio degli Eventi */
	/** Tempo di arrivo di un nuovo processo. */
	static final int NUOVO_PROCESSO = 0;

	/** Tempo rimanente di esecuzione del PCBCorrente. */
	static final int PCB_TERMINATO = 1;

	/**
	 * Salva il valore ritornato dal metodo tempoProssimoProcesso() per
	 * stabilire la correttezza del parametro tempo nel metodo
	 * incrementaTempoScheduler().
	 */
	int tempoProssimoEvento = 0;

	/**
	 * Il solo costruttore della classe. Un'istanza della classe Scheduler
	 * richiede al momento della sua inizializzazione, i parametri
	 * PoliticaOrdinamento che specifica la particolare politica di scheduling
	 * dei processi pronti che si intende utilizzare, PoliticaAssegnazione, che
	 * definisce la politica di assegnazione delle risorse per quali i processi
	 * rimangono in attesa, una lista dei processi che dovranno essere attivati
	 * dallo scheduler ordinati per tempo di arrivo ed un vettore di risorse,
	 * che rappresenta tutte le risorse disponibili che si intendono utilizzare.
	 * 
	 * @param politicaOrdinamento
	 *            La politica di ordinamento per i processi pronti.
	 * @param processiInArrivo
	 *            La lista dei processi che si dovranno attivare e schedulare
	 *            ordinati per tempo di arrivo.
	 * @see <a href="../parametri/IProcesso.html">IProcesso</a>
	 * @see <a href="../parametri/Risorsa.html">Risorsa</a>
	 */
	public Scheduler(PoliticaOrdinamentoProcessi politicaOrdinamento,
			LinkedList processiInArrivo) {

		/* In caso di inserimenti a null */
		if (politicaOrdinamento == null) {

			politicaOrdinamento = new FCFS();

		}

		if (processiInArrivo == null) {

			processiInArrivo = new LinkedList();

		}

		/* Copia la politica di ordinamento */
		this.politicaOrdinamento = politicaOrdinamento;

		/* Associa questo Scheduler alla politica di ordinamento */
		this.politicaOrdinamento.setScheduler(this);

		/* SETTING DEI PROCESSI */
		/*
		 * Copia dei processi da attivare ordinandoli per tempo di arrivo
		 * crescente.
		 */
		Processo p;

		boolean inserito = false;

		for (int i = 0; i < processiInArrivo.size(); i++) {

			/* Test sul tipo */
			if (processiInArrivo.get(i) instanceof Processo) {

				p = (Processo) processiInArrivo.get(i);

				inserito = false;

				/* Inserisce p in processiInArrivo mantenendo l'ordine di arrivo */
				for (int j = 0; j < this.processiInArrivo.size() && !inserito; j++) {

					if (p.getTempoArrivo() < ((Processo) this.processiInArrivo
							.get(j)).getTempoArrivo()) {

						this.processiInArrivo.add(j, p);

						inserito = true;

					}

				}

				if (!inserito) {

					this.processiInArrivo.addLast(p);

				}

			}

		}



		/*
		 * Setta quando sara' attivato il primo processo. Successivamente,
		 * questo settaggio sara' eseguito all'interno del metodo
		 * attivaProcesso().
		 */
		if (this.processiInArrivo.size() > 0) {

			tempoEvento[NUOVO_PROCESSO] = ((Processo) this.processiInArrivo
					.getFirst()).getTempoArrivo();

		} else {

			tempoEvento[NUOVO_PROCESSO] = -1;

		}

	}

	/**
	 * Questo metodo si occupa di attivare un nuovo processo arrivato. Per fare
	 * ci�, lo Scheduler deve istanziare il PCB (Process Control Block) relativo
	 * al processo che si intende attivare. Nel caso la politica di
	 * schedulazione adottata dallo scheduler fosse con prerilascio,
	 * l'attivamento di un nuovo processo potrebbe interrompere il PCB nello
	 * stato di esecuzione, se questo e' presente.
	 * 
	 * @see <a href="../parametri/IProcesso.html">IProcesso</a>
	 * @see <a href="../parametri/PCB.html">PCB</a>
	 * @see <a href="PoliticaOrdinamento.html">PoliticaOrdinamento</a>
	 */
	void attivaProcesso() {
		/*
		 * La lista dei processi da attivare non e' vuota e bisogna attivare il
		 * primo processo della lista.
		 */

		while (tempoEvento[NUOVO_PROCESSO] == 0) {

			/* ACTIVATION */
			Processo processo = (Processo) processiInArrivo.removeFirst();

			/* Crea il PCB relativo al processo */
			PCB PCBNuovo = new PCB(processo);

			/* Inserisce il nuovo PCB nella tabella dei PCB */
			tabellaPCB.add(PCBNuovo);

			/* SCHEDULING */
			/* Inserisce il nuovo PCB nella politica di ordinamento */
			politicaOrdinamento.inserisci(PCBNuovo);

			/*
			 * Calcola quando verra' attivato il prossimo processo e quindi
			 * quando sara' invocata questo stesso metodo. Se non ci sono piu'
			 * processi da attivare, questa funzione non sara' piu' invocata e
			 * tempoEvento[0] = -1.
			 */
			if (processiInArrivo.size() > 0) {

				tempoEvento[NUOVO_PROCESSO] = ((Processo) processiInArrivo
						.getFirst()).getTempoArrivo()
						- tempoCorrente;

			} else {

				tempoEvento[NUOVO_PROCESSO] = -1;

			}

		}

	}

	

	/**
	 * Questo metodo si occupa di eseguire tutta la simulazione della gestione
	 * dei processi. L'esecuzione termina nel momento in cui non ci sono piu'
	 * processi in arrivo e tutti i processi sono terminati e/o bloccati.
	 * Un'iterazione provvede ad estrarre un processo pronto e a farlo eseguire
	 * finch� lo stato interno dello scheduler rimane invariato. Prima di
	 * mandare in esecuzione il PCB estratto secondo la politica di ordinamento,
	 * questo metodo stabilisce se tale PCB accede ad una nuova risorsa e
	 * provvede ad attribuirgli le risorse prerilasciabili richieste.
	 * L'esecuzione del PCB estratto � limitata superiormente dal tempo
	 * rimanente del prossimo evento che accadr� nello scheduler. Cio' permette
	 * di effettuare una simulazione discreta dei processi, copiando lo stato
	 * interno dello scheduler soltanto nel momento in cui accade un evento che
	 * modifica lo stato attuale. </br> Tale stato interno costante e' descritto
	 * mediante il ritorno di un oggetto di tipo Istante il quale conterra' le
	 * strutture interne utilizzate dallo scheduler, contenenti i dati statici
	 * nel tempo. Questi dati sono di tipo IProcesso e Risorsa e sono condivisi
	 * dallo scheduler. </br> Il metodo in questione e' in grado di rilevare una
	 * situazione di stallo (deadlock) totale, ossia nel caso in cui tutti i PCB
	 * attivi sono in attesa di una risorsa non prerilasciabile attualmente non
	 * disponibile perche' attribuita ad un altro PCB. In questo caso, invocando
	 * il metodo <a
	 * href="../simulazione/Istante.html#getDeadlock()">getDeadlock()</a>
	 * sull'oggetto istante ritornato, si otterra' true. Tuttavia occorre
	 * precisare che questo non e' un algoritmo di rilevazione di deadlock, dal
	 * momento che, se un nuovo processo arriva e non necessita di risorse non
	 * disponibili, il metodo sopra menzionato ritorna false. </br> Quando la
	 * simulazione e' terminata, viene ritornato un Istante in cui tutte le code
	 * saranno prive di processi e il metodo <a
	 * href="../simulazione/Istante.html#getProcInArrivo()">getProcInArrivo()</a>
	 * della classe Istante ritorna false.
	 * 
	 * @see <a href="../simulazione/Istante.html">Istante</a>
	 * @see <a href="../parametri/PCB.html">PCB</a>
	 * @see <a href="PoliticaOrdinamento.html">PoliticaOrdinamento</a>
	 * @see <a
	 *      href="politicheAssegnazione/PoliticaAssegnazione.html">PoliticaAssegnazione</a>
	 * @see <a href="../parametri/Risorsa.html">Risorsa</a>
	 */
	public void esegui() {		

		boolean stop= false;			

                if (tempoEvento[NUOVO_PROCESSO] == 0) {

                        attivaProcesso();

                }


                if (PCBCorrente == null) {
                        /* Nessun PCB e' in esecuzione */

                        /* Si estrae un PCB dalla coda dei PCB pronti */
                        PCBCorrente = politicaOrdinamento.estrai();

                        if (PCBCorrente == null) {
                                /* La lista dei PCB pronti e' vuota */                                

                                /* Testa se la simulazione e' finita. */
                                if (processiInArrivo.size() == 0) {
                                        /* La simulazione e' terminata */

                                        fineSimulazione = true;

                                } 

                                incrementaTempo();
                                
                                stop = true;

                        } 

                } 

                if (!stop) {
                        /* RUNNING */

                        /* Il PCBCorrente esiste e puo' eseguire */

                        /*
                         * Esegue il PCBCorrente in modo specializzato secondo la
                         * politica di ordinamento, per un tempo stabilito dal valore
                         * ritornato dal metodo statoInternoCostante(). Il metodo
                         * incrementaTempoScheduler() e' invocato dalla politica di
                         * ordinamento un numero di volte stabilito dal metodo
                         * tempoProssimoEvento().
                         * 
                         */
                        tempoProssimoEvento = tempoProssimoEvento();

                        politicaOrdinamento.esegui();

                        /*
                         * Notare che questo test risultera' sempre falso nel caso in
                         * cui la politica di ordinamento terminasse prima del valore
                         * calcolato con il metodo tempoProssimoEvento(). Cio' e'
                         * possibile solo se la politica e' caratterizzata da altri
                         * eventi (almeno 1) ed uno di questi scade prima degli eventi
                         * dello Scheduler. Per esempio, una politica Round Robin tiene
                         * in considerazione la misurazione del quanto eseguito. Per
                         * cui, se il tempo da eseguire prima di alterare lo stato del
                         * PCBCorrente, e' minore del tempo del prossimo evento
                         * calcolato dallo scheduler (che e' il minore tra tutti gli
                         * eventi), nessun tempoEvento[i], per ogni i, potra' essere
                         * decrementato fino a 0. Se invece l'evento scelto dallo
                         * scheduler scade nello stesso tempo dell'evento
                         * caratterizzante la politica di ordinamento, allora e' compito
                         * della politica verificare se il PCB che esegue ha terminato
                         * la sua esecuzione.
                         */

                        if (tempoEvento[PCB_TERMINATO] == 0) {
                                /* Il PCBCorrente ha terminato di eseguire */

                                terminaPCBCorrente();

                        }

                }
		


	}



	/**
	 * Ritorna la lista dei processi che devono ancora essere attivati, ordinata
	 * per tempo di arrivo. Questo metodo condivide la memoria e la struttura.
	 * 
	 * @return Ritorna una lista di processi.
	 */
	public LinkedList getProcessiInArrivo() {

		return processiInArrivo;

	}

	/**
	 * Ritorna la lista dei processi sono terminati in ordine di terminazione.
	 * Questo metodo condivide la memoria e la struttura.
	 * 
	 * @return Ritorna una lista di processi.
	 */
	public ArrayList getProcessiTerminati() {

		return processiTerminati;

	}

	/**
	 * Ritorna il PCB attualmente nello stato di esecuzione. Questo metodo
	 * condivide la memoria.
	 * 
	 * @return Ritorna il PCB in esecuzione. Se nessun PCB � in esecuzione,
	 *         ritorna null.
	 * @see <a href="../parametri/PCB.html">PCB</a>
	 */
	public PCB getPCBCorrente() {

		return PCBCorrente;

	}

	/**
	 * Ritorna la politica di ordinamento scelta nel costruttore. Questo metodo
	 * condivide la memoria e la struttura.
	 * 
	 * @return Ritorna una politica di ordinamento.
	 * @see <a href="PoliticaOrdinamento.html">PoliticaOrdinamento</a>
	 */
	public PoliticaOrdinamentoProcessi getPoliticaOrdinamentoProcessi() {

		return politicaOrdinamento;

	}


	/**
	 * Ritorna la tabella dei PCB. Questo metodo condivide la memoria e la
	 * struttura.
	 * 
	 * @return Ritorna la tabella dei PCB.
	 */
	public LinkedList getTabellaPCB() {

		return tabellaPCB;

	}
	
	/**
	 * Ritorna un intero che rappresenta il numero di volte che un'istanza della
	 * classe Scheduler ha eseguito.
	 * 
	 * @return Ritorna il tempo interno dello scheduler.
	 */
	public int getTempoCorrente() {

		return tempoCorrente;

	}
	
	/**
	 * Incrementa di un'unita' di tempo dello scheduler.
	 * 
	 * 
	 */
	void incrementaTempo() {

		tempoCorrente++;

		/*
		 * Decrementa solo se devono arrivare ancora processi. I valori negativi
		 * non sono contemplati eccetto -1 che rappresenta che non ci sono piu'
		 * processi da attivare.
		 */
		if (tempoEvento[NUOVO_PROCESSO] > 0) {

			tempoEvento[NUOVO_PROCESSO]--;

		}

		/*
		 * Se tempoEvento[NUOVO_PROCESSO] == 0, allora un nuovo processo verra'
		 * prossimamente attivato.
		 */
	}

	

	/**
	 * Questo metodo incrementa il tempo corrente dello scheduler e decrementa
	 * automaticamente i tempi dei prossimi eventi in base al valore tempo
	 * ricevuto come parametro. E' invocato dalla politica di ordinamento per
	 * avanzare direttamente di un tempo stabilito nel parametro tempo. Questo,
	 * deve essere necessariamente positivo e superiormente limitato dal valore
	 * tempoProssimoEvento (non strettamente). Nel caso cio' non fosse
	 * rispettato, il parametro tempo diverra' pari ad 1.
	 * 	 
	 */
	void incrementaTempoScheduler() {

		
		PCBCorrente.incIstantiEseguiti();

		/* Incrementa il tempo corrente dello scheduler */
		tempoCorrente ++;

		/* Decrementa i tempi dei prossimi eventi */
		for (int i = 0; i < 2; i++) {

			if (tempoEvento[i] > 0) {

				tempoEvento[i] --;

			}

		}

	}

	/**
	 * Rimuove il PCBCorrente. La prossima volta che il metodo esegui() e'
	 * invocato, deve essere estratto un nuovo PCB dalla coda dei PCB pronti.
	 */
	void rimuoviPCBCorrente() {

		PCBCorrente = null;

	}

	/**
	 * Questo metodo calcola l'intervallo tra il tempo attuale e il prossimo
	 * evento in questo modo:
	 * 
	 * T(ProssimoEvento) = min( T(Evento) ) per ogni Evento appartenente allo
	 * Spazio degli Eventi
	 * 
	 * dove T e' il tempo. La funzione minore permette un ordinamento crescente
	 * degli eventi noti. Questo metodo, di fatto, realizza tale funzione
	 * minore.
	 * 
	 * @return Ritorna il tempo rimanente al prossimo evento.
	 */
	int tempoProssimoEvento() {
		/* Calcola i tempi di ogni evento */

		/* Evento: Tempo di arrivo di un nuovo processo */
		// tempoEvento[NUOVO_PROCESSO] gia' settato in attivaProcesso() e in
		// Scheduler()
		

		/* Evento: Tempo rimanente di esecuzione del PCBCorrente */
		tempoEvento[PCB_TERMINATO] = PCBCorrente.getIstantiDaEseguire();

		/*
		 * Questo metodo e' invocato sse esiste un PCBCorrente. Quindi avra'
		 * sicuramente valore strettamente positivo tempoEvento[3]. Nota:
		 * tempoEvento[i] con i=0..2 puo' essere -1 se questi eventi non sono
		 * presenti.
		 */
		int min = tempoEvento[PCB_TERMINATO];

		for (int i = 0; i < 2; i++) {

			if (tempoEvento[i] > 0 && tempoEvento[i] < min) {

				min = tempoEvento[i];

			}

		}
				
		return min;

	}

	/**
	 * Metodo invocato quando PCB ha completato la sua esecuzione. Si occupa di
	 * rilasciare tutte le risorse detenute dal PCB che non sono state ancora
	 * rilasciate. Questo simula il comportamento del sistema operativo che
	 * provvede a rilasciare le risorse detenute da un prorgamma se questo
	 * termina senza averle rilasciate (quindi forza il rilascio). In seguito
	 * provvede ad eliminare il PCBCorrente dalla tabella dei PCB, inserire il
	 * processo correlato al PCBCorrente nella lista dei processi terminati e di
	 * porre il PCBCorrente a null.
	 */
	void terminaPCBCorrente() {
		/* TERMINATION */

		PCB PCBTerminato = PCBCorrente;

		PCBCorrente = null;

		tabellaPCB.remove(PCBTerminato);

		processiTerminati.add(PCBTerminato.getRifProcesso());

	}

} // fine classe Scheduler
