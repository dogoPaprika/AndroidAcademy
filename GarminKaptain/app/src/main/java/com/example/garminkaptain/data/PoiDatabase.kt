package com.example.garminkaptain.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [PointOfInterest::class, Review::class, MapLocation::class, ReviewSummary::class], version = 1)
abstract class PoiDatabase : RoomDatabase() {
    abstract fun getPoiDao(): PoiDao

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE `Review` (`id` INTEGER NOT NULL, `poiId` INTEGER NOT NULL, `rating` REAL NOT NULL," +
                        "`owner` TEXT NOT NULL, `text` TEXT NOT NULL, `date` DATE NOT NULL" +
                        "PRIMARY KEY(`id`))"
            )
        }
    }

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