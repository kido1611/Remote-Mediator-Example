package id.kido1611.example.remotemediator.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.kido1611.example.remotemediator.BuildConfig
import id.kido1611.example.remotemediator.data.local.TmdbDatabase
import id.kido1611.example.remotemediator.data.local.model.MovieEntity
import id.kido1611.example.remotemediator.data.local.model.RemoteKey
import id.kido1611.example.remotemediator.data.remote.mapper.toMovieEntity
import id.kido1611.example.remotemediator.data.remote.service.TmdbService
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class DiscoverMovieMediator(
    private val service: TmdbService,
    private val database: TmdbDatabase
) : RemoteMediator<Int, MovieEntity>() {

    private val remoteKeyDao = database.remoteKeyDao()
    private val movieDao = database.movieDao()

    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            remoteKeyDao.getKeyByMovie("discover_movie")
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if((System.currentTimeMillis() - remoteKey.last_updated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieEntity>): MediatorResult {
        return try {
            val page = when(loadType) {
                LoadType.REFRESH -> {
                    1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getKeyByMovie("discover_movie")
                    } ?: return MediatorResult.Success(true)

                    if(remoteKey.next_page == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.next_page
                }
            }

            val result = service.discoverMovie(
                page = page,
                api_key = BuildConfig.API_KEY
            )

            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    movieDao.clearMovies()
                }

                val nextPage = if(result.results.isEmpty()) {
                    null
                } else {
                    page+1
                }

                val movieEntities = result.results.map {
                    it.toMovieEntity()
                }

                remoteKeyDao.insertKey(RemoteKey(
                    id = "discover_movie",
                    next_page = nextPage,
                    last_updated = System.currentTimeMillis()
                ))
                movieDao.insertMovies(movieEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = result.results.isEmpty()
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}