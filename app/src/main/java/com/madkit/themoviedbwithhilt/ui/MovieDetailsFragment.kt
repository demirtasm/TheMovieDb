package com.madkit.themoviedbwithhilt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.madkit.themoviedbwithhilt.R
import com.madkit.themoviedbwithhilt.databinding.FragmentMovieDetailsBinding
import com.madkit.themoviedbwithhilt.repository.ApiRepository
import com.madkit.themoviedbwithhilt.response.MovieDetailsResponse
import com.madkit.themoviedbwithhilt.utils.Constants.POSTER_BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding : FragmentMovieDetailsBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.movieId
        binding.apply {
            prgBarMovies.visibility = View.VISIBLE
            apiRepository.getMovieDetail(id).enqueue(object : Callback<MovieDetailsResponse>{
                override fun onResponse(
                    call: Call<MovieDetailsResponse?>,
                    response: Response<MovieDetailsResponse?>
                ) {
                    prgBarMovies.visibility = View.GONE
                    when(response.code()){
                        200->{
                            response.body().let {
                                val moviePoster = POSTER_BASE_URL + it!!.posterPath
                                tvMovieBudget.text = it.budget.toString()
                                tvMovieOverview.text = it.overview
                                tvMovieDateRelease.text = it.releaseDate
                                tvMovieRating.text = it.voteAverage.toString()
                                tvMovieRevenue.text = it.revenue.toString()
                                tvMovieRuntime.text = it.runtime.toString()
                                tvMovieTagLine.text = it.tagline
                                tvMovieTitle.text = it.originalTitle
                                imgMovie.load(moviePoster){
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                                imgMovieBack.load(moviePoster){
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                            }
                        }
                    }
                }

                override fun onFailure(
                    call: Call<MovieDetailsResponse?>,
                    t: Throwable
                ) {
                    prgBarMovies.visibility = View.GONE
                    Toast.makeText(requireContext(), "onFailure", Toast.LENGTH_SHORT).show()

                }

            })
        }
    }
}