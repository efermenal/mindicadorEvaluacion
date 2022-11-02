package com.example.mindicadorevaluation.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey val userId: String,
    val password: String,
)
