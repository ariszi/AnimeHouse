package zisis.aristofanis.animehouse.presentation.core

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


inline fun <reified T : ViewModel> FragmentActivity.assistedViewModel(
    crossinline viewModelProducer: (SavedStateHandle) -> T
) = viewModels<T> {
    object : AbstractSavedStateViewModelFactory(this, intent?.extras) {
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
            viewModelProducer(handle) as T
    }
}

inline fun <reified T : ViewModel> Fragment.assistedViewModel(
    crossinline viewModelProducer: (SavedStateHandle) -> T
) = viewModels<T> {
    object : AbstractSavedStateViewModelFactory(this, arguments) {
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
            viewModelProducer(handle) as T
    }
}

//inline fun <reified T : ViewModel> Fragment.assistedNavGraphViewModel(
//    @IdRes navGraphId: Int,
//    crossinline viewModelProducer: (SavedStateHandle) -> T
//) = navGraphViewModels<T>(navGraphId) {
//    object : AbstractSavedStateViewModelFactory(this, arguments) {
//        override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
//            viewModelProducer(handle) as T
//    }
//}

inline fun <reified T : ViewModel> Fragment.assistedActivityViewModel(
    crossinline viewModelProducer: (SavedStateHandle) -> T
) = activityViewModels<T> {
    object : AbstractSavedStateViewModelFactory(this, activity?.intent?.extras) {
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle) =
            viewModelProducer(handle) as T
    }
}
