package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Gunea",
        foreignKeys = {
            @ForeignKey(entity= Record.class, parentColumns = "id_record", childColumns = "id_record", onDelete = ForeignKey.CASCADE)
        }
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
    @ColumnInfo(name = "irudia")
    private String irudia;
    @ColumnInfo(name = "id_record")
    private int id_record;

    // Constructor
    public Gunea(String izena, String deskribapena, String koordenadak, String audioa, String irudia, int id_record) {
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.koordenadak = koordenadak;
        this.audioa = audioa;
        this.irudia = irudia;
        this.id_record = id_record;
    }

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
    public String getIrudia() {
        return irudia;
    }
    public int getId_record() {
        return id_record;
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
    public void setIrudia(String irudia) {
        this.irudia = irudia;
    }
    public void setId_record(int id_record) {
        this.id_record = id_record;
    }
}
