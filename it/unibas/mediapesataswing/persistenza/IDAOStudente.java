package it.unibas.mediapesataswing.persistenza;

import it.unibas.mediapesataswing.modello.Studente;

public interface IDAOStudente {

    public Studente carica(String nomeFile) throws DAOException;

    public void salva(Studente studente, String nomeFile) throws DAOException;

}
