package today.pathos.companya.codingtest.data.network.dto

import java.util.*

data class Image(
    val collection: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val displaySitename: String,
    val docUrl: String,
    val datetime: Date
)
