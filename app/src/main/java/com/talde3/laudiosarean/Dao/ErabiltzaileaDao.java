package com.talde3.laudiosarean.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.talde3.laudiosarean.Entities.Erabiltzailea;

@Dao
public interface ErabiltzaileaDao {
    @Query("Select * from Erabiltzailea")
    Erabiltzailea getAll();
}
