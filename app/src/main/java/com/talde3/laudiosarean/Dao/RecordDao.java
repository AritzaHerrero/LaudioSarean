package com.talde3.laudiosarean.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.talde3.laudiosarean.Entities.Record;

@Dao
public interface RecordDao {
    @Query("Select * from Record")
    Record getAll();
}