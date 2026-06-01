package com.coorvo.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.coorvo.movieapp.core.common.Constants.PAGING_PAGE_SIZE
import com.coorvo.movieapp.data.mapper.toDomain
import com.coorvo.movieapp.data.mapper.toTrailer
import com.coorvo.movieapp.data.paging.MoviePagingSource
import com.coorvo.movieapp.data.paging.ReviewPagingSource
import com.coorvo.movieapp.data.remote.datasource.MovieRemoteDataSource
import com.coorvo.movieapp.domain.model.Genre
import com.coorvo.movieapp.domain.model.Movie
import com.coorvo.movieapp.domain.model.MovieDetail
import com.coorvo.movieapp.domain.model.Review
import com.coorvo.movieapp.domain.model.Trailer
import com.coorvo.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getGenres(): Result<List<Genre>> = runCatching {
        remoteDataSource.getGenres().toDomain()
    }

    override fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGING_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(remoteDataSource, genreId) }
        ).flow

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> = runCatching {
        remoteDataSource.getMovieDetail(movieId).toDomain()
    }

    override fun getMovieReviews(movieId: Int): Flow<PagingData<Review>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGING_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ReviewPagingSource(remoteDataSource, movieId) }
        ).flow

    override suspend fun getMovieTrailer(movieId: Int): Result<Trailer?> = runCatching {
        remoteDataSource.getMovieVideos(movieId).toTrailer()
    }
}
