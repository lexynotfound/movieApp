package com.coorvo.movieapp.presentation.movies

sealed interface MoviesUiState {
    data object Loading : MoviesUiState
    data object Empty : MoviesUiState
    data class Error(val message: String) : MoviesUiState
}
