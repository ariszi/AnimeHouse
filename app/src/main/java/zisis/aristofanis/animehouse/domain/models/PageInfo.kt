package zisis.aristofanis.animehouse.domain.models

data class PageInfo(
    val currentPage: Int = 0,
    val hasNextPage: Boolean = false,
    val lastPage: Int = 0,
    val perPage: Int = 0,
    val total: Int = 0
)
