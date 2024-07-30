package snd.komga.client.collection

import snd.komga.client.common.PatchValue
import snd.komga.client.series.KomgaSeriesId
import kotlinx.serialization.Serializable

@Serializable
data class KomgaCollectionUpdateRequest(
    val name: PatchValue<String> = PatchValue.Unset,
    val ordered: PatchValue<Boolean> = PatchValue.Unset,
    val seriesIds: PatchValue<List<KomgaSeriesId>> = PatchValue.Unset,
)