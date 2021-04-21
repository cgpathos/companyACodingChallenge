package today.pathos.companya.codingtest.core.extension

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Single<T>.defaultScheduler(): Single<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.defaultScheduler(): Completable =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())