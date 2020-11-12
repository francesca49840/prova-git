package it.unibas.mediapesataswing.vista;

import it.unibas.mediapesataswing.Applicazione;
import it.unibas.mediapesataswing.Costanti;
import it.unibas.mediapesataswing.modello.Studente;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ModelloTabella extends javax.swing.table.AbstractTableModel {

    private final Studente studente;

    public ModelloTabella(Studente studente) {
        this.studente = studente;
    }

    public Object getValueAt(int x, int y) {
        if (y == 0) {
            return studente.getEsame(x).getInsegnamento();
        } else if (y == 1) {
            return studente.getEsame(x).getCrediti();
        } else if (y == 2) {
            return studente.getEsame(x).getVoto();
        } else if (y == 3) {
            return studente.getEsame(x).isLode();
        } else if (y == 4) {
            DateFormat df = SimpleDateFormat.getDateInstance();
            return df.format(studente.getEsame(x).getDataRegistrazione().getTime());
        }
        return null;
    }

    public int getRowCount() {
        return studente.getNumeroEsami();
    }

    @Override
    public String getColumnName(int i) {
        if (i == 0) {
            return Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAINSEGNAMENTO);
        } else if (i == 1) {
            return Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGACREDITI);
        } else if (i == 2) {
            return Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGAVOTO);
        } else if (i == 3) {
            return Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGALODE);
        } else if (i == 4) {
            return Applicazione.getInstance().getResourceManager().getStringResource(Costanti.STRINGADATA);
        }
        return null;
    }

    public int getColumnCount() {
        return 5;
    }

    @Override
    public Class getColumnClass(int i) {
        if (i == 3) {
            return Boolean.class;
        }
        return super.getColumnClass(i);
    }
    
    public void aggiornaContenuto() {
        super.fireTableDataChanged();
    }
    
}
