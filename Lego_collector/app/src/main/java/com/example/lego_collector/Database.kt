package com.example.lego_collector

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LegoSet::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun legoSetDao(): LegoSetDao
}