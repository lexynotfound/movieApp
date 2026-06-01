package com.coorvo.movieapp.data.mapper

import com.coorvo.movieapp.data.remote.dto.MovieDto
import com.coorvo.movieapp.domain.model.Movie

fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)
