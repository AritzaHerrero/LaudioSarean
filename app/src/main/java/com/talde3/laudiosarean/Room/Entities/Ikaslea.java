package com.talde3.laudiosarean.Room.Entities;

import androidx.room.Entity;

@Entity(
        tableName = "Ikaslea"
)
public class Ikaslea extends Erabiltzailea{
    public Ikaslea(String izena, String abizenak, String email, String pasahitza, String kurtsoa) {
        super(izena, abizenak, email, pasahitza, kurtsoa);
    }
}
