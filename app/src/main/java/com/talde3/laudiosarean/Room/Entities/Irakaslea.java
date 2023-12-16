package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Irakaslea implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id_irakaslea;
    private String izena;
    private String abizenak;
    private String email;
    private String pasahitza;
    private String kurtsoa;
    public Irakaslea(String izena, String abizenak, String email, String pasahitza, String kurtsoa) {
        this.izena = izena;
        this.abizenak = abizenak;
        this.email = email;
        this.pasahitza = pasahitza;
        this.kurtsoa = kurtsoa;
    }

    // Getters
    public int getId_irakaslea() { return id_irakaslea; }
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
    public void setId_irakaslea(int id_irakaslea) { this.id_irakaslea = id_irakaslea; }
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
