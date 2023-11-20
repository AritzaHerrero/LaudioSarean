package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Galdera;

@Dao
public interface GalderaDao {
    @Query("Select * from Galdera")
    Galdera getAll();
}
