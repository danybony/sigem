package logic.gestioneMemoria;

import logic.PCB; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.7DC02A41-EA25-A24E-ED3D-9DEBB5998108]
// </editor-fold> 
public interface FrameMemoria {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.217FCC38-70EF-A644-C321-43F1104F5572]
    // </editor-fold> 
    private PCB mPCB;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.EB7EDBB5-5668-F5CE-D72C-08B7D2AAC38C]
    // </editor-fold> 
    private Memoria mMemoria;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.BB1F242E-5887-B196-5DA6-7FB092D2632E]
    // </editor-fold> 
    public String getIndirizzo ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.93F5CEAE-7442-7CCC-18C2-2EABB6B962C8]
    // </editor-fold> 
    public int getDimensione ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D327677A-4F3E-1348-2678-3E6B495694BA]
    // </editor-fold> 
    public boolean getInRAM ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.69DAA50D-2701-54E5-FC10-957E991FCF90]
    // </editor-fold> 
    public boolean setInRAM ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B2714F85-226F-03AE-5D2C-883CD4D93C6A]
    // </editor-fold> 
    public boolean getSolaLettura ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3F3C14A5-65D2-1C97-FD7E-3C0029742039]
    // </editor-fold> 
    public boolean setSolaLettura ();

}

