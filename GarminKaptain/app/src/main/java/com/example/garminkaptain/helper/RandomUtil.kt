package com.example.garminkaptain.helper

import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

object RandomUtil {
    private val seed = AtomicInteger()

    fun getRandomId() = seed.getAndIncrement() + System.currentTimeMillis()

    fun getRandomIntInRange(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(start, end + 1)
    }

    fun getRandomRating(): Float {
        return  Random(System.nanoTime()).nextFloat() * 5
    }

    fun getRandomOwner(): String {
        val i = getRandomIntInRange(0, lastNamesOwner.size - 1)
        val j = getRandomIntInRange(0, firstNamesOwner.size - 1)

        return "${lastNamesOwner[i]} ${firstNamesOwner[j]}"
    }

    fun getRandomText(locationName: String): String {
        val i = getRandomIntInRange(0, comments.size - 1)

        return "${comments[i]} $locationName"
    }

    private val lastNamesOwner = arrayListOf("Elon", "Stephen", "Nikola", "Carl")
    private val firstNamesOwner = arrayListOf("Musk", "Hawking", "Tesla", "Sagan")
    private val comments = arrayListOf("Feeling great at", "Nice weather in", "Enjoying the view from")
}