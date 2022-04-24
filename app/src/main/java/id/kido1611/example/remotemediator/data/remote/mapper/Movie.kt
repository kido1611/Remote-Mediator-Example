package id.kido1611.example.remotemediator.data.remote.mapper

import id.kido1611.example.remotemediator.data.local.model.MovieEntity
import id.kido1611.example.remotemediator.data.remote.model.Movie

fun Movie.toMovieEntity() : MovieEntity {
    return MovieEntity(
        adult = this.adult,
        backdrop_path = this.backdrop_path,
        movie_id = this.id,
        original_language = this.original_language,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        video = this.video,
        vote_average = this.vote_average,
        vote_count = this.vote_count
    )
}