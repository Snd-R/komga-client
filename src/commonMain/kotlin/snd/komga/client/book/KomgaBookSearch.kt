package snd.komga.client.book

import kotlinx.serialization.Serializable
import snd.komga.client.search.KomgaSearchCondition

@Serializable
data class KomgaBookSearch(
    val condition: KomgaSearchCondition.BookCondition? = null,
    val fullTextSearch: String? = null,
)