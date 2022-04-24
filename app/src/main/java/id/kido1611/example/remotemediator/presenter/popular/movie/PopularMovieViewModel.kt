package id.kido1611.example.remotemediator.presenter.popular.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kido1611.example.remotemediator.data.MovieRepository
import id.kido1611.example.remotemediator.data.local.model.MovieEntity
import id.kido1611.example.remotemediator.data.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<Resource<List<MovieEntity>>>(Resource.loading(null))
    val movies: StateFlow<Resource<List<MovieEntity>>> = _movies

    init {
        refresh(false)
    }

    fun refresh(force: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.popularMovie(
                page = 3,
                force = force
            )
                .collectLatest {
                    _movies.value = it
                }
        }
    }
}