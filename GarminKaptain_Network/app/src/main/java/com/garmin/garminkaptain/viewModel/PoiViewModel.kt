package com.garmin.garminkaptain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.garmin.garminkaptain.KaptainApplication
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.data.Resource
import com.garmin.garminkaptain.model.MapBoundingBox
import com.garmin.garminkaptain.model.PoiRepository
import com.garmin.garminkaptain.network.KaptainWebservice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PoiViewModel(application: Application) : AndroidViewModel(application) {

    private var currentBbox: MapBoundingBox? = null
    private val poiRepository =
        PoiRepository((getApplication() as KaptainApplication).poiDatabase, KaptainWebservice())

    init {
        Log.d(TAG, "init called")
    }

    private val poiListLiveData: MutableLiveData<Resource<List<PointOfInterest>>> by lazy {
        MutableLiveData<Resource<List<PointOfInterest>>>()
    }

    private val poiListLiveData2: MutableLiveData<List<PointOfInterest>> by lazy {
        MutableLiveData<List<PointOfInterest>>()
    }

    private val poiLiveData: MutableLiveData<Resource<PointOfInterest>> by lazy {
        MutableLiveData<Resource<PointOfInterest>>()
    }

    fun getPoiListData(): LiveData<List<PointOfInterest>> {
        loadPoiList()
        return poiListLiveData2
    }

    fun getPoi(id: Long): LiveData<Resource<PointOfInterest>?> {
        loadPoi(id)
        return poiLiveData
    }

    private fun loadPoi(id: Long) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    poiLiveData.postValue(Resource.Loading())
                    val poi = poiRepository.getPoi(id)
                    val success = Resource.Success<PointOfInterest>(poi)

                    poiLiveData.postValue(success)
                }
            } catch (ex: Exception) {
                val message = ex.message ?: ""
                Log.d(TAG, "Exception $message")
                poiLiveData.postValue(Resource.Error(message))
            }
        }
    }

    fun getPoiList(bbBox: MapBoundingBox): LiveData<Resource<List<PointOfInterest>>> {
        this.currentBbox = bbBox
        loadPoiList(bbBox)
        return poiListLiveData
    }

    fun refreshPoiList() {
        currentBbox?.let { loadPoiList(it) }
    }

    private fun loadPoiList(bbBox: MapBoundingBox) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    poiListLiveData.postValue(Resource.Loading())
                    val poiList = poiRepository.getPoiList(bbBox)
                    val success = Resource.Success<List<PointOfInterest>>(poiList)

                    poiListLiveData.postValue(success)
                }
            } catch (ex: Exception) {
                val message = ex.message ?: ""
                Log.d(TAG, "Exception $message")
                poiListLiveData.postValue(Resource.Error(message))
            }
        }
    }

    private fun loadPoiList() {
        viewModelScope.launch {
            poiListLiveData2.postValue(poiRepository.getPoiList())
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared() called")
    }

}