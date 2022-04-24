package id.kido1611.example.remotemediator.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies"
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val adult: Boolean?,
    val backdrop_path: String?,
    val movie_id: Int,
    val original_language: String,
    val overview: String?,
    val popularity: Float?,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val video: Boolean?,
    val vote_average: Float?,
    val vote_count: Int?
)
