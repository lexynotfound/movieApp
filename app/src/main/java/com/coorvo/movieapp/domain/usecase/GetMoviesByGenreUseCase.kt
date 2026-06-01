package com.coorvo.movieapp.domain.usecase

import androidx.paging.PagingData
import com.coorvo.movieapp.domain.model.Movie
import com.coorvo.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(genreId: Int): Flow<PagingData<Movie>> =
        repository.getMoviesByGenre(genreId)
}
