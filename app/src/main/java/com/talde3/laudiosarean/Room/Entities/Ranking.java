package com.talde3.laudiosarean.Room.Entities;

public class Ranking {
    private String izena;
    private int puntuazioa;
    private String abizenak;
    private String gunea;

    // Constructors
    public Ranking(String gunea, String izena, int puntuazioa, String abizenak) {
        this.gunea = gunea;
        this.izena = izena;
        this.abizenak = abizenak;
        this.puntuazioa = puntuazioa;
    }
    public Ranking(){}

    // Getters
    public String getIzena() {
        return izena;
    }
    public int getPuntuazioa() {
        return puntuazioa;
    }
    public String getAbizenak() {
        return abizenak;
    }
    public String getGunea() {
        return gunea;
    }

    // Setters
    public void setIzena(String izena) {
        this.izena = izena;
    }
    public void setPuntuazioa(int puntuazioa) {
        this.puntuazioa = puntuazioa;
    }
    public void setAbizenak(String abizenak) {
        this.abizenak = abizenak;
    }
    public void setGunea(String gunea){
        this.gunea = gunea;
    }
}
