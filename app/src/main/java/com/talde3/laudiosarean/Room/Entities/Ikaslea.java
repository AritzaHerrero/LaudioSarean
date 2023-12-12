package com.talde3.laudiosarean.Room.Entities;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(
        tableName = "Ikaslea",
        indices = {@Index(value = "email", unique = true)}
)
public class Ikaslea extends Erabiltzailea{
    public Ikaslea(String izena, String abizenak, String email, String pasahitza, String kurtsoa) {
        super(izena, abizenak, email, pasahitza, kurtsoa);
    }
}
