package com.coorvo.movieapp.presentation.genre

import com.coorvo.movieapp.domain.model.Genre

sealed interface GenreUiState {
    data object Loading : GenreUiState
    data class Success(val genres: List<Genre>) : GenreUiState
    data object Empty : GenreUiState
    data class Error(val message: String) : GenreUiState
}
