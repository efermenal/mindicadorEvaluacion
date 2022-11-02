package com.example.mindicadorevaluation.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class MindicadorDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: MindicadorDatabase? = null

        fun getInstance(app: Context): MindicadorDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(app).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MindicadorDatabase::class.java, "mindicador.db"
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            getInstance(context).getUserDao().insertUser(PREPOPULATE_USERS)
                        }
                    }
                })
                .build()

        val PREPOPULATE_USERS = listOf(
            User("endherson", "fgLFr9mgmUqallCHW/tecQ=="),
            User("usuario1", "muOdiBvCZD4zeYVeiZVBpg=="),
            User("recluiter", "HqVHfdQtphRLOgW45RtU7w=="),
            User("carlos", "k+GcX0NLi12kB2Eje3YQCg=="),
        )
    }
}
