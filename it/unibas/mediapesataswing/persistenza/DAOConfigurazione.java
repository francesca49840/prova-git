package it.unibas.mediapesataswing.persistenza;

import it.unibas.mediapesataswing.modello.Configurazione;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DAOConfigurazione {

    public Configurazione caricaConfigurazione() throws DAOException {
        try {
            Configurazione configurazione = new Configurazione();
            Properties proprieta = caricaProperties();
            configurazione.setPercorsoExcelWin(proprieta.getProperty("excel.path.win"));
            configurazione.setPercorsoExcelMac(proprieta.getProperty("excel.path.mac"));
            return configurazione;
        } catch (IOException e) {
            throw new DAOException("Errore nel caricamento della configurazione\n" + e.getMessage());
        } catch (NumberFormatException e) {
            throw new DAOException("Errore nel nel formato del file di configurazione\n" + e.getMessage());
        }
    }

    private Properties caricaProperties() throws IOException {
        Properties proprieta = new Properties();
        FileInputStream stream = null;
        try {
            proprieta.load(DAOConfigurazione.class.getResourceAsStream("/config.properties"));
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return proprieta;
    }

}
