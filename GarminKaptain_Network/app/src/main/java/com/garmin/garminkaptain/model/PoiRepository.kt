package com.garmin.garminkaptain.model

import com.garmin.garminkaptain.data.PoiDatabase
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.data.Review
import com.garmin.garminkaptain.network.MockWebservice
import com.garmin.garminkaptain.network.Webservice
import kotlinx.coroutines.flow.Flow

class PoiRepository(
    private val database: PoiDatabase,
    private val webserice: Webservice = MockWebservice(),
) {

    private val dataIsStale = true

    suspend fun getPoiList(bbBox: MapBoundingBox): List<PointOfInterest> {
        var result: List<PointOfInterest>? = null
        val cacheList = database.getPoiDao().getAllPoi()
        result = cacheList

        if (cacheList.isEmpty() || dataIsStale) {
            val response = webserice.getPoiList(bbBox).execute()
            if (response.isSuccessful) {
                val data = response.body()?.pointsOfInterest
                if (data != null) {
                    result = data
                }
            }
        }

        return result ?: throw Exception("Empty Data")
    }


    fun getPoi(id: Long): Flow<PointOfInterest> {
        return database.getPoiDao().getPoi(id)
    }


    suspend fun getReviews(id: Long): List<Review> {
        return database.getPoiDao().getPoiWithReviews(id).reviews
    }
}