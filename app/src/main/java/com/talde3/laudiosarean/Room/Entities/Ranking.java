package com.talde3.laudiosarean.Room.Entities;

public class Ranking {
    private String izena;
    private int puntuazioa;
    private String abizenak;

    // Constructor
    public Ranking(String izena, int puntuazioa, String abizenak) {
        this.izena = izena;
        this.abizenak = abizenak;
        this.puntuazioa = puntuazioa;
    }

    // Getters
    public String getIzena() {
        return izena;
    }
    public int getPuntuazioa() {
        return puntuazioa;
    }
    public String getAbizenak() { return abizenak; }

    // Setters
    public void setIzena(String izena) {
        this.izena = izena;
    }
    public void setPuntuazioa(int puntuazioa) {
        this.puntuazioa = puntuazioa;
    }
    public void setAbizenak(String abizenak) { this.abizenak = abizenak; }
}
