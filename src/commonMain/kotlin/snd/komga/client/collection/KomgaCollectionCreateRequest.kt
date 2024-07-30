package snd.komga.client.collection

import snd.komga.client.series.KomgaSeriesId
import kotlinx.serialization.Serializable

@Serializable
data class KomgaCollectionCreateRequest(
    val name: String,
    val ordered: Boolean,
    val seriesIds: List<KomgaSeriesId>
)