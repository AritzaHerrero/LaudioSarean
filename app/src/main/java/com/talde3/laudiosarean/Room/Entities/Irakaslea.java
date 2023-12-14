package com.talde3.laudiosarean.Room.Entities;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(
        tableName = "Irakaslea",
        indices = {@Index(value = "email", unique = true)}
)
public class Irakaslea extends Erabiltzailea{
    public Irakaslea(String izena, String abizenak, String email, String pasahitza, String kurtsoa) {
        super(izena, abizenak, email, pasahitza, kurtsoa);
    }
}
