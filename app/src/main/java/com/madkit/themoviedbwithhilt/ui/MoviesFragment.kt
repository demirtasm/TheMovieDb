package com.madkit.themoviedbwithhilt.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.madkit.themoviedbwithhilt.R
import com.madkit.themoviedbwithhilt.adapter.MoviesAdapter
import com.madkit.themoviedbwithhilt.databinding.FragmentMoviesBinding
import com.madkit.themoviedbwithhilt.repository.ApiRepository
import com.madkit.themoviedbwithhilt.response.MoviesListResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            prgBarMovies.visibility = View.VISIBLE
            apiRepository.getPopularMoviesList(1).enqueue(object : Callback<MoviesListResponse> {
                override fun onResponse(
                    call: Call<MoviesListResponse>,
                    response: Response<MoviesListResponse>
                ) {
                    prgBarMovies.visibility = View.GONE

                    when (response.code()) {
                        200 -> {
                            response.body().let { itBody ->
                                if (itBody?.results!!.isNotEmpty()) {
                                    moviesAdapter.differ.submitList(itBody.results)
                                }
                                rlMovies.apply {
                                    layoutManager = LinearLayoutManager(requireContext())
                                    adapter = moviesAdapter
                                }
                            }
                        }

                        400 -> {
                            Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
                        }

                        401 -> {
                            Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()

                        }
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Toast.makeText(requireContext(), "onFailure", Toast.LENGTH_SHORT).show()


                }

            })
        }

    }


}