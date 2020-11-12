package it.unibas.mediapesataswing.controllo;

import it.unibas.mediapesataswing.Applicazione;
import it.unibas.mediapesataswing.Costanti;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import javax.swing.JComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilita {

    private static Logger logger = LoggerFactory.getLogger(Utilita.class);

    public static String verificaDatiEsame(String campoInsegnamento, String campoCrediti, String campoVoto, boolean checkLode, String giorno, String mese, String anno) {
        Applicazione applicazione = Applicazione.getInstance();
        String errori = "";
        if (campoInsegnamento == null || campoInsegnamento.equals("")) {
            errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGAINSEGNAMENTOVUOTO) + "\n";
        }
        try {
            int crediti = Integer.parseInt(campoCrediti);
            if (crediti < 0 || crediti > 30) {
                errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGAERRORECREDITI) + "\n";
            }
        } catch (NumberFormatException nfe) {
            errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGACREDITINUMERO) + "\n";
        }
        try {
            int voto = Integer.parseInt(campoVoto);
            if (voto < 18 || voto > 30) {
                errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGAERROREVOTO) + "\n";
            }
        } catch (NumberFormatException nfe) {
            errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGAVOTONUMERO) + "\n";
        }
        if (errori.equals("")) {
            if (checkLode && Integer.parseInt(campoVoto) != 30) {
                errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGAERRORELODE) + "\n";
            }
        }
        try {
            Calendar calendar = leggiData(giorno, mese, anno);
            calendar.getTime();
        } catch (Exception e) {
            errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGAERROREGIORNO) + "\n";
        }
        return errori;
    }

    public static String verificaDatiStudente(String campoNome, String campoCognome, String campoMatricola) {
        Applicazione applicazione = Applicazione.getInstance();
        String errori = "";
        if (campoNome == null || campoNome.equals("")) {
            errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGANOMEVUOTO) + "\n";
        }
        if (campoCognome == null || campoCognome.equals("")) {
            errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGACOGNOMEVUOTO) + "\n";
        }
        try {
            int matricola = Integer.parseInt(campoMatricola);
            if (matricola < 0) {
                errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGAERROREMATRICOLA) + "\n";
            }
        } catch (NumberFormatException nfe) {
            errori += applicazione.getResourceManager().getStringResource(Costanti.STRINGAMATRICOLANUMERO) + "\n";
        }
        return errori;
    }

    public static void abilitaAzioniPerNuovoStudente() {
        Applicazione applicazione = Applicazione.getInstance();
        applicazione.getControlloPrincipale().getAzioneFinestraModificaStudente().setEnabled(true);
        applicazione.getControlloPrincipale().getAzioneInserisciEsame().setEnabled(true);
        applicazione.getControlloPrincipale().getAzioneEliminaEsame().setEnabled(true);
        applicazione.getControlloModificaEsame().getAzioneCommitModificaEsame().setEnabled(true);
        applicazione.getControlloModificaStudente().getAzioneCommitModificaStudente().setEnabled(true);
        applicazione.getControlloMenu().getAzioneSalva().setEnabled(true);
        applicazione.getControlloMenu().getAzioneApriExcel().setEnabled(true);
        applicazione.getControlloMenu().getAzioneApriExcelNoThread().setEnabled(true);
    }

    public static int apriFileExcel(String nomeFile) throws Exception {
        // apertura in modalità non bloccante
        //Desktop.getDesktop().open(new File(nomeFile));
        // apertura in modalità bloccante
        Runtime runtime = Runtime.getRuntime();
        String sistemaOperativo = System.getProperty("os.name");
        String percorsoExcel;
        if (sistemaOperativo.startsWith("Win")) {
            percorsoExcel = Applicazione.getInstance().getDaoConfigurazione().caricaConfigurazione().getPercorsoExcelWin();
        } else if (sistemaOperativo.startsWith("Mac")) {
            percorsoExcel = Applicazione.getInstance().getDaoConfigurazione().caricaConfigurazione().getPercorsoExcelMac();
        } else {
            throw new IllegalArgumentException("Il sistema operativo " + sistemaOperativo + " non e' supportato");
        }
        logger.info("Eseguo il comando " + percorsoExcel);
        Process process = runtime.exec(new String[]{percorsoExcel, nomeFile});
        process.waitFor();
        int exitCode = process.exitValue();
        return exitCode;
    }

    public static Calendar leggiData(String giorno, String mese, String anno) {
        Calendar calendar = new GregorianCalendar();
        calendar.setLenient(false);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(giorno));
        calendar.set(Calendar.MONTH, Integer.parseInt(mese) - 1);
        calendar.set(Calendar.YEAR, Integer.parseInt(anno));
        return calendar;
    }

    @SuppressWarnings("unchecked")
    public static void popolaCombo(JComboBox comboGiorni, int inizio, int fine) {
        for (int i = inizio; i <= fine; i++) {
            comboGiorni.addItem(i);
        }
    }

}
