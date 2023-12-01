package com.talde3.laudiosarean.Room.Entities;

import androidx.room.Entity;

@Entity(
        tableName = "Irakaslea"
)
public class Irakaslea extends Erabiltzailea{
    public Irakaslea(String nan, String izena, String abizena, String email, String pasahitza, String klasea) {
        super(nan, izena, abizena, email, pasahitza, klasea);
    }
}
