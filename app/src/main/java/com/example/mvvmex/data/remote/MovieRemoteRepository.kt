package com.example.mvvmex.data.remote

import com.example.mvvmex.entity.MovieResult
import io.reactivex.rxjava3.core.Single

interface MovieRemoteRepository {
    fun getMovieList(movieTitle: String): Single<MovieResult>
}
