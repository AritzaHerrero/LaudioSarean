package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "Gunea"
)
public class Gunea {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id_gunea;
    @ColumnInfo(name = "izena")
    private String izena;
    @ColumnInfo(name = "deskribapena")
    private String deskribapena;
    @ColumnInfo(name = "koordenadak")
    private String koordenadak;
    @ColumnInfo(name = "audioa")
    private String audioa;
    @ColumnInfo(name = "irudiak")
    private String irudiak;

    // Constructors
    public Gunea(String izena, String deskribapena, String koordenadak, String audioa, String irudiak) {
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.koordenadak = koordenadak;
        this.audioa = audioa;
        this.irudiak = irudiak;
    }
    public Gunea(){}

    // Getters
    public int getId_gunea() {
        return id_gunea;
    }
    public String getIzena() {
        return izena;
    }
    public String getDeskribapena() {
        return deskribapena;
    }
    public String getKoordenadak() {
        return koordenadak;
    }
    public String getAudioa() {
        return audioa;
    }
    public String getIrudiak() {
        return irudiak;
    }

    // Setters
    public void setId_gunea(int id_gunea) {
        this.id_gunea = id_gunea;
    }
    public void setIzena(String izena) {
        this.izena = izena;
    }
    public void setDeskribapena(String deskribapena) {
        this.deskribapena = deskribapena;
    }
    public void setKoordenadak(String koordenadak) {
        this.koordenadak = koordenadak;
    }
    public void setAudioa(String audioa) {
        this.audioa = audioa;
    }
    public void setIrudiak(String irudiak) {
        this.irudiak = irudiak;
    }
}
