package com.coorvo.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GenreResponseDto(
    @SerializedName("genres") val genres: List<GenreDto>
)
