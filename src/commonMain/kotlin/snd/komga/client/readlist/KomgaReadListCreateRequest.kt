package snd.komga.client.readlist

import snd.komga.client.book.KomgaBookId
import kotlinx.serialization.Serializable

@Serializable
data class KomgaReadListCreateRequest(
    val name: String,
    val summary: String,
    val ordered: Boolean,
    val bookIds: List<KomgaBookId>
)