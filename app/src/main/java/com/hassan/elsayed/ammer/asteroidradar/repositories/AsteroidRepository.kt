package com.hassan.elsayed.ammer.asteroidradar.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.hassan.elsayed.ammer.asteroidradar.db.AsteroidDatabase
import com.hassan.elsayed.ammer.asteroidradar.models.Asteroid
import com.hassan.elsayed.ammer.asteroidradar.models.PictureOfDay
import com.hassan.elsayed.ammer.asteroidradar.network.RetrofitInstance
import com.hassan.elsayed.ammer.asteroidradar.network.parseAsteroidsJsonResult
import com.hassan.elsayed.ammer.asteroidradar.utilities.Constants
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroidList: LiveData<List<Asteroid>> = database.asteroidDao().getAllAsteroidData()

    val todayAsteroidList: LiveData<List<Asteroid>> = database.asteroidDao().getTodayAsteroidData(Constants.formattedCurrentDate())

    val pictureOfDay: LiveData<PictureOfDay> = database.asteroidDao().getPictureOfTheDay()


    suspend fun refreshAsteroidList() {
        withContext(Dispatchers.IO) {
            try {
                val asteroids = RetrofitInstance.createApiService.getAllAsteroids()
                val json = JSONObject(asteroids)
                val data = parseAsteroidsJsonResult(json)
                database.asteroidDao().updateAsteroidsData(data)
            } catch (e: Exception) {
                Log.e("data error", e.toString())
            }
        }
    }


    suspend fun getPictureOfTheDay() {
        withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.createApiService.getPictureOfTheDay(Constants.API_KEY)
                val pic = Moshi.Builder()
                    .build()
                    .adapter(PictureOfDay::class.java)
                    .fromJson(response)?: PictureOfDay(1, "image", "", "")
                database.asteroidDao().updateData(pic)
            } catch (e: Exception) {
                Log.e("pic error", e.toString())
            }
        }
    }



}