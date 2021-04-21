package today.pathos.companya.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Single
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteListUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    fun execute(): Single<List<SearchItem>> = favoriteRepository.getFavoriteList()
}