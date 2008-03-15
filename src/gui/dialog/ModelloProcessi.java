/*
 * Azienda: Stylosoft
 * Nome file: ModelloProcessi.java
 * Package: gui.dialog
 * Autore: Giordano Cariani
 * Data: 24/02/2008
 * Versione: 1.0
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.0 (24/02/2008): Creazione modello di tabella
 */

package gui.dialog;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Giordano Cariani
 */
public class ModelloProcessi extends AbstractTableModel {
        private String[] nomiColonna=new String[0];
        
        private Object[][] contenutiRighe=new Object[0][0];
        
        public ModelloProcessi(int nRighe) {
            nomiColonna = new String[] {"Nome", "Tempo di arrivo", "Tempo di esecuzione" };
            contenutiRighe=new Object[nRighe][3];
            for (int i=0; i<nRighe; i++) {
                contenutiRighe[i][0]=new String("P"+(i+1));
                contenutiRighe[i][1]=new Integer(0);
                contenutiRighe[i][2]=new Integer(1);
            }
            fireTableStructureChanged();
        }
        
        public ModelloProcessi(Object table[][]) {
            nomiColonna = new String[] {"Nome", "Tempo di arrivo", "Tempo di esecuzione" };
            contenutiRighe=new Object[table.length][3];
            for (int i=0; i<table.length; i++) {
                contenutiRighe[i][0]=new String((String) table[i][0]);
                contenutiRighe[i][1]=new Integer((Integer) table[i][1]);
                contenutiRighe[i][2]=new Integer((Integer) table[i][2]);
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
            contenutiRighe[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }
