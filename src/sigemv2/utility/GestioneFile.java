package sigemv2.utility;

import java.io.*;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class GestioneFile {
    /**Ha un comportamento analogo al costruttore standard.*/
    public GestioneFile() {
		super();
	}
	/**
	 * Estrae l'estensione da un oggetto file.
	 * 
	 * @param File
	 *            f: File da cui estrarre l'estensione
	 * @return oggetto String uguale all'estensione del file
	 */
	private String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	/**
	 * Apre una JDialog per permettere all'utente di selezionare il file da
	 * aprire. Se l'utente non seleziona un file ritorna null, altrimenti prova
	 * a convertire il file in oggetto di tipo ConfigurazioneIniziale e lo
	 * ritorna.
	 * 
	 * @param frame
	 *            Riferimento al frame su cui apre il JDialog
	 * @return ConfigurazioneIniziale La configurazione creata dal file.
	 */
	public boolean openFile(JFrame frame) {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			chooser.addChoosableFileFilter(new FileFilter() {
				public boolean accept(File f) {
					if (f.isDirectory()) {
						return true;
					}

					String extension = getExtension(f);
					if (extension != null && extension.equals("fcs"))
						return true;
					else
						return false;
				}

				// The description of this filter
				public String getDescription() {
					return new String(
							"File di configurazione simulazione (*.fcs)");

				}
			});

			int returnVal = chooser.showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(chooser.getSelectedFile()));
				return true;
			} else
				return false;
		} catch (Exception e) {
			System.out
					.println("[NOTICE ER002] [DATE:"
							+ Calendar.getInstance().getTime().toString()
							+ "] [MESSAGE: "
							+ "Il file selezionato non � valido" + "]");
			return false;
		}
	}

	// Controlla l'esistenza del file ricercato
	public static boolean fileExist(String relPath, String file) {

		try {
			File in = new File(relPath + file);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new FileInputStream(in)));
			return true;
		} catch (FileNotFoundException fnfe) {
			return false;
		}
	}
} // end class
