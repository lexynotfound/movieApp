package com.coorvo.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("type") val type: String
)
