package com.hassan.elsayed.ammer.asteroidradar.ui

import android.app.Application
import androidx.lifecycle.*
import com.hassan.elsayed.ammer.asteroidradar.db.AsteroidDatabase
import com.hassan.elsayed.ammer.asteroidradar.models.Asteroid
import com.hassan.elsayed.ammer.asteroidradar.repositories.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {
    private val database = AsteroidDatabase.getInstance(application)
    private val repo = AsteroidRepository(database)

    private val weeklyAsteroidList = repo.asteroidList
    private val currentlyAsteroidList = repo.todayAsteroidList

    val pictureOfTheDay = repo.pictureOfDay


    private val _navigateToSelectedItem = MutableLiveData<Asteroid?>()
    val asteroidList: MediatorLiveData<List<Asteroid>> = MediatorLiveData()


    val navigateToSelectedItem: MutableLiveData<Asteroid?> = _navigateToSelectedItem

    init {
        viewModelScope.launch {
            repo.refreshAsteroidList()
            repo.getPictureOfTheDay()
            asteroidList.addSource(weeklyAsteroidList) {
                asteroidList.value = it
            }
        }
    }


    fun showItemDetails(asteroid: Asteroid) {
        _navigateToSelectedItem.value = asteroid
    }

    fun setNull() {
        _navigateToSelectedItem.value = null
    }

    fun viewWeekAsteroids() {
        removeSourceList()
        asteroidList.addSource(weeklyAsteroidList) {
            asteroidList.value = it
        }
    }

    fun viewTodayAsteroids() {
        removeSourceList()
        asteroidList.addSource(currentlyAsteroidList) {
            asteroidList.value = it
        }
    }

    fun viewSavedAsteroids() {
        removeSourceList()
        asteroidList.addSource(weeklyAsteroidList) {
            asteroidList.value = it
        }
    }

    private fun removeSourceList() {
        asteroidList.removeSource(currentlyAsteroidList)
        asteroidList.removeSource(weeklyAsteroidList)
    }

}