package it.unibas.mediapesataswing.controllo;

import it.unibas.mediapesataswing.Applicazione;
import it.unibas.mediapesataswing.Costanti;
import it.unibas.mediapesataswing.modello.Esame;
import it.unibas.mediapesataswing.modello.Modello;
import it.unibas.mediapesataswing.modello.Studente;
import it.unibas.mediapesataswing.vista.VistaPrincipale;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlloPrincipale {

    private static final Logger logger = LoggerFactory.getLogger(ControlloPrincipale.class.getName());
    private final AzioneEliminaEsame azioneEliminaEsame = new AzioneEliminaEsame();
    private final AzioneFinestraModificaEsame azioneFinestraModificaEsame = new AzioneFinestraModificaEsame();
    private final AzioneFinestraModificaStudente azioneFinestraModificaStudente = new AzioneFinestraModificaStudente();
    private final AzioneInserisciEsame azioneInserisciEsame = new AzioneInserisciEsame();

    public class AzioneInserisciEsame extends javax.swing.AbstractAction {

        public AzioneInserisciEsame() {
            this.putValue(javax.swing.Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAINSERISCIESAME));
            this.putValue(javax.swing.Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEINSERISCIESAME));
            this.putValue(javax.swing.Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_I);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            VistaPrincipale vistaPrincipale = Applicazione.getInstance().getVistaPrincipale();
            String campoInsegnamento = vistaPrincipale.getCampoInsegnamento();
            String campoCrediti = vistaPrincipale.getCampoCrediti();
            String campoVoto = vistaPrincipale.getCampoVoto();
            String campoGiorno = vistaPrincipale.getCampoGiorno();
            String campoMese = vistaPrincipale.getCampoMese();
            String campoAnno = vistaPrincipale.getCampoAnno();
            boolean checkLode = vistaPrincipale.getCheckLode();
            String errori = Utilita.verificaDatiEsame(campoInsegnamento, campoCrediti, campoVoto, checkLode, campoGiorno, campoMese, campoAnno);
            if (errori.equals("")) {
                Modello modello = Applicazione.getInstance().getModello();
                Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
                Calendar dataRegistrazione = Utilita.leggiData(campoGiorno, campoMese, campoAnno);
                studente.addEsame(campoInsegnamento, Integer.parseInt(campoVoto), checkLode, Integer.parseInt(campoCrediti), dataRegistrazione);
                vistaPrincipale.ripulisciEsame();
                vistaPrincipale.aggiornaMessaggioStato(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAESAMEINSERITO));
                vistaPrincipale.aggiornaCollezioniEsami();
            } else {
                Applicazione.getInstance().getFrame().finestraErrore(errori);
            }
        }
    }

    public class AzioneEliminaEsame extends javax.swing.AbstractAction {

        public AzioneEliminaEsame() {
            this.putValue(javax.swing.Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAELIMINAESAME));
            this.putValue(javax.swing.Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEELIMINAESAME));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Modello modello = Applicazione.getInstance().getModello();
            VistaPrincipale vistaPrincipale = Applicazione.getInstance().getVistaPrincipale();
            int indiceSelezione = vistaPrincipale.getSelectedIndex();
            if (indiceSelezione != -1) {
                Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
                studente.eliminaEsame(indiceSelezione);
                vistaPrincipale.aggiornaCollezioniEsami();
                vistaPrincipale.aggiornaMessaggioStato(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAESAMEELIMINATO));
            } else {
                Applicazione.getInstance().getFrame().finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGANECESSARIOSELEZIONAREESAME));
            }

        }
    }

    public class AzioneFinestraModificaEsame extends javax.swing.AbstractAction {

        public AzioneFinestraModificaEsame() {
            this.putValue(javax.swing.Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAMODIFICAESAME));
            this.putValue(javax.swing.Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEMODIFICAESAME));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Modello modello = Applicazione.getInstance().getModello();
            VistaPrincipale vistaPrincipale = Applicazione.getInstance().getVistaPrincipale();
            int indiceSelezione = vistaPrincipale.getSelectedIndex();
            if (indiceSelezione != -1) {
                Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
                Esame esame = studente.getEsame(indiceSelezione);
                modello.putBean(Costanti.ESAME, esame);
                Applicazione.getInstance().getVistaModificaEsame().visualizza();
            } else {
                Applicazione.getInstance().getFrame().finestraErrore(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGANECESSARIOSELEZIONAREESAME));
            }
        }
    }

    public class AzioneFinestraModificaStudente extends javax.swing.AbstractAction {

        public AzioneFinestraModificaStudente() {
            this.putValue(javax.swing.Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAMODIFICASTUDENTE));
            this.putValue(javax.swing.Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEMODIFICASTUDENTE));
            this.putValue(javax.swing.Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_M);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Applicazione.getInstance().getVistaModificaStudente().visualizza();
        }
    }

    public AzioneEliminaEsame getAzioneEliminaEsame() {
        return azioneEliminaEsame;
    }

    public AzioneFinestraModificaEsame getAzioneFinestraModificaEsame() {
        return azioneFinestraModificaEsame;
    }

    public AzioneFinestraModificaStudente getAzioneFinestraModificaStudente() {
        return azioneFinestraModificaStudente;
    }

    public AzioneInserisciEsame getAzioneInserisciEsame() {
        return azioneInserisciEsame;
    }

}
