package com.coorvo.movieapp.data.remote.api

import com.coorvo.movieapp.data.remote.dto.GenreResponseDto
import com.coorvo.movieapp.data.remote.dto.MovieDetailDto
import com.coorvo.movieapp.data.remote.dto.MovieResponseDto
import com.coorvo.movieapp.data.remote.dto.ReviewResponseDto
import com.coorvo.movieapp.data.remote.dto.VideoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponseDto

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): MovieResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): MovieDetailDto

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): ReviewResponseDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") movieId: Int): VideoResponseDto
}
