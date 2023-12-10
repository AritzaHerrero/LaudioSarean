package com.talde3.laudiosarean.Room.Entities;

import androidx.room.Entity;

@Entity(
        tableName = "Irakaslea"
)
public class Irakaslea extends Erabiltzailea{
    public Irakaslea(String izena, String abizenak, String email, String pasahitza, String kurtsoa) {
        super(izena, abizenak, email, pasahitza, kurtsoa);
    }
}
