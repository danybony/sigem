/*
 * Azienda: Stylosoft
 * Nome file: ModelloProcessiPriorita.java
 * Package: gui.dialog
 * Autore: Giordano Cariani
 * Data: 16/03/2008
 * Versione: 1.2
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.2 (16/03/2008): Corretta gestione modifica configurazione e corretta gestione
 *                          celle in caso di campi vuoti
 *  - v.1.1 (15/03/2008): Inserita gestione modifica configurazione
 *  - v.1.0 (24/02/2008): Creazione modello di tabella
 */

package gui.dialog;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;
import logic.parametri.Processo;
import logic.parametri.ProcessoConPriorita;

/**
 *
 * @author Giordano Cariani
 */
public class ModelloProcessiPriorita extends AbstractTableModel {
        private String[] nomiColonna=new String[] {"Nome", "Tempo di arrivo", "Tempo di esecuzione", "Priorita'" };
        
        private Object[][] contenutiRighe=new Object[0][0];

        /**
	 * Campo dati contatore utilizzato per la generazione dei nomi di default
	 * dei processi.
	 */
	private int numero = 1;
        
        public ModelloProcessiPriorita(int nRighe) {
            contenutiRighe=new Object[nRighe][4];
            for (int i=0; i<nRighe; i++) {
                contenutiRighe[i][0]=new String("P"+(i+1));
                contenutiRighe[i][1]=new Integer(0);
                contenutiRighe[i][2]=new Integer(1);
                contenutiRighe[i][3]=new Integer(1);
            }
            fireTableStructureChanged();
        }
        
       public ModelloProcessiPriorita(LinkedList<Processo> processi, int nRighe) {
            contenutiRighe=new Object[nRighe][4];
            if (processi.get(0) instanceof ProcessoConPriorita) {
                ProcessoConPriorita processoConPriorita;
                if (nRighe > processi.size()) {
                    for (int i=0; i<processi.size(); i++) {
                        processoConPriorita = (ProcessoConPriorita) processi.get(i);
                        contenutiRighe[i][0]=new String((String)   processoConPriorita.getNome());
                        contenutiRighe[i][1]=new Integer((Integer) processoConPriorita.getTempoArrivo());
                        contenutiRighe[i][2]=new Integer((Integer) processoConPriorita.getTempoEsecuzione());
                        contenutiRighe[i][3]=new Integer((Integer) processoConPriorita.getPriorita());
                    }
                    for (int i=processi.size(); i<nRighe; i++) {                
                        contenutiRighe[i][0]=new String("P"+(i+1));
                        contenutiRighe[i][1]=new Integer(0);
                        contenutiRighe[i][2]=new Integer(1);
                        contenutiRighe[i][3]=new Integer(1);
                    }
                } else {
                    for (int i=0; i<nRighe; i++) {
                        processoConPriorita = (ProcessoConPriorita) processi.get(i);
                        contenutiRighe[i][0]=new String((String)   processoConPriorita.getNome());
                        contenutiRighe[i][1]=new Integer((Integer) processoConPriorita.getTempoArrivo());
                        contenutiRighe[i][2]=new Integer((Integer) processoConPriorita.getTempoEsecuzione());
                        contenutiRighe[i][3]=new Integer((Integer) processoConPriorita.getPriorita());
                    }
                }
            } else {
                if (nRighe > processi.size()) {
                    for (int i=0; i<processi.size(); i++) {
                        contenutiRighe[i][0]=new String((String)   processi.get(i).getNome());
                        contenutiRighe[i][1]=new Integer((Integer) processi.get(i).getTempoArrivo());
                        contenutiRighe[i][2]=new Integer((Integer) processi.get(i).getTempoEsecuzione());
                        contenutiRighe[i][3]=new Integer(1);
                    }
                    for (int i=processi.size(); i<nRighe; i++) {
                        contenutiRighe[i][0]=new String("P"+(i+1));
                        contenutiRighe[i][1]=new Integer(0);
                        contenutiRighe[i][2]=new Integer(1);
                        contenutiRighe[i][3]=new Integer(1);
                    }
                } else {
                     for (int i=0; i<nRighe; i++) {
                        contenutiRighe[i][0]=new String((String)   processi.get(i).getNome());
                        contenutiRighe[i][1]=new Integer((Integer) processi.get(i).getTempoArrivo());
                        contenutiRighe[i][2]=new Integer((Integer) processi.get(i).getTempoEsecuzione());
                        contenutiRighe[i][3]=new Integer(1);
                    }
                }
            }
            fireTableStructureChanged();
        }
        
        public int getColumnCount() {
            return nomiColonna.length;
        }

        public int getRowCount() {
            return contenutiRighe.length;
        }

        public String getColumnName(int col) {
            return nomiColonna[col];
        }

        public Object getValueAt(int row, int col) {
            return contenutiRighe[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the contenutiRighe/cell address is constant,
            //no matter where the cell appears onscreen.
                return true;
        }

        /*
         * Don't need to implement this method unless your table's
         * contenutiRighe can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (col == 0) {
                // controlliamo che il nome già esiste
		for (int i = 0; i < getRowCount(); i++) {
                    String tmp = (String) contenutiRighe[i][0];
                    // se esiste...
                    if (tmp.equals((String) value)
                    && !(tmp.equals(getValueAt(row, col)))) {
                    // Inseriamo un nome generato automaticamente
                        value = new String("P" + numero);
                        numero++;
                    }
                }
                // se la cella è vuota...
		if (contenutiRighe[row][0].equals(new String(""))) {
                    // Inseriamo un nome generato automaticamente
                    value = new String("P" + numero);
                    numero++;
		}
            }
            try {
                //contenutiRighe[row][col] = value;
                if (col != 0) {
                    if (((Integer) value).intValue() < 0) {
                        if (col == 2)
                            value = new Integer(1);
                        else
                            value = new Integer(0);
                        }
                    if (((Integer) value).intValue() == 0) {
                        if (col == 2)
                            value = new Integer(1);
                    }
                    if ((col==1 || col == 2) && ((Integer) value).intValue()>100)
                        value = new Integer(100);
                }
            contenutiRighe[row][col] = value;
            fireTableCellUpdated(row, col);
            } catch (NullPointerException errore) {
                if (col == 1)
                    value = new Integer(0);
		if (col == 2)
                    value = new Integer(1);
            }
        }
    }
