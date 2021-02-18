package com.example.garminkaptain.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.garminkaptain.TAG
import com.example.garminkaptain.data.PointOfInterest
import com.example.garminkaptain.model.PoiRepository

class PoiViewModel : ViewModel() {

    init {
        Log.d(TAG, "init called")
    }

    private val poiListLiveData: MutableLiveData<List<PointOfInterest>> by lazy {
        MutableLiveData<List<PointOfInterest>>()
    }

    private val poiLiveData: MutableLiveData<PointOfInterest> by lazy {
        MutableLiveData<PointOfInterest>()
    }

    fun getPoi(id: Long): LiveData<PointOfInterest> {
        loadPoi(id)
        return poiLiveData
    }

    fun getPoiList(): LiveData<List<PointOfInterest>> {
        loadPoiList()
        return poiListLiveData
    }

    fun findPoiOnLatitudeLongitude(latitude: Double, longitude: Double): LiveData<PointOfInterest> {
        loadPoi(latitude, longitude)
        return poiLiveData
    }

    private fun loadPoiList() {
        poiListLiveData.postValue(PoiRepository.getPoiList())
    }

    private fun loadPoi(id: Long) {
        poiLiveData.postValue(PoiRepository.getPoi(id))
    }

    private fun loadPoi(latitude: Double, longitude: Double) {
        poiLiveData.postValue(PoiRepository.findPoiOnLatitudeLongitude(latitude, longitude))
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared() called")
    }
}