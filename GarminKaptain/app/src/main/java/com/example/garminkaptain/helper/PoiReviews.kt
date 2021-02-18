package com.example.garminkaptain.helper

import com.example.garminkaptain.data.Date
import com.example.garminkaptain.data.PointOfInterest
import com.example.garminkaptain.data.Review
import com.example.garminkaptain.helper.RandomUtil.getRandomId
import com.example.garminkaptain.helper.RandomUtil.getRandomOwner
import com.example.garminkaptain.helper.RandomUtil.getRandomText
import kotlin.random.Random.Default.nextFloat
import kotlin.random.Random.Default.nextInt

object PoiReviews {
    fun addReviews(poiList: List<PointOfInterest>) {
        for (poi in poiList) {
            for (i in 1..poi.reviewSummary.numberOfReviews) {
                poi.userReviews.add(newReview(poi))
            }
        }
    }

    private fun newReview(poi: PointOfInterest): Review {
        return Review(
            getRandomId(),
            nextFloat() * 5,
            getRandomOwner(),
            getRandomText(poi.name),
            Date(
                nextInt(2015, 2021),
                nextInt(1, 13),
                nextInt(1, 30)
            )
        )
    }
}