package com.example.mvvmex.data.local

import com.example.mvvmex.entity.MovieResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MovieLocalRepository {
    fun getMovieList(movieTitle: String): Single<List<MovieResult.Item>>
    fun addMovieList(movieList: List<MovieResult.Item>): Completable
    fun deleteMovieList(movieItem: MovieResult.Item): Completable
}
