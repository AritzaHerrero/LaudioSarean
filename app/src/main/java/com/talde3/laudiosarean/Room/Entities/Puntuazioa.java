package com.talde3.laudiosarean.Room.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.nullness.qual.NonNull;

@Entity(tableName = "Puntuazioa",
        foreignKeys = {
                @ForeignKey(entity= Ikaslea.class, parentColumns = "id_ikaslea", childColumns = "id_ikaslea", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity= Gunea.class, parentColumns = "id_gunea", childColumns = "id_gunea", onDelete = ForeignKey.CASCADE)
        }
)
public class Puntuazioa {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id_puntuazioa;
    @ColumnInfo(name = "puntuazioa")
    private int puntuazioa;
    @ColumnInfo(name = "id_ikaslea")
    private int id_ikaslea;
    @ColumnInfo(name = "id_gunea")
    private int id_gunea;

    // Constructors
    public Puntuazioa(int puntuazioa, int id_ikaslea, int id_gunea) {
        this.puntuazioa = puntuazioa;
        this.id_ikaslea = id_ikaslea;
        this.id_gunea = id_gunea;
    }
    public Puntuazioa(){}

    // Getters
    public int getId_puntuazioa() {
        return id_puntuazioa;
    }
    public int getPuntuazioa() {
        return puntuazioa;
    }
    public int getId_ikaslea() {
        return id_ikaslea;
    }
    public int getId_gunea() {
        return id_gunea;
    }

    // Setters
    public void setId_puntuazioa(int id_puntuazioa) {
        this.id_puntuazioa = id_puntuazioa;
    }
    public void setPuntuazioa(int puntuazioa) {
        this.puntuazioa = puntuazioa;
    }
    public void setId_ikaslea(int id_ikaslea) {
        this.id_ikaslea = id_ikaslea;
    }
    public void setId_gunea(int id_gunea) { this.id_gunea = id_gunea; }
}
