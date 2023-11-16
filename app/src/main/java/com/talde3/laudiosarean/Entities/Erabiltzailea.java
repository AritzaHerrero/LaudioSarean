package com.talde3.laudiosarean.Entities;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Erabiltzailea {
    @PrimaryKey
    private String nan;
    @ColumnInfo(name = "izena")
    private String izena;
    @ColumnInfo(name = "abizena")
    private String abizena;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "pasahitza")
    private String pasahitza;
    @ColumnInfo(name = "klasea")
    private String klasea;
}
