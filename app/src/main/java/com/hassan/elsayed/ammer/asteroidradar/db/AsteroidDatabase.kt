package com.hassan.elsayed.ammer.asteroidradar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hassan.elsayed.ammer.asteroidradar.utilities.Constants.DATA_BASE_NAME
import com.hassan.elsayed.ammer.asteroidradar.models.Asteroid
import com.hassan.elsayed.ammer.asteroidradar.models.PictureOfDay

@Database(entities = [Asteroid::class, PictureOfDay::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDataDao

    companion object {
        private var instance: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            if (instance == null) {
                instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        DATA_BASE_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

}