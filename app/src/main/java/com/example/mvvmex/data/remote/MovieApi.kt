package com.example.mvvmex.data.remote

import com.example.mvvmex.entity.MovieResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("v1/search/movie")
    fun getMovieList(@Query("query") query: String): Single<MovieResult>
}
