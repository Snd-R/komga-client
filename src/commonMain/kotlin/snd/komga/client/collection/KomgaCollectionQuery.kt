package snd.komga.client.collection

import snd.komga.client.book.KomgaReadStatus
import snd.komga.client.common.KomgaAuthor
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.series.KomgaSeriesStatus
import kotlinx.serialization.Serializable

@Serializable
data class KomgaCollectionQuery(
    val libraryIds: List<KomgaLibraryId>? =null,
    val status: List<KomgaSeriesStatus>? = null,
    val readStatus: List<KomgaReadStatus>? = null,
    val publishers: List<String>? = null,
    val languages: List<String>? = null,
    val genres: List<String>? = null,
    val tags: List<String>? = null,
    val ageRatings: List<String>? = null,
    val releaseYears: List<String>? = null,
    val authors: List<KomgaAuthor>? = null,
    val deleted: Boolean? = null,
    val complete: Boolean? = null,
)