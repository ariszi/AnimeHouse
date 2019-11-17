package zisis.aristofanis.animehouse.domain

interface AnimeListDataSource {

    suspend fun getAnimeList():QueryData

}
