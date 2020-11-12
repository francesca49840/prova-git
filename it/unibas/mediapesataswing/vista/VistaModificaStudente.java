package it.unibas.mediapesataswing.vista;

import com.jeta.forms.components.panel.FormPanel;
import it.unibas.mediapesataswing.Applicazione;
import it.unibas.mediapesataswing.Costanti;
import it.unibas.mediapesataswing.modello.Modello;
import it.unibas.mediapesataswing.modello.Studente;
import java.awt.BorderLayout;
import javax.swing.Action;
import javax.swing.JDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VistaModificaStudente extends JDialog {

    private static final Logger logger = LoggerFactory.getLogger(VistaModificaStudente.class.getName());
    private FormPanel formPanel;
    
    public VistaModificaStudente(Frame frame){
        super(frame);
    }

    public void inizializza() {
        this.setModal(true);
        this.setTitle(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGATITOLOMODIFICASTUDENTE));
        this.formPanel = new FormPanel("risorse/PannelloFinestraStudente.jfrm");
        this.setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.CENTER);
        Action azioneAggiornaStudente = Applicazione.getInstance().getControlloModificaStudente().getAzioneCommitModificaStudente();
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICANOME).setAction(azioneAggiornaStudente);
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICACOGNOME).setAction(azioneAggiornaStudente);
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICAMATRICOLA).setAction(azioneAggiornaStudente);
        formPanel.getButton(Costanti.BOTTONECOMMITMODIFICASTUDENTE).setAction(azioneAggiornaStudente);
        formPanel.getButton(Costanti.BOTTONEROLLBACKMODIFICASTUDENTE).setAction(Applicazione.getInstance().getControlloModificaStudente().getAzioneRollabckModificaStudente());
        // stringhe
        formPanel.getLabel(Costanti.STRINGANOME).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGANOME));
        formPanel.getLabel(Costanti.STRINGACOGNOME).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGACOGNOME));
        formPanel.getLabel(Costanti.STRINGAMATRICOLA).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAMATRICOLA));
        this.pack();
        this.setLocationRelativeTo(Applicazione.getInstance().getFrame());
    }

    public void visualizza() {
        Modello modello = Applicazione.getInstance().getModello();
        Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICANOME).setText(studente.getNome());
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICACOGNOME).setText(studente.getCognome());
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICAMATRICOLA).setText(studente.getMatricola() + "");
        this.setVisible(true);
    }

    public void nascondi() {
        this.setVisible(false);
    }

    public String getCampoNome() {
        return this.formPanel.getTextField(Costanti.TEXTFIELDMODIFICANOME).getText();
    }

    public String getCampoCognome() {
        return this.formPanel.getTextField(Costanti.TEXTFIELDMODIFICACOGNOME).getText();
    }

    public String getCampoMatricola() {
        return this.formPanel.getTextField(Costanti.TEXTFIELDMODIFICAMATRICOLA).getText();
    }
}
