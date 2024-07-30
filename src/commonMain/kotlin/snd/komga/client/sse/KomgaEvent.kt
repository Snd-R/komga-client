package snd.komga.client.sse

import snd.komga.client.book.KomgaBookId
import snd.komga.client.collection.KomgaCollectionId
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.readlist.KomgaReadListId
import snd.komga.client.series.KomgaSeriesId
import snd.komga.client.sse.KomgaEvent.BookAdded
import snd.komga.client.sse.KomgaEvent.BookChanged
import snd.komga.client.sse.KomgaEvent.BookDeleted
import snd.komga.client.sse.KomgaEvent.BookImported
import snd.komga.client.sse.KomgaEvent.CollectionAdded
import snd.komga.client.sse.KomgaEvent.CollectionChanged
import snd.komga.client.sse.KomgaEvent.CollectionDeleted
import snd.komga.client.sse.KomgaEvent.LibraryAdded
import snd.komga.client.sse.KomgaEvent.LibraryChanged
import snd.komga.client.sse.KomgaEvent.LibraryDeleted
import snd.komga.client.sse.KomgaEvent.ReadListAdded
import snd.komga.client.sse.KomgaEvent.ReadListChanged
import snd.komga.client.sse.KomgaEvent.ReadListDeleted
import snd.komga.client.sse.KomgaEvent.ReadProgressChanged
import snd.komga.client.sse.KomgaEvent.ReadProgressDeleted
import snd.komga.client.sse.KomgaEvent.ReadProgressSeriesChanged
import snd.komga.client.sse.KomgaEvent.ReadProgressSeriesDeleted
import snd.komga.client.sse.KomgaEvent.SeriesAdded
import snd.komga.client.sse.KomgaEvent.SeriesChanged
import snd.komga.client.sse.KomgaEvent.SeriesDeleted
import snd.komga.client.sse.KomgaEvent.SessionExpired
import snd.komga.client.sse.KomgaEvent.TaskQueueStatus
import snd.komga.client.sse.KomgaEvent.ThumbnailBookAdded
import snd.komga.client.sse.KomgaEvent.ThumbnailBookDeleted
import snd.komga.client.sse.KomgaEvent.ThumbnailReadListAdded
import snd.komga.client.sse.KomgaEvent.ThumbnailReadListDeleted
import snd.komga.client.sse.KomgaEvent.ThumbnailSeriesAdded
import snd.komga.client.sse.KomgaEvent.ThumbnailSeriesCollectionAdded
import snd.komga.client.sse.KomgaEvent.ThumbnailSeriesCollectionDeleted
import snd.komga.client.sse.KomgaEvent.ThumbnailSeriesDeleted
import snd.komga.client.sse.KomgaEvent.UnknownEvent
import snd.komga.client.user.KomgaUserId
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed interface KomgaEvent {

    @Serializable
    data class LibraryAdded(override val libraryId: KomgaLibraryId) : LibraryEvent

    @Serializable
    data class LibraryChanged(override val libraryId: KomgaLibraryId) : LibraryEvent

    @Serializable
    data class LibraryDeleted(override val libraryId: KomgaLibraryId) : LibraryEvent

    @Serializable
    data class SeriesAdded(
        override val seriesId: KomgaSeriesId,
        override val libraryId: KomgaLibraryId
    ) : SeriesEvent

    @Serializable
    data class SeriesChanged(
        override val seriesId: KomgaSeriesId,
        override val libraryId: KomgaLibraryId
    ) : SeriesEvent

    @Serializable
    data class SeriesDeleted(
        override val seriesId: KomgaSeriesId,
        override val libraryId: KomgaLibraryId
    ) : SeriesEvent

    @Serializable
    data class BookAdded(
        override val bookId: KomgaBookId,
        override val seriesId: KomgaSeriesId,
        override val libraryId: KomgaLibraryId
    ) : BookEvent

    @Serializable
    data class BookChanged(
        override val bookId: KomgaBookId,
        override val seriesId: KomgaSeriesId,
        override val libraryId: KomgaLibraryId
    ) : BookEvent

    @Serializable
    data class BookDeleted(
        override val bookId: KomgaBookId,
        override val seriesId: KomgaSeriesId,
        override val libraryId: KomgaLibraryId
    ) : BookEvent

    @Serializable
    data class BookImported(
        val bookId: KomgaBookId?,
        val sourceFile: String,
        val success: Boolean,
        val message: String?
    ) : KomgaEvent

    @Serializable
    data class ReadListAdded(
        override val readListId: KomgaReadListId,
        override val bookIds: List<KomgaBookId>
    ) : ReadListEvent

    @Serializable
    data class ReadListChanged(
        override val readListId: KomgaReadListId,
        override val bookIds: List<KomgaBookId>
    ) : ReadListEvent

    @Serializable
    data class ReadListDeleted(
        override val readListId: KomgaReadListId,
        override val bookIds: List<KomgaBookId>
    ) : ReadListEvent

    @Serializable
    data class CollectionAdded(
        override val collectionId: KomgaCollectionId,
        override val seriesIds: List<KomgaSeriesId>
    ) : CollectionEvent

    @Serializable
    data class CollectionChanged(
        override val collectionId: KomgaCollectionId,
        override val seriesIds: List<KomgaSeriesId>
    ) : CollectionEvent

    @Serializable
    data class CollectionDeleted(
        override val collectionId: KomgaCollectionId,
        override val seriesIds: List<KomgaSeriesId>
    ) : CollectionEvent

    @Serializable
    data class ReadProgressChanged(
        override val bookId: KomgaBookId,
        override val userId: KomgaUserId
    ) : ReadProgressEvent

    @Serializable
    data class ReadProgressDeleted(
        override val bookId: KomgaBookId,
        override val userId: KomgaUserId
    ) : ReadProgressEvent

    @Serializable
    data class ReadProgressSeriesChanged(
        override val seriesId: KomgaSeriesId,
        override val userId: KomgaUserId
    ) : ReadProgressSeriesEvent

    @Serializable
    data class ReadProgressSeriesDeleted(
        override val seriesId: KomgaSeriesId,
        override val userId: KomgaUserId
    ) : ReadProgressSeriesEvent

    @Serializable
    data class ThumbnailBookAdded(
        override val bookId: KomgaBookId,
        override val seriesId: KomgaSeriesId,
        override val selected: Boolean
    ) : ThumbnailBookEvent

    @Serializable
    data class ThumbnailBookDeleted(
        override val bookId: KomgaBookId,
        override val seriesId: KomgaSeriesId,
        override val selected: Boolean
    ) : ThumbnailBookEvent

    @Serializable
    data class ThumbnailSeriesAdded(
        override val seriesId: KomgaSeriesId,
        override val selected: Boolean
    ) : ThumbnailSeriesEvent

    @Serializable
    data class ThumbnailSeriesDeleted(
        override val seriesId: KomgaSeriesId,
        override val selected: Boolean
    ) : ThumbnailSeriesEvent

    @Serializable
    data class ThumbnailSeriesCollectionAdded(
        override val collectionId: KomgaCollectionId,
        override val selected: Boolean
    ) : ThumbnailCollectionEvent

    @Serializable
    data class ThumbnailSeriesCollectionDeleted(
        override val collectionId: KomgaCollectionId,
        override val selected: Boolean
    ) : ThumbnailCollectionEvent

    @Serializable
    data class ThumbnailReadListAdded(
        override val readListId: KomgaReadListId,
        override val selected: Boolean
    ) : ThumbnailReadListEvent

    @Serializable
    data class ThumbnailReadListDeleted(
        override val readListId: KomgaReadListId,
        override val selected: Boolean
    ) : ThumbnailReadListEvent

    @Serializable
    data class SessionExpired(
        val userId: KomgaUserId,
    ) : KomgaEvent

    @Serializable
    data class TaskQueueStatus(
        val count: Int,
        val countByType: Map<String, Int>
    ) : KomgaEvent

    data class UnknownEvent(val event: String?, val data: String?) : KomgaEvent

    interface LibraryEvent : KomgaEvent {
        val libraryId: KomgaLibraryId
    }

    interface SeriesEvent : KomgaEvent {
        val seriesId: KomgaSeriesId
        val libraryId: KomgaLibraryId
    }

    interface BookEvent : KomgaEvent {
        val bookId: KomgaBookId
        val seriesId: KomgaSeriesId
        val libraryId: KomgaLibraryId
    }

    interface ReadListEvent : KomgaEvent {
        val readListId: KomgaReadListId
        val bookIds: List<KomgaBookId>
    }

    interface CollectionEvent : KomgaEvent {
        val collectionId: KomgaCollectionId
        val seriesIds: List<KomgaSeriesId>
    }

    interface ReadProgressEvent : KomgaEvent {
        val bookId: KomgaBookId
        val userId: KomgaUserId
    }

    interface ReadProgressSeriesEvent : KomgaEvent {
        val seriesId: KomgaSeriesId
        val userId: KomgaUserId
    }

    interface ThumbnailBookEvent : KomgaEvent {
        val bookId: KomgaBookId
        val seriesId: KomgaSeriesId
        val selected: Boolean
    }

    interface ThumbnailSeriesEvent : KomgaEvent {
        val seriesId: KomgaSeriesId
        val selected: Boolean
    }

    interface ThumbnailCollectionEvent : KomgaEvent {
        val collectionId: KomgaCollectionId
        val selected: Boolean
    }

    interface ThumbnailReadListEvent : KomgaEvent {
        val readListId: KomgaReadListId
        val selected: Boolean
    }

}


fun Json.toKomgaEvent(event: String?, data: String?): KomgaEvent {
    if (data == null) return UnknownEvent(event, null)

    return when (event) {
        "TaskQueueStatus" -> decodeFromString<TaskQueueStatus>(data)
        "LibraryAdded" -> decodeFromString<LibraryAdded>(data)
        "LibraryChanged" -> decodeFromString<LibraryChanged>(data)
        "LibraryDeleted" -> decodeFromString<LibraryDeleted>(data)
        "SeriesAdded" -> decodeFromString<SeriesAdded>(data)
        "SeriesChanged" -> decodeFromString<SeriesChanged>(data)
        "SeriesDeleted" -> decodeFromString<SeriesDeleted>(data)
        "BookAdded" -> decodeFromString<BookAdded>(data)
        "BookChanged" -> decodeFromString<BookChanged>(data)
        "BookDeleted" -> decodeFromString<BookDeleted>(data)
        "BookImported" -> decodeFromString<BookImported>(data)
        "ReadListAdded" -> decodeFromString<ReadListAdded>(data)
        "ReadListChanged" -> decodeFromString<ReadListChanged>(data)
        "ReadListDeleted" -> decodeFromString<ReadListDeleted>(data)
        "CollectionAdded" -> decodeFromString<CollectionAdded>(data)
        "CollectionChanged" -> decodeFromString<CollectionChanged>(data)
        "CollectionDeleted" -> decodeFromString<CollectionDeleted>(data)
        "ReadProgressChanged" -> decodeFromString<ReadProgressChanged>(data)
        "ReadProgressDeleted" -> decodeFromString<ReadProgressDeleted>(data)
        "ReadProgressSeriesChanged" -> decodeFromString<ReadProgressSeriesChanged>(data)
        "ReadProgressSeriesDeleted" -> decodeFromString<ReadProgressSeriesDeleted>(data)
        "ThumbnailBookAdded" -> decodeFromString<ThumbnailBookAdded>(data)
        "ThumbnailBookDeleted" -> decodeFromString<ThumbnailBookDeleted>(data)
        "ThumbnailSeriesAdded" -> decodeFromString<ThumbnailSeriesAdded>(data)
        "ThumbnailSeriesDeleted" -> decodeFromString<ThumbnailSeriesDeleted>(data)
        "ThumbnailSeriesCollectionAdded" -> decodeFromString<ThumbnailSeriesCollectionAdded>(data)
        "ThumbnailSeriesCollectionDeleted" -> decodeFromString<ThumbnailSeriesCollectionDeleted>(data)

        "ThumbnailReadListAdded" -> decodeFromString<ThumbnailReadListAdded>(data)
        "ThumbnailReadListDeleted" -> decodeFromString<ThumbnailReadListDeleted>(data)
        "SessionExpired" -> decodeFromString<SessionExpired>(data)
        else -> UnknownEvent(event, data)
    }
}
