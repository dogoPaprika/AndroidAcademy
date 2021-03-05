package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.model.MapBoundingBox
import retrofit2.Call

class MockWebservice : Webservice {
    override fun getPoiList(bbBox: MapBoundingBox): Call<PoiListResponse> {
        TODO("Not yet implemented")
    }

}