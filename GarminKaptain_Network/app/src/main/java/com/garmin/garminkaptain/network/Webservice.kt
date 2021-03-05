package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.model.MapBoundingBox
import com.garmin.garminkaptain.data.PointOfInterest
import retrofit2.Call

interface Webservice {
    fun getPoiList(bbBox: MapBoundingBox): Call<PoiListResponse>
    fun getPoiReviews(poiId: Long): Call<ReviewListResponse>
    fun getPoi(poiId: Long): Call<PoiResponse>
}