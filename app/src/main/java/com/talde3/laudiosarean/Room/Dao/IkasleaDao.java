package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Ikaslea;

import java.util.List;

@Dao
public interface IkasleaDao {
    @Query("SELECT * FROM Ikaslea")
    List<Ikaslea> getAll();

    @Insert
    void insertAll(Ikaslea ikaslea);

    @Insert
    void delete(Ikaslea ikaslea);
}
