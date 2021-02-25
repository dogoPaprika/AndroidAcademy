package com.example.garminkaptain.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PointOfInterest::class, Review::class], version = 1)
abstract class PoiDatabase : RoomDatabase() {
    abstract fun getPoiDao(): PoiDao

    companion object{
        private var INSTANCE: PoiDatabase? = null
        fun getInstance(context: Context): PoiDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    PoiDatabase::class.java,
                    "poi-database")
                    .build()
            }

            return INSTANCE as PoiDatabase
        }
    }
}