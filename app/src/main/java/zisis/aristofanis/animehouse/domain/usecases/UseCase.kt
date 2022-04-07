package zisis.aristofanis.animehouse.domain.usecases

abstract class UseCase<T, Params> {

    abstract suspend fun call(params: Params): T

    suspend operator fun invoke(params: Params): T = call(params)

}
