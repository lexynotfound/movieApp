package com.coorvo.movieapp.presentation.detail

import com.coorvo.movieapp.domain.model.MovieDetail
import com.coorvo.movieapp.domain.model.Trailer

sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState
    data class Success(
        val movieDetail: MovieDetail,
        val trailer: Trailer?
    ) : MovieDetailUiState
    data class Error(val message: String) : MovieDetailUiState
}
