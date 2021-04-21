package today.pathos.companya.codingtest.core.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    val disposableBag = CompositeDisposable()

    override fun onCleared() {
        if (!disposableBag.isDisposed) {
            disposableBag.dispose()
        }
    }
}