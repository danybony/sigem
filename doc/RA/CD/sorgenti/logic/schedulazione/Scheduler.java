package logic.schedulazione;

/*
 * Azienda: Stylosoft
 * Nome file: Scheduler.java
 * Package: logic.scheduler
 * Autore: Daniele Bonaldo
 * Data: 29/02/2008
 * Versione: 1.3
 * Licenza: open-source
 * Registro delle modifiche:
 * v1.3 (29/01/2008):  Divisione del metodo esegui() in seguito a TestScheduler.
 * v1.2 (29/01/2008):  Aggiunto metodo fineSimulazione();
 * v1.1 (29/01/2008):  Corretta inizializzazione di processiInArrivo
 * v1.0 (29/02/2008):  Creazione e scrittura documentazione.
 */

import java.util.*;
import logic.parametri.Processo;
/**
 * Questa classe implementa i meccanismi necessari per realizzare una
 * simulazione discreta di processi in un elaboratore multi-programmato. 
 * Lo Scheduler viene interrogato per ogni istante dal Processore, a cui ritorna 
 * un riferimento al PCB correntemente in esecuzione, che potra' essere un riferimento
 * nullo nel caso in cui nessun processo abbia il controllo della CPU.
 * L'esecuzione dei vari processi avverra' in maniera specializzata a seconda della 
 * politica di ordinamento scelta.
 * 
 * @author Daniele Bonaldo
 * @version 1.3 29/02/2008
 */

public class Scheduler {

	

	/**
	 * Il PCBCorrente rappresenta il PCB che, all'istante definito nella
	 * variabile tempoCorrente, e' nello stato di esecuzione. Il caso in cui tale
	 * campo dati fosse null, rappresenta la situazione nella quale la CPU non
	 * sta eseguendo nessun processo.
	 */
	PCB PCBCorrente = null;

	/**
	 * Questo campo dati specifica la politica di schedulazione che si intende
	 * utilizzare per questo scheduler. All'interno di questa e' definita la coda
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
	ArrayList processiTerminati = new ArrayList();	

	/**
	 * Questa variabile e' il contatore interno della classe Scheduler. Esso e'
	 * una rappresentazione del clock della CPU.
	 * 
	 */
	int tempoCorrente = 0;
        
        /**
         * Questa variabile indica se la simulazione e' giunta al termine o meno.
         * 
         */
        boolean fineSimulazione = false;
   

        /**
         * Questo metodo ritorna lo stato della simulazione.
         * 
         * @return Ritorna true se la simulazione e' terminata, false altrimenti.
         */
        public boolean fineSimulazione(){
            return fineSimulazione;
        }
        
	/**
	 * Questa struttura serve per memorizzare i tempi dei due eventi che
	 * possono ricorrere in questa simulazione di schedulazione. Tali eventi
	 * specificano lo spazio degli eventi, noto allo scheduler.
	 */
	int tempoEvento[] = new int[2];

	/* Spazio degli Eventi */
	/** Tempo di arrivo di un nuovo processo. */
	static final int NUOVO_PROCESSO = 0;

	/** Tempo rimanente di esecuzione del PCBCorrente. */
	static final int PCB_TERMINATO = 1;        
        
	
	/**
	 * Il solo costruttore della classe. Un'istanza della classe Scheduler
	 * richiede al momento della sua inizializzazione, i parametri
	 * PoliticaOrdinamento che specifica la particolare politica di scheduling
	 * dei processi pronti che si intende utilizzaree una lista dei processi 
         * che dovranno essere attivati dallo scheduler ordinati per tempo di 
         * arrivo ed un vettore di risorse.
	 * 
	 * @param politicaOrdinamento
	 *            La politica di ordinamento per i processi pronti.
	 * @param processiInArrivo
	 *            La lista dei processi che si dovranno attivare e schedulare
	 *            ordinati per tempo di arrivo.
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
	 * cio', lo Scheduler deve istanziare il PCB (Process Control Block) relativo
	 * al processo che si intende attivare. Nel caso la politica di
	 * schedulazione adottata dallo scheduler fosse con prerilascio,
	 * l'attivamento di un nuovo processo potrebbe interrompere il PCB nello
	 * stato di esecuzione, se questo e' presente.
	 * 
	 */
	void attivaProcesso() {
		/*
		 * La lista dei processi da attivare non e' vuota e bisogna attivare il
		 * primo processo della lista.
		 */

		while (tempoEvento[NUOVO_PROCESSO] == 0) {

			/* ACTIVATION */
			final Processo processo = (Processo) processiInArrivo.removeFirst();

			/* Crea il PCB relativo al processo */
			final PCB PCBNuovo = new PCB(processo);			

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
	 * Questo metodo si occupa di preparare il prossimo PCB in esecuzione.
         * Per fare cio' controlla se e' il momento corrispondente all'arrivo di 
         * un nuovo processo e in caso affermativo lo attiva.
         * Se non c'e' nessun PCB in esecuzione, allora estrae il primo PCB dalla
         * coda dei pronti, organizzata secondo la politica di ordinamento corrente.
         * Se non c'e' nessun PCB e' in esecuzione e la coda dei pronti e' vuota, 
         * viene controllata la lista dei processi in arrivo ancora da attivare:
         * se e' vuota significa che la simulazione e' terminata.
         * 
	 * @return Ritorna un valore booleano che indica la necessita' di eseguire
         * anche l'avanzamento tramite la politica di ordinamento dei processi.
	 */
	public boolean eseguiAttivazione() {
            
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
                                if (processiInArrivo.isEmpty()) {
                                        /* La simulazione e' terminata */

                                        fineSimulazione = true;

                                } 
                                
                                /* Incrementa il contatore interno dello scheduler,
                                 visto che ritornando true non verra'
                                 invocato incrementaTempoScheduler() */
                                incrementaTempo();
                                
                                stop = true;

                        } 

                } 
     
                return stop;
         }

	/**
	 * Questo metodo viene invocato solo nel caso ci sia un PCB che puo' 
         * eseguire. Esegue il PCBCorrente in modo specializzato secondo la
         * politica di ordinamento,che invoca il metodo incrementaTempoScheduler().
         * Inoltre se il PCBcorrente terminera' la sua esecuzione, verra' invocato il
         * metodo terminaPCBCorrente().
	 * 
	 */
	public void eseguiIncremento() {
            
                /* RUNNING */

                /* Il PCBCorrente esiste e puo' eseguire */
                
                tempoProssimoEvento();

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
	 * @return Ritorna il PCB in esecuzione. Se nessun PCB e' in esecuzione,
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
	 * automaticamente i tempi dei prossimi.' invocato dalla politica di ordinamento per
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
	 * provvede ad  inserire il processo correlato al PCBCorrente nella lista 
         * dei processi terminati e di porre il PCBCorrente a null.
	 */
	void terminaPCBCorrente() {
		/* TERMINATION */

		PCB PCBTerminato = PCBCorrente;

		PCBCorrente = null;

		processiTerminati.add(PCBTerminato.getRifProcesso());

	}

} 
