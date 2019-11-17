package zisis.aristofanis.animehouse.domain

class AnimeListUseCase(private val animeListDataSource: AnimeListDataSource) {

   suspend fun getAnimeListWithInfo(): QueryData {
        return animeListDataSource.getAnimeList()
    }

}
