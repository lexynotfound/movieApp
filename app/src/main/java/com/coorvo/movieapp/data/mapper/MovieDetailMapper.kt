package com.coorvo.movieapp.data.mapper

import com.coorvo.movieapp.data.remote.dto.MovieDetailDto
import com.coorvo.movieapp.domain.model.MovieDetail

fun MovieDetailDto.toDomain(): MovieDetail = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    runtime = runtime,
    genres = genres.map { it.toDomain() }
)
