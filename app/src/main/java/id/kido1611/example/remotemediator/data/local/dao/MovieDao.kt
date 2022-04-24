package id.kido1611.example.remotemediator.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.kido1611.example.remotemediator.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("delete from movies")
    suspend fun clearMovies()

    @Query("select * from movies order by id asc")
    fun getPagingMovie() : PagingSource<Int, MovieEntity>

    @Query("select * from movies order by id asc limit :limit")
    fun getFlowMovies(limit: Int): Flow<List<MovieEntity>>
}