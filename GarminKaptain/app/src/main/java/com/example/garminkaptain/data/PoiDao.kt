package com.example.garminkaptain.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {

    @Insert
    suspend fun insertPoi(poi: PointOfInterest)

    @Insert
    suspend fun insertAllPoi(poiList: List<PointOfInterest>)

    @Delete
    suspend fun deletePoi(poi: PointOfInterest)

    @Update
    suspend fun updatePoi(poi: PointOfInterest)

    @Query("SELECT * from poi_table")
    fun getAllPoi(): Flow<List<PointOfInterest>>

    @Query("SELECT * from poi_table WHERE id=:id")
    fun getPoi(id: Long): Flow<PointOfInterest>

    @Query("SELECT * from review_table WHERE poiId = :id")
    fun getReviews(id: Long): Flow<List<Review>?>

    @Insert
    fun insertAllReview(reviewList: List<Review>)

    @Transaction
    @Query("SELECT * FROM poi_table WHERE id=:id")
    suspend fun getPoiWithReviews(id: Long): PoiWithReviews

    @Transaction
    @Query("SELECT * FROM poi_table")
    suspend fun getAllPoiWithReviews(): List<PoiWithReviews>

    @Query("DELETE FROM review_table")
    fun deleteAllReview()

    @Query("DELETE FROM poi_table WHERE id=:id")
    fun deletePoi(id: Long)

    @Query("DELETE FROM review_table WHERE poiId=:id")
    fun deleteReviews(id: Long)

    @Query("DELETE FROM poi_table")
    fun deleteAllPoi()
}