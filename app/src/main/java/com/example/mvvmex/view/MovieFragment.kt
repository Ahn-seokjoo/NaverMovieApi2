package com.example.mvvmex.view

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.mvvmex.R
import com.example.mvvmex.databinding.FragmentMovieBinding
import com.example.mvvmex.entity.MovieResult
import com.example.mvvmex.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {
    private lateinit var _binding: FragmentMovieBinding
    private val binding get() = _binding
    private val movieViewModel: MovieViewModel by activityViewModels()
    private val movieAdapter = MovieAdapter(::showMovieDetail, ::itemRemove)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("fail to bind")

        binding.apply {
            recyclerView.adapter = movieAdapter
            lifecycleOwner = viewLifecycleOwner
            viewModel = movieViewModel
        }

        movieViewModel.movieList.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }
    }

    private fun showMovieDetail(movie: MovieResult.Item) {
        parentFragmentManager.commit {
            arguments = Bundle().apply {
                putParcelable("movie", movie)
            }
            replace<DetailFragment>(R.id.fragment_container, args = arguments)
            setReorderingAllowed(true)
            addToBackStack("movieRecyclerView")
        }
    }

    private fun itemRemove(item: MovieResult.Item) {
        Log.d(TAG, "itemRemove: $item")
        AlertDialog.Builder(context)
            .setTitle("삭제하시겠습니까?")
            .setPositiveButton("예") { _, _ ->
                movieViewModel.deleteMovie(item)
            }
            .setNegativeButton("아니오") { _, _ ->

            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

}
