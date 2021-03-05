package com.garmin.garminkaptain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.garmin.garminkaptain.KaptainApplication
import com.garmin.garminkaptain.data.Review
import com.garmin.garminkaptain.model.PoiRepository
import kotlinx.coroutines.launch

class ReviewViewModel(application: Application) : AndroidViewModel(application) {

    private val poiRepository = PoiRepository((getApplication() as KaptainApplication).poiDatabase)

    private val _reviewLiveData: MutableLiveData<List<Review>> = MutableLiveData()

    val reviewLiveData: LiveData<List<Review>>
        get() = _reviewLiveData

    fun getReviews(id: Long) = viewModelScope.launch {
        _reviewLiveData.postValue(poiRepository.getReviews(id))
    }
}