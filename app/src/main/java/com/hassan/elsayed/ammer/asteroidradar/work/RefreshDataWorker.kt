package com.hassan.elsayed.ammer.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hassan.elsayed.ammer.asteroidradar.db.AsteroidDatabase
import com.hassan.elsayed.ammer.asteroidradar.repositories.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(
    appContext,
    params
) {
    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)
        return try {
            repository.refreshAsteroidList()
            repository.getPictureOfTheDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "refresh_data_worker"

    }
}
