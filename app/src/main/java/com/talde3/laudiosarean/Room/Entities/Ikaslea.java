package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        tableName = "Ikaslea",
        indices = {@Index(value = "email", unique = true)}
)
public class Ikaslea implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id_ikaslea;
    private String izena;
    private String abizenak;
    private String email;
    private String kurtsoa;
    public Ikaslea(String izena, String abizenak, String email, String kurtsoa) {
        this.izena = izena;
        this.abizenak = abizenak;
        this.email = email;
        this.kurtsoa = kurtsoa;
    }

    public Ikaslea(){}

    // Getters
    public int getId_ikaslea() { return id_ikaslea; }
    public String getIzena() {
        return izena;
    }
    public String getAbizenak() {
        return abizenak;
    }
    public String getEmail() {
        return email;
    }
    public String getKurtsoa() {
        return kurtsoa;
    }

    // Setters
    public void setId_ikaslea(int id_ikaslea) { this.id_ikaslea = id_ikaslea; }
    public void setIzena(String izena) {
        this.izena = izena;
    }
    public void setAbizenak(String abizenak) {
        this.abizenak = abizenak;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setKurtsoa(String kurtsoa) {
        this.kurtsoa = kurtsoa;
    }
}
