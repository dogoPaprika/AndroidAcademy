package com.example.garminkaptain.helper

import com.example.garminkaptain.data.PointOfInterest
import com.example.garminkaptain.data.Review
import com.example.garminkaptain.helper.RandomUtil.getRandomId
import com.example.garminkaptain.helper.RandomUtil.getRandomIntInRange
import com.example.garminkaptain.helper.RandomUtil.getRandomOwner
import com.example.garminkaptain.helper.RandomUtil.getRandomRating
import com.example.garminkaptain.helper.RandomUtil.getRandomText
import java.time.LocalDate

object PoiReviews {
    fun addReviews(poiList: List<PointOfInterest>) {
        for (poi in poiList) {
            for(i in 1..poi.reviewSummary.numberOfReviews) {
                poi.userReviews.add(newReview(poi))
            }
        }
    }

    private fun newReview(poi: PointOfInterest): Review {
        return Review(getRandomId(),
            getRandomRating(),
            getRandomOwner(),
            getRandomText(poi.name),
            LocalDate.of(getRandomIntInRange(2015, 2020), getRandomIntInRange(1, 12), getRandomIntInRange(1, 29)))
    }
}