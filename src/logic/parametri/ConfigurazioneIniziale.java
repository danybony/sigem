package logic.parametri;

import java.util.ArrayList; 
import logic.gestioneMemoria.FrameMemoria; 

public class ConfigurazioneIniziale {

    private static int bandaBusDati;
    private static int politicaGestioneMemoria;
    private static int modalitaGestioneMemoria;
    private static int politicaSchedulazioneProcessi;
    private static int dimensioneRAM;
    private static int dimensioneSwap;
    private static int tempoContextSwitch;
    private static int tempoAccessoDisco;
    private static ArrayList<FrameMemoria> configurazioneRAM;
    private static ArrayList<FrameMemoria> configurazioneSwap;
    private static int dimensionePagina;

    public static int getBandaBusDati () {
        return bandaBusDati;
    }

    public void setBandaBusDati (int val) {
        this.bandaBusDati = val;
    }

    public static ArrayList<FrameMemoria> getConfigurazioneRAM () {
        return configurazioneRAM;
    }

    public void setConfigurazioneRAM (ArrayList<FrameMemoria> val) {
        this.configurazioneRAM = val;
    }

    public static ArrayList<FrameMemoria> getConfigurazioneSwap () {
        return configurazioneSwap;
    }

    public void setConfigurazioneSwap (ArrayList<FrameMemoria> val) {
        this.configurazioneSwap = val;
    }

    public static int getDimensionePagina () {
        return dimensionePagina;
    }

    public void setDimensionePagina (int val) {
        this.dimensionePagina = val;
    }

    public static int getDimensioneRAM () {
        return dimensioneRAM;
    }

    public void setDimensioneRAM (int val) {
        this.dimensioneRAM = val;
    }

    public static int getDimensioneSwap () {
        return dimensioneSwap;
    }

    public void setDimensioneSwap (int val) {
        this.dimensioneSwap = val;
    }

    public static int getModalitaGestioneMemoria () {
        return modalitaGestioneMemoria;
    }

    public void setModalitaGestioneMemoria (int val) {
        this.modalitaGestioneMemoria = val;
    }

    public static int getPoliticaGestioneMemoria () {
        return politicaGestioneMemoria;
    }

    public void setPoliticaGestioneMemoria (int val) {
        this.politicaGestioneMemoria = val;
    }

    public static int getPoliticaSchedulazioneProcessi () {
        return politicaSchedulazioneProcessi;
    }

    public void setPoliticaSchedulazioneProcessi (int val) {
        this.politicaSchedulazioneProcessi = val;
    }

    public static int getTempoAccessoDisco () {
        return tempoAccessoDisco;
    }

    public void setTempoAccessoDisco (int val) {
        this.tempoAccessoDisco = val;
    }

    public static int getTempoContextSwitch () {
        return tempoContextSwitch;
    }

    public void setTempoContextSwitch (int val) {
        this.tempoContextSwitch = val;
    }

}

