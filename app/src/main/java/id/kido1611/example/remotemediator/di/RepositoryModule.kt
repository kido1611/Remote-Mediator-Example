package id.kido1611.example.remotemediator.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.kido1611.example.remotemediator.data.MovieRepository
import id.kido1611.example.remotemediator.data.local.TmdbDatabase
import id.kido1611.example.remotemediator.data.remote.service.TmdbService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        service: TmdbService,
        database: TmdbDatabase
    ) : MovieRepository {
        return MovieRepository(service, database)
    }
}