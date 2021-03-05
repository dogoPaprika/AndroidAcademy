package com.garmin.garminkaptain.data

import android.util.LongSparseArray
import com.garmin.garminkaptain.model.MapBoundingBox
import kotlin.random.Random

val poiList: List<PointOfInterest> = listOf(
    PointOfInterest(
        46067,
        MapLocation(37.8180564724432, -122.52704143524173),
        "Point Bonita",
        "Anchorage",
        ReviewSummary(3.0, 1)
    ),
    PointOfInterest(
        12975,
        MapLocation(37.8770892291283, -122.503309249878),
        "Richardson Bay Marina",
        "Marina",
        ReviewSummary(5.0, 1)
    ),
    PointOfInterest(
        46085,
        MapLocation(37.82878469060811, -122.47633210712522),
        "Needles",
        "Anchorage",
        ReviewSummary(1.0, 2)
    ),
    PointOfInterest(
        19637,
        MapLocation(37.82077, -122.4786),
        "Golden Gate Bridge",
        "Bridge",
        ReviewSummary(0.0, 0)
    ),
    PointOfInterest(
        60928,
        MapLocation(37.8325155338083, -122.47500389814363),
        "Horseshoe Cove",
        "Anchorage",
        ReviewSummary(3.0, 2)
    ),
    PointOfInterest(
        39252,
        MapLocation(37.833886767314, -122.475371360779),
        "Presidio Yacht Club",
        "Marina",
        ReviewSummary(3.0, 5)
    ),
    PointOfInterest(
        25644,
        MapLocation(37.8673327691044, -122.435932159424),
        "Ayala Cove",
        "Anchorage",
        ReviewSummary(4.7, 18)
    ),
    PointOfInterest(
        61865,
        MapLocation(37.850002964208095, -122.41632213957898),
        "Tide Rips",
        "Hazard",
        ReviewSummary(0.0, 0)
    ),
    PointOfInterest(
        46713,
        MapLocation(37.827799573006274, -122.42648773017541),
        "Dangerous Rock",
        "Hazard",
        ReviewSummary(0.0, 0)
    ),
    PointOfInterest(
        57109,
        MapLocation(37.87572310328571, -122.50570595169079),
        "Woodrum Marine Boat Repair/Carpentry",
        "Business",
        ReviewSummary(0.0, 0)
    )
)

val reviews = LongSparseArray<List<Review>>(poiList.size).also { map ->
    poiList.forEach {
        map.put(
            it.id,
            randomReviewList(
                it.id,
                it.reviewSummary?.numberOfReviews ?: 0,
                it.reviewSummary?.averageRating ?: 0.0
            )
        )
    }
}

val mockBoundingBox = MapBoundingBox(north = 28.074301976209195, south = 27.837456158746768, east = -80.2721992135048, west = -80.65328747034074)


private fun randomReviewList(poiId: Long, size: Int, rating: Double) =
    mutableListOf<Review>().also { list ->
        (0 until size).forEach {
            list += randomReview(
                poiId,
                rating
            )
        }
    }

private fun randomReview(poiId: Long, rating: Double): Review {
    return Review(
        Random.nextLong(1L, Long.MAX_VALUE),
        poiId,
        rating,
        randomString(Random.nextInt(7, 20)),
        randomString(Random.nextInt(50, 150))
    )
}

private fun randomString(length: Int): String {
    return (0 until length)
        .map { (('A'..'Z') + ('a'..'z') + ('0'..'9')).random() }
        .joinToString("")
}