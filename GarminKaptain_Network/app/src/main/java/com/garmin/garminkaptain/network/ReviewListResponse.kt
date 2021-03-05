package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.data.Review
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewListResponse(@field:Json(name = "reviews") val reviews: List<Review>)