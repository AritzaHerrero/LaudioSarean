package com.talde3.laudiosarean.Room.Entities;

import androidx.room.Entity;

@Entity(
        tableName = "Ikaslea"
)
public class Ikaslea extends Erabiltzailea{
    public Ikaslea(String nan, String izena, String abizena, String email, String pasahitza, String klasea) {
        super(nan, izena, abizena, email, pasahitza, klasea);
    }
}
