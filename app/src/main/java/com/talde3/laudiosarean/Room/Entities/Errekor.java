package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Errekor",
        foreignKeys = {
                @ForeignKey(entity= Puntuazioa.class, parentColumns = "id_puntuazioa", childColumns = "id_puntuazioa", onDelete = ForeignKey.CASCADE)
        }
)
public class Errekor {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_errekor")
    private int id_errekor;
    @ColumnInfo(name = "id_puntuazioa")
    private int id_puntuazioa;
    // Constructor
    public Errekor(int id_puntuazioa) {
        this.id_puntuazioa = id_puntuazioa;
    }

    //Getters
    public int getId_errekor() {
        return id_errekor;
    }
    public int getId_puntuazioa() {
        return id_puntuazioa;
    }

    // Setters
    public void setId_errekor(int id_errekor) {
        this.id_errekor = id_errekor;
    }
    public void setId_puntuazioa(int id_puntuazioa) {
        this.id_puntuazioa = id_puntuazioa;
    }
}
