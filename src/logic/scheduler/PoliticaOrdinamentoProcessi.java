package logic.scheduler;

/*
 * Azienda: Stylosoft
 * Nome file: PoliticaOrdinamentoProcessi.java
 * Package: scheduler
 * Autore: Daniele Bonaldo
 * Data: 06/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.0 (06/02/2008): Creazione e scrittura documentazione.
 */
 
import java.util.ArrayList;


/**
 * Lo scopo dell'interfaccia PoliticaOrdinamento, è quello di specializzare il
 * concetto di Politica. Una Politica di Ordinamento ha il compito di ordinare i
 * processi che sono attivi in un sistema, scegliendo quale di questi deve
 * eseguire ad ogni istante. Una politica di ordinamento viene specializzata a
 * seconda dei sistemi in cui questa verrà applicata. Elemento comune tra le
 * varie politiche di ordinamento, è quello di dover mantenere i processi pronti
 * all'interno di una struttura dati.
 * 
 * @author Daniele Bonaldo
 * @version 1.0
 */
public interface PoliticaOrdinamentoProcessi {

	/**
	 * Metodo che simula l'esecuzione del processo che sta detenendo la CPU.
	 * La simulazione dell'esecuzione avviene aumentando il numero di istanti
	 * eseguiti di tale processo di un certo valore passato come parametro, 
	 * valore che viene calcolato dallo scheduler.
	 * 
	 */
	public void esegui();

	/**
	 * Metodo che ritorna un ArrayList contenente tutti i riferimenti a IProcesso 
	 * a cui sono associati i PCB contenuti nella coda dei pronti.
	 * 
	 * @return Ritorna un ArrayList di IProcesso.
	 */
	public ArrayList getCodaPronti();

	/**
	 * Metodo che imposta il campo dato di tipo Scheduler che dovr� essere previsto
	 * da ogni politica di ordinamento concreta che implementi direttamente la presente
	 * interfaccia.
	 * 
         * @param scheduler
	 *            è il riferimento dello scheduler che detiene la politica di
	 *            ordinamento.
	 */
	public void setScheduler(Scheduler scheduler);
        
        /**
	 * Rimuove l'elemento specificato dalla struttura dati definita nella classe
	 * che lo implementerà.
	 * 
	 * @return Ritorna il PCB estratto.
	 */
	public abstract PCB estrai();
        
        /**
	 * Inserisce l'elemento specificato nella struttura dati della classe che lo
	 * implementer�.
	 * 
	 * @param pcb
	 *            è il PCB da inserire nella struttura dati.
	 */
	public void inserisci(PCB pcb);
        
        /**
	 * Ritorna il numero di PCB correnti presenti nella struttura dati definita
	 * nella classe che andr� ad implementarlo.
	 * 
	 * @return Ritorna la capacità corrente della struttura.
	 */
	public abstract int size();
}
