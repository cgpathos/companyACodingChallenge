package today.pathos.companya.codingtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import today.pathos.companya.codingtest.BuildConfig
import today.pathos.companya.codingtest.core.extension.addTo
import today.pathos.companya.codingtest.core.extension.defaultScheduler
import today.pathos.companya.codingtest.core.presentation.BaseViewModel
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.interactor.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val delFavoriteUseCase: DelFavoriteUseCase,
    private val favoriteListUseCase: FavoriteListUseCase,
    private val clearFavoriteUseCase: ClearFavoriteUseCase
) : BaseViewModel() {
    val viewState = MutableLiveData<MainViewState>()
    val searchResult = MutableLiveData<List<SearchItem>>()
    val favorite = MutableLiveData<List<SearchItem>>()

    init {
        searchResult.value = emptyList()
        favorite.value = emptyList()
    }

    fun searchImage(keyword: String) {
        searchUseCase.execute(keyword)
            .defaultScheduler()
            .doOnSuccess { viewState.value = MainViewState.Success }
            .startWith(Completable.fromAction { viewState.value = MainViewState.Loading })

            .subscribe({ searchResult.value = it }, { handleError(it) })
            .addTo(disposableBag)
    }

    fun addFavorite(searchItem: SearchItem) {
        addFavoriteUseCase.execute(searchItem)
            .defaultScheduler()
            .doOnComplete { viewState.value = MainViewState.SuccessAddFavorite }
            .startWith(Completable.fromAction { viewState.value = MainViewState.Loading })

            .subscribe({ getFavoriteList() }, { handleError(it) })
            .addTo(disposableBag)
    }

    fun delFavorite(searchItem: SearchItem) {
        delFavoriteUseCase.execute(searchItem)
            .defaultScheduler()
            .doOnComplete { viewState.value = MainViewState.SuccessDelFavorite }
            .startWith(Completable.fromAction { viewState.value = MainViewState.Loading })

            .subscribe({ getFavoriteList() }, { handleError(it) })
            .addTo(disposableBag)
    }

    fun getFavoriteList() {
        favoriteListUseCase.execute()
            .defaultScheduler()
            .doOnSuccess { viewState.value = MainViewState.Success }

            .subscribe({ favorite.value = it }, { handleError(it) })
            .addTo(disposableBag)
    }

    fun purgeData() {
        clearFavoriteUseCase.execute()
            .defaultScheduler()
            .subscribe({
                favorite.value = emptyList()
                searchResult.value = emptyList()
                viewState.value = MainViewState.FinishApp
            }, { handleError(it) })
            .addTo(disposableBag)
    }

    private fun handleError(error: Throwable) {
        viewState.value = MainViewState.Error(error)
        if (BuildConfig.DEBUG) {
            error.printStackTrace()
        }
    }
}
