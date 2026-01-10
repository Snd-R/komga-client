package snd.komga.client.series

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import snd.komga.client.book.KomgaBook
import snd.komga.client.book.KomgaMediaStatus
import snd.komga.client.book.KomgaReadStatus
import snd.komga.client.collection.KomgaCollection
import snd.komga.client.common.*
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.search.SeriesConditionBuilder

class HttpSeriesClient(private val ktor: HttpClient) : KomgaSeriesClient {

    override suspend fun getOneSeries(seriesId: KomgaSeriesId): KomgaSeries {
        return ktor.get("api/v1/series/$seriesId").body()
    }

    @Deprecated("use getSeriesList()")
    override suspend fun getAllSeries(
        query: KomgaSeriesQuery?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaSeries> {
        return ktor.get("api/v1/series") {
            url.parameters.apply {
                query?.searchTerm?.let { append("search", it) }
                query?.searchRegex?.let { append("search_regex", "${it.regex},${it.searchField}.}") }
                query?.libraryIds?.let {
                    if (it.isNotEmpty()) append("library_id", it.joinToString(","))
                }
                query?.collectionIds?.let {
                    if (it.isNotEmpty()) append("collection_id", it.joinToString(","))
                }
                query?.status?.let {
                    if (it.isNotEmpty()) append("status", it.joinToString(","))
                }
                query?.readStatus?.let {
                    if (it.isNotEmpty()) append("read_status", it.joinToString(","))
                }
                query?.publishers?.let {
                    if (it.isNotEmpty()) append("publisher", it.joinToString(","))
                }
                query?.languages?.let {
                    if (it.isNotEmpty()) append("language", it.joinToString(","))
                }
                query?.genres?.let {
                    if (it.isNotEmpty()) append("genre", it.joinToString(","))
                }
                query?.tags?.let {
                    if (it.isNotEmpty()) append("tag", it.joinToString(","))
                }
                query?.ageRatings?.let {
                    if (it.isNotEmpty()) append("age_rating", it.joinToString(","))
                }
                query?.releaseYears?.let {
                    if (it.isNotEmpty()) append("release_year", it.joinToString(","))
                }
                query?.sharingLabels?.let {
                    if (it.isNotEmpty()) append("sharing_label", it.joinToString(","))
                }
                query?.authors?.let { authors ->
                    if (authors.isNotEmpty())
                        authors.forEach { author -> append("author", "${author.name},${author.role}") }
                }
                query?.deleted?.let { append("deleted", it.toString()) }
                query?.complete?.let { append("complete", it.toString()) }
                query?.oneshot?.let { append("oneshot", it.toString()) }
                pageRequest?.let { appendAll(it.toParams()) }
            }
        }.body()
    }

    override suspend fun getSeriesList(
        conditionBuilder: SeriesConditionBuilder,
        fulltextSearch: String?,
        pageRequest: KomgaPageRequest?
    ): Page<KomgaSeries> {
        return getSeriesList(
            search = KomgaSeriesSearch(
                condition = conditionBuilder.toSeriesCondition(),
                fullTextSearch = fulltextSearch
            ),
            pageRequest = pageRequest
        )
    }

    override suspend fun getSeriesList(
        search: KomgaSeriesSearch,
        pageRequest: KomgaPageRequest?
    ): Page<KomgaSeries> {
        return ktor.post("api/v1/series/list") {
            pageRequest?.let { url.parameters.appendAll(it.toParams()) }
            contentType(ContentType.Application.Json)
            setBody(search)
        }.body()
    }

    @Deprecated("use getBookist() from book client")
    override suspend fun getAllBooksBySeries(
        seriesId: KomgaSeriesId,
        mediaStatus: List<KomgaMediaStatus>?,
        readStatus: List<KomgaReadStatus>?,
        tag: List<String>?,
        authors: List<KomgaAuthor>?,
        deleted: Boolean?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaBook> {
        return ktor.get("api/v1/series/$seriesId/books") {
            url.parameters.apply {
                mediaStatus?.let {
                    if (it.isNotEmpty()) append("media_status", it.joinToString(","))
                }
                readStatus?.let {
                    if (it.isNotEmpty()) append("read_status", it.joinToString(","))
                }
                tag?.let {
                    if (it.isNotEmpty()) append("tag", it.joinToString(","))
                }
                authors?.let { authors ->
                    if (authors.isNotEmpty())
                        authors.forEach { author -> append("author", "${author.name},${author.role}") }
                }

                deleted?.let { append("deleted", it.toString()) }
                pageRequest?.let { appendAll(it.toParams()) }
            }
        }.body()
    }

    override suspend fun getNewSeries(
        libraryIds: List<KomgaLibraryId>?,
        oneshot: Boolean?,
        deleted: Boolean?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaSeries> {
        return ktor.get("api/v1/series/new") {
            url.parameters.apply {
                libraryIds?.let { append("library_id", it.joinToString(",")) }
                oneshot?.let { append("oneshot", oneshot.toString()) }
                deleted?.let { append("deleted", deleted.toString()) }
                pageRequest?.let { appendAll(it.toParams()) }
            }
        }.body()
    }

    override suspend fun getUpdatedSeries(
        libraryIds: List<KomgaLibraryId>?,
        oneshot: Boolean?,
        deleted: Boolean?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaSeries> {
        return ktor.get("api/v1/series/updated") {
            url.parameters.apply {
                libraryIds?.let { append("library_id", it.joinToString(",")) }
                deleted?.let { append("deleted", deleted.toString()) }
                oneshot?.let { append("oneshot", oneshot.toString()) }
                pageRequest?.let { appendAll(it.toParams()) }
            }
        }.body()
    }

    override suspend fun analyze(seriesId: KomgaSeriesId) {
        ktor.post("api/v1/series/$seriesId/analyze")
    }

    override suspend fun refreshMetadata(seriesId: KomgaSeriesId) {
        ktor.post("api/v1/series/$seriesId/metadata/refresh")
    }

    override suspend fun markAsRead(seriesId: KomgaSeriesId) {
        ktor.post("api/v1/series/$seriesId/read-progress")
    }

    override suspend fun markAsUnread(seriesId: KomgaSeriesId) {
        ktor.delete("api/v1/series/$seriesId/read-progress")
    }

    override suspend fun delete(seriesId: KomgaSeriesId) {
        ktor.delete("api/v1/series/$seriesId/file")
    }

    override suspend fun update(seriesId: KomgaSeriesId, request: KomgaSeriesMetadataUpdateRequest) {
        ktor.patch("api/v1/series/$seriesId/metadata") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun getDefaultThumbnail(seriesId: KomgaSeriesId): ByteArray? {
        return try {
            ktor.get("api/v1/series/$seriesId/thumbnail") {
                accept(ContentType.Any)
            }.body()
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) null
            else throw e
        }
    }

    override suspend fun getThumbnail(seriesId: KomgaSeriesId, thumbnailId: KomgaThumbnailId): ByteArray {
        return ktor.get("api/v1/series/$seriesId/thumbnails/$thumbnailId") {
            accept(ContentType.Any)
        }.body()
    }

    override suspend fun getThumbnails(seriesId: KomgaSeriesId): List<KomgaSeriesThumbnail> {
        return ktor.get("api/v1/series/$seriesId/thumbnails").body()
    }

    override suspend fun uploadThumbnail(
        seriesId: KomgaSeriesId,
        file: ByteArray,
        filename: String,
        selected: Boolean
    ): KomgaSeriesThumbnail {
        return ktor.post("api/v1/series/$seriesId/thumbnails") {
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

    override suspend fun selectThumbnail(seriesId: KomgaSeriesId, thumbnailId: KomgaThumbnailId) {
        ktor.put("api/v1/series/$seriesId/thumbnails/$thumbnailId/selected")
    }

    override suspend fun deleteThumbnail(seriesId: KomgaSeriesId, thumbnailId: KomgaThumbnailId) {
        ktor.delete("api/v1/series/$seriesId/thumbnails/$thumbnailId")
    }

    override suspend fun getAllCollectionsBySeries(seriesId: KomgaSeriesId): List<KomgaCollection> {
        return ktor.get("api/v1/series/$seriesId/collections").body()
    }
}