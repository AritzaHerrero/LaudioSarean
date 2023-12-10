package com.talde3.laudiosarean.Room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Record",
        foreignKeys = {
                @ForeignKey(entity= Ikaslea.class, parentColumns = "id", childColumns = "id", onDelete = ForeignKey.CASCADE)
        }
)
public class Record {
    @PrimaryKey
    @NonNull
    private int record_id;
    @ColumnInfo(name = "izena")
    private String pasahitza;
    @ColumnInfo(name = "id")
    private int id;

    // Constructor
    public Record(int record_id, String pasahitza, int id) {
        this.record_id = record_id;
        this.pasahitza = pasahitza;
        this.id = id;
    }

    //Getters
    public int getRecord_id() {
        return record_id;
    }
    public String getPasahitza() {
        return pasahitza;
    }
    public int getId() {
        return id;
    }

    // Setters
    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }
    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }
    public void setId(int id) {
        this.id = id;
    }
}
