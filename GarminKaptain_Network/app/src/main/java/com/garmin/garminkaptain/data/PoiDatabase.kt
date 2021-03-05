package com.garmin.garminkaptain.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [PointOfInterest::class, Review::class], version = 4)
abstract class PoiDatabase : RoomDatabase() {
    abstract fun getPoiDao(): PoiDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE `Review` (`id` INTEGER NOT NULL, `poiId` INTEGER NOT NULL, `rating` REAL NOT NULL," +
                    "`title` TEXT NOT NULL, `text` TEXT NOT NULL," +
                    "PRIMARY KEY(`id`))"
        )
    }
}