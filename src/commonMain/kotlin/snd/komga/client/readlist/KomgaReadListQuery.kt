package snd.komga.client.readlist

import snd.komga.client.book.KomgaMediaStatus
import snd.komga.client.book.KomgaReadStatus
import snd.komga.client.common.KomgaAuthor
import snd.komga.client.library.KomgaLibraryId
import kotlinx.serialization.Serializable

@Serializable
data class KomgaReadListQuery(
    val libraryIds: List<KomgaLibraryId>? = null,
    val readStatus: List<KomgaReadStatus>? = null,
    val tags: List<String>? = null,
    val mediaStatus: List<KomgaMediaStatus>? = null,
    val deleted: Boolean? = null,
    val authors: List<KomgaAuthor>? = null,
)