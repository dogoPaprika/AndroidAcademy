package com.example.garminkaptain.data

import androidx.room.*

@Entity(tableName = "poi_table")
data class PointOfInterest(
    @PrimaryKey val id: Long,
    @Embedded val mapLocation: MapLocation,
    val name: String,
    val poiType: String,
    @Embedded val reviewSummary: ReviewSummary
)

data class MapLocation(
    val latitude: Double,
    val longitude: Double
)

data class ReviewSummary(
    val averageRating: Double,
    val numberOfReviews: Int
)

@Entity(tableName = "review_table")
data class Review(
    @PrimaryKey val id: Long,
    val poiId: Long,
    val rating: Float,
    val owner: String,
    val text: String,
    @Embedded val date: Date
    )

data class Date(
    val year: Int,
    val month: Int,
    val day: Int
)

data class PoiWithReviews(
    @Embedded val poi: PointOfInterest,
    @Relation(
        parentColumn = "id",
        entityColumn = "poiId"
    )
    val reviews: List<Review>
)