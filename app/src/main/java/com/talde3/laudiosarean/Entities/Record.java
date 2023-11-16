package com.talde3.laudiosarean.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity= Erabiltzailea.class, parentColumns = "nan", childColumns = "nan", onDelete = ForeignKey.CASCADE)})
public class Record {
    @PrimaryKey
    private int record_id;
    @ColumnInfo(name = "izena")
    private String pasahitza;
    @ColumnInfo(name = "nan")
    private String nan;
}
