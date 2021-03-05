package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.data.PointOfInterest
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PoiListResponse( @field:Json(name = "pointsOfInterest")  val pointsOfInterest: List<PointOfInterest>)
