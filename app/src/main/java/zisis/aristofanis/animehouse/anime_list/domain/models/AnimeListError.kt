package zisis.aristofanis.animehouse.anime_list.domain.models

import zisis.aristofanis.animehouse.core.domain.Result

data class AnimeListError(val message: String, val errorKey: String) : Result.Error()
