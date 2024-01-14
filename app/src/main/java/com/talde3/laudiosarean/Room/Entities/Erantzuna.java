package com.talde3.laudiosarean.Room.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.nullness.qual.NonNull;

@Entity(
        foreignKeys = {
                @ForeignKey(entity= Galdera.class, parentColumns = "id_galdera", childColumns = "id_galdera", onDelete = ForeignKey.CASCADE)
        }
)
public class Erantzuna {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id_erantzuna;
    @ColumnInfo(name = "erantzuna")
    private String erantzuna;
    @ColumnInfo(name = "zuzena")
    private boolean zuzena;
    @ColumnInfo(name = "id_galdera")
    private int id_galdera;

    // Constructor
    public Erantzuna(String erantzuna, boolean zuzena, int id_galdera) {
        this.erantzuna = erantzuna;
        this.zuzena = zuzena;
        this.id_galdera = id_galdera;
    }

    public Erantzuna(){}

    // Getters
    public int getId_erantzuna() {
        return id_erantzuna;
    }
    public void setId_erantzuna(int id_erantzuna) {
        this.id_erantzuna = id_erantzuna;
    }
    public String getErantzuna() {
        return erantzuna;
    }
    public int getId_galdera() {
        return id_galdera;
    }

    // Setters
    public void setErantzuna(String erantzuna) {
        this.erantzuna = erantzuna;
    }
    public boolean isZuzena() {
        return zuzena;
    }
    public void setZuzena(boolean zuzena) {
        this.zuzena = zuzena;
    }
    public void setId_galdera(int id_galdera) {
        this.id_galdera = id_galdera;
    }
}
