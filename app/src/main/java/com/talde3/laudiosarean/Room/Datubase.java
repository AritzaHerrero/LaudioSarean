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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

@Database(
        entities = {Erabiltzailea.class, Irakaslea.class, Ikaslea.class, Galdera.class, Gunea.class, Record.class},
        version = 2,
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
                            Datubase.class, "LaudioDB")
                    .allowMainThreadQueries()
                    .createFromAsset("database/LaudioDB.db")
                    .build();
        }
        return instance;
    }

    public static void exportDatabase(File currentDB, File backupDB) throws IOException {
        try (FileInputStream fis = new FileInputStream(currentDB);
             FileOutputStream fos = new FileOutputStream(backupDB);
             FileChannel inChannel = fis.getChannel();
             FileChannel outChannel = fos.getChannel()) {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
    }
}

