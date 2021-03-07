package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.data.ReviewSummary
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PoiSummaryResponse(@field:Json(name = "reviewSummary") val reviewSummary: ReviewSummary?)