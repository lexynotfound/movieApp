package com.coorvo.movieapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.coorvo.movieapp.domain.model.Review
import com.coorvo.movieapp.domain.usecase.GetMovieDetailUseCase
import com.coorvo.movieapp.domain.usecase.GetMovieReviewsUseCase
import com.coorvo.movieapp.domain.usecase.GetMovieTrailerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _detailUiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val detailUiState: StateFlow<MovieDetailUiState> = _detailUiState.asStateFlow()

    val reviews: Flow<PagingData<Review>> =
        getMovieReviewsUseCase(movieId).cachedIn(viewModelScope)

    init {
        loadDetail()
    }

    fun loadDetail() {
        viewModelScope.launch {
            _detailUiState.value = MovieDetailUiState.Loading
            val detailDeferred = async { getMovieDetailUseCase(movieId) }
            val trailerDeferred = async { getMovieTrailerUseCase(movieId) }
            val detail = detailDeferred.await()
            val trailer = trailerDeferred.await()
            detail
                .onSuccess { movieDetail ->
                    _detailUiState.value = MovieDetailUiState.Success(
                        movieDetail = movieDetail,
                        trailer = trailer.getOrNull()
                    )
                }
                .onFailure { throwable ->
                    _detailUiState.value = MovieDetailUiState.Error(
                        throwable.message ?: "Unknown error"
                    )
                }
        }
    }
}
