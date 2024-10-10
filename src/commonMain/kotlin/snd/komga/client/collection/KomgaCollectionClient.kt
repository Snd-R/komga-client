package snd.komga.client.collection

import snd.komga.client.collection.KomgaCollection
import snd.komga.client.collection.KomgaCollectionCreateRequest
import snd.komga.client.collection.KomgaCollectionId
import snd.komga.client.collection.KomgaCollectionQuery
import snd.komga.client.collection.KomgaCollectionThumbnail
import snd.komga.client.collection.KomgaCollectionUpdateRequest
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.KomgaThumbnailId
import snd.komga.client.common.Page
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.series.KomgaSeries

interface KomgaCollectionClient {

    suspend fun getAll(
        search: String? = null,
        libraryIds: List<KomgaLibraryId>? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaCollection>

    suspend fun getOne(id: KomgaCollectionId): KomgaCollection

    suspend fun addOne(request: KomgaCollectionCreateRequest): KomgaCollection

    suspend fun updateOne(id: KomgaCollectionId, request: KomgaCollectionUpdateRequest)

    suspend fun deleteOne(id: KomgaCollectionId)

    suspend fun getSeriesForCollection(
        id: KomgaCollectionId,
        query: KomgaCollectionQuery? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaSeries>

    suspend fun getCollectionThumbnails(collectionId: KomgaCollectionId): List<KomgaCollectionThumbnail>

    suspend fun uploadCollectionThumbnail(
        collectionId: KomgaCollectionId,
        file: ByteArray,
        filename: String = "",
        selected: Boolean = true
    ): KomgaCollectionThumbnail

    suspend fun selectCollectionThumbnail(collectionId: KomgaCollectionId, thumbnailId: KomgaThumbnailId)

    suspend fun deleteCollectionThumbnail(collectionId: KomgaCollectionId, thumbnailId: KomgaThumbnailId)
}