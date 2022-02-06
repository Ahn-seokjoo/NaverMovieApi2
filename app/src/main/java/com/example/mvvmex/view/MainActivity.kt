package com.example.mvvmex.view

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.mvvmex.R
import com.example.mvvmex.databinding.ActivityMainBinding
import com.example.mvvmex.viewmodel.MovieViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private val behaviorSubject = BehaviorSubject.createDefault(0L)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setMovieFinder()
        setBackPressed()
    }

    private fun setMovieFinder() {
        binding.movieFinder.textChanges()
            .debounce(1000L, TimeUnit.MILLISECONDS)
            .subscribe { movieSearchText ->
                if (supportFragmentManager.findFragmentById(R.id.fragment_container) is DetailFragment) {
                    supportFragmentManager.commit {
                        replace<MovieFragment>(R.id.fragment_container)
                        addToBackStack("")
                        setReorderingAllowed(true)
                    }
                }
                if (movieSearchText.isNotEmpty()) {
                    movieViewModel.getMovieList(movieSearchText.toString())
                    hideKeyboard()
                }
            }.addTo(compositeDisposable)
    }

    private fun setBackPressed() {
        behaviorSubject.buffer(2, 1)
            .map {
                it[0] to it[1]
            }.subscribe {
                if (it.second - it.first > 2000L) {
                    Toast.makeText(this, "한번 더 누르면 종료", Toast.LENGTH_SHORT).show()
                } else {
                    super.onBackPressed()
                }
            }.addTo(compositeDisposable)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            behaviorSubject.onNext(System.currentTimeMillis())
        } else {
            super.onBackPressed()
        }
    }

    private fun hideKeyboard() {
        val mInputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mInputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}
