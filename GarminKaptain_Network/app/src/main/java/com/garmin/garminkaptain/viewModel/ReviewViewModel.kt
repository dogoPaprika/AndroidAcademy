package com.garmin.garminkaptain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.garmin.garminkaptain.KaptainApplication
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.Resource
import com.garmin.garminkaptain.data.Review
import com.garmin.garminkaptain.data.ReviewSummary
import com.garmin.garminkaptain.model.PoiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReviewViewModel(application: Application) : AndroidViewModel(application) {

    private val poiRepository = PoiRepository((getApplication() as KaptainApplication).poiDatabase)

    private val _reviewLiveData: MutableLiveData<Resource<List<Review>>> = MutableLiveData()

    val reviewLiveData: LiveData<Resource<List<Review>>>
        get() = _reviewLiveData

    private val reviewSummaryLiveData: MutableLiveData<Resource<ReviewSummary>> by lazy {
        MutableLiveData<Resource<ReviewSummary>>()
    }

    fun getReviews(id: Long) = viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                _reviewLiveData.postValue(Resource.Loading())
                val reviewList = poiRepository.getPoiReviews(id)
                val success = Resource.Success<List<Review>>(reviewList)

                _reviewLiveData.postValue(success)
            }
        } catch (ex: Exception) {
            val message = ex.message ?: ""
            Log.d(TAG, "Exception $message")
            _reviewLiveData.postValue(Resource.Error(message))
        }
    }

    fun getReviewSummary(id: Long): LiveData<Resource<ReviewSummary>> {
        loadReviewSummary(id)
        return reviewSummaryLiveData
    }

    private fun loadReviewSummary(id: Long) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    reviewSummaryLiveData.postValue(Resource.Loading())
                    val reviewSummary = poiRepository.getReviewSummary(id)
                    val success = Resource.Success(reviewSummary)

                    reviewSummaryLiveData.postValue(success)
                }
            } catch (ex: Exception) {
                val message = ex.message ?: ""
                Log.d(TAG, "Exception review summary $message")
                reviewSummaryLiveData.postValue(Resource.Error(message))
            }
        }
    }
}