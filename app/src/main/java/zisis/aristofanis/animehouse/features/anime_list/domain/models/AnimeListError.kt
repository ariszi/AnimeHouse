package zisis.aristofanis.animehouse.features.anime_list.domain.models

import zisis.aristofanis.animehouse.core.domain.Result

data class AnimeListError(val message: String, val errorKey: String) : Result.Error()
