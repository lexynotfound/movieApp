package com.coorvo.movieapp.data.mapper

import com.coorvo.movieapp.data.remote.dto.GenreDto
import com.coorvo.movieapp.data.remote.dto.GenreResponseDto
import com.coorvo.movieapp.domain.model.Genre

fun GenreDto.toDomain(): Genre = Genre(id = id, name = name)

fun GenreResponseDto.toDomain(): List<Genre> = genres.map { it.toDomain() }
