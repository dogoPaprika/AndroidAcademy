package com.example.garminkaptain.helper

import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

object RandomUtil {
    private val seed = AtomicInteger()

    fun getRandomId() = seed.getAndIncrement() + System.currentTimeMillis()

    fun getRandomOwner(): String {
        val i = nextInt(0, lastNamesOwner.size)
        val j = nextInt(0, firstNamesOwner.size)

        return "${lastNamesOwner[i]} ${firstNamesOwner[j]}"
    }

    fun getRandomText(locationName: String): String {
        val i = nextInt(0, comments.size)

        return "${comments[i]} $locationName"
    }

    private val lastNamesOwner = arrayListOf("Elon", "Stephen", "Nikola", "Carl")
    private val firstNamesOwner = arrayListOf("Musk", "Hawking", "Tesla", "Sagan")
    private val comments = arrayListOf("Feeling great at", "Nice weather in", "Enjoying the view from")
}