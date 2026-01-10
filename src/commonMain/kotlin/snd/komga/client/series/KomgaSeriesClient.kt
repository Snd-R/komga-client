package snd.komga.client.series

import snd.komga.client.book.KomgaBook
import snd.komga.client.book.KomgaMediaStatus
import snd.komga.client.book.KomgaReadStatus
import snd.komga.client.collection.KomgaCollection
import snd.komga.client.common.KomgaAuthor
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.KomgaThumbnailId
import snd.komga.client.common.Page
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.search.SeriesConditionBuilder

interface KomgaSeriesClient {
    suspend fun getOneSeries(seriesId: KomgaSeriesId): KomgaSeries

    @Deprecated("use getSeriesList()")
    suspend fun getAllSeries(
        query: KomgaSeriesQuery? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaSeries>

    suspend fun getSeriesList(
        conditionBuilder: SeriesConditionBuilder,
        fulltextSearch: String?,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaSeries>

    suspend fun getSeriesList(
        search: KomgaSeriesSearch,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaSeries>

    @Deprecated("use getBookist() from book client")
    suspend fun getAllBooksBySeries(
        seriesId: KomgaSeriesId,
        mediaStatus: List<KomgaMediaStatus>? = null,
        readStatus: List<KomgaReadStatus>? = null,
        tag: List<String>? = null,
        authors: List<KomgaAuthor>? = null,
        deleted: Boolean? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaBook>

    suspend fun getNewSeries(
        libraryIds: List<KomgaLibraryId>? = null,
        oneshot: Boolean? = null,
        deleted: Boolean? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaSeries>

    suspend fun getUpdatedSeries(
        libraryIds: List<KomgaLibraryId>? = null,
        oneshot: Boolean? = null,
        deleted: Boolean? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaSeries>

    suspend fun analyze(seriesId: KomgaSeriesId)
    suspend fun refreshMetadata(seriesId: KomgaSeriesId)
    suspend fun markAsRead(seriesId: KomgaSeriesId)
    suspend fun markAsUnread(seriesId: KomgaSeriesId)
    suspend fun delete(seriesId: KomgaSeriesId)
    suspend fun update(seriesId: KomgaSeriesId, request: KomgaSeriesMetadataUpdateRequest)
    suspend fun getDefaultThumbnail(seriesId: KomgaSeriesId): ByteArray?
    suspend fun getThumbnail(seriesId: KomgaSeriesId, thumbnailId: KomgaThumbnailId): ByteArray
    suspend fun getThumbnails(seriesId: KomgaSeriesId): List<KomgaSeriesThumbnail>

    suspend fun uploadThumbnail(
        seriesId: KomgaSeriesId,
        file: ByteArray,
        filename: String = "",
        selected: Boolean = true
    ): KomgaSeriesThumbnail

    suspend fun selectThumbnail(seriesId: KomgaSeriesId, thumbnailId: KomgaThumbnailId)
    suspend fun deleteThumbnail(seriesId: KomgaSeriesId, thumbnailId: KomgaThumbnailId)
    suspend fun getAllCollectionsBySeries(seriesId: KomgaSeriesId): List<KomgaCollection>
}