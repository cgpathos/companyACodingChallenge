package today.pathos.companya.codingtest.data.network.dto

data class DtoResult<T>(
    val meta: Meta,
    val documents: List<T>
)
