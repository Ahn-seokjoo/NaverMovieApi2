package com.example.mvvmex.data.local.room

import androidx.room.*
import com.example.mvvmex.entity.MovieResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDao {
    // 영화 제목이 들어간 타이틀 가져오기 LIKE를 통해 넘어온 movieTitle이 내부에 있는지 확인
    @Query("SELECT * FROM movie WHERE title LIKE '%' || :movieTitle || '%'")
    fun getMovieList(movieTitle: String): Single<List<MovieResult.Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieList(movieList: List<MovieResult.Item>): Completable

    @Delete
    fun deleteMovieList(movieItem: MovieResult.Item): Completable
}
