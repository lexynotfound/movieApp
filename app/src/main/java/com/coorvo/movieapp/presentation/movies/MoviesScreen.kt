package com.coorvo.movieapp.presentation.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.coorvo.movieapp.core.ui.component.EmptyView
import com.coorvo.movieapp.core.ui.component.ErrorView
import com.coorvo.movieapp.core.ui.component.LoadingView
import com.coorvo.movieapp.core.ui.component.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    genreName: String,
    onMovieClick: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(genreName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                movies.loadState.refresh is LoadState.Loading -> {
                    LoadingView()
                }
                movies.loadState.refresh is LoadState.Error -> {
                    val error = (movies.loadState.refresh as LoadState.Error).error
                    ErrorView(
                        message = error.message ?: "Failed to load movies",
                        onRetry = { movies.retry() }
                    )
                }
                movies.itemCount == 0 && movies.loadState.refresh is LoadState.NotLoading -> {
                    EmptyView(message = "No movies found for this genre.")
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(movies.itemCount) { index ->
                            val movie = movies[index]
                            if (movie != null) {
                                MovieCard(
                                    movie = movie,
                                    onClick = { onMovieClick(movie.id) }
                                )
                            }
                        }
                        if (movies.loadState.append is LoadState.Loading) {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                                }
                            }
                        }
                        if (movies.loadState.append is LoadState.Error) {
                            item {
                                val error = (movies.loadState.append as LoadState.Error).error
                                ErrorView(
                                    message = error.message ?: "Load more failed",
                                    onRetry = { movies.retry() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
