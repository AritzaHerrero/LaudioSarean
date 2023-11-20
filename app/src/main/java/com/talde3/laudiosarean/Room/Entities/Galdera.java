package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity= Gunea.class, parentColumns = "gunea_id", childColumns = "gunea_id", onDelete = ForeignKey.CASCADE)})
public class Galdera {
    @PrimaryKey
    @NonNull
    private int galdera_id;
    @ColumnInfo(name = "galdera")
    private String galdera;
    @ColumnInfo(name = "gunea_id")
    private int gunea_id;

    public Galdera(int galdera_id, String galdera, int gunea_id) {
        this.galdera_id = galdera_id;
        this.galdera = galdera;
        this.gunea_id = gunea_id;
    }

    // Getters
    public int getGaldera_id() {
        return galdera_id;
    }
    public String getGaldera() {
        return galdera;
    }
    public int getGunea_id() {
        return gunea_id;
    }

    // Setters
    public void setGaldera_id(int galdera_id) {
        this.galdera_id = galdera_id;
    }
    public void setGaldera(String galdera) {
        this.galdera = galdera;
    }
    public void setGunea_id(int gunea_id) {
        this.gunea_id = gunea_id;
    }
}
