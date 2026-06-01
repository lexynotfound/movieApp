package com.coorvo.movieapp.domain.repository

import androidx.paging.PagingData
import com.coorvo.movieapp.domain.model.Genre
import com.coorvo.movieapp.domain.model.Movie
import com.coorvo.movieapp.domain.model.MovieDetail
import com.coorvo.movieapp.domain.model.Review
import com.coorvo.movieapp.domain.model.Trailer
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getGenres(): Result<List<Genre>>
    fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>>
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>
    fun getMovieReviews(movieId: Int): Flow<PagingData<Review>>
    suspend fun getMovieTrailer(movieId: Int): Result<Trailer?>
}
