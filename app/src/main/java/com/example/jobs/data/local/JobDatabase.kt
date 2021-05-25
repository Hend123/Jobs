package com.example.weatherforecast.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobs.data.local.FavoriteDao
import com.example.jobs.pojo.Job


@Database(
    entities = [Job::class],
    version = 1,
    exportSchema = false
)
abstract class JobDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var instance: JobDatabase? = null

        @Synchronized
        fun getInstance(context: Context): JobDatabase {
            instance
                ?: synchronized(this) {
                    instance = Room.databaseBuilder(
                        context,
                        JobDatabase::class.java, "job_database"
                    ).build()
                }

            return instance!!
        }
    }
}