package it.unibas.mediapesataswing;

import it.unibas.mediapesataswing.controllo.ControlloMenu;
import it.unibas.mediapesataswing.controllo.ControlloModificaEsame;
import it.unibas.mediapesataswing.controllo.ControlloModificaStudente;
import it.unibas.mediapesataswing.controllo.ControlloPrincipale;
import it.unibas.mediapesataswing.modello.Modello;
import it.unibas.mediapesataswing.persistenza.DAOConfigurazione;
import it.unibas.mediapesataswing.persistenza.DAOStudenteJSon;
import it.unibas.mediapesataswing.persistenza.IDAOStudente;
import it.unibas.mediapesataswing.vista.Frame;
import it.unibas.mediapesataswing.vista.VistaModificaEsame;
import it.unibas.mediapesataswing.vista.VistaModificaStudente;
import it.unibas.mediapesataswing.vista.VistaPrincipale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Applicazione {

    private static final Logger logger = LoggerFactory.getLogger(Applicazione.class.getName());

    private static final Applicazione singleton = new Applicazione();

    public static Applicazione getInstance() {
        return singleton;
    }

    private Applicazione() {
    }

    private Modello modello;
    private IDAOStudente daoStudente;
    private DAOConfigurazione daoConfigurazione;
    private Frame frame;
    private VistaPrincipale vistaPrincipale;
    private VistaModificaEsame vistaModificaEsame;
    private VistaModificaStudente vistaModificaStudente;
    private ControlloMenu controlloMenu;
    private ControlloPrincipale controlloPrincipale;
    private ControlloModificaStudente controlloModificaStudente;
    private ControlloModificaEsame controlloModificaEsame;
    private ResourceManager resourceManager;

    /* *******************************************
     *              Main
     * ********************************************/
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Applicazione.getInstance().inizializza();
            }
        }
        );
    }

    public void inizializza() {
        this.resourceManager = new ResourceManager();
        this.resourceManager.inizializza();
        this.modello = new Modello();
        this.daoStudente = new DAOStudenteJSon();
        this.daoConfigurazione = new DAOConfigurazione();
        this.frame = new Frame();
        this.vistaModificaStudente = new VistaModificaStudente(frame);
        this.vistaModificaEsame = new VistaModificaEsame(frame);
        this.vistaPrincipale = new VistaPrincipale();
        this.controlloMenu = new ControlloMenu();
        this.controlloModificaEsame = new ControlloModificaEsame();
        this.controlloModificaStudente = new ControlloModificaStudente();
        this.controlloPrincipale = new ControlloPrincipale();
        this.vistaPrincipale.inizializza();
        this.vistaModificaStudente.inizializza();
        this.vistaModificaEsame.inizializza();
        this.frame.inizializza();
    }

    public Modello getModello() {
        return modello;
    }

    public IDAOStudente getDaoStudente() {
        return daoStudente;
    }

    public DAOConfigurazione getDaoConfigurazione() {
        return daoConfigurazione;
    }

    public Frame getFrame() {
        return frame;
    }

    public VistaPrincipale getVistaPrincipale() {
        return vistaPrincipale;
    }

    public VistaModificaEsame getVistaModificaEsame() {
        return vistaModificaEsame;
    }

    public VistaModificaStudente getVistaModificaStudente() {
        return vistaModificaStudente;
    }

    public ControlloMenu getControlloMenu() {
        return controlloMenu;
    }

    public ControlloPrincipale getControlloPrincipale() {
        return controlloPrincipale;
    }

    public ControlloModificaStudente getControlloModificaStudente() {
        return controlloModificaStudente;
    }

    public ControlloModificaEsame getControlloModificaEsame() {
        return controlloModificaEsame;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

}
