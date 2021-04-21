package today.pathos.companya.codingtest.core.extension

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.addTo(disposableBag: CompositeDisposable) {
    disposableBag.add(this)
}