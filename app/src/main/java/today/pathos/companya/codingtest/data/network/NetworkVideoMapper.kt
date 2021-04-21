package today.pathos.companya.codingtest.data.network

import today.pathos.companya.codingtest.data.network.dto.Video
import today.pathos.companya.codingtest.domain.Mapper
import today.pathos.companya.codingtest.domain.entity.SearchItem
import javax.inject.Inject

class NetworkVideoMapper @Inject constructor() : Mapper<Video, SearchItem>() {
    override fun map(value: Video): SearchItem {
        return SearchItem(value.thumbnail, SearchItem.TYPE_VIDEO, value.datetime)
    }

    override fun reverseMap(value: SearchItem): Video {
        throw NotImplementedError()
    }
}