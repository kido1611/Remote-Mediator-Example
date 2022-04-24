package id.kido1611.example.remotemediator.data.remote.service

import id.kido1611.example.remotemediator.data.remote.model.ApiResult
import id.kido1611.example.remotemediator.data.remote.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ) : ApiResult<Movie>

    @GET("movie/popular")
    suspend fun popularMovie(
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ) : ApiResult<Movie>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: Int,
        @Query("page") page: Int,
        @Query("api_key") api_key: String,
    ) : ApiResult<Movie>
}