package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Galdera;

import java.util.List;

@Dao
public interface GalderaDao {
    @Query("Select * from Galdera")
    List<Galdera> getAll();
    @Insert
    void InsertAll(Galdera galdera);
    @Delete
    void delete(Galdera galdera);
}
