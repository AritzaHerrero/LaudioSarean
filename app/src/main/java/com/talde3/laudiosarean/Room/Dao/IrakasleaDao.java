package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Irakaslea;

import java.util.List;

@Dao
public interface IrakasleaDao {
    @Query("SELECT * FROM Irakaslea")
    List<Irakaslea> getAll();
    @Insert
    void insertAll(Irakaslea irakaslea);
    @Insert
    void delete(Irakaslea irakaslea);
}
