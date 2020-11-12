package it.unibas.mediapesataswing.vista;

import com.jeta.forms.components.panel.FormPanel;
import it.unibas.mediapesataswing.Applicazione;
import it.unibas.mediapesataswing.Costanti;
import it.unibas.mediapesataswing.controllo.Utilita;
import it.unibas.mediapesataswing.modello.Esame;
import it.unibas.mediapesataswing.modello.Modello;
import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class VistaModificaEsame extends JDialog {

    private FormPanel formPanel;
    
    public VistaModificaEsame(Frame frame){
        super(frame);
    }

    public void inizializza() {
        this.setModal(true);
        this.formPanel = new FormPanel("risorse/PannelloFinestraEsame.jfrm");
        this.setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.CENTER);
        // azioni
        Action azioneCommitModificaEsame = Applicazione.getInstance().getControlloModificaEsame().getAzioneCommitModificaEsame();
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICAINSEGNAMENTO).setAction(azioneCommitModificaEsame);
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICACREDITI).setAction(azioneCommitModificaEsame);
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICAVOTO).setAction(azioneCommitModificaEsame);
        formPanel.getButton(Costanti.BOTTONECOMMITMODIFICAESAME).setAction(azioneCommitModificaEsame);
        formPanel.getButton(Costanti.BOTTONEROLLBACKMODIFICAESAME).setAction(Applicazione.getInstance().getControlloModificaEsame().getAzioneRollbackModificaEsame());
        // stringhe
        formPanel.getLabel(Costanti.STRINGAINSEGNAMENTO).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAINSEGNAMENTO));
        formPanel.getLabel(Costanti.STRINGACREDITI).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGACREDITI));
        formPanel.getLabel(Costanti.STRINGAVOTO).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAVOTO));
        formPanel.getLabel(Costanti.STRINGALODE).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGALODE));
//        formPanel.getCheckBox(Costanti.CHECKBOXMODIFICALODE).setText(applicazione.getStringFromBundle(Costanti.STRINGALODE));
        inizializzaCombo();
        // show
        this.pack();
        this.setLocationRelativeTo(Applicazione.getInstance().getFrame());
    }

    private void inizializzaCombo() {
        JComboBox comboGiorni = this.formPanel.getComboBox(Costanti.COMBOBOXGIORNO);
        Utilita.popolaCombo(comboGiorni, 1, 31);
        JComboBox comboMese = this.formPanel.getComboBox(Costanti.COMBOBOXMESE);
        Utilita.popolaCombo(comboMese, 1, 12);
        JComboBox comboAnno = this.formPanel.getComboBox(Costanti.COMBOBOXANNO);
        Utilita.popolaCombo(comboAnno, 2000, new GregorianCalendar().get(Calendar.YEAR));
    }

    public void visualizza() {
        this.schermoEsame();
        this.setVisible(true);
    }

    public void nascondi() {
        this.setVisible(false);
    }

    public void schermoEsame() {
        Modello modello = Applicazione.getInstance().getModello();
        Esame esame = (Esame) modello.getBean(Costanti.ESAME);
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICAINSEGNAMENTO).setText(esame.getInsegnamento());
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICACREDITI).setText(esame.getCrediti() + "");
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICAVOTO).setText(esame.getVoto() + "");
        formPanel.getCheckBox(Costanti.CHECKBOXMODIFICALODE).setSelected(esame.isLode());
        formPanel.getComboBox(Costanti.COMBOBOXGIORNO).setSelectedItem(esame.getDataRegistrazione().get(Calendar.DAY_OF_MONTH));
        formPanel.getComboBox(Costanti.COMBOBOXMESE).setSelectedItem(esame.getDataRegistrazione().get(Calendar.MONTH) + 1);
        formPanel.getComboBox(Costanti.COMBOBOXANNO).setSelectedItem(esame.getDataRegistrazione().get(Calendar.YEAR));
    }

    public void disabilitaControlli() {
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICAINSEGNAMENTO).setEnabled(false);
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICACREDITI).setEnabled(false);
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICAVOTO).setEnabled(false);
        formPanel.getCheckBox(Costanti.CHECKBOXMODIFICALODE).setEnabled(false);
        formPanel.getComboBox(Costanti.COMBOBOXGIORNO).setEnabled(false);
        formPanel.getComboBox(Costanti.COMBOBOXMESE).setEnabled(false);
        formPanel.getComboBox(Costanti.COMBOBOXANNO).setEnabled(false);
    }

    public void abilitaControlli() {
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICAINSEGNAMENTO).setEnabled(true);
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICACREDITI).setEnabled(true);
        formPanel.getTextField(Costanti.TEXTFIELDMODIFICAVOTO).setEnabled(true);
        formPanel.getCheckBox(Costanti.CHECKBOXMODIFICALODE).setEnabled(true);
        formPanel.getComboBox(Costanti.COMBOBOXGIORNO).setEnabled(true);
        formPanel.getComboBox(Costanti.COMBOBOXMESE).setEnabled(true);
        formPanel.getComboBox(Costanti.COMBOBOXANNO).setEnabled(true);
    }

    public String getCampoInsegnamento() {
        return formPanel.getTextField(Costanti.TEXTFIELDMODIFICAINSEGNAMENTO).getText();
    }

    public String getCampoCrediti() {
        return formPanel.getTextField(Costanti.TEXTFIELDMODIFICACREDITI).getText();
    }

    public String getCampoVoto() {
        return formPanel.getTextField(Costanti.TEXTFIELDMODIFICAVOTO).getText();
    }

    public boolean getCheckLode() {
        return formPanel.getCheckBox(Costanti.CHECKBOXMODIFICALODE).isSelected();
    }

    public String getCampoGiorno() {
        return this.formPanel.getComboBox(Costanti.COMBOBOXGIORNO).getSelectedItem().toString();
    }

    public String getCampoMese() {
        return this.formPanel.getComboBox(Costanti.COMBOBOXMESE).getSelectedItem().toString();
    }

    public String getCampoAnno() {
        return this.formPanel.getComboBox(Costanti.COMBOBOXANNO).getSelectedItem().toString();
    }

}
