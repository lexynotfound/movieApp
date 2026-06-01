package com.coorvo.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoResponseDto(
    @SerializedName("results") val results: List<VideoDto>
)
