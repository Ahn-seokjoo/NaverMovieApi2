package com.example.mvvmex.data

import com.example.mvvmex.data.local.MovieLocalRepository
import com.example.mvvmex.data.remote.MovieRemoteRepository
import com.example.mvvmex.entity.MovieResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val remote: MovieRemoteRepository, private val local: MovieLocalRepository) : MovieRepository {
    override fun getMovieList(movieTitle: String): Single<List<MovieResult.Item>> {
        return local.getMovieList(movieTitle)
    }

    override fun getRemoteMovieList(movieTitle: String): Single<MovieResult> {
        return remote.getMovieList(movieTitle)
    }

    override fun addMovieList(movieList: List<MovieResult.Item>): Completable {
        return local.addMovieList(movieList)
    }

    override fun deleteMovieList(movieItem: MovieResult.Item): Completable {
        return local.deleteMovieList(movieItem)
    }

}
