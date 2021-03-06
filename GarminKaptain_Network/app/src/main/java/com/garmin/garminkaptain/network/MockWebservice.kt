package com.garmin.garminkaptain.network

import android.graphics.Point
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.model.MapBoundingBox
import retrofit2.Call

class MockWebservice : Webservice {
    override fun getPoiList(bbBox: MapBoundingBox): Call<PoiListResponse> {
        TODO("Not yet implemented")
    }

    override fun getPoiReviews(poiId: Long): Call<ReviewListResponse> {
        TODO("Not yet implemented")
    }

    override fun getPoi(poiId: Long): Call<PoiResponse> {
        TODO("Not yet implemented")
    }

    override fun getReviewSummary(id: Long): Call<PoiSummaryResponse> {
        TODO("Not yet implemented")
    }
}