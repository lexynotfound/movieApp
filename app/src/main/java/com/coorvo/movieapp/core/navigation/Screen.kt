package com.coorvo.movieapp.core.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object GenreList : Screen("genre_list")

    object MovieList : Screen("movie_list/{genreId}/{genreName}") {
        fun createRoute(genreId: Int, genreName: String): String =
            "movie_list/$genreId/${Uri.encode(genreName)}"
    }

    object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int): String = "movie_detail/$movieId"
    }
}
