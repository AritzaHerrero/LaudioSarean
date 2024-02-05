package com.talde3.laudiosarean.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.talde3.laudiosarean.Room.Dao.GuneaDao;
import com.talde3.laudiosarean.Room.Dao.IkasleaDao;
import com.talde3.laudiosarean.Room.Dao.IrakasleaDao;
import com.talde3.laudiosarean.Room.Dao.PuntuazioaDao;
import com.talde3.laudiosarean.Room.Entities.Gunea;
import com.talde3.laudiosarean.Room.Entities.Ikaslea;
import com.talde3.laudiosarean.Room.Entities.Irakaslea;
import com.talde3.laudiosarean.Room.Entities.Puntuazioa;

@Database(
        entities = {Irakaslea.class, Ikaslea.class, Gunea.class, Puntuazioa.class},
        version = 5,
        exportSchema = false
)
public abstract class Datubase extends RoomDatabase {
    public abstract IkasleaDao ikasleaDao();
    public abstract IrakasleaDao irakasleaDao();
    public abstract GuneaDao guneaDao();
    public abstract PuntuazioaDao puntuazioaDao();

    private static Datubase instance;

    public static synchronized Datubase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            Datubase.class, "LaudioDB.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

