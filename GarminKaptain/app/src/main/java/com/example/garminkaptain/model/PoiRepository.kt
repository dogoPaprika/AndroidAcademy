package com.example.garminkaptain.model

import android.app.Application
import android.content.Context
import com.example.garminkaptain.data.PoiDatabase
import com.example.garminkaptain.data.PointOfInterest
import com.example.garminkaptain.data.Review
import kotlinx.coroutines.flow.Flow

object PoiRepository {
    fun getPoiList(db: PoiDatabase): Flow<List<PointOfInterest>> =
        db.getPoiDao().getAllPoi()

    fun getPoi(db: PoiDatabase, id: Long): Flow<PointOfInterest?> =
        db.getPoiDao().getPoi(id)

    fun getReviewList(db: PoiDatabase, id: Long): Flow<List<Review>?> =
        db.getPoiDao().getReviews(id)
}