package com.example.garminkaptain.model

import com.example.garminkaptain.KaptainApplication
import com.example.garminkaptain.data.PointOfInterest
import com.example.garminkaptain.data.Review
import com.example.garminkaptain.data.poiList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object PoiRepository {
    fun getPoiList(application: KaptainApplication): Flow<List<PointOfInterest>> =
        application.poiDatabase.getPoiDao().getAllPoi()

    fun getPoi(application: KaptainApplication, id: Long): Flow<PointOfInterest?> =
        application.poiDatabase.getPoiDao().getPoi(id)

    fun getReviewList(application: KaptainApplication, id: Long): Flow<List<Review>?> =
        application.poiDatabase.getPoiDao().getReviews(id)
}