package zisis.aristofanis.animehouse.features.anime_list.data.repository

import com.apollographql.apollo.ApolloClient
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.core.data.mapError
import zisis.aristofanis.animehouse.core.data.toResult
import zisis.aristofanis.animehouse.core.data.validateMap
import zisis.aristofanis.animehouse.core.domain.ApolloError
import zisis.aristofanis.animehouse.core.domain.GENERIC_ERROR_MESSAGE
import zisis.aristofanis.animehouse.core.domain.Result
import zisis.aristofanis.animehouse.features.anime_list.data.raw.toAnimeList
import zisis.aristofanis.animehouse.features.anime_list.domain.models.AnimeListError
import zisis.aristofanis.animehouse.features.anime_list.domain.models.AnimeListWithInfo
import zisis.aristofanis.animehouse.features.anime_list.domain.repository.AnimeListRepositoryContract
import zisis.aristofanis.animehouse.features.anime_list.domain.usecases.AnimeListUseCase
import javax.inject.Inject


class AnimeListWithInfoRepository @Inject constructor(private val api: ApolloClient) :
    AnimeListRepositoryContract {

    override suspend fun getAnimeList(params: AnimeListUseCase.AnimeFilter): Result<AnimeListWithInfo> {
        return api.query(filterToQuery(params)).toResult()
            .validateMap { it.toAnimeList() }
            .mapError {error ->
                when (error) {
                is ApolloError -> AnimeListError(message = error.message, errorKey = "APOLLO_ERROR")
                    else -> AnimeListError(message = GENERIC_ERROR_MESSAGE, errorKey = "GENERIC_ERROR")
                }
            }
    }


    private fun filterToQuery(filter: AnimeListUseCase.AnimeFilter): AnimeListQuery {
        val query = AnimeListQuery.builder()
        filter.id?.let { query.id(it) }
        filter.page?.let { query.page(it) }
        filter.perPage?.let { query.perPage(it) }
        filter.search?.let { query.search(it) }
        filter.genre?.let { query.genre(it) }
        filter.status?.let { query.status(it) }
        filter.sort?.let { query.sort(it) }
        return query.build()
    }
}

