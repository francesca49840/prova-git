package it.unibas.mediapesataswing.persistenza;

import it.unibas.mediapesataswing.modello.Esame;
import it.unibas.mediapesataswing.modello.Studente;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOStudenteCSV implements IDAOStudente {

    private static Logger logger = LoggerFactory.getLogger(DAOStudenteCSV.class);

    /* ******************************************
     *               Salvataggio
     * ****************************************** */
    public void salva(Studente studente, String nomeFile) throws DAOException {
        PrintWriter flusso = null;
        try {
            FileWriter fileWriter = new FileWriter(nomeFile);
            flusso = new PrintWriter(fileWriter);
            flusso.println("Studente:");
            flusso.println(studente.toSaveString());
            flusso.println("--------------------------------------");
            salvaEsami(studente, flusso);
        } catch (IOException ioe) {
            logger.error("Errore nel salvataggio dello studente. " + ioe.getLocalizedMessage());
            throw new DAOException(ioe);
        } finally {
            if (flusso != null) {
                flusso.close();
            }
        }
    }

    private void salvaEsami(Studente studente, PrintWriter flusso) {
        for (int i = 0; i < studente.getNumeroEsami(); i++) {
            flusso.println(studente.getEsame(i).toSaveString());
        }
    }

    /* ******************************************
     *               Caricamento
     * ****************************************** */
    public Studente carica(String nomeFile) throws DAOException {
        Studente studente = new Studente();
        BufferedReader flusso = null;
        try {
            FileReader fileReader = new FileReader(nomeFile);
            flusso = new BufferedReader(fileReader);
            estraiDatiStudente(studente, flusso);
            caricaEsami(studente, flusso);
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            try {
                if (flusso != null) {
                    flusso.close();
                }
            } catch (IOException ioe) {
            }
        }
        return studente;
    }

    private void estraiDatiStudente(Studente studente, BufferedReader flusso)
            throws IOException {
        flusso.readLine();
        String lineaStudente = flusso.readLine();
        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(lineaStudente, ",");
        studente.setCognome(tokenizer.nextToken().trim());
        studente.setNome(tokenizer.nextToken().trim());
        studente.setMatricola(Integer.parseInt(tokenizer.nextToken().trim()));
    }

    private void caricaEsami(Studente studente, BufferedReader flusso)
            throws IOException, ParseException {
        flusso.readLine();
        String lineaEsame;
        while ((lineaEsame = flusso.readLine()) != null) {
            studente.addEsame(estraiDatiEsame(lineaEsame, flusso));
        }
    }

    private Esame estraiDatiEsame(String lineaEsame, BufferedReader flusso) throws ParseException {
        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(lineaEsame, ",");
        String insegnamento = tokenizer.nextToken().trim();
        int crediti = Integer.parseInt(tokenizer.nextToken().trim());
        int voto = Integer.parseInt(tokenizer.nextToken().trim());
        boolean lode = Boolean.valueOf(tokenizer.nextToken().trim());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar dataRegistrazione = new GregorianCalendar();
        Date data = df.parse(tokenizer.nextToken().trim());
        dataRegistrazione.setTime(data);
        return new Esame(insegnamento, voto, lode, crediti, dataRegistrazione);
    }

}
