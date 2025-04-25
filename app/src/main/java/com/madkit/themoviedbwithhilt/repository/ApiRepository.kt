package com.madkit.themoviedbwithhilt.repository

import com.madkit.themoviedbwithhilt.api.ApiServices
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(private val apiServices: ApiServices) {

    fun getPopularMoviesList(page: Int) = apiServices.getPopularMoviewsList(page)

    fun getMovieDetail(id: Int) = apiServices.getMoviewDetails(id)
}