package com.talde3.laudiosarean.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity= Record.class, parentColumns = "record_id", childColumns = "record_id", onDelete = ForeignKey.CASCADE)})
public class Gunea {
    @PrimaryKey
    private int guneak_id;
    @ColumnInfo(name = "izena")
    private String izena;
    @ColumnInfo(name = "deskribapena")
    private String deskribapena;
    @ColumnInfo(name = "koordenadak")
    private String koordenadak;
    @ColumnInfo(name = "audioa")
    private String audioa;
    @ColumnInfo(name = "irudia")
    private String irudia;
    @ColumnInfo(name = "record_id")
    private int record_id;
}
