package models;

import java.io.Serializable;

public class DashboardCentroVaccinale implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;
    private int vaccinati_con_eventi_avversi;
    private int somma_eventi_avversi;
    private int vaccinati;

    public DashboardCentroVaccinale(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVaccinati_con_eventi_avversi() {
        return vaccinati_con_eventi_avversi;
    }

    public void setVaccinati_con_eventi_avversi(int vaccinati_con_eventi_avversi) {
        this.vaccinati_con_eventi_avversi = vaccinati_con_eventi_avversi;
    }

    public int getSomma_eventi_avversi() {
        return somma_eventi_avversi;
    }

    public void setSomma_eventi_avversi(int somma_eventi_avversi) {
        this.somma_eventi_avversi = somma_eventi_avversi;
    }

    public int getVaccinati() {
        return vaccinati;
    }

    public void setVaccinati(int vaccinati) {
        this.vaccinati = vaccinati;
    }

    public String toString() {
        return "DashboardCentroVaccinale<[" + id + "] " + nome + ", vaccinati con ea=" + vaccinati_con_eventi_avversi +
                ", somma ea=" + somma_eventi_avversi + ", " + vaccinati + ">";
    }
}
