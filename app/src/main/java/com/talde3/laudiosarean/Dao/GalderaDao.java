package com.talde3.laudiosarean.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.talde3.laudiosarean.Entities.Galdera;

@Dao
public interface GalderaDao {
    @Query("Select * from Galdera")
    Galdera getAll();
}
