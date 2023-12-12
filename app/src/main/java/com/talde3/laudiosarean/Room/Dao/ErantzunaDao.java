package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Erantzuna;

import java.util.List;

@Dao
public interface ErantzunaDao {
    @Query("Select * from Erantzuna")
    List<Erantzuna> getAll();
    @Insert
    void InsertAll(Erantzuna erantzuna);
    @Delete
    void delete(Erantzuna erantzuna);
}
