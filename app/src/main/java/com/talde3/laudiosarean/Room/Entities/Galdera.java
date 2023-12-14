package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Galdera",
        foreignKeys = {
            @ForeignKey(entity= Gunea.class, parentColumns = "id_gunea", childColumns = "id_gunea", onDelete = ForeignKey.CASCADE)
        }
)
public class Galdera {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id_galdera;
    @ColumnInfo(name = "galdera")
    private String galdera;
    @ColumnInfo(name = "id_gunea")
    private int id_gunea;

    public Galdera(String galdera, int id_gunea) {
        this.galdera = galdera;
        this.id_gunea = id_gunea;
    }

    // Getters
    public int getId_galdera() {
        return id_galdera;
    }
    public String getGaldera() {
        return galdera;
    }
    public int getId_gunea() {
        return id_gunea;
    }

    // Setters
    public void setId_galdera(int id_galdera) {
        this.id_galdera = id_galdera;
    }
    public void setGaldera(String galdera) {
        this.galdera = galdera;
    }
    public void setId_gunea(int id_gunea) {
        this.id_gunea = id_gunea;
    }
}
