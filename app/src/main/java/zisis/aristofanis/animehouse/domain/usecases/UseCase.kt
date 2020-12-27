package zisis.aristofanis.animehouse.domain.usecases

import kotlinx.coroutines.flow.Flow

abstract class UseCase<T, Params> {

    abstract suspend fun call(params: Params): Flow<T>

    suspend operator fun invoke(params: Params): Flow<T> = call(params)

}
