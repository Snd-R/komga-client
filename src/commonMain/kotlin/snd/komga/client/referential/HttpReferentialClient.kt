package snd.komga.client.referential

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import snd.komga.client.collection.KomgaCollectionId
import snd.komga.client.common.KomgaAuthor
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.Page
import snd.komga.client.common.toParams
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.readlist.KomgaReadListId
import snd.komga.client.series.KomgaSeriesId

class HttpReferentialClient internal constructor(private val ktor: HttpClient) : KomgaReferentialClient {
    override suspend fun getAuthors(
        search: String?,
        role: String?,
        libraryId: KomgaLibraryId?,
        collectionId: KomgaCollectionId?,
        seriesId: KomgaSeriesId?,
        readListId: KomgaReadListId?,
        pageRequest: KomgaPageRequest?,
    ): Page<KomgaAuthor> {
        return ktor.get("api/v2/authors") {
            url.parameters.apply {
                search?.let { append("search", it) }
                role?.let { append("role", it) }
                libraryId?.let { append("library_id", it.value) }
                collectionId?.let { append("collection_id", it.value) }
                seriesId?.let { append("series_id", it.value) }
                readListId?.let { append("readlist_id", it.value) }
                pageRequest?.let { appendAll(it.toParams()) }
            }
        }.body()
    }

    override suspend fun getAuthorsNames(search: String?): List<String> {
        return ktor.get("api/v1/authors/names") {
            url.parameters.apply {
                search?.let { append("search", it) }
            }
        }.body()
    }

    override suspend fun getAuthorsRoles(): List<String> {
        return ktor.get("api/v1/authors/roles").body()
    }

    override suspend fun getGenres(
        libraryId: KomgaLibraryId?,
        collectionId: KomgaCollectionId?,
    ): List<String> {
        return ktor.get("api/v1/genres") {
            url.parameters.apply {
                libraryId?.let { append("library_id", it.value) }
                collectionId?.let { append("collection_id", it.value) }
            }
        }.body()
    }

    override suspend fun getSharingLabels(
        libraryId: KomgaLibraryId?,
        collectionId: KomgaCollectionId?
    ): List<String> {
        return ktor.get("api/v1/sharing-labels") {
            url.parameters.apply {
                libraryId?.let { append("library_id", it.value) }
                collectionId?.let { append("collection_id", it.value) }
            }
        }.body()
    }

    override suspend fun getTags(
        libraryId: KomgaLibraryId?,
        collectionId: KomgaCollectionId?
    ): List<String> {
        return ktor.get("api/v1/tags") {
            url.parameters.apply {
                libraryId?.let { append("library_id", it.value) }
                collectionId?.let { append("collection_id", it.value) }
            }
        }.body()
    }

    override suspend fun getBookTags(
        seriesId: KomgaSeriesId?,
        readListId: KomgaReadListId?
    ): List<String> {
        return ktor.get("api/v1/tags/book") {
            url.parameters.apply {
                seriesId?.let { append("series_id", it.value) }
                readListId?.let { append("readlist_id", it.value) }
            }
        }.body()
    }

    override suspend fun getSeriesTags(
        libraryId: KomgaLibraryId?,
        collectionId: KomgaCollectionId?
    ): List<String> {
        return ktor.get("api/v1/tags/series") {
            url.parameters.apply {
                libraryId?.let { append("library_id", it.value) }
                collectionId?.let { append("collection_id", it.value) }
            }
        }.body()
    }

    override suspend fun getLanguages(
        libraryId: KomgaLibraryId?,
        collectionId: KomgaCollectionId?
    ): List<String> {
        return ktor.get("api/v1/languages") {
            url.parameters.apply {
                libraryId?.let { append("library_id", it.value) }
                collectionId?.let { append("collection_id", it.value) }
            }
        }.body()
    }

    override suspend fun getPublishers(
        libraryId: KomgaLibraryId?,
        collectionId: KomgaCollectionId?
    ): List<String> {
        return ktor.get("api/v1/publishers") {
            url.parameters.apply {
                libraryId?.let { append("library_id", it.value) }
                collectionId?.let { append("collection_id", it.value) }
            }
        }.body()
    }

    override suspend fun getAgeRatings(
        libraryId: KomgaLibraryId?,
        collectionId: KomgaCollectionId?
    ): List<String> {
        return ktor.get("api/v1/age-ratings") {
            url.parameters.apply {
                libraryId?.let { append("library_id", it.value) }
                collectionId?.let { append("collection_id", it.value) }
            }
        }.body()
    }

    override suspend fun getSeriesReleaseDates(
        libraryId: KomgaLibraryId?,
        collectionId: KomgaCollectionId?
    ): List<String> {
        return ktor.get("api/v1/series/release-dates") {
            url.parameters.apply {
                libraryId?.let { append("library_id", it.value) }
                collectionId?.let { append("collection_id", it.value) }
            }
        }.body()
    }
}
