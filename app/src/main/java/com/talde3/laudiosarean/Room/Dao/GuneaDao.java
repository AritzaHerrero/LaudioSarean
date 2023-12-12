package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Gunea;

import java.util.List;

@Dao
public interface GuneaDao {
    @Query("Select * from Gunea")
    List<Gunea> getAll();
    @Insert
    void InsertAll(Gunea gunea);
    @Delete
    void delete(Gunea gunea);
}
