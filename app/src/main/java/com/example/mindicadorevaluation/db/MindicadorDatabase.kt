package com.example.mindicadorevaluation.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mindicadorevaluation.core.models.User

@Database(
    entities =[User::class],
    version = 1,
    exportSchema = false
)
abstract class MindicadorDatabase : RoomDatabase() {
    abstract fun getIndicatorDao() : UserDao
}