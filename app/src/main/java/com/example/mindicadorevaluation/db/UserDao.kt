package com.example.mindicadorevaluation.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mindicadorevaluation.core.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table WHERE userId = :id")
    fun getUserById(id : String) : Flow<List<User>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: List<User>)
}
