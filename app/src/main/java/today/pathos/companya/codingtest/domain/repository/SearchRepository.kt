package today.pathos.companya.codingtest.domain.repository

import io.reactivex.rxjava3.core.Single
import today.pathos.companya.codingtest.domain.entity.SearchItem

interface SearchRepository {
    fun getImageList(keyword: String) : Single<List<SearchItem>>
    fun getVideoList(keyword: String) : Single<List<SearchItem>>
}