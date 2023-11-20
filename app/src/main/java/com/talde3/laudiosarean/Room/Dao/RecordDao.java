package com.talde3.laudiosarean.Room.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.talde3.laudiosarean.Room.Entities.Record;

@Dao
public interface RecordDao {
    @Query("Select * from Record")
    Record getAll();
}