package snd.komga.client.readlist

import snd.komga.client.book.KomgaBook
import snd.komga.client.book.KomgaBookId
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.KomgaThumbnailId
import snd.komga.client.common.Page
import snd.komga.client.library.KomgaLibraryId

interface KomgaReadListClient {

    suspend fun getAll(
        search: String? = null,
        libraryIds: List<KomgaLibraryId>? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaReadList>

    suspend fun getOne(id: KomgaReadListId): KomgaReadList
    suspend fun addOne(request: KomgaReadListCreateRequest): KomgaReadList
    suspend fun updateOne(id: KomgaReadListId, request: KomgaReadListUpdateRequest)
    suspend fun deleteOne(id: KomgaReadListId)
    suspend fun getBooksForReadList(
        id: KomgaReadListId,
        query: KomgaReadListQuery? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaBook>

    suspend fun getDefaultThumbnail(readListId: KomgaReadListId): ByteArray?
    suspend fun getThumbnail(readListId: KomgaReadListId, thumbnailId: KomgaThumbnailId): ByteArray
    suspend fun getThumbnails(readListId: KomgaReadListId): List<KomgaReadListThumbnail>
    suspend fun uploadThumbnail(
        readListId: KomgaReadListId,
        file: ByteArray,
        filename: String = "",
        selected: Boolean = true
    ): KomgaReadListThumbnail

    suspend fun selectThumbnail(readListId: KomgaReadListId, thumbnailId: KomgaThumbnailId)
    suspend fun deleteThumbnail(readListId: KomgaReadListId, thumbnailId: KomgaThumbnailId)

    suspend fun getBookSiblingNext(readListId: KomgaReadListId, bookId: KomgaBookId): KomgaBook?
    suspend fun getBookSiblingPrevious(readListId: KomgaReadListId, bookId: KomgaBookId): KomgaBook?
}