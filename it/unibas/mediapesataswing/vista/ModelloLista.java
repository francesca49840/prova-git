package it.unibas.mediapesataswing.vista;

import it.unibas.mediapesataswing.modello.Studente;

public class ModelloLista extends javax.swing.AbstractListModel {

    private final Studente studente;

    public ModelloLista(Studente studente) {
        this.studente = studente;
    }

    public int getSize() {
        return this.studente.getNumeroEsami();
    }

    public Object getElementAt(int i) {
        return this.studente.getEsame(i);
    }

    public void aggiornaContenuto() {
        super.fireContentsChanged(this, 0, getSize());
    }
}
