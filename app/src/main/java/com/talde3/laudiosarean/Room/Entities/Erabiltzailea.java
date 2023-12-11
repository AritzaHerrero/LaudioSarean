package com.talde3.laudiosarean.Room.Entities;

public abstract class Erabiltzailea {
    private int id;
    private String izena;
    private String abizenak;
    private String email;
    private String pasahitza;
    private String kurtsoa;

    public Erabiltzailea(String izena, String abizenak, String email, String pasahitza, String kurtsoa) {
        this.izena = izena;
        this.abizenak = abizenak;
        this.email = email;
        this.pasahitza = pasahitza;
        this.kurtsoa = kurtsoa;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getIzena() {
        return izena;
    }
    public String getAbizenak() {
        return abizenak;
    }
    public String getEmail() {
        return email;
    }
    public String getPasahitza() {
        return pasahitza;
    }
    public String getKurtsoa() {
        return kurtsoa;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setIzena(String izena) {
        this.izena = izena;
    }
    public void setAbizenak(String abizenak) {
        this.abizenak = abizenak;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }

    public void setKurtsoa(String kurtsoa) {
        this.kurtsoa = kurtsoa;
    }
}
