package com.talde3.laudiosarean.Room.Entities;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Erabiltzailea {
    @PrimaryKey
    @NonNull
    private String nan;
    @ColumnInfo(name = "izena")
    private String izena;
    @ColumnInfo(name = "abizena")
    private String abizena;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "pasahitza")
    private String pasahitza;
    @ColumnInfo(name = "klasea")
    private String klasea;

    public Erabiltzailea(String nan, String izena, String abizena, String email, String pasahitza, String klasea) {
        this.nan = nan;
        this.izena = izena;
        this.abizena = abizena;
        this.email = email;
        this.pasahitza = pasahitza;
        this.klasea = klasea;
    }

    // Getters
    public String getNan() {
        return nan;
    }
    public String getIzena() {
        return izena;
    }
    public String getAbizena() {
        return abizena;
    }
    public String getEmail() {
        return email;
    }
    public String getPasahitza() {
        return pasahitza;
    }
    public String getKlasea() {
        return klasea;
    }

    // Setters
    public void setNan(String nan) {
        this.nan = nan;
    }
    public void setIzena(String izena) {
        this.izena = izena;
    }
    public void setAbizena(String abizena) {
        this.abizena = abizena;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }

    public void setKlasea(String klasea) {
        this.klasea = klasea;
    }
}
