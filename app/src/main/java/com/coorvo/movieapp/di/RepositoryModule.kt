package com.coorvo.movieapp.di

import com.coorvo.movieapp.data.remote.datasource.MovieRemoteDataSource
import com.coorvo.movieapp.data.remote.datasource.MovieRemoteDataSourceImpl
import com.coorvo.movieapp.data.repository.MovieRepositoryImpl
import com.coorvo.movieapp.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(impl: MovieRemoteDataSourceImpl): MovieRemoteDataSource
}
