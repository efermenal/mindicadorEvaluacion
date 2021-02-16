package com.example.mindicadorevaluation.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mindicadorevaluation.core.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("Select * from user_table where userId = :id")
    fun getUserById(id : String) : Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)
}