package com.coorvo.movieapp.domain.usecase

import com.coorvo.movieapp.domain.model.Genre
import com.coorvo.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Result<List<Genre>> = repository.getGenres()
}
