package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Puntuazioa;

import java.util.List;

@Dao
public interface PuntuazioaDao {
    @Query("Select * from Puntuazioa")
    List<Puntuazioa> getAll();
    @Insert
    void insert(Puntuazioa puntuazioa);
    @Delete
    void delete(Puntuazioa puntuazioa);
    @Query("UPDATE sqlite_sequence SET seq = 1 WHERE name = 'Puntuazioa'")
    void resetPrimaryKeyAutoIncrementValuePuntuazioa();
}
