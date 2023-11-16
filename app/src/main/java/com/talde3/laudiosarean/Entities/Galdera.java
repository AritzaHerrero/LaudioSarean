package com.talde3.laudiosarean.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity= Gunea.class, parentColumns = "gunea_id", childColumns = "gunea_id", onDelete = ForeignKey.CASCADE)})
public class Galdera {
    @PrimaryKey
    private int galdera_id;
    @ColumnInfo(name = "galdera")
    private String galdera;
    @ColumnInfo(name = "gunea_id")
    private int gunea_id;
}
