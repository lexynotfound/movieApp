package com.coorvo.movieapp.presentation.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.coorvo.movieapp.domain.model.Movie
import com.coorvo.movieapp.domain.usecase.GetMoviesByGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase
) : ViewModel() {

    private val genreId: Int = checkNotNull(savedStateHandle["genreId"])

    val movies: Flow<PagingData<Movie>> =
        getMoviesByGenreUseCase(genreId).cachedIn(viewModelScope)
}
