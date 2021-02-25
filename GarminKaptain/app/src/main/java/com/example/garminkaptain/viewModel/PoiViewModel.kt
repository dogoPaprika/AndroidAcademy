package com.example.garminkaptain.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.garminkaptain.TAG
import com.example.garminkaptain.data.PointOfInterest
import com.example.garminkaptain.data.Review
import com.example.garminkaptain.model.PoiRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import android.app.Application
import com.example.garminkaptain.data.PoiDatabase
import kotlinx.coroutines.launch

class PoiViewModel(application: Application) : AndroidViewModel(application) {
    private var poiDatabase: PoiDatabase

    init {
        Log.d(TAG, "init called")
        poiDatabase = PoiDatabase.getInstance(application)

        refreshList()
    }

    private val poiListLiveData: MutableLiveData<List<PointOfInterest>> by lazy {
        MutableLiveData<List<PointOfInterest>>()
    }

    private val reviewListLiveData: MutableLiveData<List<Review>> by lazy {
        MutableLiveData<List<Review>>()
    }

    private val loadingLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getLoading(): LiveData<Boolean> = loadingLiveData

    fun getPoi(id: Long): LiveData<PointOfInterest?> = liveData {
        loadingLiveData.postValue(true)
        PoiRepository.getPoi(poiDatabase, id)?.collect {
            emit(it)
            loadingLiveData.postValue(false)
        }
    }


    fun getPoiList(): LiveData<List<PointOfInterest>> {
        loadPoiList()
        return poiListLiveData
    }

    fun loadPoiList() {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            PoiRepository.getPoiList(poiDatabase)?.collect {
                poiListLiveData.postValue(it)
                loadingLiveData.postValue(false)
            }
        }
    }

    fun getReviewList(id: Long): LiveData<List<Review>> {
        loadReviewList(id)
        return reviewListLiveData
    }

    fun loadReviewList(id: Long) {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            PoiRepository.getReviewList(poiDatabase, id).collect {
                reviewListLiveData.postValue(it)
                loadingLiveData.postValue(false)
            }
        }
    }

    private fun refreshList() {
        viewModelScope.launch {
            while (true) {
                delay(5000)
                loadPoiList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared() called")
    }
}