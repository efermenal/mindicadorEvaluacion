package com.example.mindicadorevaluation.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var db : MindicadorDatabase
    private lateinit var userDao: UserDao
    private val id = "id"
    private val password = "password"

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb(){
        val context : Context = ApplicationProvider.getApplicationContext()
        db = Room
                .inMemoryDatabaseBuilder(context, MindicadorDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        userDao = db.getUserDao()
    }

    @After
    fun tearDown(){
        db.close()
    }

    @Test
    fun insertAndGetUser_returnsUserInserted() = runTest {
        val userTest = User(id, password)
        val user = listOf(userTest)

        userDao.insertUser(user)

        val users = userDao.getUserById(id).first()

        MatcherAssert.assertThat(users[0], IsEqual.equalTo(userTest))
    }

    @Test
    fun getUserById_noUser_returnsEmpty() = runTest {
        val userTest = User(id, password)
        val user = listOf(userTest)
        val anotherId = "anotherId"

        userDao.insertUser(user)

        val users = userDao.getUserById(anotherId).first()

        MatcherAssert.assertThat("must be empty", users.isEmpty())
    }
}
