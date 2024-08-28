package snd.komga.client.book

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import snd.komga.client.common.KomgaAuthor
import snd.komga.client.common.KomgaThumbnailId
import snd.komga.client.common.KomgaWebLink
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.series.KomgaSeriesId
import kotlin.jvm.JvmInline


@Serializable
@JvmInline
value class KomgaBookId(val value: String) {
    override fun toString() = value
}

@Serializable
data class KomgaBook(
    val id: KomgaBookId,
    val seriesId: KomgaSeriesId,
    val seriesTitle: String,
    val libraryId: KomgaLibraryId,
    val name: String,
    val url: String,
    val number: Int,
    val created: Instant,
    val lastModified: Instant,
    val fileLastModified: Instant,
    val sizeBytes: Long,
    val size: String,
    val media: Media,
    val metadata: KomgaBookMetadata,
    val readProgress: ReadProgress?,
    val deleted: Boolean,
    val fileHash: String,
    val oneshot: Boolean,
)

@Serializable
data class KomgaBookMetadata(
    val title: String,
    val summary: String,
    val number: String,
    val numberSort: Float,
    val releaseDate: LocalDate?,
    val authors: List<KomgaAuthor>,
    val tags: List<String>,
    val isbn: String,
    val links: List<KomgaWebLink>,

    val titleLock: Boolean,
    val summaryLock: Boolean,
    val numberLock: Boolean,
    val numberSortLock: Boolean,
    val releaseDateLock: Boolean,
    val authorsLock: Boolean,
    val tagsLock: Boolean,
    val isbnLock: Boolean,
    val linksLock: Boolean,

    val created: Instant,
    val lastModified: Instant,
)


@Serializable
data class KomgaBookThumbnail(
    val id: KomgaThumbnailId,
    val bookId: KomgaBookId,
    val type: String,
    val selected: Boolean,
    val mediaType: String,
    val fileSize: Long,
    val width: Int,
    val height: Int
)

@Serializable
data class Media(
    val status: KomgaMediaStatus,
    val mediaType: String,
    val pagesCount: Int,
    val comment: String,
    val mediaProfile: MediaProfile,
    val epubDivinaCompatible: Boolean
)

enum class MediaProfile {
    DIVINA,
    PDF,
    EPUB,
}

@Serializable
data class ReadProgress(
    val page: Int,
    val completed: Boolean,
    val readDate: Instant,
    val created: Instant,
    val lastModified: Instant,
)


enum class KomgaMediaStatus {
    READY,
    UNKNOWN,
    ERROR,
    UNSUPPORTED,
    OUTDATED
}

enum class KomgaReadStatus {
    UNREAD,
    IN_PROGRESS,
    READ
}

enum class CopyMode {
    MOVE,
    COPY,
    HARDLINK
}
