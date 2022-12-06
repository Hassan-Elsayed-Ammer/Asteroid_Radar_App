package com.hassan.elsayed.ammer.asteroidradar.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hassan.elsayed.ammer.asteroidradar.models.Asteroid
import com.hassan.elsayed.ammer.asteroidradar.models.PictureOfDay

@Dao
interface AsteroidDataDao {

    @Query("SELECT * FROM asteroids ORDER BY date(closeApproachDate) ASC")
    fun getAllAsteroidData(): LiveData<List<Asteroid>>


    @Query("SELECT * FROM asteroids WHERE closeApproachDate <=:date ORDER BY date(closeApproachDate) ASC ")
    fun getTodayAsteroidData(date: String): LiveData<List<Asteroid>>

    @Transaction
    fun updateAsteroidsData(users: List<Asteroid>): List<Long> {
        deleteAllAsteroids()
        return insertAllAsteroids(users)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(asteroids: List<Asteroid>): List<Long>

    @Query("DELETE FROM asteroids")
    fun deleteAllAsteroids()



    @Query("SELECT * FROM imageOfTheDay")
    fun getPictureOfTheDay(): LiveData<PictureOfDay>

    @Transaction
    fun updateData(pic: PictureOfDay): Long {
        deleteAllPictures()
        return insertPicture(pic)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPicture(pic: PictureOfDay): Long

    @Query("DELETE FROM imageOfTheDay")
    fun deleteAllPictures()

}