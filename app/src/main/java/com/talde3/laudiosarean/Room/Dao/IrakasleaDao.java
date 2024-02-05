package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.talde3.laudiosarean.Room.Entities.Irakaslea;

import java.util.List;

@Dao
public interface IrakasleaDao {
    @Query("SELECT * FROM Irakaslea")
    List<Irakaslea> getIrakasleak();
    @Insert
    void insert(Irakaslea irakaslea);
    @Insert
    void delete(Irakaslea irakaslea);
    @Update
    void update(Irakaslea irakaslea);
    // Irakasle baten informazioa berreskuratu bere posta elektronikoz abiatuta
    @Query("SELECT * FROM Irakaslea WHERE email = :email")
    Irakaslea getIrakasleaByEmail(String email);
    // AutoIncrement balioa 1-etik berriz astea
    @Query("UPDATE sqlite_sequence SET seq = 1 WHERE name = 'Irakaslea'")
    void resetPrimaryKeyAutoIncrementValueIrakaslea();
}
