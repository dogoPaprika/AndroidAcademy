package com.example.garminkaptain.data

import com.example.garminkaptain.helper.PoiReviews

val poiList: List<PointOfInterest> = listOf(
    PointOfInterest(
        46067,
        MapLocation(37.8180564724432, -122.52704143524173, 46067),
        "Point Bonita",
        "Anchorage",
        ReviewSummary(3.4, 1, 46067)
    ),
    PointOfInterest(
        12975,
        MapLocation(37.8770892291283, -122.503309249878, 12975),
        "Richardson Bay Marina",
        "Marina",
        ReviewSummary(5.0, 1, 12975)
    ),
    PointOfInterest(
        46085,
        MapLocation(37.82878469060811, -122.47633210712522, 46085),
        "Needles",
        "Anchorage",
        ReviewSummary(1.0, 2, 46085)
    ),
    PointOfInterest(
        19637,
        MapLocation(37.82077, -122.4786, 19637),
        "Golden Gate Bridge",
        "Bridge",
        ReviewSummary(0.0, 0, 19637)
    ),
    PointOfInterest(
        60928,
        MapLocation(37.8325155338083, -122.47500389814363, 60928),
        "Horseshoe Cove",
        "Anchorage",
        ReviewSummary(3.0, 2, 60928)
    ),
    PointOfInterest(
        39252,
        MapLocation(37.833886767314, -122.475371360779, 39252),
        "Presidio Yacht Club",
        "Marina",
        ReviewSummary(3.0, 5, 39252)
    ),
    PointOfInterest(
        25644,
        MapLocation(37.8673327691044, -122.435932159424, 25644),
        "Ayala Cove",
        "Anchorage",
        ReviewSummary(4.7, 18, 25644)
    ),
    PointOfInterest(
        61865,
        MapLocation(37.850002964208095, -122.41632213957898, 61865),
        "Tide Rips",
        "Hazard",
        ReviewSummary(0.0, 0,61865)
    ),
    PointOfInterest(
        46713,
        MapLocation(37.827799573006274, -122.42648773017541, 61865),
        "Dangerous Rock",
        "Hazard",
        ReviewSummary(0.0, 0, 61865)
    ),
    PointOfInterest(
        57109,
        MapLocation(37.87572310328571, -122.50570595169079, 57109),
        "Woodrum Marine Boat Repair/Carpentry",
        "Business",
        ReviewSummary(0.0, 0, 57109)
    )
)

val reviewList = PoiReviews.addReviews(poiList)