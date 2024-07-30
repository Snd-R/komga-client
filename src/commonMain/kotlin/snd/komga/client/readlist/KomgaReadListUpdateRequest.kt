package snd.komga.client.readlist

import snd.komga.client.book.KomgaBookId
import snd.komga.client.common.PatchValue
import kotlinx.serialization.Serializable

@Serializable
data class KomgaReadListUpdateRequest(
    val name: PatchValue<String> = PatchValue.Unset,
    val summary: PatchValue<String> = PatchValue.Unset,
    val ordered: PatchValue<Boolean> = PatchValue.Unset,
    val bookIds: PatchValue<List<KomgaBookId>> = PatchValue.Unset,
)