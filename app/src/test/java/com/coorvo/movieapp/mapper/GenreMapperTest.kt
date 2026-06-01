package com.coorvo.movieapp.mapper

import com.coorvo.movieapp.data.mapper.toDomain
import com.coorvo.movieapp.data.remote.dto.GenreDto
import com.coorvo.movieapp.data.remote.dto.GenreResponseDto
import com.coorvo.movieapp.domain.model.Genre
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GenreMapperTest {

    @Test
    fun `GenreDto toDomain maps id and name correctly`() {
        val dto = GenreDto(id = 28, name = "Action")
        val domain = dto.toDomain()
        assertEquals(Genre(28, "Action"), domain)
    }

    @Test
    fun `GenreResponseDto toDomain maps all genres`() {
        val dto = GenreResponseDto(
            genres = listOf(
                GenreDto(28, "Action"),
                GenreDto(35, "Comedy"),
                GenreDto(18, "Drama")
            )
        )
        val domains = dto.toDomain()
        assertEquals(3, domains.size)
        assertEquals("Action", domains[0].name)
        assertEquals("Comedy", domains[1].name)
        assertEquals("Drama", domains[2].name)
    }

    @Test
    fun `GenreResponseDto toDomain returns empty list for empty genres`() {
        val dto = GenreResponseDto(genres = emptyList())
        val domains = dto.toDomain()
        assertTrue(domains.isEmpty())
    }

    @Test
    fun `GenreDto toDomain preserves genre id`() {
        val dto = GenreDto(id = 99, name = "Sci-Fi")
        val domain = dto.toDomain()
        assertEquals(99, domain.id)
    }
}
