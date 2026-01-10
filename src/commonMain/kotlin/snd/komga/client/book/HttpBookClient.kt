package snd.komga.client.book

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
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
import snd.komga.client.search.BookConditionBuilder


class HttpBookClient(private val ktor: HttpClient) : KomgaBookClient {

    override suspend fun getOne(bookId: KomgaBookId): KomgaBook {
        return ktor.get("api/v1/books/$bookId").body()
    }

    override suspend fun getBookList(
        conditionBuilder: BookConditionBuilder,
        fullTextSearch: String?,
        pageRequest: KomgaPageRequest?
    ): Page<KomgaBook> {
        return getBookList(
            search = KomgaBookSearch(
                condition = conditionBuilder.toBookCondition(),
                fullTextSearch = fullTextSearch
            ),
            pageRequest = pageRequest
        )
    }

    override suspend fun getBookList(
        search: KomgaBookSearch,
        pageRequest: KomgaPageRequest?
    ): Page<KomgaBook> {
        return ktor.post("api/v1/books/list") {
            pageRequest?.let { url.parameters.appendAll(it.toParams()) }
            contentType(ContentType.Application.Json)
            setBody(search)
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
                libraryIds?.ifEmpty { null }?.let { parameters.append("library_id", it.joinToString()) }
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

    override suspend fun getBookSiblingPrevious(bookId: KomgaBookId): KomgaBook? {
        return try {
            ktor.get("api/v1/books/$bookId/previous").body()
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) null
            else throw e
        }
    }

    override suspend fun getBookSiblingNext(bookId: KomgaBookId): KomgaBook? {
        return try {
            ktor.get("api/v1/books/$bookId/next").body()
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) null
            else throw e
        }
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

    override suspend fun getDefaultThumbnail(bookId: KomgaBookId): ByteArray? {
        return try {
            return ktor.get("api/v1/books/$bookId/thumbnail") {
                accept(ContentType.Any)
            }.body()
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) null
            else throw e
        }
    }

    override suspend fun getThumbnail(
        bookId: KomgaBookId,
        thumbnailId: KomgaThumbnailId
    ): ByteArray {
        return ktor.get("api/v1/books/$bookId/thumbnails/$thumbnailId") {
            accept(ContentType.Any)
        }.body()
    }

    override suspend fun getThumbnails(bookId: KomgaBookId): List<KomgaBookThumbnail> {
        return ktor.get("api/v1/books/$bookId/thumbnails").body()
    }

    override suspend fun uploadThumbnail(
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

    override suspend fun selectThumbnail(bookId: KomgaBookId, thumbnailId: KomgaThumbnailId) {
        ktor.put("api/v1/books/$bookId/thumbnails/$thumbnailId/selected")
    }

    override suspend fun deleteThumbnail(bookId: KomgaBookId, thumbnailId: KomgaThumbnailId) {
        ktor.delete("api/v1/books/$bookId/thumbnails/$thumbnailId")
    }

    override suspend fun getAllReadListsByBook(bookId: KomgaBookId): List<KomgaReadList> {
        return ktor.get("api/v1/books/$bookId/readlists").body()
    }

    override suspend fun getPage(bookId: KomgaBookId, page: Int): ByteArray {
        return ktor.get("api/v1/books/${bookId}/pages/$page").body()
    }

    override suspend fun getPageThumbnail(
        bookId: KomgaBookId,
        page: Int
    ): ByteArray {
        return ktor.get("api/v1/books/${bookId}/pages/$page/thumbnail") {
            accept(ContentType.Any)
        }.body()
    }

    override suspend fun getReadiumProgression(bookId: KomgaBookId): R2Progression? {
        val response: HttpResponse = ktor.get("api/v1/books/${bookId}/progression") {
            accept(ContentType.Any)
        }
        return if (response.status == HttpStatusCode.NoContent) null
        else response.body()
    }

    override suspend fun updateReadiumProgression(bookId: KomgaBookId, progression: R2Progression) {
        ktor.put("api/v1/books/${bookId}/progression") {
            contentType(ContentType.Application.Json)
            setBody(progression)
        }
    }

    override suspend fun getReadiumPositions(bookId: KomgaBookId): R2Positions {
        return ktor.get("api/v1/books/${bookId}/positions") {
            accept(ContentType.Any)
        }.body()
    }

    override suspend fun getWebPubManifest(bookId: KomgaBookId): WPPublication {
        return ktor.get("api/v1/books/${bookId}/manifest") {
            accept(ContentType.Any)
        }.body()
    }

    override suspend fun getBookFile(bookId: KomgaBookId, block: suspend (response: HttpResponse) -> Unit) {
        ktor.prepareGet("api/v1/books/${bookId}/file") {
            accept(ContentType.Any)
        }.execute(block)
    }

    override suspend fun getBookEpubResource(bookId: KomgaBookId, resourceName: String) : ByteArray{
        return ktor.get("api/v1/books/${bookId}/resource/$resourceName") {
            accept(ContentType.Any)
        }.body()
    }
}