package it.unibas.mediapesataswing.controllo;

import it.unibas.mediapesataswing.Applicazione;
import it.unibas.mediapesataswing.Costanti;
import it.unibas.mediapesataswing.modello.Esame;
import it.unibas.mediapesataswing.modello.Modello;
import it.unibas.mediapesataswing.vista.Frame;
import it.unibas.mediapesataswing.vista.VistaModificaEsame;
import it.unibas.mediapesataswing.vista.VistaPrincipale;
import javax.swing.AbstractAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlloModificaEsame {

    private static final Logger logger = LoggerFactory.getLogger(ControlloModificaEsame.class.getName());
    
    private final AzioneCommitModificaEsame azioneCommitModificaEsame = new AzioneCommitModificaEsame();
    private final AzioneRollbackModificaEsame azioneRollbackModificaEsame = new AzioneRollbackModificaEsame();

    public class AzioneCommitModificaEsame extends javax.swing.AbstractAction {

        public AzioneCommitModificaEsame() {
            this.putValue(javax.swing.Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAMODIFICA));
            this.putValue(javax.swing.Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEMODIFICAESAME));
            this.putValue(javax.swing.Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_E);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            Modello modello = Applicazione.getInstance().getModello();
            VistaModificaEsame vistaModificaEsame = Applicazione.getInstance().getVistaModificaEsame();
            VistaPrincipale vistaPrincipale = Applicazione.getInstance().getVistaPrincipale();
            String campoInsegnamento = vistaModificaEsame.getCampoInsegnamento();
            String campoCrediti = vistaModificaEsame.getCampoCrediti();
            String campoVoto = vistaModificaEsame.getCampoVoto();
            String campoGiorno = vistaModificaEsame.getCampoGiorno();
            String campoMese = vistaModificaEsame.getCampoMese();
            String campoAnno = vistaModificaEsame.getCampoAnno();
            boolean checkLode = vistaPrincipale.getCheckLode();
            String errori = Utilita.verificaDatiEsame(campoInsegnamento, campoCrediti, campoVoto, checkLode, campoGiorno, campoMese, campoAnno);
            if (errori.equals("")) {
                Esame esame = (Esame) modello.getBean(Costanti.ESAME);
                esame.setInsegnamento(campoInsegnamento);
                esame.setCrediti(Integer.parseInt(campoCrediti));
                esame.setVoto(Integer.parseInt(campoVoto));
                esame.setLode(checkLode);
                esame.setDataRegistrazione(Utilita.leggiData(campoGiorno, campoMese, campoAnno));
                VistaPrincipale pannelloPrincipale = Applicazione.getInstance().getVistaPrincipale();
                pannelloPrincipale.aggiornaCollezioniEsami();
                vistaModificaEsame.nascondi();
                vistaPrincipale.aggiornaMessaggioStato(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAESAMEMODIFICATO));
            } else {
                Applicazione.getInstance().getFrame().finestraErrore(errori);
            }
        }
    }

    public class AzioneRollbackModificaEsame extends AbstractAction {

        public AzioneRollbackModificaEsame() {
            this.putValue(javax.swing.Action.NAME, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAANNULLA));
            this.putValue(javax.swing.Action.SHORT_DESCRIPTION, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADESCRIZIONEANNULLAMODIFICAESAME));
//            this.putValue(javax.swing.Action.MNEMONIC_KEY, java.awt.event.KeyEvent.VK_M);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            VistaModificaEsame finestra = Applicazione.getInstance().getVistaModificaEsame();
            finestra.setVisible(false);
            Frame frame = Applicazione.getInstance().getFrame();
            frame.setVisible(true);
        }
    }

    public AzioneCommitModificaEsame getAzioneCommitModificaEsame() {
        return azioneCommitModificaEsame;
    }

    public AzioneRollbackModificaEsame getAzioneRollbackModificaEsame() {
        return azioneRollbackModificaEsame;
    }


}
