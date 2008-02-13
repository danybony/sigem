package logic.parametri;

import java.util.ArrayList; 
import logic.Processore; 
import logic.caricamento.GestioneFile; 
import logic.gestioneMemoria.String; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.33B43FD3-A2AC-3B39-29D9-1E847F8B07CE]
// </editor-fold> 
public class ConfigurazioneIniziale {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.88072D81-A085-E120-F299-CF8CC1BA7C98]
    // </editor-fold> 
    private String tecnicaGestioneMemoria;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D51D8817-60AB-6B72-2F02-87ACC6E090A6]
    // </editor-fold> 
    private String politicaSchedulazioneProcessi;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7D023090-7205-0619-F9ED-3385CD17ADE5]
    // </editor-fold> 
    private int clockRefTime = -1;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.6A4ABB5A-FB2A-D37C-3705-173A37712992]
    // </editor-fold> 
    private int politicaRimpiazzo;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.8D510645-356A-D725-C809-DBA832414025]
    // </editor-fold> 
    private int timeSlice = -1;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.6E6982C1-3770-AF93-381F-A13F7C586250]
    // </editor-fold> 
    private Vector processi;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.84739EAB-4CD2-A3D2-38BF-C9A9F67C5489]
    // </editor-fold> 
    private int dimensioneRAM;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.41B60E6B-75B9-A123-D99B-DB33D2A4374C]
    // </editor-fold> 
    private int dimensioneSwap;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3D7A7C7E-F2F4-EC66-ACD9-47727010C71C]
    // </editor-fold> 
    private boolean copyOnWrite;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.907A95D5-F57E-73A8-F1E7-984B21584BF9]
    // </editor-fold> 
    private int tempoContextSwitch;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5593DE67-84F8-7F0B-567F-30928D999E3A]
    // </editor-fold> 
    private int tempoAccessoDisco;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.175A4007-D0FD-1C72-8F6B-FCDF6A6496D4]
    // </editor-fold> 
    private ArrayList<Integer> configurazioneRAM;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.637295E7-D6CA-2DE7-E551-1D4B68E00844]
    // </editor-fold> 
    private ArrayList<Integer> configurazioneSwap;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D5744188-90A4-602C-9A5F-8E6A67C5AA74]
    // </editor-fold> 
    private int dimensionePagina;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.19997A32-4BFB-5D18-7320-A3ABCCD43971]
    // </editor-fold> 
    private Processo mProcesso;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F084EF1B-D8D1-9F7E-1609-8C20EDB23021]
    // </editor-fold> 
    private Gui mGui;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5ED7068F-6671-7DAA-BDC0-556245E715BE]
    // </editor-fold> 
    private GestioneFile mGestioneFile;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.55CBF63B-1634-9B79-403D-B106C4C3EB5B]
    // </editor-fold> 
    private Processore mProcessore;

}

