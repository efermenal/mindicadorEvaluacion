package com.example.mindicadorevaluation.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindicadorevaluation.core.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var db : MindicadorDatabase
    private lateinit var userDao: UserDao

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
    @Throws(IOException::class)
    fun tearDown(){
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetUser_returnsUserInserted() = runBlockingTest {
        val id = "Endherson"
        val userTest = User(id, "ddfgffg")
        val user = listOf(userTest)

        userDao.insertUser(user)

        val users = userDao.getUserById(id).first()

        MatcherAssert.assertThat(users[0], IsEqual.equalTo(userTest))
    }

    @Test
    @Throws(IOException::class)
    fun getUserById_noUser_returnsEmpty() = runBlockingTest {
        val id = "Endherson"
        val userTest = User(id, "ddfgffg")
        val user = listOf(userTest)

        userDao.insertUser(user)

        val users = userDao.getUserById("id").first()

        MatcherAssert.assertThat("must be empty", users.isEmpty());
    }



}

