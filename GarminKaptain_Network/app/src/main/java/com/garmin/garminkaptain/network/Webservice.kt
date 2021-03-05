package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.model.MapBoundingBox
import retrofit2.Call

interface Webservice {
    fun getPoiList(bbBox: MapBoundingBox): Call<PoiListResponse>
}