package today.pathos.companya.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Completable
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    fun execute(searchItem: SearchItem): Completable {
        return favoriteRepository.getFavoriteList()
            .flatMapCompletable { favoriteList ->
                val sameItem = favoriteList.firstOrNull() { it.thumbnailUrl == searchItem.thumbnailUrl }
                if(null == sameItem) {
                    favoriteRepository.addFavorite(searchItem)
                }
                else {
                    Completable.complete()
                }
            }
    }
}