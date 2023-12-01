package com.talde3.laudiosarean.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.talde3.laudiosarean.Room.Dao.GalderaDao;
import com.talde3.laudiosarean.Room.Dao.GuneaDao;
import com.talde3.laudiosarean.Room.Dao.IkasleaDao;
import com.talde3.laudiosarean.Room.Dao.IrakasleaDao;
import com.talde3.laudiosarean.Room.Dao.RecordDao;
import com.talde3.laudiosarean.Room.Entities.Erabiltzailea;
import com.talde3.laudiosarean.Room.Entities.Galdera;
import com.talde3.laudiosarean.Room.Entities.Gunea;
import com.talde3.laudiosarean.Room.Entities.Ikaslea;
import com.talde3.laudiosarean.Room.Entities.Irakaslea;
import com.talde3.laudiosarean.Room.Entities.Record;

@Database(
        entities = {Erabiltzailea.class, Irakaslea.class, Ikaslea.class, Galdera.class, Gunea.class, Record.class},
        version = 1,
        exportSchema = false
)
public abstract class Datubase extends RoomDatabase {
    public abstract IkasleaDao ikasleaDao();
    public abstract IrakasleaDao irakasleaDao();
    public abstract GalderaDao galderaDao();
    public abstract GuneaDao guneaDao();
    public abstract RecordDao recordDao();

    private static volatile Datubase instance;

    public static Datubase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            Datubase.class, "database-name")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}

