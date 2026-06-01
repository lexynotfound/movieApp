package com.coorvo.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReviewResponseDto(
    @SerializedName("results") val results: List<ReviewDto>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int
)
