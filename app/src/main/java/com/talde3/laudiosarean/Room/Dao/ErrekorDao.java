package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Errekor;

import java.util.List;

@Dao
public interface ErrekorDao {
    @Query("Select * from Errekor")
    List<Errekor> getAll();
    @Insert
    void insert(Errekor errekor);
    @Delete
    void delete(Errekor errekor);
    @Query("UPDATE sqlite_sequence SET seq = 1 WHERE name = 'Errekor'")
    void resetPrimaryKeyAutoIncrementValueErrekor();
}