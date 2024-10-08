package snd.komga.client.collection

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.KomgaThumbnailId
import snd.komga.client.common.Page
import snd.komga.client.common.toParams
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.series.KomgaSeries

class HttpCollectionClient internal constructor(private val ktor: HttpClient) : KomgaCollectionClient {

    override suspend fun getAll(
        search: String?,
        libraryIds: List<KomgaLibraryId>?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaCollection> {
        return ktor.get("api/v1/collections") {
            url.parameters.apply {
                search?.let { append("search", it) }
                libraryIds?.let { ids -> if (ids.isNotEmpty()) append("library_id", ids.joinToString()) }
                pageRequest?.let { appendAll(it.toParams()) }
            }
        }.body()
    }

    override suspend fun getOne(id: KomgaCollectionId): KomgaCollection {
        return ktor.get("api/v1/collections/$id").body()
    }

    override suspend fun addOne(request: KomgaCollectionCreateRequest): KomgaCollection {
        return ktor.post("api/v1/collections") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun updateOne(id: KomgaCollectionId, request: KomgaCollectionUpdateRequest) {
        ktor.patch("api/v1/collections/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun deleteOne(id: KomgaCollectionId) {
        ktor.delete("api/v1/collections/$id")
    }

    override suspend fun getSeriesForCollection(
        id: KomgaCollectionId,
        query: KomgaCollectionQuery?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaSeries> {
        return ktor.get("api/v1/collections/$id/series") {
            url.parameters.apply {
                query?.libraryIds?.let { append("library_id", it.joinToString()) }
                query?.readStatus?.let { append("read_status", it.joinToString()) }
                query?.status?.let { append("status", it.joinToString()) }
                query?.languages?.let { append("language", it.joinToString()) }
                query?.publishers?.let { append("publisher", it.joinToString()) }
                query?.tags?.let { append("tag", it.joinToString()) }
                query?.genres?.let { append("genre", it.joinToString()) }
                query?.ageRatings?.let { append("age_rating", it.joinToString()) }
                query?.releaseYears?.let { append("release_year", it.joinToString()) }
                query?.authors?.let { authors ->
                    append("authors", authors.joinToString { "${it.name},${it.role}" })
                }
                query?.complete?.let { append("complete", it.toString()) }
                query?.deleted?.let { append("deleted", it.toString()) }
                pageRequest?.let { appendAll(it.toParams()) }
            }
        }.body()

    }

    override suspend fun getCollectionThumbnails(collectionId: KomgaCollectionId): List<KomgaCollectionThumbnail> {
        return ktor.get("api/v1/collections/$collectionId/thumbnails").body()
    }

    override suspend fun uploadCollectionThumbnail(
        collectionId: KomgaCollectionId,
        file: ByteArray,
        filename: String,
        selected: Boolean
    ): KomgaCollectionThumbnail {
        return ktor.post("api/v1/collections/$collectionId/thumbnails") {
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

    override suspend fun selectCollectionThumbnail(collectionId: KomgaCollectionId, thumbnailId: KomgaThumbnailId) {
        ktor.put("api/v1/collections/$collectionId/thumbnails/$thumbnailId/selected")
    }

    override suspend fun deleteCollectionThumbnail(collectionId: KomgaCollectionId, thumbnailId: KomgaThumbnailId) {
        ktor.delete("api/v1/collections/$collectionId/thumbnails/$thumbnailId")
    }

}