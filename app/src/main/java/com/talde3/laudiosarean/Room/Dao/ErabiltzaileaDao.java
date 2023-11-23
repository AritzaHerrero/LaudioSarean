package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Erabiltzailea;

import java.util.List;

@Dao
public interface ErabiltzaileaDao {
    @Query("SELECT * FROM Erabiltzailea")
    List<Erabiltzailea> getAll();

    @Insert
    void insertAll(Erabiltzailea erabiltzailea);

    @Insert
    void delete(Erabiltzailea erabiltzailea);
}
