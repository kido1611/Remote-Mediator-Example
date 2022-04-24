package id.kido1611.example.remotemediator.data

import androidx.paging.*
import androidx.room.withTransaction
import id.kido1611.example.remotemediator.BuildConfig
import id.kido1611.example.remotemediator.data.local.TmdbDatabase
import id.kido1611.example.remotemediator.data.local.model.MovieEntity
import id.kido1611.example.remotemediator.data.local.model.RemoteKey
import id.kido1611.example.remotemediator.data.remote.Resource
import id.kido1611.example.remotemediator.data.remote.mapper.toMovieEntity
import id.kido1611.example.remotemediator.data.remote.mediator.DiscoverMovieMediator
import id.kido1611.example.remotemediator.data.remote.networkBoundResource
import id.kido1611.example.remotemediator.data.remote.service.TmdbService
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
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

    fun popularMovie(page: Int, force: Boolean = true) : Flow<Resource<List<MovieEntity>>> {
        return networkBoundResource(
            query = {
                database.movieDao().getFlowMovies(50)
            },
            fetch = {
                service.popularMovie(page, BuildConfig.API_KEY)
            },
            saveFetchResult = { items ->
                val resultEntity = items.results.map {
                    it.toMovieEntity()
                }
                database.withTransaction {
                    database.movieDao().clearMovies()

                    database.remoteKeyDao().insertKey(
                        RemoteKey(
                            id = "popular_movie",
                            next_page = page + 1,
                            last_updated = System.currentTimeMillis()
                        )
                    )
                    database.movieDao().insertMovies(resultEntity)
                }
            },
            shouldFetch = {
                if(force) {
                    true
                } else {
                    val remoteKey = database.withTransaction {
                        database.remoteKeyDao().getKeyByMovie("popular_movie")
                    }

                    if(remoteKey == null) {
                        true
                    }
                    else {
                        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

                        (System.currentTimeMillis() - remoteKey.last_updated) < cacheTimeout
                    }
                }
            }
        )
    }
}