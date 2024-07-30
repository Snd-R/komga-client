package snd.komga.client.book

import snd.komga.client.library.KomgaLibraryId
import kotlinx.datetime.LocalDate

data class KomgaBookQuery(
    val searchTerm: String? = null,
    val libraryIds: List<KomgaLibraryId>? = null,
    val mediaStatus: List<KomgaMediaStatus>? = null,
    val readStatus: List<KomgaReadStatus>? = null,
    val releasedAfter: LocalDate? = null,
    val tags: List<String>? = null,
)