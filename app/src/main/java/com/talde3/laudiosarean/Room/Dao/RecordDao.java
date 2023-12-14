package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Record;

import java.util.List;

@Dao
public interface RecordDao {
    @Query("Select * from Record")
    List<Record> getAll();
    @Insert
    void InsertAll(Record record);
    @Delete
    void delete(Record record);
}