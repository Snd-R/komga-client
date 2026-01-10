package snd.komga.client.book

import io.ktor.client.statement.*
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.KomgaThumbnailId
import snd.komga.client.common.Page
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.readlist.KomgaReadList
import snd.komga.client.search.BookConditionBuilder

interface KomgaBookClient {
    suspend fun getOne(bookId: KomgaBookId): KomgaBook

    suspend fun getBookList(
        conditionBuilder: BookConditionBuilder,
        fullTextSearch: String? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaBook>

    suspend fun getBookList(
        search: KomgaBookSearch,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaBook>

    suspend fun getLatestBooks(pageRequest: KomgaPageRequest? = null): Page<KomgaBook>
    suspend fun getBooksOnDeck(
        libraryIds: List<KomgaLibraryId>? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaBook>

    suspend fun getDuplicateBooks(pageRequest: KomgaPageRequest? = null): Page<KomgaBook>
    suspend fun getBookSiblingPrevious(bookId: KomgaBookId): KomgaBook?
    suspend fun getBookSiblingNext(bookId: KomgaBookId): KomgaBook?
    suspend fun updateMetadata(bookId: KomgaBookId, request: KomgaBookMetadataUpdateRequest)
    suspend fun getBookPages(bookId: KomgaBookId): List<KomgaBookPage>
    suspend fun analyze(bookId: KomgaBookId)
    suspend fun refreshMetadata(bookId: KomgaBookId)
    suspend fun markReadProgress(bookId: KomgaBookId, request: KomgaBookReadProgressUpdateRequest)
    suspend fun deleteReadProgress(bookId: KomgaBookId)
    suspend fun deleteBook(bookId: KomgaBookId)
    suspend fun regenerateThumbnails(forBiggerResultOnly: Boolean)
    suspend fun getDefaultThumbnail(bookId: KomgaBookId): ByteArray?
    suspend fun getThumbnail(bookId: KomgaBookId, thumbnailId: KomgaThumbnailId): ByteArray
    suspend fun getThumbnails(bookId: KomgaBookId): List<KomgaBookThumbnail>
    suspend fun uploadThumbnail(
        bookId: KomgaBookId,
        file: ByteArray,
        filename: String = "",
        selected: Boolean = true
    ): KomgaBookThumbnail

    suspend fun selectThumbnail(bookId: KomgaBookId, thumbnailId: KomgaThumbnailId)
    suspend fun deleteThumbnail(bookId: KomgaBookId, thumbnailId: KomgaThumbnailId)
    suspend fun getAllReadListsByBook(bookId: KomgaBookId): List<KomgaReadList>
    suspend fun getPage(bookId: KomgaBookId, page: Int): ByteArray
    suspend fun getPageThumbnail(bookId: KomgaBookId, page: Int): ByteArray

    suspend fun getReadiumProgression(bookId: KomgaBookId): R2Progression?
    suspend fun updateReadiumProgression(bookId: KomgaBookId, progression: R2Progression)
    suspend fun getReadiumPositions(bookId: KomgaBookId): R2Positions

    suspend fun getWebPubManifest(bookId: KomgaBookId): WPPublication

    suspend fun getBookFile(bookId: KomgaBookId, block: suspend (response: HttpResponse) -> Unit)
    suspend fun getBookEpubResource(bookId: KomgaBookId, resourceName: String): ByteArray
}