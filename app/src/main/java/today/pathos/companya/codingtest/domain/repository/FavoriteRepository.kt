package today.pathos.companya.codingtest.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import today.pathos.companya.codingtest.domain.entity.SearchItem

interface FavoriteRepository {
    fun addFavorite(searchItem: SearchItem) : Completable
    fun delFavorite(searchItem: SearchItem) : Completable
    fun getFavoriteList() : Single<List<SearchItem>>
    fun clearFavorite(): Completable
}