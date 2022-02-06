package com.example.mvvmex.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmex.entity.MovieResult

@Database(entities = [MovieResult.Item::class], version = 1, exportSchema = true)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

