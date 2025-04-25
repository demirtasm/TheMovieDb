package com.madkit.themoviedbwithhilt.utils

import com.madkit.themoviedbwithhilt.BuildConfig

object Constants {
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500/" //342

    const val NETWORK_TIMEOUT = 60L
}