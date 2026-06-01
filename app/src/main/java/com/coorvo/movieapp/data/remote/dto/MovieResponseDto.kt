package com.coorvo.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieResponseDto(
    @SerializedName("results") val results: List<MovieDto>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int
)
