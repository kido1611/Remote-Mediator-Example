package id.kido1611.example.remotemediator.presenter.popular.movie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import id.kido1611.example.remotemediator.data.remote.Status

@Composable
fun PopularMovieScreen(
    viewModel: PopularMovieViewModel = hiltViewModel()
) {
    val movieState = viewModel.movies.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.refresh(true) },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
                state = listState
            ) {
                movieState.value.data?.let { movies ->
                    itemsIndexed(
                        items = movies,
                        key = { _, movie ->
                            movie.movie_id
                        }
                    ) { index, movie ->
                        Text(
                            text = "$index. ${movie.title}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 20.dp,
                                    vertical = 10.dp
                                )
                        )
                    }
                }
                when(movieState.value.status) {
                    Status.ERROR -> {
                        item {
                            Text(
                                text = "Error, ${movieState.value.message}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 10.dp
                                    )
                            )
                        }
                    }
                    Status.LOADING -> {
                        item {
                            Text(
                                text = "Loading",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 10.dp
                                    )
                            )
                        }
                    }
                    else -> {
                        // Do Nothing
                    }
                }
            }
        }
    }
}