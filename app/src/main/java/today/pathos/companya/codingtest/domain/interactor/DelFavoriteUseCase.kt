package today.pathos.companya.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Completable
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.repository.FavoriteRepository
import javax.inject.Inject

class DelFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    fun execute(searchItem: SearchItem): Completable {
        return favoriteRepository.getFavoriteList()
            .flatMapCompletable {
                if (it.contains(searchItem)) {
                    favoriteRepository.delFavorite(searchItem)
                } else {
                    Completable.complete()
                }
            }
    }
}