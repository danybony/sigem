
package logic.parametri;

/*
 * Azienda: Stylosoft
 * Nome file: EccezioneConfigurazioneNonValida.java
 * Package: logic.parametri
 * Autore: Luca Rubin
 * Data: 29/02/2008
 * Versione: 1.1
 * Licenza: open-source
 * Registro delle modifiche:
 *  - v.1.1 (26/02/2008): Aggiunta la documentazione JavaDoc.
 *  - v.1.0 (26/02/2008): Impostazione base della classe
 */

/**
 * Eccezione lanciata nel momento in cui viene costruita una ConfigurazioneIniziale
 * con valori non validi per i propri campi dati.
 */
public class EccezioneConfigurazioneNonValida extends Exception{
    public EccezioneConfigurazioneNonValida(){
        super();
    }
}

