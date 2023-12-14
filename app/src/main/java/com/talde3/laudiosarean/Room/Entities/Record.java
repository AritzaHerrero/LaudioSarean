package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Record",
        foreignKeys = {
                @ForeignKey(entity= Puntuazioa.class, parentColumns = "id_puntuazioa", childColumns = "id_puntuazioa", onDelete = ForeignKey.CASCADE)
        }
)
public class Record {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_record")
    private int id_record;
    @ColumnInfo(name = "id_puntuazioa")
    private int id_puntuazioa;
    // Constructor
    public Record(int id_puntuazioa) {
        this.id_puntuazioa = id_puntuazioa;
    }

    //Getters
    public int getId_record() {
        return id_record;
    }
    public int getId_puntuazioa() {
        return id_puntuazioa;
    }

    // Setters
    public void setId_record(int id_record) {
        this.id_record = id_record;
    }
    public void setId_puntuazioa(int id_puntuazioa) {
        this.id_puntuazioa = id_puntuazioa;
    }
}
