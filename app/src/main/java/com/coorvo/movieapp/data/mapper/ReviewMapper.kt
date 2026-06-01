package com.coorvo.movieapp.data.mapper

import com.coorvo.movieapp.data.remote.dto.ReviewDto
import com.coorvo.movieapp.domain.model.Review

fun ReviewDto.toDomain(): Review = Review(
    id = id,
    author = author,
    content = content,
    createdAt = createdAt
)
