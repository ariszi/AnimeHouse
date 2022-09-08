package zisis.aristofanis.animehouse.core.domain

abstract class UseCase<T, Params> {

    abstract suspend fun call(params: Params): T

    suspend operator fun invoke(params: Params): T = call(params)

}
