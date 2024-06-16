package com.example.lego_collector

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LegoSetDao {
    @Query("SELECT * FROM LegoSet")
    fun getAll(): List<LegoSet>

    @Query("SELECT * FROM LegoSet WHERE set_num = :setNum LIMIT 1")
    fun getLegoSet(setNum: String): LegoSet?

    @Insert
    fun insertAll(vararg legoSets: LegoSet)

    @Delete
    fun delete(legoSet: LegoSet)
}