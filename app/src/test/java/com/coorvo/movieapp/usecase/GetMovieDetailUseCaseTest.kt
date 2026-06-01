package com.coorvo.movieapp.usecase

import com.coorvo.movieapp.domain.model.Genre
import com.coorvo.movieapp.domain.model.MovieDetail
import com.coorvo.movieapp.domain.repository.MovieRepository
import com.coorvo.movieapp.domain.usecase.GetMovieDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class GetMovieDetailUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: GetMovieDetailUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetMovieDetailUseCase(repository)
    }

    @Test
    fun `returns movie detail on success`() = runTest {
        val detail = MovieDetail(
            id = 1,
            title = "Inception",
            overview = "A mind-bending thriller",
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            releaseDate = "2010-07-16",
            voteAverage = 8.8,
            runtime = 148,
            genres = listOf(Genre(28, "Action"))
        )
        coEvery { repository.getMovieDetail(1) } returns Result.success(detail)

        val result = useCase(1)

        assertTrue(result.isSuccess)
        assertEquals(detail, result.getOrNull())
    }

    @Test
    fun `returns failure on http error`() = runTest {
        val httpException = mockk<HttpException>()
        coEvery { repository.getMovieDetail(999) } returns Result.failure(httpException)

        val result = useCase(999)

        assertTrue(result.isFailure)
    }

    @Test
    fun `trailer is null when not available`() = runTest {
        val detail = MovieDetail(
            id = 2,
            title = "Unknown",
            overview = "",
            posterPath = null,
            backdropPath = null,
            releaseDate = "",
            voteAverage = 0.0,
            runtime = null,
            genres = emptyList()
        )
        coEvery { repository.getMovieDetail(2) } returns Result.success(detail)

        val result = useCase(2)

        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull()?.posterPath)
    }
}
