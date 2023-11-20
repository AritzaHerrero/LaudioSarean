package com.talde3.laudiosarean.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.talde3.laudiosarean.Room.Dao.ErabiltzaileaDao;
import com.talde3.laudiosarean.Room.Dao.GalderaDao;
import com.talde3.laudiosarean.Room.Dao.GuneaDao;
import com.talde3.laudiosarean.Room.Dao.RecordDao;
import com.talde3.laudiosarean.Room.Entities.Erabiltzailea;
import com.talde3.laudiosarean.Room.Entities.Galdera;
import com.talde3.laudiosarean.Room.Entities.Gunea;
import com.talde3.laudiosarean.Room.Entities.Record;

public class Datubase {
    @Database(entities = {Erabiltzailea.class, Galdera.class, Gunea.class, Record.class}, version = 1, exportSchema = false)
    public abstract static class AppDatabase extends RoomDatabase {
        public abstract ErabiltzaileaDao erabiltzaileaDao();
        public abstract GalderaDao galderaDao();
        public abstract GuneaDao guneaDao();
        public abstract RecordDao recordDao();

        private static volatile AppDatabase instance;

        public static synchronized AppDatabase getInstance(Context context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "database-name")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
            return instance;
        }
    }
}
