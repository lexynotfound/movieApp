package com.coorvo.movieapp.data.mapper

import com.coorvo.movieapp.core.common.Constants.TRAILER_TYPE
import com.coorvo.movieapp.core.common.Constants.YOUTUBE_SITE
import com.coorvo.movieapp.data.remote.dto.VideoResponseDto
import com.coorvo.movieapp.domain.model.Trailer

fun VideoResponseDto.toTrailer(): Trailer? =
    (results.firstOrNull { it.site == YOUTUBE_SITE && it.type == TRAILER_TYPE }
        ?: results.firstOrNull { it.site == YOUTUBE_SITE })
        ?.let { Trailer(key = it.key, name = it.name, site = it.site, type = it.type) }
