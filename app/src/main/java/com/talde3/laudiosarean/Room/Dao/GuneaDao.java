package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Gunea;

@Dao
public interface GuneaDao {
    @Query("Select * from Gunea")
    Gunea getAll();
}
