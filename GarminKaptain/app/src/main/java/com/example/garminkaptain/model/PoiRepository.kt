package com.example.garminkaptain.model

import com.example.garminkaptain.data.PointOfInterest
import com.example.garminkaptain.data.Review
import com.example.garminkaptain.data.poiList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object PoiRepository {
    fun getPoiList(): Flow<List<PointOfInterest>> = flow {
        delay(2000)
        emit(poiList)
    }

    fun getPoi(id: Long): Flow<PointOfInterest?> = flow {
        delay(2000)
        emit(poiList.find { it.id == id })
    }

    fun getReviewList(id: Long): Flow<List<Review>?> = flow {
        delay(2000)
        emit(poiList.find { it.id == id }?.userReviews)
    }
}