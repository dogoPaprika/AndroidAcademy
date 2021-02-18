package com.example.garminkaptain.model

import com.example.garminkaptain.data.poiList

object PoiRepository {
    fun getPoiList() = poiList

    fun getPoi(id: Long) = poiList.find { it.id == id }
}