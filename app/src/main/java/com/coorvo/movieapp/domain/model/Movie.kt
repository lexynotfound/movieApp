package com.coorvo.movieapp.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double
)
