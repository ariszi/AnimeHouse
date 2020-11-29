package zisis.aristofanis.animehouse.domain.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class UseCase<T, Params> {

    private val useCaseScope: CoroutineScope = useCaseScope()

    abstract suspend fun call(params: Params): T

    suspend operator fun invoke(params: Params): T {
        return call(params)
    }

    internal class UseCaseScope(context: CoroutineContext) : CoroutineScope {
        override val coroutineContext: CoroutineContext = context
    }

    private fun useCaseScope() = UseCaseScope(SupervisorJob() + Dispatchers.Main)

    fun unregister() {
        useCaseScope.cancel()
    }
}
