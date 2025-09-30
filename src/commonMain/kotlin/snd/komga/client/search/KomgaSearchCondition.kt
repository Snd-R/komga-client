package snd.komga.client.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import snd.komga.client.book.KomgaMediaStatus
import snd.komga.client.book.KomgaReadStatus
import snd.komga.client.collection.KomgaCollectionId
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.readlist.KomgaReadListId
import snd.komga.client.series.KomgaSeriesId
import snd.komga.client.series.KomgaSeriesStatus

class KomgaSearchCondition {
    @Serializable
    sealed interface BookCondition

    @Serializable
    sealed interface SeriesCondition

    @Serializable
    data object NoopCondition : BookCondition, SeriesCondition

    @Serializable
    data class AnyOfBook(
        @SerialName("anyOf")
        val conditions: List<BookCondition>,
    ) : BookCondition {
        constructor(vararg args: BookCondition) : this(args.toList())
    }

    @Serializable
    data class AllOfBook(
        @SerialName("allOf")
        val conditions: List<BookCondition>,
    ) : BookCondition {
        constructor(vararg args: BookCondition) : this(args.toList())
    }

    @Serializable
    data class AnyOfSeries(
        @SerialName("anyOf")
        val conditions: List<SeriesCondition>,
    ) : SeriesCondition {
        constructor(vararg args: SeriesCondition) : this(args.toList())
    }

    @Serializable
    data class AllOfSeries(
        @SerialName("allOf")
        val conditions: List<SeriesCondition>,
    ) : SeriesCondition {
        constructor(vararg args: SeriesCondition) : this(args.toList())
    }

    @Serializable
    data class LibraryId(
        @SerialName("libraryId")
        val operator: KomgaSearchOperator.Equality<KomgaLibraryId>,
    ) : BookCondition,
        SeriesCondition

    @Serializable
    data class CollectionId(
        @SerialName("collectionId")
        val operator: KomgaSearchOperator.Equality<KomgaCollectionId>,
    ) : SeriesCondition

    @Serializable
    data class ReadListId(
        @SerialName("readListId")
        val operator: KomgaSearchOperator.Equality<KomgaReadListId>,
    ) : BookCondition

    @Serializable
    data class SeriesId(
        @SerialName("seriesId")
        val operator: KomgaSearchOperator.Equality<KomgaSeriesId>,
    ) : BookCondition

    @Serializable
    data class Deleted(
        @SerialName("deleted")
        val operator: KomgaSearchOperator.Boolean,
    ) : BookCondition,
        SeriesCondition

    @Serializable
    data class Complete(
        @SerialName("complete")
        val operator: KomgaSearchOperator.Boolean,
    ) : SeriesCondition

    @Serializable
    data class OneShot(
        @SerialName("oneShot")
        val operator: KomgaSearchOperator.Boolean,
    ) : BookCondition,
        SeriesCondition

    @Serializable
    data class Title(
        @SerialName("title")
        val operator: KomgaSearchOperator.StringOp,
    ) : BookCondition,
        SeriesCondition

    @Serializable
    data class TitleSort(
        @SerialName("titleSort")
        val operator: KomgaSearchOperator.StringOp,
    ) : SeriesCondition

    @Serializable
    data class ReleaseDate(
        @SerialName("releaseDate")
        val operator: KomgaSearchOperator.Date,
    ) : BookCondition,
        SeriesCondition

    @Serializable
    data class NumberSort(
        @SerialName("numberSort")
        val operator: KomgaSearchOperator.Numeric<Float>,
    ) : BookCondition

    @Serializable
    data class Tag(
        @SerialName("tag")
        val operator: KomgaSearchOperator.EqualityNullable<String>,
    ) : BookCondition,
        SeriesCondition

    @Serializable
    data class SharingLabel(
        @SerialName("sharingLabel")
        val operator: KomgaSearchOperator.EqualityNullable<String>,
    ) : SeriesCondition

    @Serializable
    data class Publisher(
        @SerialName("publisher")
        val operator: KomgaSearchOperator.Equality<String>,
    ) : SeriesCondition

    @Serializable
    data class Language(
        @SerialName("language")
        val operator: KomgaSearchOperator.Equality<String>,
    ) : SeriesCondition

    @Serializable
    data class Genre(
        @SerialName("genre")
        val operator: KomgaSearchOperator.EqualityNullable<String>,
    ) : SeriesCondition

    @Serializable
    data class AgeRating(
        @SerialName("ageRating")
        val operator: KomgaSearchOperator.NumericNullable<Int>,
    ) : SeriesCondition

    @Serializable
    data class ReadStatus(
        @SerialName("readStatus")
        val operator: KomgaSearchOperator.Equality<KomgaReadStatus>,
    ) : BookCondition,
        SeriesCondition

    @Serializable
    data class MediaStatus(
        @SerialName("mediaStatus")
        val operator: KomgaSearchOperator.Equality<KomgaMediaStatus>,
    ) : BookCondition

    @Serializable
    data class SeriesStatus(
        @SerialName("seriesStatus")
        val operator: KomgaSearchOperator.Equality<KomgaSeriesStatus>,
    ) : SeriesCondition

    @Serializable
    data class MediaProfile(
        @SerialName("mediaProfile")
        val operator: KomgaSearchOperator.Equality<snd.komga.client.book.MediaProfile>,
    ) : BookCondition

    @Serializable
    data class Author(
        @SerialName("author")
        val operator: KomgaSearchOperator.Equality<AuthorMatch>,
    ) : BookCondition,
        SeriesCondition

    @Serializable
    data class AuthorMatch(
        val name: String? = null,
        val role: String? = null,
    )

    @Serializable
    data class Poster(
        @SerialName("poster")
        val operator: KomgaSearchOperator.Equality<PosterMatch>,
    ) : BookCondition

    @Serializable
    data class PosterMatch(
        val type: Type? = null,
        val selected: Boolean? = null,
    ) {
        enum class Type {
            GENERATED,
            SIDECAR,
            USER_UPLOADED,
        }
    }

}