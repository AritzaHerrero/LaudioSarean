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
    List<Puntuazioa> getPuntuazioak();
    @Insert
    void insert(Puntuazioa puntuazioa);
    @Delete
    void delete(Puntuazioa puntuazioa);
    // AutoIncrement balioa 1-etik berriz astea
    @Query("UPDATE sqlite_sequence SET seq = 1 WHERE name = 'Puntuazioa'")
    void resetPrimaryKeyAutoIncrementValuePuntuazioa();
    // Puntuazioa erregistro kopurua
    @Query("Select count(*) from Puntuazioa")
    int countPuntuazioa();
    // Puntuazio taulako azken puntuazioa erregistroaren id-a
    @Query("Select id_puntuazioa from Puntuazioa order by id_puntuazioa desc limit 1")
    int lastPuntuazioa();
    // Ikasle baten puntuazioa maximoa guneka taldekatuta
    @Query("Select i.izena, max(p.puntuazioa) puntuazioa, i.abizenak, g.izena gunea from Puntuazioa p inner join Ikaslea i on p.id_ikaslea = i.id_ikaslea inner join Gunea g on g.id_gunea = p.id_gunea where i.id_ikaslea=:id group by p.id_gunea")
    List<Ranking> topIkasle(int id);
    // Gune bakoitzeko ikasleen top 10
    @Query("Select i.izena, max(p.puntuazioa) puntuazioa, i.abizenak, g.izena gunea from Puntuazioa p inner join Ikaslea i on p.id_ikaslea = i.id_ikaslea inner join Gunea g on g.id_gunea = p.id_gunea where p.id_gunea=:gune group by p.id_ikaslea order by p.puntuazioa desc limit 10")
    List<Ranking> topGune(int gune);
}
