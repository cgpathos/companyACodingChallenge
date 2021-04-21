package today.pathos.companya.codingtest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import today.pathos.companya.codingtest.core.rx.RxSchedulerRule
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.interactor.*
import java.util.*

class MainViewModelTest {
    companion object {
        const val SEARCH_KEYWORD = "DUMMY_KEYWORD"
    }

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var searchUseCase: SearchUseCase

    @Mock
    lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Mock
    lateinit var delFavoriteUseCase: DelFavoriteUseCase

    @Mock
    lateinit var favoriteListUseCase: FavoriteListUseCase

    @Mock
    lateinit var clearFavoriteUseCase: ClearFavoriteUseCase

    @Mock
    lateinit var viewStateObserver: Observer<MainViewState>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner


    private lateinit var lifecycle: Lifecycle
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        lifecycle = LifecycleRegistry(lifecycleOwner)
        viewModel = MainViewModel(
            searchUseCase,
            addFavoriteUseCase,
            delFavoriteUseCase,
            favoriteListUseCase,
            clearFavoriteUseCase
        )
        viewModel.viewState.observeForever(viewStateObserver)
    }

    @Test
    fun `검색 성공시 ViewState 변화 테스트`() {
        val listImage = listOf(
            SearchItem("DUMMY_URL1", SearchItem.TYPE_IMAGE, Date()),
            SearchItem("DUMMY_URL2", SearchItem.TYPE_IMAGE, Date()),
            SearchItem("DUMMY_URL3", SearchItem.TYPE_VIDEO, Date()),
            SearchItem("DUMMY_URL4", SearchItem.TYPE_VIDEO, Date()),
        )
        given(searchUseCase.execute(SEARCH_KEYWORD)).willReturn(Single.just(listImage))

        viewModel.searchImage(SEARCH_KEYWORD)
        verify(viewStateObserver).onChanged(MainViewState.Loading)
        verify(viewStateObserver).onChanged(MainViewState.Success)
    }

    @Test
    fun `검색 실패시 ViewState 변화 테스트`() {
        val fakeError = Throwable("HTTP 401 Unauthorized")

        given(searchUseCase.execute(SEARCH_KEYWORD)).willReturn(Single.error(fakeError))

        viewModel.searchImage(SEARCH_KEYWORD)
        verify(viewStateObserver).onChanged(MainViewState.Loading)
        verify(viewStateObserver).onChanged(MainViewState.Error(fakeError))
    }

    @Test
    fun `보관함 추가시 ViewState 변화 테스트`() {
        val newFavorite = SearchItem("NEW_DUMMY_URL", SearchItem.TYPE_VIDEO, Date())

        given(addFavoriteUseCase.execute(newFavorite)).willReturn(Completable.complete())
        given(favoriteListUseCase.execute()).willReturn(Single.just(emptyList()))

        viewModel.addFavorite(newFavorite)
        verify(viewStateObserver).onChanged(MainViewState.Loading)
        verify(viewStateObserver).onChanged(MainViewState.SuccessAddFavorite)
    }

    @Test
    fun `보관함 삭제시 ViewState 변화 테스트`() {
        val targetFavorite = SearchItem("DUMMY_URL", SearchItem.TYPE_IMAGE, Date())

        given(delFavoriteUseCase.execute(targetFavorite)).willReturn(Completable.complete())
        given(favoriteListUseCase.execute()).willReturn(Single.just(emptyList()))

        viewModel.delFavorite(targetFavorite)
        verify(viewStateObserver).onChanged(MainViewState.Loading)
        verify(viewStateObserver).onChanged(MainViewState.SuccessDelFavorite)
    }
}