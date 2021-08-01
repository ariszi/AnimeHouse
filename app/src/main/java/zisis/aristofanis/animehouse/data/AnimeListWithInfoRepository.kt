package zisis.aristofanis.animehouse.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.data.api.network.result
import zisis.aristofanis.animehouse.data.mappers.AnimeListWithInfoMappers
import zisis.aristofanis.animehouse.domain.datasources.AnimeListRepositoryContract
import zisis.aristofanis.animehouse.domain.models.QueryData
import zisis.aristofanis.animehouse.domain.usecases.AnimeListUseCase
import zisis.aristofanis.animehouse.presentation.state_contracts.AnimeList
import javax.inject.Inject

class AnimeListWithInfoRepository @Inject constructor(private val api: ApolloClient) :
    AnimeListRepositoryContract {

    override suspend fun getAnimeList(params: AnimeListUseCase.AnimeFilter): AnimeList {
        val result = result {
            AnimeListWithInfoMappers().transform(
                api.query(filterToQuery(params)).toDeferred().await().data
            )
        }

        return when (result) {
            is QueryData.Success -> AnimeList.AnimeListItems(result.data)
            is QueryData.Error -> AnimeList.ListError(result.errorMessage.message)
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
