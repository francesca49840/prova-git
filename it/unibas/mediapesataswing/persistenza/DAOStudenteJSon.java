package it.unibas.mediapesataswing.persistenza;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.unibas.mediapesataswing.modello.Studente;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOStudenteJSon implements IDAOStudente {

    private static Logger logger = LoggerFactory.getLogger(DAOStudenteJSon.class);

    /* ******************************************
     *               Caricamento
     * ****************************************** */
    @Override
    public Studente carica(String nomeFile) throws DAOException {
        if (logger.isDebugEnabled()) logger.debug("Carico lo studente dal file " + nomeFile);
        Studente studente = null;
        FileReader flusso = null;
        try {
            flusso = new FileReader(nomeFile);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Calendar.class, new AdapterCalendario());
            Gson gson = builder.create();
            studente = gson.fromJson(flusso, Studente.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        } finally {
            try {
                if (flusso != null) {
                    flusso.close();
                }
            } catch (java.io.IOException ioe) {
            }
        }
        return studente;
    }

    /* ******************************************
     *               Salvataggio
     * ****************************************** */
    @Override
    public void salva(Studente studente, String nomeFile) throws DAOException {
        PrintWriter flusso = null;
        try {
            FileWriter fileWriter = new FileWriter(nomeFile);
            flusso = new java.io.PrintWriter(fileWriter);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(GregorianCalendar.class, new AdapterCalendario());
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            String stringaJson = gson.toJson(studente);
            flusso.print(stringaJson);
        } catch (IOException ioe) {
            throw new DAOException(ioe);
        } finally {
            if (flusso != null) {
                flusso.close();
            }
        }

    }

    private class AdapterCalendario implements JsonSerializer<GregorianCalendar>, JsonDeserializer<GregorianCalendar> {

        public JsonElement serialize(GregorianCalendar calendario, Type tipo, JsonSerializationContext context) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return new JsonPrimitive(dateFormat.format(calendario.getTime()));
        }

        public GregorianCalendar deserialize(JsonElement json, Type tipo, JsonDeserializationContext context) throws JsonParseException {
            try {
                String stringaData = json.getAsString();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date dataRegistrazione = dateFormat.parse(stringaData);
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(dataRegistrazione);
                return calendar;
            } catch (ParseException ex) {
                throw new JsonParseException(ex);
            }
        }
    }
}
