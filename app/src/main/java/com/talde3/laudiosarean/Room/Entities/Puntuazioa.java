package com.talde3.laudiosarean.Room.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.nullness.qual.NonNull;

@Entity(tableName = "Puntuazioa",
        foreignKeys = {
                @ForeignKey(entity= Ikaslea.class, parentColumns = "id_ikaslea", childColumns = "id_ikaslea", onDelete = ForeignKey.CASCADE)
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

    // Constructor
    public Puntuazioa(int id_puntuazioa, int puntuazioa, int id_ikaslea) {
        this.id_puntuazioa = id_puntuazioa;
        this.puntuazioa = puntuazioa;
        this.id_ikaslea = id_ikaslea;
    }

    // Getters
    public int getId_puntuazioa() {
        return id_puntuazioa;
    }
    public int getPuntuazioa() {
        return puntuazioa;
    }
    public int getId_rikaslea() {
        return id_ikaslea;
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
}
