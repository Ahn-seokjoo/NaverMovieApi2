package com.example.mvvmex.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mvvmex.R
import com.example.mvvmex.databinding.FragmentDetailBinding
import com.example.mvvmex.entity.MovieResult

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var _binding: FragmentDetailBinding
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("fail to bind")
        binding.lifecycleOwner = viewLifecycleOwner

        val movieData = arguments?.getParcelable<MovieResult.Item>("movie")
        setMovieData(movieData)
        moviePosterClickListener(movieData?.link)
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val mInputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mInputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    private fun setMovieData(movie: MovieResult.Item?) {
        binding.movie = movie
        binding.executePendingBindings()
    }

    private fun moviePosterClickListener(link: String?) {
        binding.moviePoster.setOnClickListener {
            showMovieDataInWeb(link)
        }
    }

    private fun showMovieDataInWeb(url: String?) {
        val webPage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}
