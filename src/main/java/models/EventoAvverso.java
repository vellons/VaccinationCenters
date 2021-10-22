package models;

import java.io.Serializable;

public class EventoAvverso implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int vaccinato_id;
    private int tipologia_evento_id;
    private int severita;
    private String note;

    public EventoAvverso(int id, int vaccinato_id, int tipologia_evento_id, int severita, String note) {
        this.id = id;
        this.vaccinato_id = vaccinato_id;
        this.tipologia_evento_id = tipologia_evento_id;
        this.severita = severita;
        this.note = note;
    }

    public EventoAvverso(int vaccinato_id, int tipologia_evento_id, int severita, String note) {
        this.vaccinato_id = vaccinato_id;
        this.tipologia_evento_id = tipologia_evento_id;
        this.severita = severita;
        this.note = note;
    }

    public EventoAvverso(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVaccinato_id() {
        return vaccinato_id;
    }

    public void setVaccinato_id(int vaccinato_id) {
        this.vaccinato_id = vaccinato_id;
    }

    public int getTipologia_evento_id() {
        return tipologia_evento_id;
    }

    public void setTipologia_evento_id(int tipologia_evento_id) {
        this.tipologia_evento_id = tipologia_evento_id;
    }

    public int getSeverita() {
        return severita;
    }

    public void setSeverita(int severita) {
        this.severita = severita;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String toString() {
        return "EventoAvverso<[" + id + "], vaccinato=" + vaccinato_id + ", tipo=" + tipologia_evento_id + ", severita=" + severita + ", " + note + ">";
    }
}
