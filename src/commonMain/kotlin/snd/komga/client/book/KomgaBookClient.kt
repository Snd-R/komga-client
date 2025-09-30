package snd.komga.client.book

import io.ktor.client.statement.HttpResponse
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.KomgaThumbnailId
import snd.komga.client.common.Page
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.readlist.KomgaReadList
import snd.komga.client.search.BookConditionBuilder

interface KomgaBookClient {
    suspend fun getBook(bookId: KomgaBookId): KomgaBook

    @Deprecated("use getBookList()")
    suspend fun getAllBooks(query: KomgaBookQuery? = null, pageRequest: KomgaPageRequest? = null): Page<KomgaBook>

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
    suspend fun getBookSiblingPrevious(bookId: KomgaBookId): KomgaBook
    suspend fun getBookSiblingNext(bookId: KomgaBookId): KomgaBook
    suspend fun updateMetadata(bookId: KomgaBookId, request: KomgaBookMetadataUpdateRequest)
    suspend fun getBookPages(bookId: KomgaBookId): List<KomgaBookPage>
    suspend fun analyze(bookId: KomgaBookId)
    suspend fun refreshMetadata(bookId: KomgaBookId)
    suspend fun markReadProgress(bookId: KomgaBookId, request: KomgaBookReadProgressUpdateRequest)
    suspend fun deleteReadProgress(bookId: KomgaBookId)
    suspend fun deleteBook(bookId: KomgaBookId)
    suspend fun regenerateThumbnails(forBiggerResultOnly: Boolean)
    suspend fun getBookThumbnail(bookId: KomgaBookId): ByteArray
    suspend fun getBookThumbnails(bookId: KomgaBookId): List<KomgaBookThumbnail>
    suspend fun uploadBookThumbnail(
        bookId: KomgaBookId,
        file: ByteArray,
        filename: String = "",
        selected: Boolean = true
    ): KomgaBookThumbnail

    suspend fun selectBookThumbnail(bookId: KomgaBookId, thumbnailId: KomgaThumbnailId)
    suspend fun deleteBookThumbnail(bookId: KomgaBookId, thumbnailId: KomgaThumbnailId)
    suspend fun getAllReadListsByBook(bookId: KomgaBookId): List<KomgaReadList>
    suspend fun getBookPage(bookId: KomgaBookId, page: Int): ByteArray
    suspend fun <T> streamBookPage(bookId: KomgaBookId, page: Int, block: suspend (response: HttpResponse) -> T): T

    suspend fun getReadiumProgression(bookId: KomgaBookId): R2Progression?
    suspend fun updateReadiumProgression(bookId: KomgaBookId, progression: R2Progression)
    suspend fun getReadiumPositions(bookId: KomgaBookId): R2Positions

    suspend fun getWebPubManifest(bookId: KomgaBookId): WPPublication
}