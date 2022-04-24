package id.kido1611.example.remotemediator.presenter.discover.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.kido1611.example.remotemediator.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class DiscoverMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movies = movieRepository.discoverMovie()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}