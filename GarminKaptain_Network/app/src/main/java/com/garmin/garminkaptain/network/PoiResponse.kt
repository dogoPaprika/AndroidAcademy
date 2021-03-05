package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.data.PointOfInterest
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PoiResponse( @field:Json(name = "pointOfInterest")  val pointOfInterest: PointOfInterest)