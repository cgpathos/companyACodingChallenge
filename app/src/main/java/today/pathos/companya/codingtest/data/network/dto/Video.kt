package today.pathos.companya.codingtest.data.network.dto

import java.util.*

data class Video(
    val title: String,
    val url: String,
    val datetime: Date,
    val playTime: Int,
    val thumbnail: String,
    val author: String
)
