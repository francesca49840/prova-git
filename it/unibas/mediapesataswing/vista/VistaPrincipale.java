package it.unibas.mediapesataswing.vista;

import com.jeta.forms.components.panel.FormPanel;
import it.unibas.mediapesataswing.Applicazione;
import it.unibas.mediapesataswing.Costanti;
import it.unibas.mediapesataswing.controllo.Utilita;
import it.unibas.mediapesataswing.modello.Modello;
import it.unibas.mediapesataswing.modello.Studente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VistaPrincipale extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(VistaPrincipale.class.getName());

    private FormPanel formPanel;
    private JTabbedPane tabbedPane;
    private JTable jTabellaEsami;
    private ModelloTabella modelloTabellaEsami;
    private JList jListaEsami;
    private ModelloLista modelloListaEsami;

    /* ********************************
     *     Inizializzazione
     * ****************************** */
    public void inizializza() {
        formPanel = new FormPanel("risorse/PannelloPrincipale.jfrm");
        this.setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.CENTER);
        inizializzaTesto();
        inizializzaAzioni();
        inizializzaCombo();
        this.tabbedPane = (JTabbedPane) formPanel.getComponentByName(Costanti.TABBEDPANE);
        tabbedPane.setTitleAt(0, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGATABELLAESAMI));
        tabbedPane.setTitleAt(1, Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGALISTAESAMI));
        JScrollPane scrollPaneTabella = (JScrollPane) formPanel.getComponentByName(Costanti.SCROLLPANETABELLAESAMI);
        scrollPaneTabella.getViewport().setBackground(Color.WHITE);
        jTabellaEsami = new JTable();
        scrollPaneTabella.setViewportView(jTabellaEsami);
        ListSelectionModel selezioneTabella = jTabellaEsami.getSelectionModel();
        selezioneTabella.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                Object oggetto = e.getSource();
                ListSelectionModel selection = (ListSelectionModel) oggetto;
                int indice = selection.getMinSelectionIndex();
                if (indice != -1) {
                    Applicazione.getInstance().getControlloPrincipale().getAzioneFinestraModificaEsame().setEnabled(true);
                    Applicazione.getInstance().getControlloPrincipale().getAzioneEliminaEsame().setEnabled(true);
                } else {
                    Applicazione.getInstance().getControlloPrincipale().getAzioneFinestraModificaEsame().setEnabled(false);
                    Applicazione.getInstance().getControlloPrincipale().getAzioneEliminaEsame().setEnabled(false);
                }
            }
        });
        jListaEsami = formPanel.getList(Costanti.JLISTESAMI);
        this.jListaEsami.setVisibleRowCount(20);
        // le selezioni vengono considerate solo sulla tabella
//        ListSelectionModel selezioneLista = jListaEsami.getSelectionModel();
//        selezioneLista.addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent e) {
//                Object oggetto = e.getSource();
//                ListSelectionModel selection = (ListSelectionModel) oggetto;
//                int indice = selection.getMinSelectionIndex();
//                if (indice != -1) {
//                    Applicazione.getInstance().getControlloPrincipale().getAzioneFinestraModificaEsame.setEnabled(true);
//                    Applicazione.getInstance().getControlloPrincipale().getAzioneEliminaEsame.setEnabled(true);
//                } else {
//                    Applicazione.getInstance().getControlloPrincipale().getAzioneFinestraModificaEsame.setEnabled(false);
//                    Applicazione.getInstance().getControlloPrincipale().getAzioneEliminaEsame.setEnabled(false);
//                }
//            }
//        });
        this.disabilitaBottoni();
        this.disabilitaControlliEsame();
    }

    private void inizializzaCombo() {
        JComboBox comboGiorni = this.formPanel.getComboBox(Costanti.COMBOBOXGIORNO);
        Utilita.popolaCombo(comboGiorni, 1, 31);
        JComboBox comboMese = this.formPanel.getComboBox(Costanti.COMBOBOXMESE);
        Utilita.popolaCombo(comboMese, 1, 12);
        JComboBox comboAnno = this.formPanel.getComboBox(Costanti.COMBOBOXANNO);
        Utilita.popolaCombo(comboAnno, 2000, new GregorianCalendar().get(Calendar.YEAR));
    }

    private void inizializzaAzioni() {
        this.formPanel.getButton(Costanti.BOTTONEFINESTRAMODIFICASTUDENTE).setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneFinestraModificaStudente());
        this.formPanel.getButton(Costanti.BOTTONEINSERISCIESAME).setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneInserisciEsame());
        this.formPanel.getButton(Costanti.BOTTONEFINESTRAMODIFICAESAME).setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneFinestraModificaEsame());
        this.formPanel.getButton(Costanti.BOTTONEELIMINAESAME).setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneEliminaEsame());
        this.formPanel.getButton(Costanti.BOTTONEFINESTRAMODIFICAESAMELISTA).setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneFinestraModificaEsame());
        this.formPanel.getButton(Costanti.BOTTONEELIMINAESAMELISTA).setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneEliminaEsame());
    }

    private void inizializzaTesto() {
        this.formPanel.getLabel(Costanti.STRINGADATISTUDENTE).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADATISTUDENTE));
        this.formPanel.getLabel(Costanti.STRINGANOME).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGANOME));
        this.formPanel.getLabel(Costanti.STRINGACOGNOME).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGACOGNOME));
        this.formPanel.getLabel(Costanti.STRINGAMATRICOLA).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAMATRICOLA));
        this.formPanel.getLabel(Costanti.STRINGAINSERISCIESAME).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAINSERISCIESAME));
        this.formPanel.getLabel(Costanti.STRINGAINSEGNAMENTO).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAINSEGNAMENTO));
        this.formPanel.getLabel(Costanti.STRINGACREDITI).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGACREDITI));
        this.formPanel.getLabel(Costanti.STRINGAVOTO).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAVOTO));
        this.formPanel.getCheckBox(Costanti.CHECKBOXLODE).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGALODE));
        this.formPanel.getLabel(Costanti.STRINGAGIORNO).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAGIORNO));
        this.formPanel.getLabel(Costanti.STRINGAMESE).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAMESE));
        this.formPanel.getLabel(Costanti.STRINGAANNO).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAANNO));
        this.formPanel.getTextField(Costanti.TEXTFIELDINSEGNAMENTO).setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneInserisciEsame());
        this.formPanel.getTextField(Costanti.TEXTFIELDCREDITI).setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneInserisciEsame());
        this.formPanel.getTextField(Costanti.TEXTFIELDVOTO).setAction(Applicazione.getInstance().getControlloPrincipale().getAzioneInserisciEsame());
        this.formPanel.getLabel(Costanti.STRINGAELENCOESAMI).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAELENCOESAMI));
        this.formPanel.getLabel(Costanti.STRINGAMEDIAPESATA).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAMEDIAPESATA));
        this.formPanel.getLabel(Costanti.STRINGACREDITITOTALI).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGACREDITITOTALI));
        this.formPanel.getLabel(Costanti.STRINGAMEDIAPESATA30MI).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAMEDIAPESATA30MI));
        this.formPanel.getLabel(Costanti.STRINGAMEDIAPESATA110MI).setText(Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAMEDIAPESATA110MI));
    }

    private void disabilitaBottoni() {
        Applicazione.getInstance().getControlloPrincipale().getAzioneFinestraModificaStudente().setEnabled(false);
        Applicazione.getInstance().getControlloPrincipale().getAzioneInserisciEsame().setEnabled(false);
        Applicazione.getInstance().getControlloPrincipale().getAzioneFinestraModificaEsame().setEnabled(false);
        Applicazione.getInstance().getControlloPrincipale().getAzioneEliminaEsame().setEnabled(false);
    }

    /* ********************************
     *     Schermo Studente
     * ****************************** */
    public void schermoStudente() {
        Modello modello = Applicazione.getInstance().getModello();
        Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
        this.formPanel.getLabel(Costanti.LABELNOME).setText(studente.getNome());
        this.formPanel.getLabel(Costanti.LABELCOGNOME).setText(studente.getCognome());
        this.formPanel.getLabel(Costanti.LABELMATRICOLA).setText(studente.getMatricola() + "");
        this.creaCollezioniEsami();
        this.abilitaControlliEsame();
    }

    public void ripulisciEsame() {
        this.formPanel.getTextField(Costanti.TEXTFIELDINSEGNAMENTO).setText("");
        this.formPanel.getTextField(Costanti.TEXTFIELDCREDITI).setText("");
        this.formPanel.getTextField(Costanti.TEXTFIELDVOTO).setText("");
        this.formPanel.getCheckBox(Costanti.CHECKBOXLODE).setSelected(false);
    }

    public void calcolaMedia() {
        Modello modello = Applicazione.getInstance().getModello();
        Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
        if (studente != null && studente.getNumeroEsami() != 0) {
            DecimalFormat formattatore = new DecimalFormat("###.##");
            this.formPanel.getLabel(Costanti.LABELCREDITITOTALI).setText(studente.getCreditiTotali() + "");
            this.formPanel.getLabel(Costanti.LABELMEDIA30MI).setText(formattatore.format(studente.getMedia30mi()));
            this.formPanel.getLabel(Costanti.LABELMEDIA110MI).setText(formattatore.format(studente.getMedia110mi()));
        } else {
            this.formPanel.getLabel(Costanti.LABELCREDITITOTALI).setText("");
            this.formPanel.getLabel(Costanti.LABELMEDIA30MI).setText("");
            this.formPanel.getLabel(Costanti.LABELMEDIA110MI).setText("");
        }
    }

    public void creaCollezioniEsami() {
        calcolaMedia();
        creaListaEsami();
        creaTabellaEsami();
    }

    @SuppressWarnings("unchecked")
    private void creaListaEsami() {
        Modello modello = Applicazione.getInstance().getModello();
        Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
        this.modelloListaEsami = new ModelloLista(studente);
        this.jListaEsami.setModel(modelloListaEsami);
    }

    private void creaTabellaEsami() {
        Modello modello = Applicazione.getInstance().getModello();
        Studente studente = (Studente) modello.getBean(Costanti.STUDENTE);
        this.modelloTabellaEsami = new ModelloTabella(studente);
        this.jTabellaEsami.setModel(modelloTabellaEsami);
    }

    public void aggiornaCollezioniEsami() {
        calcolaMedia();
        this.modelloTabellaEsami.aggiornaContenuto();
        this.modelloListaEsami.aggiornaContenuto();
    }


    public void disabilitaControlliEsame() {
        this.formPanel.getTextField(Costanti.TEXTFIELDINSEGNAMENTO).setEnabled(false);
        this.formPanel.getTextField(Costanti.TEXTFIELDCREDITI).setEnabled(false);
        this.formPanel.getTextField(Costanti.TEXTFIELDVOTO).setEnabled(false);
        this.formPanel.getCheckBox(Costanti.CHECKBOXLODE).setEnabled(false);
        this.formPanel.getComboBox(Costanti.COMBOBOXGIORNO).setEnabled(false);
        this.formPanel.getComboBox(Costanti.COMBOBOXMESE).setEnabled(false);
        this.formPanel.getComboBox(Costanti.COMBOBOXANNO).setEnabled(false);
    }

    public void abilitaControlliEsame() {
        this.formPanel.getTextField(Costanti.TEXTFIELDINSEGNAMENTO).setEnabled(true);
        this.formPanel.getTextField(Costanti.TEXTFIELDCREDITI).setEnabled(true);
        this.formPanel.getTextField(Costanti.TEXTFIELDVOTO).setEnabled(true);
        this.formPanel.getCheckBox(Costanti.CHECKBOXLODE).setEnabled(true);
        this.formPanel.getComboBox(Costanti.COMBOBOXGIORNO).setEnabled(true);
        this.formPanel.getComboBox(Costanti.COMBOBOXMESE).setEnabled(true);
        this.formPanel.getComboBox(Costanti.COMBOBOXANNO).setEnabled(true);
    }

    public void aggiornaMessaggioStato(String messaggio) {
        this.formPanel.getLabel(Costanti.LABELSTATUSBAR).setText(":: " + messaggio + " ::");
    }

    public String getCampoInsegnamento() {
        return this.formPanel.getTextField(Costanti.TEXTFIELDINSEGNAMENTO).getText();
    }

    public String getCampoCrediti() {
        return this.formPanel.getTextField(Costanti.TEXTFIELDCREDITI).getText();
    }

    public String getCampoVoto() {
        return this.formPanel.getTextField(Costanti.TEXTFIELDVOTO).getText();
    }

    public boolean getCheckLode() {
        return this.formPanel.getCheckBox(Costanti.CHECKBOXLODE).isSelected();
    }

    public int getSelectedIndex() {
//        return this.jListaEsami.getSelectedIndex();
        return this.jTabellaEsami.getSelectedRow();
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
