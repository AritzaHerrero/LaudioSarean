package com.talde3.laudiosarean.Room.Entities;

public class Ranking {
    private String izena;
    private int puntuazioa;

    // Constructor
    public Ranking(String izena, int puntuazioa) {
        this.izena = izena;
        this.puntuazioa = puntuazioa;
    }

    // Getters
    public String getIzena() {
        return izena;
    }
    public int getPuntuazioa() {
        return puntuazioa;
    }

    // Setters
    public void setIzena(String izena) {
        this.izena = izena;
    }
    public void setPuntuazioa(int puntuazioa) {
        this.puntuazioa = puntuazioa;
    }
}
