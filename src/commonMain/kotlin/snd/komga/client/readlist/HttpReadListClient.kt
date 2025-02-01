package snd.komga.client.readlist

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import snd.komga.client.book.KomgaBook
import snd.komga.client.book.KomgaBookId
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.KomgaThumbnailId
import snd.komga.client.common.Page
import snd.komga.client.common.toParams
import snd.komga.client.library.KomgaLibraryId

class HttpReadListClient internal constructor(private val ktor: HttpClient) : KomgaReadListClient {
    override suspend fun getAll(
        search: String?,
        libraryIds: List<KomgaLibraryId>?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaReadList> {
        return ktor.get("api/v1/readlists") {
            url.parameters.apply {
                search?.let { append("search", it) }
                libraryIds?.let { ids -> if (ids.isNotEmpty()) append("library_id", ids.joinToString()) }
                pageRequest?.let { appendAll(it.toParams()) }
            }
        }.body()
    }

    override suspend fun getOne(id: KomgaReadListId): KomgaReadList {
        return ktor.get("api/v1/readlists/$id").body()
    }

    override suspend fun addOne(request: KomgaReadListCreateRequest): KomgaReadList {
        return ktor.post("api/v1/readlists") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun updateOne(id: KomgaReadListId, request: KomgaReadListUpdateRequest) {
        ktor.patch("api/v1/readlists/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun deleteOne(id: KomgaReadListId) {
        ktor.delete("api/v1/readlists/$id")
    }

    override suspend fun getBooksForReadList(
        id: KomgaReadListId,
        query: KomgaReadListQuery?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaBook> {
        return ktor.get("api/v1/readlists/$id/books") {
            url.parameters.apply {
                query?.libraryIds?.let { append("library_id", it.joinToString()) }
                query?.readStatus?.let { append("read_status", it.joinToString()) }
                query?.tags?.let { append("tag", it.joinToString()) }
                query?.mediaStatus?.let { append("media_status", it.joinToString()) }
                query?.authors?.let { authors ->
                    append("authors", authors.joinToString { "${it.name},${it.role}" })
                }
                query?.deleted?.let { append("deleted", it.toString()) }
                pageRequest?.let { appendAll(it.toParams()) }
            }
        }.body()
    }

    override suspend fun getReadListThumbnails(readListId: KomgaReadListId): List<KomgaReadListThumbnail> {
        return ktor.get("api/v1/readlists/$readListId/thumbnails").body()
    }

    override suspend fun uploadReadListThumbnail(
        readListId: KomgaReadListId,
        file: ByteArray,
        filename: String,
        selected: Boolean
    ): KomgaReadListThumbnail {
        return ktor.post("api/v1/readlists/$readListId/thumbnails") {
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

    override suspend fun selectReadListThumbnail(readListId: KomgaReadListId, thumbnailId: KomgaThumbnailId) {
        ktor.put("api/v1/readlists/$readListId/thumbnails/$thumbnailId/selected")
    }

    override suspend fun deleteReadListThumbnail(readListId: KomgaReadListId, thumbnailId: KomgaThumbnailId) {
        ktor.delete("api/v1/readlists/$readListId/thumbnails/$thumbnailId")
    }

    override suspend fun getBookSiblingNext(readListId: KomgaReadListId, bookId: KomgaBookId): KomgaBook {
        return ktor.get("api/v1/readlists/$readListId/books/$bookId/next").body()
    }

    override suspend fun getBookSiblingPrevious(readListId: KomgaReadListId, bookId: KomgaBookId): KomgaBook {
        return ktor.get("api/v1/readlists/$readListId/books/$bookId/previous").body()
    }
}