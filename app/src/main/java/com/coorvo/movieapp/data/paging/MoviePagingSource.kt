package com.coorvo.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.coorvo.movieapp.core.common.Constants.INITIAL_PAGE
import com.coorvo.movieapp.data.mapper.toDomain
import com.coorvo.movieapp.data.remote.datasource.MovieRemoteDataSource
import com.coorvo.movieapp.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val dataSource: MovieRemoteDataSource,
    private val genreId: Int
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: INITIAL_PAGE
        return try {
            val response = dataSource.discoverMovies(genreId, page)
            LoadResult.Page(
                data = response.results.map { it.toDomain() },
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
