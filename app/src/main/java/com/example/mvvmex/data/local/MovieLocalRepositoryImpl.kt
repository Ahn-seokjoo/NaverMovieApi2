package com.example.mvvmex.data.local

import com.example.mvvmex.data.local.room.MovieDao
import com.example.mvvmex.entity.MovieResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieLocalRepositoryImpl @Inject constructor(private val movieDao: MovieDao) : MovieLocalRepository {
    override fun getMovieList(movieTitle: String): Single<List<MovieResult.Item>> {
        return movieDao.getMovieList(movieTitle)
    }

    override fun addMovieList(movieList: List<MovieResult.Item>): Completable {
        return movieDao.addMovieList(movieList)
    }

    override fun deleteMovieList(movieItem: MovieResult.Item): Completable {
        return movieDao.deleteMovieList(movieItem)
    }
}
