package com.garmin.garminkaptain.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "poi_table")
data class PointOfInterest(
    @PrimaryKey val id: Long,
    @Embedded val mapLocation: MapLocation,
    val name: String?,
    val poiType: String,
)

data class MapLocation(
    val latitude: Double,
    val longitude: Double
)

@Entity(tableName = "review_summary_table")
data class ReviewSummary(
    val averageRating: Double,
    val numberOfReviews: Int,
    @PrimaryKey val poiIdReviewSummary: Long
)

@Entity(tableName = "review_table")
data class Review(
    @PrimaryKey val id: Long,
    val poiId: Long,
    val rating: Double,
    val title: String,
    val text: String
)

data class PoiWithReviews(
    @Embedded val poi: PointOfInterest,
    @Relation(
        parentColumn = "id",
        entityColumn = "poiId"
    )

    val reviews: List<Review>
)