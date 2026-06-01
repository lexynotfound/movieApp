package com.coorvo.movieapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.coorvo.movieapp.presentation.detail.MovieDetailScreen
import com.coorvo.movieapp.presentation.genre.GenreScreen
import com.coorvo.movieapp.presentation.movies.MoviesScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.GenreList.route
    ) {
        composable(route = Screen.GenreList.route) {
            GenreScreen(
                onGenreClick = { id, name ->
                    navController.navigate(Screen.MovieList.createRoute(id, name))
                }
            )
        }

        composable(
            route = Screen.MovieList.route,
            arguments = listOf(
                navArgument("genreId") { type = NavType.IntType },
                navArgument("genreName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val genreName = backStackEntry.arguments?.getString("genreName") ?: ""
            MoviesScreen(
                genreName = genreName,
                onMovieClick = { movieId ->
                    navController.navigate(Screen.MovieDetail.createRoute(movieId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType }
            )
        ) {
            MovieDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
