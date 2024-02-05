package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.talde3.laudiosarean.Room.Entities.Ikaslea;
import com.talde3.laudiosarean.Room.Entities.ItemSpinner;

import java.util.List;

@Dao
public interface IkasleaDao {
    @Query("SELECT * FROM Ikaslea")
    List<Ikaslea> getIkasleak();
    @Query("SELECT * FROM Ikaslea WHERE email = :email")
    Ikaslea getIkasleaByEmail(String email);
    @Insert
    void insert(Ikaslea ikaslea);
    @Update
    void update(Ikaslea ikaslea);
    @Delete
    void delete(Ikaslea ikaslea);
    // AutoIncrement balioa 1-etik berriz astea
    @Query("UPDATE sqlite_sequence SET seq = 1 WHERE name = 'Ikaslea'")
    void resetPrimaryKeyAutoIncrementValueIkaslea();
    // Ikasele taulako ikasle_id eta izen guztiak berreskuratu
    @Query("Select i.id_ikaslea id, i.izena gunea from Ikaslea i")
    List<ItemSpinner> getIkasleakSpinner();
}
