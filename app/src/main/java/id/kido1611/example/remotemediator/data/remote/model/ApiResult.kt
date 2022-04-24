package id.kido1611.example.remotemediator.data.remote.model

data class ApiResult<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
)
