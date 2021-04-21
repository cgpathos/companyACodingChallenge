package today.pathos.companya.codingtest.domain.entity

import java.util.*

data class SearchItem(
    val thumbnailUrl: String,
    val type: String = TYPE_UNKNOWN,
    val datetime: Date,
) {
    companion object {
        const val TYPE_UNKNOWN = "unknown"
        const val TYPE_IMAGE = "image"
        const val TYPE_VIDEO = "videoClip"
    }
}
