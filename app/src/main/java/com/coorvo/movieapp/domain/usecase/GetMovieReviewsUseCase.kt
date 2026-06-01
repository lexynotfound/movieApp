package com.coorvo.movieapp.domain.usecase

import androidx.paging.PagingData
import com.coorvo.movieapp.domain.model.Review
import com.coorvo.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<PagingData<Review>> =
        repository.getMovieReviews(movieId)
}
