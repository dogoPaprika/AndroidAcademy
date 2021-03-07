package com.garmin.garminkaptain.model

import android.util.Log
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.PoiDatabase
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.data.Review
import com.garmin.garminkaptain.data.ReviewSummary
import com.garmin.garminkaptain.network.KaptainWebservice
import com.garmin.garminkaptain.network.Webservice

class PoiRepository(
    private val database: PoiDatabase,
    private val webService: Webservice = KaptainWebservice(),
) {

    private val dataIsStale = true

    suspend fun getPoiList(bbBox: MapBoundingBox): List<PointOfInterest> {
        var result: List<PointOfInterest>? = null
        val cacheList = database.getPoiDao().getAllPoi()
        result = cacheList

        if (cacheList.isEmpty() || dataIsStale) {
            val response = webService.getPoiList(bbBox).execute()
            if (response.isSuccessful) {
                val data = response.body()?.pointsOfInterest
                if (data != null) {
                    result = data
                }
            }
        }

        return result ?: throw Exception("Empty Data")
    }

    suspend fun getPoiReviews(poiId: Long): List<Review> {
        var result: List<Review>?
        val cacheList = database.getPoiDao().getAllReview(poiId)
        result = cacheList

        if (cacheList.isEmpty() || dataIsStale) {
            val response = webService.getPoiReviews(poiId).execute()
            if (response.isSuccessful) {
                val data = response.body()
                data?.let {
                    result = data
                }
            }
        }

        return result ?: throw Exception("Empty Data")
    }

    fun getReviewSummary(id: Long): ReviewSummary {
        var result: ReviewSummary?
        val cacheResult = database.getPoiDao().getReviewSummary(id)
        result = cacheResult

        if (result == null || dataIsStale) {
            val response = webService.getReviewSummary(id).execute()
            if (response.isSuccessful) {
                val data = response.body()?.reviewSummary
                data?.let {
                    result = it
                }
            } else
                Log.d(TAG, "Unsuccessful")
        }

        return result ?: throw Exception("Empty Data")
    }

    suspend fun getPoiList() = database.getPoiDao().getAllPoi()
    fun getPoi(id: Long) = database.getPoiDao().getPoi(id)
}