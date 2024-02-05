package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Gunea;

import java.util.List;

@Dao
public interface GuneaDao {
    @Query("SELECT * FROM Gunea")
    List<Gunea> getGuneak();
    // Guneraen informazioa berreskuratu id-tik abiatuta
    @Query("SELECT * FROM Gunea WHERE id_gunea = :id")
    Gunea getGuneaById(int id);
    @Insert
    void insert(Gunea gunea);
    @Delete
    void delete(Gunea gunea);
    // AutoIncrement balioa 1-etik berriz astea
    @Query("UPDATE sqlite_sequence SET seq = 1 WHERE name = 'Gunea'")
    void resetPrimaryKeyAutoIncrementValueGunea();
}
