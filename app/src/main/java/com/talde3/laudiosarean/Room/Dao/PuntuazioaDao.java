package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Puntuazioa;
import com.talde3.laudiosarean.Room.Entities.Ranking;

import java.util.List;

@Dao
public interface PuntuazioaDao {
    @Query("Select * from Puntuazioa")
    List<Puntuazioa> getAll();
    @Insert
    void insert(Puntuazioa puntuazioa);
    @Delete
    void delete(Puntuazioa puntuazioa);
    @Query("UPDATE sqlite_sequence SET seq = 1 WHERE name = 'Puntuazioa'")
    void resetPrimaryKeyAutoIncrementValuePuntuazioa();
    @Query("Select count(*) from Puntuazioa")
    int countPuntuazioa();
    @Query("Select id_puntuazioa from Puntuazioa order by id_puntuazioa desc limit 1")
    int lastPuntuazioa();
    @Query("Select max(puntuazioa) from Puntuazioa where id_gunea='1'")
    int maxPuntuazioaGune1();
    @Query("Select puntuazioa from Puntuazioa where id_gunea='1' order by puntuazioa desc limit 10")
    List<Integer> topGune1();
    @Query("Select i.izena, max(p.puntuazioa) puntuazioa, abizenak from Puntuazioa p inner join Ikaslea i on p.id_ikaslea = i.id_ikaslea where id_gunea=:gune group by p.id_ikaslea order by p.puntuazioa desc limit 10")
    List<Ranking> topGune(int gune);
}
