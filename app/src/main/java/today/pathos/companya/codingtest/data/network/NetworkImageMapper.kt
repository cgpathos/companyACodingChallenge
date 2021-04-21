package today.pathos.companya.codingtest.data.network

import today.pathos.companya.codingtest.data.network.dto.Image
import today.pathos.companya.codingtest.domain.Mapper
import today.pathos.companya.codingtest.domain.entity.SearchItem
import javax.inject.Inject

class NetworkImageMapper @Inject constructor() : Mapper<Image, SearchItem>() {
    override fun map(value: Image): SearchItem {
        return SearchItem(value.thumbnailUrl, SearchItem.TYPE_IMAGE, value.datetime)
    }

    override fun reverseMap(value: SearchItem): Image {
        throw NotImplementedError()
    }
}