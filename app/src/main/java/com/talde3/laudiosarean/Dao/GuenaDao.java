package com.talde3.laudiosarean.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.talde3.laudiosarean.Entities.Gunea;

@Dao
public interface GuenaDao {
    @Query("Select * from Gunea")
    Gunea getAll();
}
