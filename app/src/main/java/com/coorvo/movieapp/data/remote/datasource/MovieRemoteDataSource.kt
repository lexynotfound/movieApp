package com.coorvo.movieapp.data.remote.datasource

import com.coorvo.movieapp.data.remote.dto.GenreResponseDto
import com.coorvo.movieapp.data.remote.dto.MovieDetailDto
import com.coorvo.movieapp.data.remote.dto.MovieResponseDto
import com.coorvo.movieapp.data.remote.dto.ReviewResponseDto
import com.coorvo.movieapp.data.remote.dto.VideoResponseDto

interface MovieRemoteDataSource {
    suspend fun getGenres(): GenreResponseDto
    suspend fun discoverMovies(genreId: Int, page: Int): MovieResponseDto
    suspend fun getMovieDetail(movieId: Int): MovieDetailDto
    suspend fun getMovieReviews(movieId: Int, page: Int): ReviewResponseDto
    suspend fun getMovieVideos(movieId: Int): VideoResponseDto
}
