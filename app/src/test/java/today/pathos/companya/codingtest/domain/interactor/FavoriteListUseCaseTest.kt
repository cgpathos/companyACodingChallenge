package today.pathos.companya.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.repository.FavoriteRepository
import java.util.*
import kotlin.collections.ArrayList

class FavoriteListUseCaseTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    private lateinit var favoriteRepository: FavoriteRepository

    private lateinit var addFavoriteUseCase: AddFavoriteUseCase
    private lateinit var favoriteListUseCase: FavoriteListUseCase

    @Before
    fun setUp() {
        addFavoriteUseCase = AddFavoriteUseCase(favoriteRepository)
        favoriteListUseCase = FavoriteListUseCase(favoriteRepository)
    }

    @Test
    fun testFavoriteList() {
        val favoriteList = ArrayList<SearchItem>()
        val newFavorite1 = SearchItem("NEW_DUMMY1_URL", SearchItem.TYPE_IMAGE, Date())
        val newFavorite2 = SearchItem("NEW_DUMMY2_URL", SearchItem.TYPE_VIDEO, Date())
        val newFavorite3 = SearchItem("NEW_DUMMY3_URL", SearchItem.TYPE_VIDEO, Date())

        val expectResult = listOf(
            SearchItem("DUMMY_URL1", SearchItem.TYPE_VIDEO, Date()),
            SearchItem("DUMMY_URL2", SearchItem.TYPE_IMAGE, Date()),
            SearchItem("NEW_DUMMY_URL", SearchItem.TYPE_VIDEO, Date()),
        )

        given(favoriteRepository.addFavorite(newFavorite1)).willReturn(
            Completable.fromAction { favoriteList.add(newFavorite1) }
        )
        given(favoriteRepository.addFavorite(newFavorite2)).willReturn(
            Completable.fromAction { favoriteList.add(newFavorite2) }
        )
        given(favoriteRepository.addFavorite(newFavorite3)).willReturn(
            Completable.fromAction { favoriteList.add(newFavorite3) }
        )
        given(favoriteRepository.getFavoriteList()).willReturn(Single.just(favoriteList))

        addFavoriteUseCase.execute(newFavorite1)
            .andThen(addFavoriteUseCase.execute(newFavorite2))
            .andThen(addFavoriteUseCase.execute(newFavorite3))
            .test()
            .assertNoErrors()
            .assertNoValues()
            .assertComplete()
            .dispose()

        favoriteListUseCase.execute()
            .test()
            .assertNoErrors()
            .assertValue { it.size == expectResult.size }
            .assertComplete()
            .dispose()
    }
}