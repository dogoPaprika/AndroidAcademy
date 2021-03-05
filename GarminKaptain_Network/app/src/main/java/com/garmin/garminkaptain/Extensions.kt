package com.garmin.garminkaptain

inline val <reified T> T.TAG
    get() = T::class.java.name.substringAfterLast('.')