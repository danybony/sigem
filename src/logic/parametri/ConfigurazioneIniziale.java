package logic.parametri;

import java.util.ArrayList; 
import logic.gestioneMemoria.FrameMemoria; 
import logic.Processore; 
import logic.caricamento.GestioneFile; 
import logic.gestioneMemoria.String; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.33B43FD3-A2AC-3B39-29D9-1E847F8B07CE]
// </editor-fold> 
public class ConfigurazioneIniziale {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.AC04C1B3-ECD6-A2BF-277F-D63BE2116B0D]
    // </editor-fold> 
    private int bandaBusDati;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.8EE863D2-3A69-6602-C638-52247114A1F0]
    // </editor-fold> 
    private int politicaGestioneMemoria;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F09EB2A3-C2F5-8595-9DAB-1DE4C26E9785]
    // </editor-fold> 
    private int modalitaGestioneMemoria;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E3FD9551-578C-4718-E8D6-F8F0FCA55338]
    // </editor-fold> 
    private int politicaSchedulazioneProcessi;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CDE58946-8113-0677-F615-BC8D48A29F69]
    // </editor-fold> 
    private int dimensioneRAM;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.FD69B72C-282A-3CD4-7EB8-9A426F1B4E50]
    // </editor-fold> 
    private int dimensioneSwap;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.09666231-C86E-532B-2B25-7EF838B58234]
    // </editor-fold> 
    private boolean copyOnWrite;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E2404C73-8462-56B1-48CA-CD825115F784]
    // </editor-fold> 
    private int tempoContextSwitch;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.81B91E82-D51D-6CBE-FEA1-50F917CE337B]
    // </editor-fold> 
    private int tempoAccessoDisco;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.54ED59A9-8808-D2FD-0107-7635F22F9154]
    // </editor-fold> 
    private ArrayList<FrameMemoria> configurazioneRAM;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F76853B9-28B4-950A-BBEC-1431DDDD1F6B]
    // </editor-fold> 
    private ArrayList<FrameMemoria> configurazioneSwap;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.527AE685-BDCF-3D21-C7D5-C85D50837466]
    // </editor-fold> 
    private int dimensionePagina;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.19997A32-4BFB-5D18-7320-A3ABCCD43971]
    // </editor-fold> 
    private Processo mProcesso;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5ED7068F-6671-7DAA-BDC0-556245E715BE]
    // </editor-fold> 
    private GestioneFile mGestioneFile;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.55CBF63B-1634-9B79-403D-B106C4C3EB5B]
    // </editor-fold> 
    private Processore mProcessore;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.A262BEB5-78AF-31A6-4DC0-17859E1A3D76]
    // </editor-fold> 
    public int getBandaBusDati () {
        return bandaBusDati;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.A1D7566F-E91C-B614-F651-501A53F05A67]
    // </editor-fold> 
    public void setBandaBusDati (int val) {
        this.bandaBusDati = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.4DDF5BC3-663B-F698-F532-F47B7C2C1B79]
    // </editor-fold> 
    public ArrayList<FrameMemoria> getConfigurazioneRAM () {
        return configurazioneRAM;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.D1805890-18D5-A09F-F6B2-9C7BDECE2ADB]
    // </editor-fold> 
    public void setConfigurazioneRAM (ArrayList<FrameMemoria> val) {
        this.configurazioneRAM = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.46561FD6-EB73-ECCC-31C3-788FD2C53EC6]
    // </editor-fold> 
    public ArrayList<FrameMemoria> getConfigurazioneSwap () {
        return configurazioneSwap;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.AFCA8F12-386A-1921-C7C0-5A7BBBA598A2]
    // </editor-fold> 
    public void setConfigurazioneSwap (ArrayList<FrameMemoria> val) {
        this.configurazioneSwap = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.832310FB-912B-844B-AD70-730886BB66D2]
    // </editor-fold> 
    public boolean getCopyOnWrite () {
        return copyOnWrite;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.B26BF6BF-DFCA-2729-D1A6-A12B08AB62A1]
    // </editor-fold> 
    public void setCopyOnWrite (boolean val) {
        this.copyOnWrite = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5E1C5D1C-4A7F-23A1-E180-DF083F75D920]
    // </editor-fold> 
    public int getDimensionePagina () {
        return dimensionePagina;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.72E5F4BA-E7D9-B4A9-DE61-A88ACAFFAA2A]
    // </editor-fold> 
    public void setDimensionePagina (int val) {
        this.dimensionePagina = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.949A75FE-27B7-3A8E-93EA-3FDBBC65EDB2]
    // </editor-fold> 
    public int getDimensioneRAM () {
        return dimensioneRAM;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.DFF27563-405F-C5D1-648E-1A6B064C5805]
    // </editor-fold> 
    public void setDimensioneRAM (int val) {
        this.dimensioneRAM = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.1EF11CAE-8590-EFEF-AED8-240E03351AA1]
    // </editor-fold> 
    public int getDimensioneSwap () {
        return dimensioneSwap;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C4EAC9DD-3F80-5095-F7C9-12B71E1F2D34]
    // </editor-fold> 
    public void setDimensioneSwap (int val) {
        this.dimensioneSwap = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.48797995-B518-3D1A-6E4B-DF8DEBD9657B]
    // </editor-fold> 
    public int getModalitaGestioneMemoria () {
        return modalitaGestioneMemoria;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.BB74A9D4-C387-08DE-03EA-92587A6A65D5]
    // </editor-fold> 
    public void setModalitaGestioneMemoria (int val) {
        this.modalitaGestioneMemoria = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.1F9D4FF9-BDC6-1099-5561-28C9A8BB7D87]
    // </editor-fold> 
    public int getPoliticaGestioneMemoria () {
        return politicaGestioneMemoria;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.DDE1DED5-3DD8-B814-E33D-BF31C1327BAC]
    // </editor-fold> 
    public void setPoliticaGestioneMemoria (int val) {
        this.politicaGestioneMemoria = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5606006E-4CDA-68D3-EC16-36738AB56B07]
    // </editor-fold> 
    public int getPoliticaSchedulazioneProcessi () {
        return politicaSchedulazioneProcessi;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5437EE66-9BEA-7091-0ECC-8C8E0E58A4BF]
    // </editor-fold> 
    public void setPoliticaSchedulazioneProcessi (int val) {
        this.politicaSchedulazioneProcessi = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.30EC0F11-11F3-4675-689E-67800F95974F]
    // </editor-fold> 
    public int getTempoAccessoDisco () {
        return tempoAccessoDisco;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.8377AE34-DB42-E416-2D76-389490EE37D3]
    // </editor-fold> 
    public void setTempoAccessoDisco (int val) {
        this.tempoAccessoDisco = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.BD8ABDB1-0975-A447-BA22-BD6BBE897223]
    // </editor-fold> 
    public int getTempoContextSwitch () {
        return tempoContextSwitch;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.31E16B3C-610E-C5F8-CABD-E36B94653705]
    // </editor-fold> 
    public void setTempoContextSwitch (int val) {
        this.tempoContextSwitch = val;
    }

}

