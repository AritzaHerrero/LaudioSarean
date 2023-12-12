package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Ikaslea;

import java.util.List;

@Dao
public interface IkasleaDao {
    @Query("SELECT * FROM Ikaslea")
    List<Ikaslea> getAll();
    @Query("SELECT * FROM Ikaslea WHERE email = :email")
    Ikaslea getUserByEmail(String email);
    @Insert
    void insertAll(Ikaslea... ikaslea);
    @Delete
    void delete(Ikaslea ikaslea);
}
