package com.example.mvvmex.data.remote

import com.example.mvvmex.data.MovieApi
import com.example.mvvmex.entity.MovieResult
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieRemoteRepositoryImpl @Inject constructor(private val movieApi: MovieApi) : MovieRemoteRepository {
    override fun getMovieList(movieTitle: String): Single<MovieResult> {
        return movieApi.getMovieList(movieTitle)
    }
}
