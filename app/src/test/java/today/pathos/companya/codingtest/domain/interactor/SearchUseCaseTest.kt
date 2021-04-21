package today.pathos.companya.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.repository.SearchRepository
import java.text.SimpleDateFormat

class SearchUseCaseTest {
    companion object {
        const val SEARCH_KEYWORD = "DUMMY_KEYWORD"
    }

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    private lateinit var searchRepository: SearchRepository

    private lateinit var searchUseCase: SearchUseCase

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Before
    fun setUp() {
        searchUseCase = SearchUseCase(searchRepository)
    }

    @Test
    fun testSearchImage() {
        val listImage = listOf(
            SearchItem("DUMMY_URL1", SearchItem.TYPE_IMAGE, dateFormat.parse("2021-03-21 01:12:00")!!),
            SearchItem("DUMMY_URL2", SearchItem.TYPE_IMAGE, dateFormat.parse("2021-01-11 05:12:00")!!),
            SearchItem("DUMMY_URL3", SearchItem.TYPE_IMAGE, dateFormat.parse("2020-03-21 11:12:05")!!),
            SearchItem("DUMMY_URL4", SearchItem.TYPE_IMAGE, dateFormat.parse("2021-03-19 03:12:10")!!),
        )
        val listVideo = listOf(
            SearchItem("DUMMY_URL5", SearchItem.TYPE_VIDEO, dateFormat.parse("2021-01-01 01:12:00")!!),
            SearchItem("DUMMY_URL6", SearchItem.TYPE_VIDEO, dateFormat.parse("2020-03-12 21:12:00")!!),
            SearchItem("DUMMY_URL7", SearchItem.TYPE_VIDEO, dateFormat.parse("2021-03-23 00:00:00")!!),
        )
        val expectResult = listOf(
            SearchItem("DUMMY_URL7", SearchItem.TYPE_VIDEO, dateFormat.parse("2021-03-21 03:00:20")!!),
            SearchItem("DUMMY_URL1", SearchItem.TYPE_IMAGE, dateFormat.parse("2021-03-21 01:12:00")!!),
            SearchItem("DUMMY_URL4", SearchItem.TYPE_IMAGE, dateFormat.parse("2021-03-19 03:12:10")!!),
            SearchItem("DUMMY_URL2", SearchItem.TYPE_IMAGE, dateFormat.parse("2021-01-11 05:12:00")!!),
            SearchItem("DUMMY_URL5", SearchItem.TYPE_VIDEO, dateFormat.parse("2021-01-01 01:12:00")!!),
            SearchItem("DUMMY_URL3", SearchItem.TYPE_IMAGE, dateFormat.parse("2020-03-21 11:12:05")!!),
            SearchItem("DUMMY_URL6", SearchItem.TYPE_VIDEO, dateFormat.parse("2020-03-12 21:12:00")!!),
        )

        given(searchRepository.getImageList(SEARCH_KEYWORD)).willReturn(Single.just(listImage))
        given(searchRepository.getVideoList(SEARCH_KEYWORD)).willReturn(Single.just(listVideo))
        searchUseCase.execute(SEARCH_KEYWORD)
            .test()
            .assertNoErrors()
            .assertValue {
                it[0].thumbnailUrl == expectResult[0].thumbnailUrl
                        && it[1].thumbnailUrl == expectResult[1].thumbnailUrl
                        && it[2].thumbnailUrl == expectResult[2].thumbnailUrl
                        && it[3].thumbnailUrl == expectResult[3].thumbnailUrl
                        && it[4].thumbnailUrl == expectResult[4].thumbnailUrl
                        && it[5].thumbnailUrl == expectResult[5].thumbnailUrl
                        && it[6].thumbnailUrl == expectResult[6].thumbnailUrl
            }
            .assertComplete()
            .dispose()
    }
}