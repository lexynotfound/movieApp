package com.coorvo.movieapp.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coorvo.movieapp.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GenreUiState>(GenreUiState.Loading)
    val uiState: StateFlow<GenreUiState> = _uiState.asStateFlow()

    init {
        loadGenres()
    }

    fun loadGenres() {
        viewModelScope.launch {
            _uiState.value = GenreUiState.Loading
            getGenresUseCase()
                .onSuccess { genres ->
                    _uiState.value = if (genres.isEmpty()) GenreUiState.Empty
                                     else GenreUiState.Success(genres)
                }
                .onFailure { throwable ->
                    _uiState.value = GenreUiState.Error(throwable.message ?: "Unknown error")
                }
        }
    }
}
