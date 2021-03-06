package com.garmin.garminkaptain.data

import androidx.room.*

@Dao
interface PoiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoi(poi: PointOfInterest)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPoi(poiList: List<PointOfInterest>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReviews(reviews: List<Review>)

    @Delete
    suspend fun deletePoi(poi: PointOfInterest)

    @Update
    suspend fun updatePoi(poi: PointOfInterest)

    @Query("SELECT * from poi_table")
    suspend fun getAllPoi(): List<PointOfInterest>

    @Query("SELECT * from review_table WHERE poiId=:id")
    suspend fun getAllReview(id: Long): List<Review>

    @Query("SELECT * from poi_table WHERE id=:id")
    fun getPoi(id: Long): PointOfInterest

    @Transaction
    @Query("SELECT * FROM poi_table WHERE id=:id")
    suspend fun getPoiWithReviews(id: Long): PoiWithReviews

    @Transaction
    @Query("SELECT * FROM poi_table")
    suspend fun getAllPoiWithReviews(): List<PoiWithReviews>

    @Transaction
    @Query("SELECT * from review_summary_table WHERE poiId=:id")
    fun getReviewSummary(id: Long): ReviewSummary?

}