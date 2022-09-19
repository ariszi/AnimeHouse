package zisis.aristofanis.animehouse.features.anime_list.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import zisis.aristofanis.animehouse.AnimeListQuery
import zisis.aristofanis.animehouse.core.domain.*
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
            .mapError { error ->
                when (error) {
                    is ApolloError -> AnimeListError(
                        message = error.message,
                        errorKey = "APOLLO_ERROR"
                    )
                    else -> AnimeListError(
                        message = GENERIC_ERROR_MESSAGE,
                        errorKey = "GENERIC_ERROR"
                    )
                }
            }
    }


    private fun filterToQuery(filter: AnimeListUseCase.AnimeFilter): AnimeListQuery {
        return AnimeListQuery(
            id = Optional.Present(filter.id),
            page = Optional.Present(filter.page),
            perPage = Optional.Present(filter.perPage),
            search = Optional.Present(filter.search),
            genre = Optional.Present(filter.genre),
            status = Optional.Present(filter.status),
            sort = Optional.Present(filter.sort),
        )
    }
}

