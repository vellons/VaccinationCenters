package models;

public class EventiAvversi {

    private int id;
    private int vaccinato_id;
    private int tipologia_evento_id;
    private String severita;
    private String note;

    public EventiAvversi(int id, int vaccinato_id, int tipologia_evento_id, String severita, String note) {
        this.id = id;
        this.vaccinato_id = vaccinato_id;
        this.tipologia_evento_id = tipologia_evento_id;
        this.severita = severita;
        this.note = note;
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

    public String getSeverita() {
        return severita;
    }

    public void setSeverita(String severita) {
        this.severita = severita;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
