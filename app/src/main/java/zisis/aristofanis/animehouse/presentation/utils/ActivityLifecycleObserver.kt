package zisis.aristofanis.animehouse.presentation.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import zisis.aristofanis.animehouse.domain.usecases.UseCase

class ActivityLifecycleObserver<T : UseCase<*, *>>(private val useCases: List<T>) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyEvent() {
        for (useCase in useCases) {
            useCase.unregister()
        }
    }

}
