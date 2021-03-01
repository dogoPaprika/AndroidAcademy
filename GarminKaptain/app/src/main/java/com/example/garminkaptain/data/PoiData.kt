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

@Entity(tableName = "map_location_table")
data class MapLocation(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey val poiId: Long
)

@Entity(tableName = "review_summary_table")
data class ReviewSummary(
    val averageRating: Double,
    val numberOfReviews: Int,
    @PrimaryKey @Embedded(prefix = "poi_id") val poiId: Long
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

// relation 1:n for Poi:Review
data class PoiWithReviews(
    @Embedded val poi: PointOfInterest,
    @Relation(
        parentColumn = "id",
        entityColumn = "poiId"
    )
    val reviews: List<Review>
)

// relation 1:1 for Poi:MapLocation
data class PoiWithMapLocation(
    @Embedded val poi: PointOfInterest,
    @Relation(
        parentColumn = "id",
        entityColumn = "poiId"
    )
    val mapLocation: MapLocation
)

// relation 1:1 for Poi:ReviewSummary
data class PoiWithReviewSummary(
    @Embedded val poi: PointOfInterest,
    @Relation(
        parentColumn = "id",
        entityColumn = "poiId"
    )
    val reviewSummary: ReviewSummary
)