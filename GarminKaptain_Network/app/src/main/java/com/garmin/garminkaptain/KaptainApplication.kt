package com.garmin.garminkaptain

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.garmin.garminkaptain.data.MIGRATION_1_2
import com.garmin.garminkaptain.data.PoiDatabase
import com.garmin.garminkaptain.data.poiList
import com.garmin.garminkaptain.data.reviews
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class KaptainApplication : Application() {

    lateinit var poiDatabase: PoiDatabase

    val roomListener = object : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            GlobalScope.launch {
                //add initial mock data
                poiDatabase.getPoiDao().insertAllPoi(poiList)

                poiList.forEach {
                    poiDatabase.getPoiDao().insertAllReviews(reviews[it.id])
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        poiDatabase =
            Room.databaseBuilder(applicationContext, PoiDatabase::class.java, "poi-database")
                .fallbackToDestructiveMigration()
                .addCallback(roomListener)
                .addMigrations(MIGRATION_1_2).build()
    }
}