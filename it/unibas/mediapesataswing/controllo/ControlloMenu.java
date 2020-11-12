package it.unibas.mediapesataswing.controllo;

import it.unibas.mediapesataswing.Applicazione;
import it.unibas.mediapesataswing.Costanti;
import it.unibas.mediapesataswing.modello.Modello;
import it.unibas.mediapesataswing.modello.Studente;
import it.unibas.mediapesataswing.persistenza.DAOException;
import it.unibas.mediapesataswing.persistenza.DAOStudenteCSV;
import it.unibas.mediapesataswing.persistenza.IDAOStudente;
import it.unibas.mediapesataswing.vista.Frame;
import it.unibas.mediapesataswing.vista.VistaModificaStudente;
import it.unibas.mediapesataswing.vista.VistaPrincipale;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlloMenu {

    private static final Logger logger = LoggerFactory.getLogger(ControlloMenu.class.getName());

    private final AzioneNuovo azioneNuovo = new AzioneNuovo();
    private final AzioneApri azioneApri = new AzioneApri();
    private final AzioneSalva azioneSalva = new AzioneSalva();
    private final AzioneEsci azioneEsci = new AzioneEsci();
    private final AzioneFinestraInformazioni azioneFinestraInformazioni = new AzioneFinestraInformazioni();
    private final AzioneApriExcel azioneApriExcel = new AzioneApriExcel();
    private final AzioneApriExcelNoThread azioneApriExcelNoThread = new AzioneApriExcelNoThread();

    public class AzioneNuovo extends AbstractAction {

        public AzioneNuovo() {
            this.putValue(Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGANUOVOSTUDENTE));
            this.putValue(Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONENUOVOSTUDENTE));
            this.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_N);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Modello modello = Applicazione.getInstance().getModello();
            modello.putBean(Costanti.STUDENTE, new Studente());
            VistaPrincipale pannelloPrincipale = Applicazione.getInstance().getVistaPrincipale();
            pannelloPrincipale.schermoStudente();
            Utilita.abilitaAzioniPerNuovoStudente();
            VistaModificaStudente finestraStudente = Applicazione.getInstance().getVistaModificaStudente();
            finestraStudente.visualizza();
        }

    }

    public class AzioneApri extends AbstractAction {

        public AzioneApri() {
            this.putValue(Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAAPRI));
            this.putValue(Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEAPRI));
            this.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_A);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            String nomeFile = acquisisciFile();
            if (nomeFile != null) {
                try {
                    caricaDatiStudente(nomeFile);
                    Utilita.abilitaAzioniPerNuovoStudente();
                } catch (DAOException daoe) {
                    logger.error(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAIMPOSSIBILECARICARE) + daoe);
                    Applicazione.getInstance().getFrame().finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAIMPOSSIBILECARICARE) + daoe);
                }
            }
        }

        private String acquisisciFile() {
            JFileChooser fileChooser = Applicazione.getInstance().getFrame().getFileChooser();
            int codice = fileChooser.showOpenDialog(Applicazione.getInstance().getFrame());
            if (codice == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                return file.toString();
            } else if (codice == JFileChooser.CANCEL_OPTION) {
                logger.info(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGACOMANDOANNULLATO));
            }
            return null;
        }

        private void caricaDatiStudente(String nomeFile) throws DAOException {
            Studente studente = Applicazione.getInstance().getDaoStudente().carica(nomeFile);
            Modello modello = Applicazione.getInstance().getModello();
            modello.putBean(Costanti.STUDENTE, studente);
            VistaPrincipale pannello = Applicazione.getInstance().getVistaPrincipale();
            pannello.schermoStudente();
        }

    }

    public class AzioneSalva extends AbstractAction {

        public AzioneSalva() {
            this.putValue(Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGASALVA));
            this.putValue(Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONESALVA));
            this.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_S);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            String nomeFile = acquisisciFile();
            if (nomeFile != null) {
                try {
                    salvaDatiStudente(nomeFile);
                    Applicazione.getInstance().getVistaPrincipale().aggiornaMessaggioStato(Costanti.STRINGAFILESALVATO);
                } catch (DAOException daoe) {
                    logger.error(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAIMPOSSIBILESALVARE) + daoe.toString());
                    Applicazione.getInstance().getFrame().finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAIMPOSSIBILESALVARE) + daoe);
                }
            }
        }

        private String acquisisciFile() {
            JFileChooser fileChooser = Applicazione.getInstance().getFrame().getFileChooser();
            int codice = fileChooser.showSaveDialog(Applicazione.getInstance().getFrame());
            if (codice == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                return file.toString();
            } else {
                logger.info(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGACOMANDOANNULLATO));
            }
            return null;
        }

        private void salvaDatiStudente(String nomeFile) throws DAOException {
            Modello modello = Applicazione.getInstance().getModello();
            Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
            Applicazione.getInstance().getDaoStudente().salva(studente, nomeFile);
        }

    }

    public class AzioneFinestraInformazioni extends AbstractAction {

        public AzioneFinestraInformazioni() {
            this.putValue(Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAINFORMAZIONI));
            this.putValue(Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEINFORMAZIONI));
            this.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_M);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl H"));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Applicazione.getInstance().getFrame().finestraAbout();
        }
    }

    public class AzioneEsci extends AbstractAction {

        public AzioneEsci() {
            this.putValue(Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAESCI));
            this.putValue(Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEESCI));
            this.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_E);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            System.exit(0);
        }
    }

    public class AzioneApriExcel extends AbstractAction {

        public AzioneApriExcel() {
            this.putValue(Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAAPRICONEXCEL));
            this.putValue(Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEAPRICONEXCEL));
            this.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_L);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            String tmpDir = System.getProperty(Costanti.TEMPDIR);
            String nomeFile = tmpDir + Costanti.FILESTUDENTECSV;
            Frame frame = Applicazione.getInstance().getFrame();
            Modello modello = Applicazione.getInstance().getModello();
            Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
            if (studente == null) {
                frame.finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGASTUDENTENULLO));
                return;
            }
            try {
                IDAOStudente daoStudenteCSV = new DAOStudenteCSV();
                daoStudenteCSV.salva(studente, nomeFile);
                eseguiProcesso(nomeFile);
            } catch (DAOException daoe) {
                frame.finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAIMPOSSIBILESALVARE) + daoe);
            }
        }

        private void eseguiProcesso(final String nomeFile) {
            SwingWorker worker = new SwingWorker() {
                private int exitCode = -1;

                protected Object doInBackground() throws Exception {
                    try {
                        this.exitCode = Utilita.apriFileExcel(nomeFile);
                    } catch (InterruptedException e) { // per waitFor()
                    } catch (Exception ioe) {
                        Frame frame = Applicazione.getInstance().getFrame();
                        frame.finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAIMPOSSIBILEESEGUIREEXCEL) + ioe);
                    }
                    return null;
                }

                protected void done() {
                    try {
                        Frame frame = Applicazione.getInstance().getFrame();
                        if (exitCode != 0) {
                            frame.finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAIMPOSSIBILEESEGUIREEXCEL));
                        } else {
                            frame.toFront();
                        }
                    } catch (Exception ignore) {
                    }
                }
            };
            worker.execute();
        }
    }

    public class AzioneApriExcelNoThread extends AbstractAction {

        public AzioneApriExcelNoThread() {
            this.putValue(Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAAPRICONEXCELNOTHREAD));
            this.putValue(Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEAPRICONEXCELNOTHREAD));
            this.putValue(Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_L);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            String tmpDir = System.getProperty(Costanti.TEMPDIR);
            String nomeFile = tmpDir + Costanti.FILESTUDENTECSV;
            Frame frame = Applicazione.getInstance().getFrame();
            Modello modello = Applicazione.getInstance().getModello();
            Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
            if (studente == null) {
                frame.finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGASTUDENTENULLO));
                return;
            }
            try {
                IDAOStudente daoStudenteCSV = new DAOStudenteCSV();
                daoStudenteCSV.salva(studente, nomeFile);
                eseguiProcesso(nomeFile);
            } catch (DAOException daoe) {
                frame.finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAIMPOSSIBILESALVARE) + daoe);
            }
        }

        private void eseguiProcesso(String nomeFile) {
            Frame frame = Applicazione.getInstance().getFrame();
            try {
                int exitCode = Utilita.apriFileExcel(nomeFile);
                if (exitCode != 0) {
                    frame.finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAIMPOSSIBILEESEGUIREEXCEL));
                } else {
                    frame.toFront();
                }
            } catch (InterruptedException e) { // per waitFor()
            } catch (Exception ioe) {
                frame.finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAIMPOSSIBILESALVARE) + ioe);
            }
        }
    }

    public AzioneNuovo getAzioneNuovo() {
        return azioneNuovo;
    }

    public AzioneApri getAzioneApri() {
        return azioneApri;
    }

    public AzioneSalva getAzioneSalva() {
        return azioneSalva;
    }

    public AzioneEsci getAzioneEsci() {
        return azioneEsci;
    }

    public AzioneFinestraInformazioni getAzioneFinestraInformazioni() {
        return azioneFinestraInformazioni;
    }

    public AzioneApriExcel getAzioneApriExcel() {
        return azioneApriExcel;
    }

    public AzioneApriExcelNoThread getAzioneApriExcelNoThread() {
        return azioneApriExcelNoThread;
    }

}
