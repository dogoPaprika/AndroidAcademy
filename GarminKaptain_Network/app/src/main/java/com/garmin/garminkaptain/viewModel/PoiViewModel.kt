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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class PoiViewModel(application: Application) : AndroidViewModel(application) {

    private var currentBbox: MapBoundingBox? = null
    private val poiRepository = PoiRepository((getApplication() as KaptainApplication).poiDatabase, KaptainWebservice())

    init {
        Log.d(TAG, "init called")
    }

    private val poiListLiveData: MutableLiveData<Resource<List<PointOfInterest>>> by lazy {
        MutableLiveData<Resource<List<PointOfInterest>>>()
    }

    fun getPoiListData(): LiveData<Resource<List<PointOfInterest>>> {
        return poiListLiveData
    }

    fun getPoi(id: Long): LiveData<PointOfInterest?> = liveData {
        poiRepository.getPoi(id)?.collect {
            emit(it)
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

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared() called")
    }

}