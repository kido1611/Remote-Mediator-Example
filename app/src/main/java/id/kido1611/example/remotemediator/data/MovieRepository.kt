package id.kido1611.example.remotemediator.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.kido1611.example.remotemediator.data.local.TmdbDatabase
import id.kido1611.example.remotemediator.data.local.model.MovieEntity
import id.kido1611.example.remotemediator.data.remote.mediator.DiscoverMovieMediator
import id.kido1611.example.remotemediator.data.remote.paging.DiscoverMoviePagingSource
import id.kido1611.example.remotemediator.data.remote.model.Movie
import id.kido1611.example.remotemediator.data.remote.service.TmdbService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: TmdbService,
    private val database: TmdbDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun discoverMovie() : Flow<PagingData<MovieEntity>> {
        val dbSource = {
            database.movieDao().getPagingMovie()
        }
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = DiscoverMovieMediator(
                service, database
            ),
            pagingSourceFactory = dbSource
        )
            .flow
    }
}