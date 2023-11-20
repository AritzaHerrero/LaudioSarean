package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity= Record.class, parentColumns = "record_id", childColumns = "record_id", onDelete = ForeignKey.CASCADE)})
public class Gunea {
    @PrimaryKey
    @NonNull
    private int gunea_id;
    @ColumnInfo(name = "izena")
    private String izena;
    @ColumnInfo(name = "deskribapena")
    private String deskribapena;
    @ColumnInfo(name = "koordenadak")
    private String koordenadak;
    @ColumnInfo(name = "audioa")
    private String audioa;
    @ColumnInfo(name = "irudia")
    private String irudia;
    @ColumnInfo(name = "record_id")
    private int record_id;

    // Constructor
    public Gunea(int gunea_id, String izena, String deskribapena, String koordenadak, String audioa, String irudia, int record_id) {
        this.gunea_id = gunea_id;
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.koordenadak = koordenadak;
        this.audioa = audioa;
        this.irudia = irudia;
        this.record_id = record_id;
    }

    // Getters
    public int getGunea_id() {
        return gunea_id;
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
    public String getIrudia() {
        return irudia;
    }
    public int getRecord_id() {
        return record_id;
    }

    // Setters
    public void setGuneak_id(int guneak_id) {
        this.gunea_id = guneak_id;
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
    public void setIrudia(String irudia) {
        this.irudia = irudia;
    }
    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }
}
