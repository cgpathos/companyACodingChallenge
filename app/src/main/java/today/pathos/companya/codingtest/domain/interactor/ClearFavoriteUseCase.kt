package today.pathos.companya.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Completable
import today.pathos.companya.codingtest.domain.repository.FavoriteRepository
import javax.inject.Inject

class ClearFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    fun execute(): Completable {
        return favoriteRepository.clearFavorite()
    }
}