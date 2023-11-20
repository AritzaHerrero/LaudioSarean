package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Erabiltzailea;

@Dao
public interface ErabiltzaileaDao {
    @Query("Select * from Erabiltzailea")
    Erabiltzailea getAll();
}
