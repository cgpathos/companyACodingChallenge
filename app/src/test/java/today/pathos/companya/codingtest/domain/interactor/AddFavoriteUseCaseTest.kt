package today.pathos.companya.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.repository.FavoriteRepository
import java.util.*

class AddFavoriteUseCaseTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    private lateinit var favoriteRepository: FavoriteRepository

    private lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Before
    fun setUp() {
        addFavoriteUseCase = AddFavoriteUseCase(favoriteRepository)
    }

    @Test
    fun testAddFavorite() {
        val favoriteList = arrayListOf(
            SearchItem("DUMMY_URL1", SearchItem.TYPE_VIDEO, Date()),
            SearchItem("DUMMY_URL2", SearchItem.TYPE_IMAGE, Date()),
        )
        val expectResult = listOf(
            SearchItem("DUMMY_URL1", SearchItem.TYPE_VIDEO, Date()),
            SearchItem("DUMMY_URL2", SearchItem.TYPE_IMAGE, Date()),
            SearchItem("NEW_DUMMY_URL", SearchItem.TYPE_VIDEO, Date()),
        )
        val newFavorite = SearchItem("NEW_DUMMY_URL", SearchItem.TYPE_VIDEO, Date())

        given(favoriteRepository.addFavorite(newFavorite)).willReturn(
            Completable.fromAction { favoriteList.add(newFavorite) }
        )
        given(favoriteRepository.getFavoriteList()).willReturn(Single.just(favoriteList))
        addFavoriteUseCase.execute(newFavorite)
            .test()
            .assertNoErrors()
            .assertNoValues()
            .assertComplete()
            .dispose()

        Assert.assertEquals(expectResult, favoriteList)
    }

    @Test
    fun testPreventAddSameItem() {
        val favoriteList = arrayListOf(
            SearchItem("DUMMY_URL1", SearchItem.TYPE_VIDEO, Date()),
            SearchItem("DUMMY_URL2", SearchItem.TYPE_IMAGE, Date()),
            SearchItem("NEW_DUMMY_URL", SearchItem.TYPE_VIDEO, Date()),
        )
        val expectResult = listOf(
            SearchItem("DUMMY_URL1", SearchItem.TYPE_VIDEO, Date()),
            SearchItem("DUMMY_URL2", SearchItem.TYPE_IMAGE, Date()),
            SearchItem("NEW_DUMMY_URL", SearchItem.TYPE_VIDEO, Date()),
        )
        val newFavorite = SearchItem("NEW_DUMMY_URL", SearchItem.TYPE_VIDEO, Date())

        given(favoriteRepository.addFavorite(newFavorite)).willReturn(
            Completable.fromAction { favoriteList.add(newFavorite) }
        )
        given(favoriteRepository.getFavoriteList()).willReturn(Single.just(favoriteList))
        addFavoriteUseCase.execute(newFavorite)
            .test()
            .assertNoErrors()
            .assertNoValues()
            .assertComplete()
            .dispose()

        Assert.assertEquals(expectResult.size, favoriteList.size)
    }
}