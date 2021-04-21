package today.pathos.companya.codingtest.data.network

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.repository.SearchRepository
import javax.inject.Inject

class NetworkSearchRepository @Inject constructor(
    private val searchService: SearchService,
    private val imageMapper: NetworkImageMapper,
    private val videoMapper: NetworkVideoMapper,
) : SearchRepository {

    override fun getImageList(keyword: String): Single<List<SearchItem>> {
        return searchService.searchImage(keyword)
            .subscribeOn(Schedulers.io())
            .map { imageMapper.map(it.documents) }
    }

    override fun getVideoList(keyword: String): Single<List<SearchItem>> {
        return searchService.searchVideo(keyword)
            .subscribeOn(Schedulers.io())
            .map { videoMapper.map(it.documents) }
    }
}