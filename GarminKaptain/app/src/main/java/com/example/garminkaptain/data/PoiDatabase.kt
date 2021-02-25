package com.example.garminkaptain.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PointOfInterest::class, Review::class], version = 1)
abstract class PoiDatabase : RoomDatabase() {
    abstract fun getPoiDao(): PoiDao
}