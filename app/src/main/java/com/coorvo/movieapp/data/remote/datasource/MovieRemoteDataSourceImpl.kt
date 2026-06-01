package com.coorvo.movieapp.data.remote.datasource

import com.coorvo.movieapp.data.remote.api.TmdbApiService
import com.coorvo.movieapp.data.remote.dto.GenreResponseDto
import com.coorvo.movieapp.data.remote.dto.MovieDetailDto
import com.coorvo.movieapp.data.remote.dto.MovieResponseDto
import com.coorvo.movieapp.data.remote.dto.ReviewResponseDto
import com.coorvo.movieapp.data.remote.dto.VideoResponseDto
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MovieRemoteDataSource {

    override suspend fun getGenres(): GenreResponseDto = apiService.getGenres()

    override suspend fun discoverMovies(genreId: Int, page: Int): MovieResponseDto =
        apiService.discoverMovies(genreId, page)

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto =
        apiService.getMovieDetail(movieId)

    override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewResponseDto =
        apiService.getMovieReviews(movieId, page)

    override suspend fun getMovieVideos(movieId: Int): VideoResponseDto =
        apiService.getMovieVideos(movieId)
}
