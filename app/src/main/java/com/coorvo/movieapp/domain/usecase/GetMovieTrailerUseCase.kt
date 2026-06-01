package com.coorvo.movieapp.domain.usecase

import com.coorvo.movieapp.domain.model.Trailer
import com.coorvo.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieTrailerUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<Trailer?> =
        repository.getMovieTrailer(movieId)
}
