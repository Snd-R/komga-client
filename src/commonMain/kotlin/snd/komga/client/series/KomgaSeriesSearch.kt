package snd.komga.client.series

import kotlinx.serialization.Serializable
import snd.komga.client.search.KomgaSearchCondition

@Serializable
data class KomgaSeriesSearch(
    val condition: KomgaSearchCondition.SeriesCondition? = null,
    val fulltextSearch: String? = null,
)
