package zisis.aristofanis.animehouse.domain.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import zisis.aristofanis.animehouse.domain.models.QueryData
import kotlin.coroutines.CoroutineContext

abstract class UseCase<T, Params> {

    private val useCaseScope: CoroutineScope = useCaseScope()

    abstract suspend fun call(params: Params): QueryData<T>

    operator fun invoke(params: Params, onResult: (QueryData<T>) -> Unit) {
        useCaseScope.launch { onResult(call(params)) }
    }

    operator fun invoke(coroutineScope: CoroutineScope, params: Params, onResult: (QueryData<T>) -> Unit) {
        coroutineScope.launch { onResult(call(params)) }
    }

    internal class UseCaseScope(context: CoroutineContext) : CoroutineScope {
        override val coroutineContext: CoroutineContext = context
    }

    private fun useCaseScope() = UseCaseScope(SupervisorJob() + Dispatchers.Main)

    fun unregister() {
        useCaseScope.cancel()
    }
}
