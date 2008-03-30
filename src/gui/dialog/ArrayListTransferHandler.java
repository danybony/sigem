/*
 * Azienda: Stylosoft
 * Nome file: ArrayListTransferHandler.java
 * Package: gui.dialog
 * Autore: Bonaldo Daniele
 * Data: 16/03/2008
 * Versione: 1.2
 * Licenza: open-source
 * Registro delle modifiche: *  
 *  - v.1.1 (15/03/2008): inserito controllo sulle copie e sulla dimensione massima
 *  - v.1.1 (15/03/2008): inserito spostamento per il riferimento e non solo epr la stringa
 *  - v.1.0 (14/03/2008): Creazione a partire da esempio preso dal tutorial di java.sun.com
 */

package gui.dialog;

/*
 * ArrayListTransferHandler.java is used by the 1.4
 * DragListDemo.java example.
 */

import gui.dialog.AssociazioneProcessiJDialog.FrameModifica;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.*;
import logic.gestioneMemoria.FrameMemoria;

public class ArrayListTransferHandler extends TransferHandler {
    DataFlavor localArrayListFlavor, serialArrayListFlavor;
    String localArrayListType = DataFlavor.javaJVMLocalObjectMimeType +
                                ";class=java.util.ArrayList";
    JList source = null;
    int[] indices = null;
    int addIndex = -1; //Location where items were added
    int addCount = 0;  //Number of items added
    int dimensioneRAM;
    
    /**
     * Unico costruttore della classe.
     * 
     * @param dimensioneRAM
     *          La dimensione della memoria RAM espressa in KB
     */
    public ArrayListTransferHandler(int dimensioneRAM) {
        try {
            localArrayListFlavor = new DataFlavor(localArrayListType);
        } catch (ClassNotFoundException e) {
            System.out.println(
             "ArrayListTransferHandler: unable to create data flavor");
        }
        serialArrayListFlavor = new DataFlavor(ArrayList.class,
                                              "ArrayList");
        this.dimensioneRAM = dimensioneRAM;
    }

    /**
     * Metodo chiamato quando si rilasciano degli oggetti su un componente.
     * Chiama gli altri metodi per controllare se il componente puo' accettare gli 
     * oggetti passati ed e' incaricato di controllare che, nello specifico, la 
     * dimensione dei FrameMemoria spostati sommata a quella dei FrameMemoria gia'
     * presenti, non superi la dimensione della RAM.
     * 
     * @param c
     *      Il componente ricevente
     * @param t
     *      La lista di oggetti passati
     * @return
     *      Un booleano che indica il successo dell'operazione
     */
    public boolean importData(JComponent c, Transferable t) {
        JList target = null;
        ArrayList alist = null;
        if (!canImport(c, t.getTransferDataFlavors())) {
            return false;
        }
        try {
            target = (JList)c;
            
            if (hasLocalArrayListFlavor(t.getTransferDataFlavors())) {
                alist = (ArrayList)t.getTransferData(localArrayListFlavor);
            } else if (hasSerialArrayListFlavor(t.getTransferDataFlavors())) {
                alist = (ArrayList)t.getTransferData(serialArrayListFlavor);
            } else {
                return false;
            }
        } catch (UnsupportedFlavorException ufe) {
            System.out.println("importData: unsupported data flavor");
            return false;
        } catch (IOException ioe) {
            System.out.println("importData: I/O exception");
            return false;
        }

        //At this point we use the same code to retrieve the data
        //locally or serially.

        //We'll drop at the current selected index.
        int index = target.getSelectedIndex();

        //Prevent the user from dropping data back on itself.
        //For example, if the user is moving items #4,#5,#6 and #7 and
        //attempts to insert the items after item #5, this would
        //be problematic when removing the original items.
        //This is interpreted as dropping the same data on itself
        //and has no effect.
        if (source.equals(target)) {
            if (indices != null && index >= indices[0] - 1 &&
                  index <= indices[indices.length - 1]) {
                indices = null;
                return true;
            }
        }

        DefaultListModel listModel = (DefaultListModel)target.getModel();
        int max = listModel.getSize();
        if (index < 0) {
            index = max; 
        } else {
            index++;
            if (index > max) {
                index = max;
            }
        }
        addIndex = index;
        addCount = alist.size();
        
        boolean hit;
        
        for (int i=0; i < alist.size(); i++) {
            int spazioOccupato = 0;
            hit=false;
            for(int j = 0; j< listModel.getSize(); j++){
                if(listModel.getElementAt(j) instanceof FrameMemoria){
                    spazioOccupato += ((FrameMemoria)listModel.getElementAt(j)).getDimensione();
                    if((listModel.getElementAt(j)).equals(alist.get(i))){
                         hit = true;
                    }
                }
                else{
                    spazioOccupato += ((FrameModifica)listModel.getElementAt(j)).frame.getDimensione();
                    if(((FrameModifica)listModel.getElementAt(j)).frame.equals(alist.get(i))){
                         hit = true;
                    }
                }                   
            }
            if(!hit){
                if(spazioOccupato+((FrameMemoria)alist.get(i)).getDimensione() <= dimensioneRAM){
                    listModel.add(index++, alist.get(i));
                    spazioOccupato += ((FrameMemoria)alist.get(i)).getDimensione();
                }
                
            }
        }
        return true;
    }

    /**
     * Metodo invocato dopo che l'operazione di spostamento e' stata eseguita
     * @param c
     * @param data
     * @param action
     */
    protected void exportDone(JComponent c, Transferable data, int action) {
        if ((action == MOVE) && (indices != null)) {
            DefaultListModel model = (DefaultListModel)source.getModel();

            //If we are moving items around in the same list, we
            //need to adjust the indices accordingly since those
            //after the insertion point have moved.
            if (addCount > 0) {
                for (int i = 0; i < indices.length; i++) {
                    if (indices[i] > addIndex) {
                        indices[i] += addCount;
                    }
                }
            }
            for (int i = indices.length -1; i >= 0; i--)
                model.remove(indices[i]);
        }
        indices = null;
        addIndex = -1;
        addCount = 0;
    }

    private boolean hasLocalArrayListFlavor(DataFlavor[] flavors) {
        if (localArrayListFlavor == null) {
            return false;
        }

        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(localArrayListFlavor)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSerialArrayListFlavor(DataFlavor[] flavors) {
        if (serialArrayListFlavor == null) {
            return false;
        }

        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(serialArrayListFlavor)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Indica se un componente puo' accettare degli oggetti lasciati (dropped )dal 
     * Drag and Drop
     * 
     * @param c
     *      Il componente ricevente
     * @param flavors
     *      Il tipo degli oggetti passati
     * @return
     *      Ritorna true se puo' ricevere gli oggetti, false altrimenti
     */
    public boolean canImport(JComponent c, DataFlavor[] flavors) {
        if(c.getName()!=null && c.getName().equals("jListFrame")){
            return false;
        } 
        if (hasLocalArrayListFlavor(flavors))  { return true; }
        if (hasSerialArrayListFlavor(flavors)) { return true; }
        return false;
    }

    protected Transferable createTransferable(JComponent c) {
        if (c instanceof JList) {
            source = (JList)c;
            indices = source.getSelectedIndices();
            Object[] values = source.getSelectedValues();
            if (values == null || values.length == 0) {
                return null;
            }
            ArrayList alist = new ArrayList(values.length);
            for (int i = 0; i < values.length; i++) {
                Object o = values[i];
                alist.add(o);
            }
            return new ArrayListTransferable(alist);
        }
        return null;
    }

    /**
     * Ritorna un intero rappresentante l'azione che il componente passato puo'
     * eseguire
     * @param c
     *      Il componente da testare
     * @return
     *      L'intero rappresentante l'azione
     */
    public int getSourceActions(JComponent c) {
        if(c.getName()!=null && c.getName().equals("jListFrame")){
            return COPY;
        }       
        return NONE;
    }

    public class ArrayListTransferable implements Transferable {
        ArrayList data;

        public ArrayListTransferable(ArrayList alist) {
            data = alist;
        }

        public Object getTransferData(DataFlavor flavor)
                                 throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return data;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { localArrayListFlavor,
                                      serialArrayListFlavor };
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            if (localArrayListFlavor.equals(flavor)) {
                return true;
            }
            if (serialArrayListFlavor.equals(flavor)) {
                return true;
            }
            return false;
        }
    }
}
