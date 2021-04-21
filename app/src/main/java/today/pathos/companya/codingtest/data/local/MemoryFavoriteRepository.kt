package today.pathos.companya.codingtest.data.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import today.pathos.companya.codingtest.domain.entity.SearchItem
import today.pathos.companya.codingtest.domain.repository.FavoriteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryFavoriteRepository @Inject constructor(

) : FavoriteRepository {
    private val favoriteList: ArrayList<SearchItem> = ArrayList()

    override fun addFavorite(searchItem: SearchItem): Completable {
        return Completable.fromAction { favoriteList.add(searchItem) }
    }

    override fun delFavorite(searchItem: SearchItem): Completable {
        return Completable.fromAction { favoriteList.remove(searchItem) }
    }

    override fun getFavoriteList(): Single<List<SearchItem>> {
        return Single.just(favoriteList)
    }

    override fun clearFavorite(): Completable {
        return Completable.fromAction { favoriteList.clear() }
    }
}