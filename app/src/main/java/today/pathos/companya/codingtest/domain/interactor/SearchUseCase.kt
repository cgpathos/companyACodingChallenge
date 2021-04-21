package today.pathos.companya.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    fun execute(keyword: String): Single<List<SearchItem>> {
        return Observable.combineLatest(
            searchRepository.getImageList(keyword).toObservable(),
            searchRepository.getVideoList(keyword).toObservable(),
            { images, videos -> (images + videos).sortedByDescending { it.datetime } })
            .single(emptyList())
    }
}