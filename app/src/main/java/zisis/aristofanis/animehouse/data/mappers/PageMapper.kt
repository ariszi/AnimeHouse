package zisis.aristofanis.animehouse.data.mappers

import zisis.aristofanis.animehouse.domain.models.PageInfo

class PageMapper {
    fun transform(response: AnimeListQuery.PageInfo?): PageInfo {
        response?.let {
           return PageInfo(
               currentPage = it.currentPage() ?: 0,
               hasNextPage = it.hasNextPage() ?: false,
               lastPage = it.lastPage() ?: 0,
               perPage = it.perPage() ?: 0,
               total = it.total() ?: 0
           )
        } ?: return PageInfo()
    }
}
