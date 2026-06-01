package com.coorvo.movieapp.usecase

import com.coorvo.movieapp.domain.model.Genre
import com.coorvo.movieapp.domain.repository.MovieRepository
import com.coorvo.movieapp.domain.usecase.GetGenresUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetGenresUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: GetGenresUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetGenresUseCase(repository)
    }

    @Test
    fun `returns genre list on success`() = runTest {
        val genres = listOf(Genre(1, "Action"), Genre(2, "Drama"))
        coEvery { repository.getGenres() } returns Result.success(genres)

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(genres, result.getOrNull())
    }

    @Test
    fun `returns failure on network error`() = runTest {
        coEvery { repository.getGenres() } returns Result.failure(IOException("No internet"))

        val result = useCase()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IOException)
    }

    @Test
    fun `returns empty list when no genres`() = runTest {
        coEvery { repository.getGenres() } returns Result.success(emptyList())

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(emptyList<Genre>(), result.getOrNull())
    }
}
