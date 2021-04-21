package today.pathos.companya.codingtest.data.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import today.pathos.companya.codingtest.data.network.dto.DtoResult
import today.pathos.companya.codingtest.data.network.dto.Image
import today.pathos.companya.codingtest.data.network.dto.Video

interface SearchService {
    @GET("image")
    fun searchImage(@Query("query") query: String): Single<DtoResult<Image>>

    @GET("vclip")
    fun searchVideo(@Query("query") query: String): Single<DtoResult<Video>>
}
