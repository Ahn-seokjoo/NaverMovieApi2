package com.example.mvvmex.viewmodel

import android.content.ContentValues.TAG
import android.text.Html
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmex.data.MovieRepositoryImpl
import com.example.mvvmex.entity.MovieResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepositoryImpl) : ViewModel() {
    private var searchString: String = ""

    private val _progressBarVisible = MutableLiveData(false)
    val progressBarVisible: LiveData<Boolean> = _progressBarVisible

    private val _movieList = MutableLiveData<List<MovieResult.Item>>()
    val movieList: LiveData<List<MovieResult.Item>> = _movieList

    fun getMovieList(movieTitle: String) {
        searchString = movieTitle

        repository.getMovieList(movieTitle)
            .doOnSubscribe { _progressBarVisible.postValue(true) }
            .doOnSuccess { _progressBarVisible.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "로컬 리스트 가져오기: $it")
                if (it.isNullOrEmpty()) {
                    Log.d(TAG, "getMovieList: 없어서 가져올게요!")
                    doRemote(movieTitle)
                }
                _movieList.value = it
            }) {
                Log.d(TAG, "getMovieList error : ${it.message}")
            }
    }

    private fun doRemote(movieTitle: String) {
        repository.getRemoteMovieList(movieTitle)
            .map { movieResult ->
                movieResult.items.forEach { movie ->
                    // 제목에 HTML 태그 제거
                    movie.title = Html.fromHtml(movie.title).toString().replace(" ", "")
                }
                return@map movieResult.items
            }
            .doOnSuccess {
                Log.d(TAG, "doRemote에서 추가하는 리스트들: $it")
                repository.addMovieList(it)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _movieList.value = it
            }) {
                Log.d(TAG, "doRemote error: ${it.message}")
            }
    }

    fun deleteMovie(movie: MovieResult.Item) {
        repository.deleteMovieList(movie)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _progressBarVisible.postValue(true) }
            .doOnComplete { _progressBarVisible.postValue(false) }
            .doFinally {
                getMovieList(searchString)
            }
            .subscribe({
                Log.d(TAG, "deleteMovie: ${movie.title} 삭제 성공")
            }) {
                Log.d(TAG, "deleteMovie: ${it.message}")
            }
    }

}
