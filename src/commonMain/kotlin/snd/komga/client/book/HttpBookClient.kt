package snd.komga.client.book

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.KomgaThumbnailId
import snd.komga.client.common.Page
import snd.komga.client.common.toParams
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.readlist.KomgaReadList


class HttpBookClient(private val ktor: HttpClient) : KomgaBookClient {

    override suspend fun getBook(bookId: KomgaBookId): KomgaBook {
        return ktor.get("api/v1/books/$bookId").body()
    }

    override suspend fun getAllBooks(
        query: KomgaBookQuery?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaBook> {
        val params = ParametersBuilder().apply {
            query?.searchTerm?.let { append("search", it) }
            query?.libraryIds?.let { if (it.isNotEmpty()) append("library_id", it.joinToString(",")) }
            query?.mediaStatus?.let { append("media_status", it.joinToString(",")) }
            query?.readStatus?.let { append("read_status", it.joinToString(",")) }
            query?.releasedAfter?.let { append("released_after", it.toString()) }
            query?.tags?.let { append("tag", it.joinToString(",")) }
            pageRequest?.let { appendAll(it.toParams()) }
        }.build()

        return ktor.get {
            url {
                path("api/v1/books")
                parameters.appendAll(params)
            }
        }.body()
    }

    override suspend fun getLatestBooks(pageRequest: KomgaPageRequest?): Page<KomgaBook> {
        return ktor.get {
            url {
                path("api/v1/books/latest")
                pageRequest?.let { parameters.appendAll(it.toParams()) }
            }
        }.body()
    }

    override suspend fun getBooksOnDeck(
        libraryIds: List<KomgaLibraryId>?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaBook> {
        return ktor.get {
            url {
                path("api/v1/books/ondeck")
                pageRequest?.let { parameters.appendAll(it.toParams()) }
                libraryIds?.let { parameters.append("library_id", it.joinToString()) }
            }
        }.body()
    }

    override suspend fun getDuplicateBooks(pageRequest: KomgaPageRequest?): Page<KomgaBook> {
        return ktor.get {
            url {
                path("api/v1/books/duplicates")
                pageRequest?.let { parameters.appendAll(it.toParams()) }
            }
        }.body()
    }

    override suspend fun getBookSiblingPrevious(bookId: KomgaBookId): KomgaBook {
        return ktor.get("api/v1/books/$bookId/previous").body()
    }

    override suspend fun getBookSiblingNext(bookId: KomgaBookId): KomgaBook {
        return ktor.get("api/v1/books/$bookId/next").body()
    }

    override suspend fun updateMetadata(bookId: KomgaBookId, request: KomgaBookMetadataUpdateRequest) {
        ktor.patch("api/v1/books/$bookId/metadata") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun getBookPages(bookId: KomgaBookId): List<KomgaBookPage> {
        return ktor.get("api/v1/books/$bookId/pages").body()
    }

    override suspend fun analyze(bookId: KomgaBookId) {
        ktor.post("api/v1/books/$bookId/analyze")
    }

    override suspend fun refreshMetadata(bookId: KomgaBookId) {
        ktor.post("api/v1/books/$bookId/metadata/refresh")
    }

    override suspend fun markReadProgress(bookId: KomgaBookId, request: KomgaBookReadProgressUpdateRequest) {
        ktor.patch("api/v1/books/$bookId/read-progress") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun deleteReadProgress(bookId: KomgaBookId) {
        ktor.delete("api/v1/books/$bookId/read-progress")
    }

    override suspend fun deleteBook(bookId: KomgaBookId) {
        ktor.delete("api/v1/books/$bookId/file")
    }

    override suspend fun regenerateThumbnails(forBiggerResultOnly: Boolean) {
        ktor.put("api/v1/books/thumbnails") {
            parameter("for_bigger_result_only", forBiggerResultOnly)
        }
    }

    override suspend fun getBookThumbnail(bookId: KomgaBookId): ByteArray {
        return ktor.get("api/v1/books/$bookId/thumbnail") {
            accept(ContentType.Any)
        }.body()
    }

    override suspend fun getBookThumbnails(bookId: KomgaBookId): List<KomgaBookThumbnail> {
        return ktor.get("api/v1/books/$bookId/thumbnails").body()
    }

    override suspend fun uploadBookThumbnail(
        bookId: KomgaBookId,
        file: ByteArray,
        filename: String,
        selected: Boolean
    ): KomgaBookThumbnail {
        return ktor.post("api/v1/books/$bookId/thumbnails") {
            contentType(ContentType.MultiPart.FormData)
            setBody(
                MultiPartFormDataContent(formData {
                    append("file", file, Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=\"$filename\"")
                    })
                    append("selected", selected)
                })
            )
        }.body()
    }

    override suspend fun selectBookThumbnail(bookId: KomgaBookId, thumbnailId: KomgaThumbnailId) {
        ktor.put("api/v1/books/$bookId/thumbnails/$thumbnailId/selected")
    }

    override suspend fun deleteBookThumbnail(bookId: KomgaBookId, thumbnailId: KomgaThumbnailId) {
        ktor.delete("api/v1/books/$bookId/thumbnails/$thumbnailId")
    }

    override suspend fun getAllReadListsByBook(bookId: KomgaBookId): List<KomgaReadList> {
        return ktor.get("api/v1/books/$bookId/readlists").body()
    }

    override suspend fun getBookPage(bookId: KomgaBookId, page: Int): ByteArray {
        return ktor.get("api/v1/books/${bookId}/pages/$page").body()
    }

    override suspend fun <T> streamBookPage(
        bookId: KomgaBookId,
        page: Int,
        block: suspend (response: HttpResponse) -> T
    ): T {
        return ktor.prepareGet("api/v1/books/${bookId}/pages/$page").execute { block(it) }
    }
}