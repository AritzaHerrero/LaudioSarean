package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Record",
        foreignKeys = {
                @ForeignKey(entity= Erabiltzailea.class, parentColumns = "nan", childColumns = "nan", onDelete = ForeignKey.CASCADE)
        }
)
public class Record {
    @PrimaryKey
    @NonNull
    private int record_id;
    @ColumnInfo(name = "izena")
    private String pasahitza;
    @ColumnInfo(name = "nan")
    private String nan;

    // Constructor
    public Record(int record_id, String pasahitza, String nan) {
        this.record_id = record_id;
        this.pasahitza = pasahitza;
        this.nan = nan;
    }

    //Getters
    public int getRecord_id() {
        return record_id;
    }
    public String getPasahitza() {
        return pasahitza;
    }
    public String getNan() {
        return nan;
    }

    // Setters
    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }
    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }
    public void setNan(String nan) {
        this.nan = nan;
    }
}
