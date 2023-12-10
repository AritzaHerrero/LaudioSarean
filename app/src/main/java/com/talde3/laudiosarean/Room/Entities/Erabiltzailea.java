package com.talde3.laudiosarean.Room.Entities;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.checkerframework.common.aliasing.qual.Unique;

@Entity(indices = {@Index(value = {"email"},unique = true)})
public abstract class Erabiltzailea {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo(name = "izena")
    private String izena;
    @ColumnInfo(name = "abizenak")
    private String abizenak;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "pasahitza")
    private String pasahitza;
    @ColumnInfo(name = "kurtsoa")
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
