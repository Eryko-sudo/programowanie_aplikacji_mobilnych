package com.example.lego_collector

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LegoSetDao {
    @Query("SELECT * FROM LegoSet")
    fun getAll(): List<LegoSet>

    @Insert
    fun insertAll(vararg legoSets: LegoSet)
}