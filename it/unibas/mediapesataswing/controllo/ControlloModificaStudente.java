package it.unibas.mediapesataswing.controllo;

import it.unibas.mediapesataswing.Applicazione;
import it.unibas.mediapesataswing.Costanti;
import it.unibas.mediapesataswing.modello.Modello;
import it.unibas.mediapesataswing.modello.Studente;
import it.unibas.mediapesataswing.vista.Frame;
import it.unibas.mediapesataswing.vista.VistaModificaStudente;
import it.unibas.mediapesataswing.vista.VistaPrincipale;
import javax.swing.AbstractAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlloModificaStudente {

    private static final Logger logger = LoggerFactory.getLogger(ControlloModificaStudente.class.getName());

    private final AzioneCommitModificaStudente azioneCommitModificaStudente = new AzioneCommitModificaStudente();
    private final AzioneRollbackModificaStudente azioneRollabckModificaStudente = new AzioneRollbackModificaStudente();

    public class AzioneCommitModificaStudente extends AbstractAction {

        public AzioneCommitModificaStudente() {
            this.putValue(javax.swing.Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAMODIFICA));
            this.putValue(javax.swing.Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEMODIFICASTUDENTE));
            this.putValue(javax.swing.Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_M);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            VistaModificaStudente finestra = Applicazione.getInstance().getVistaModificaStudente();
            VistaPrincipale vistaPrincipale = Applicazione.getInstance().getVistaPrincipale();
            String campoNome = finestra.getCampoNome();
            String campoCognome = finestra.getCampoCognome();
            String campoMatricola = finestra.getCampoMatricola();
            String errori = Utilita.verificaDatiStudente(campoNome, campoCognome, campoMatricola);
            if (errori.equals("")) {
                Modello modello = Applicazione.getInstance().getModello();
                Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
                studente.setNome(campoNome);
                studente.setCognome(campoCognome);
                studente.setMatricola(Integer.parseInt(campoMatricola));
                VistaModificaStudente finestraStudente = Applicazione.getInstance().getVistaModificaStudente();
                finestraStudente.nascondi();
                vistaPrincipale.schermoStudente();
                vistaPrincipale.aggiornaMessaggioStato(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGASTUDENTEMODIFICATO));
            } else {
                Applicazione.getInstance().getFrame().finestraErrore(errori);
            }
        }
    }

    public class AzioneRollbackModificaStudente extends AbstractAction {

        public AzioneRollbackModificaStudente() {
            this.putValue(javax.swing.Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAANNULLA));
            this.putValue(javax.swing.Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEANNULLAMODIFICASTUDENTE));
//            this.putValue(javax.swing.Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_M);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            VistaModificaStudente finestra = Applicazione.getInstance().getVistaModificaStudente();
            finestra.setVisible(false);
            Frame frame = Applicazione.getInstance().getFrame();
            frame.setVisible(true);
        }
    }

    public AzioneCommitModificaStudente getAzioneCommitModificaStudente() {
        return azioneCommitModificaStudente;
    }

    public AzioneRollbackModificaStudente getAzioneRollabckModificaStudente() {
        return azioneRollabckModificaStudente;
    }
    
}
