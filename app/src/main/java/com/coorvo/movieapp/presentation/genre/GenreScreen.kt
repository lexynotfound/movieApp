package com.coorvo.movieapp.presentation.genre

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coorvo.movieapp.core.ui.component.EmptyView
import com.coorvo.movieapp.core.ui.component.ErrorView
import com.coorvo.movieapp.core.ui.component.GenreItem
import com.coorvo.movieapp.core.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreScreen(
    onGenreClick: (Int, String) -> Unit,
    viewModel: GenreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Movie Genres") })
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is GenreUiState.Loading -> LoadingView(
                modifier = Modifier.padding(innerPadding)
            )
            is GenreUiState.Success -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.genres, key = { it.id }) { genre ->
                    GenreItem(
                        genre = genre,
                        onClick = { onGenreClick(genre.id, genre.name) }
                    )
                }
            }
            is GenreUiState.Empty -> EmptyView(
                message = "No genres available.",
                modifier = Modifier.padding(innerPadding)
            )
            is GenreUiState.Error -> ErrorView(
                message = state.message,
                onRetry = viewModel::loadGenres,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
