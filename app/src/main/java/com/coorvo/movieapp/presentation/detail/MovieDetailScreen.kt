package com.coorvo.movieapp.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.coorvo.movieapp.core.common.Constants.TMDB_IMAGE_BASE_URL
import com.coorvo.movieapp.core.common.Constants.YOUTUBE_BASE_URL
import com.coorvo.movieapp.core.ui.component.EmptyView
import com.coorvo.movieapp.core.ui.component.ErrorView
import com.coorvo.movieapp.core.ui.component.LoadingView
import com.coorvo.movieapp.core.ui.component.ReviewItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MovieDetailScreen(
    onBack: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val detailUiState by viewModel.detailUiState.collectAsStateWithLifecycle()
    val reviews = viewModel.reviews.collectAsLazyPagingItems()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Detail") },
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
            when (val state = detailUiState) {
                is MovieDetailUiState.Loading -> LoadingView()
                is MovieDetailUiState.Error -> ErrorView(
                    message = state.message,
                    onRetry = viewModel::loadDetail
                )
                is MovieDetailUiState.Success -> {
                    val movie = state.movieDetail
                    val trailer = state.trailer

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        item {
                            val imageUrl = when {
                                movie.backdropPath != null -> "$TMDB_IMAGE_BASE_URL${movie.backdropPath}"
                                movie.posterPath != null -> "$TMDB_IMAGE_BASE_URL${movie.posterPath}"
                                else -> null
                            }
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = movie.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16f / 9f)
                            )
                        }

                        item {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = movie.title,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Release: ${movie.releaseDate.take(10).ifEmpty { "N/A" }}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "★ ${"%.1f".format(movie.voteAverage)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                if (movie.runtime != null && movie.runtime > 0) {
                                    Text(
                                        text = "Runtime: ${movie.runtime} min",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                if (movie.genres.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        movie.genres.forEach { genre ->
                                            AssistChip(
                                                onClick = {},
                                                label = { Text(genre.name) }
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = movie.overview.ifEmpty { "No overview available." },
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                if (trailer != null) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = {
                                            val intent = Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse("$YOUTUBE_BASE_URL${trailer.key}")
                                            )
                                            context.startActivity(intent)
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text("Watch Trailer")
                                    }
                                }
                            }
                        }

                        item {
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                            Text(
                                text = "Reviews",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            )
                        }

                        when {
                            reviews.loadState.refresh is LoadState.Loading -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                            reviews.loadState.refresh is LoadState.Error -> {
                                item {
                                    val error = (reviews.loadState.refresh as LoadState.Error).error
                                    ErrorView(
                                        message = error.message ?: "Failed to load reviews",
                                        onRetry = { reviews.retry() }
                                    )
                                }
                            }
                            reviews.itemCount == 0 && reviews.loadState.refresh is LoadState.NotLoading -> {
                                item {
                                    EmptyView(
                                        message = "No reviews yet.",
                                        modifier = Modifier.height(100.dp)
                                    )
                                }
                            }
                            else -> {
                                items(reviews.itemCount) { index ->
                                    val review = reviews[index]
                                    if (review != null) {
                                        ReviewItem(
                                            review = review,
                                            modifier = Modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 4.dp
                                            )
                                        )
                                    }
                                }
                                if (reviews.loadState.append is LoadState.Loading) {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                                if (reviews.loadState.append is LoadState.Error) {
                                    item {
                                        val error =
                                            (reviews.loadState.append as LoadState.Error).error
                                        ErrorView(
                                            message = error.message ?: "Load more failed",
                                            onRetry = { reviews.retry() },
                                            modifier = Modifier.height(150.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
