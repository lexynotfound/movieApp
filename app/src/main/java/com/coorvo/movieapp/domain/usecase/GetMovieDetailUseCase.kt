package com.coorvo.movieapp.domain.usecase

import com.coorvo.movieapp.domain.model.MovieDetail
import com.coorvo.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<MovieDetail> =
        repository.getMovieDetail(movieId)
}
