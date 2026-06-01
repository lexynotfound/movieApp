package com.coorvo.movieapp.repository

import com.coorvo.movieapp.data.remote.datasource.MovieRemoteDataSource
import com.coorvo.movieapp.data.remote.dto.GenreDto
import com.coorvo.movieapp.data.remote.dto.GenreResponseDto
import com.coorvo.movieapp.data.repository.MovieRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class MovieRepositoryImplTest {

    private lateinit var dataSource: MovieRemoteDataSource
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setUp() {
        dataSource = mockk()
        repository = MovieRepositoryImpl(dataSource)
    }

    @Test
    fun `getGenres returns mapped domain list on success`() = runTest {
        val dto = GenreResponseDto(
            genres = listOf(GenreDto(1, "Action"), GenreDto(2, "Drama"))
        )
        coEvery { dataSource.getGenres() } returns dto

        val result = repository.getGenres()

        assertTrue(result.isSuccess)
        val genres = result.getOrNull()!!
        assertEquals(2, genres.size)
        assertEquals("Action", genres[0].name)
        assertEquals("Drama", genres[1].name)
    }

    @Test
    fun `getGenres returns failure on IOException`() = runTest {
        coEvery { dataSource.getGenres() } throws IOException("No internet")

        val result = repository.getGenres()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IOException)
    }

    @Test
    fun `getGenres returns empty list when API returns empty genres`() = runTest {
        coEvery { dataSource.getGenres() } returns GenreResponseDto(genres = emptyList())

        val result = repository.getGenres()

        assertTrue(result.isSuccess)
        assertEquals(emptyList<Any>(), result.getOrNull())
    }
}
