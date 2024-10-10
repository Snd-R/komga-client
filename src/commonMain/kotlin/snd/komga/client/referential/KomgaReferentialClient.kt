package snd.komga.client.referential

import snd.komga.client.collection.KomgaCollectionId
import snd.komga.client.common.KomgaAuthor
import snd.komga.client.common.KomgaPageRequest
import snd.komga.client.common.Page
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.readlist.KomgaReadListId
import snd.komga.client.series.KomgaSeriesId

interface KomgaReferentialClient {

    suspend fun getAuthors(
        search: String? = null,
        role: String? = null,
        libraryId: KomgaLibraryId? = null,
        collectionId: KomgaCollectionId? = null,
        seriesId: KomgaSeriesId? = null,
        readListId: KomgaReadListId? = null,
        pageRequest: KomgaPageRequest? = null,
    ): Page<KomgaAuthor>

    suspend fun getAuthorsNames(search: String? = null): List<String>

    suspend fun getAuthorsRoles(): List<String>

    suspend fun getGenres(
        libraryId: KomgaLibraryId? = null,
        collectionId: KomgaCollectionId? = null
    ): List<String>

    suspend fun getSharingLabels(
        libraryId: KomgaLibraryId? = null,
        collectionId: KomgaCollectionId? = null
    ): List<String>

    suspend fun getTags(
        libraryId: KomgaLibraryId? = null,
        collectionId: KomgaCollectionId? = null
    ): List<String>

    suspend fun getBookTags(
        seriesId: KomgaSeriesId? = null,
        readListId: KomgaReadListId? = null
    ): List<String>

    suspend fun getSeriesTags(
        libraryId: KomgaLibraryId? = null,
        collectionId: KomgaCollectionId? = null
    ): List<String>

    suspend fun getLanguages(
        libraryId: KomgaLibraryId? = null,
        collectionId: KomgaCollectionId? = null
    ): List<String>

    suspend fun getPublishers(
        libraryId: KomgaLibraryId? = null,
        collectionId: KomgaCollectionId? = null
    ): List<String>

    suspend fun getAgeRatings(
        libraryId: KomgaLibraryId? = null,
        collectionId: KomgaCollectionId? = null
    ): List<String>

    suspend fun getSeriesReleaseDates(
        libraryId: KomgaLibraryId? = null,
        collectionId: KomgaCollectionId? = null
    ): List<String>
}