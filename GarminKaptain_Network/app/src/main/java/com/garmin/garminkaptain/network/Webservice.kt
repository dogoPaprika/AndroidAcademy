package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.data.Review
import com.garmin.garminkaptain.model.MapBoundingBox
import retrofit2.Call

interface Webservice {
    fun getPoiList(bbBox: MapBoundingBox): Call<PoiListResponse>
    fun getPoiReviews(poiId: Long): Call<List<Review>>
    fun getReviewSummary(id: Long): Call<PoiSummaryResponse>
}