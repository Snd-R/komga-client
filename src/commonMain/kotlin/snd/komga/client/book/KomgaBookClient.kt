package snd.komga.client.book

import io.ktor.client.statement.*
import snd.komga.client.book.KomgaBook
import snd.komga.client.book.KomgaBookId
import snd.komga.client.book.KomgaBookMetadataUpdateRequest
import snd.komga.client.book.KomgaBookPage
import snd.komga.client.book.KomgaBookQuery
import snd.komga.client.book.KomgaBookReadProgressUpdateRequest
import snd.komga.client.book.KomgaBookThumbnail
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.KomgaThumbnailId
import snd.komga.client.common.Page
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.readlist.KomgaReadList

interface KomgaBookClient {
    suspend fun getBook(bookId: KomgaBookId): KomgaBook
    suspend fun getAllBooks(query: KomgaBookQuery? = null, pageRequest: KomgaPageRequest? = null): Page<KomgaBook>
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
}