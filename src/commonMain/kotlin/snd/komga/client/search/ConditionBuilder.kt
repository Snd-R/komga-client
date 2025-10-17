package snd.komga.client.search

import snd.komga.client.book.KomgaMediaStatus
import snd.komga.client.book.KomgaReadStatus
import snd.komga.client.book.MediaProfile
import snd.komga.client.collection.KomgaCollectionId
import snd.komga.client.library.KomgaLibraryId
import snd.komga.client.readlist.KomgaReadListId
import snd.komga.client.search.KomgaSearchCondition.AuthorMatch
import snd.komga.client.search.KomgaSearchCondition.SeriesCondition
import snd.komga.client.series.KomgaSeriesId
import snd.komga.client.series.KomgaSeriesStatus

@DslMarker
annotation class ConditionDsl


@ConditionDsl
sealed interface SeriesDsl {
    fun toSeriesCondition(): SeriesCondition
}

@ConditionDsl
sealed interface BookDsl {
    fun toBookCondition(): KomgaSearchCondition.BookCondition
}


fun anyOfSeries(block: SeriesConditionBuilder.() -> Unit): SeriesConditionBuilder {
    val condition = SeriesConditionBuilder(MatchType.ANY)
    condition.block()
    return condition
}

fun allOfSeries(block: SeriesConditionBuilder.() -> Unit): SeriesConditionBuilder {
    val condition = SeriesConditionBuilder(MatchType.ALL)
    condition.block()
    return condition
}

fun anyOfBooks(block: BookConditionBuilder.() -> Unit): BookConditionBuilder {
    val condition = BookConditionBuilder(MatchType.ANY)
    condition.block()
    return condition
}

fun allOfBooks(block: BookConditionBuilder.() -> Unit): BookConditionBuilder {
    val condition = BookConditionBuilder(MatchType.ALL)
    condition.block()
    return condition
}

class SeriesConditionBuilder internal constructor(
    internal val conditionMatchType: MatchType = MatchType.ALL
) : SeriesDsl {
    internal val conditions = mutableListOf<SeriesDsl>()

    fun anyOf(block: SeriesConditionBuilder.() -> Unit) {
        val condition = SeriesConditionBuilder(MatchType.ANY)
        condition.block()
        conditions.add(condition)
    }

    fun allOf(block: SeriesConditionBuilder.() -> Unit) {
        val condition = SeriesConditionBuilder(MatchType.ALL)
        condition.block()
        conditions.add(condition)
    }

    fun library(block: LibraryConditionBuilder.() -> KomgaSearchOperator.Equality<KomgaLibraryId>) {
        val condition = LibraryConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun collection(block: CollectionIdConditionBuilder.() -> KomgaSearchOperator.Equality<KomgaCollectionId>) {
        val condition = CollectionIdConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun isDeleted() {
        val condition = DeletedConditionBuilder()
        condition.operator = KomgaSearchOperator.IsTrue
        conditions.add(condition)
    }

    fun isNotDeleted() {
        val condition = DeletedConditionBuilder()
        condition.operator = KomgaSearchOperator.IsFalse
        conditions.add(condition)
    }

    fun isCompleted() {
        val condition = CompleteConditionBuilder()
        condition.operator = KomgaSearchOperator.IsTrue
        conditions.add(condition)
    }

    fun isNotCompleted() {
        val condition = CompleteConditionBuilder()
        condition.operator = KomgaSearchOperator.IsFalse
        conditions.add(condition)
    }

    fun isOneshot() {
        val condition = OneShotConditionBuilder()
        condition.operator = KomgaSearchOperator.IsTrue
        conditions.add(condition)
    }

    fun isNotOneshot() {
        val condition = OneShotConditionBuilder()
        condition.operator = KomgaSearchOperator.IsFalse
        conditions.add(condition)
    }

    fun title(block: TitleConditionBuilder.() -> KomgaSearchOperator.StringOp) {
        val condition = TitleConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun titleSort(block: TitleSortConditionBuilder.() -> KomgaSearchOperator.StringOp) {
        val condition = TitleSortConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun releaseDate(block: ReleaseDateConditionBuilder.() -> KomgaSearchOperator.Date) {
        val condition = ReleaseDateConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun tag(block: TagConditionBuilder.() -> KomgaSearchOperator.EqualityNullable<String>) {
        val condition = TagConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun sharingLabel(block: SharingLabelConditionBuilder.() -> KomgaSearchOperator.EqualityNullable<String>) {
        val condition = SharingLabelConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun publisher(block: PublisherConditionBuilder.() -> KomgaSearchOperator.Equality<String>) {
        val condition = PublisherConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun language(block: LanguageConditionBuilder.() -> KomgaSearchOperator.Equality<String>) {
        val condition = LanguageConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun genre(block: GenreConditionBuilder.() -> KomgaSearchOperator.EqualityNullable<String>) {
        val condition = GenreConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun ageRating(block: AgeRatingConditionBuilder.() -> KomgaSearchOperator.NumericNullable<Int>) {
        val condition = AgeRatingConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun readStatus(block: ReadStatusConditionBuilder.() -> KomgaSearchOperator.Equality<KomgaReadStatus>) {
        val condition = ReadStatusConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun author(block: AuthorConditionBuilder.() -> KomgaSearchOperator.Equality<AuthorMatch>) {
        val condition = AuthorConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun seriesStatus(block: SeriesStatusConditionBuilder.() -> KomgaSearchOperator.Equality<KomgaSeriesStatus>) {
        val condition = SeriesStatusConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    override fun toSeriesCondition(): SeriesCondition {
        return when (conditionMatchType) {
            MatchType.ANY -> KomgaSearchCondition.AnyOfSeries(conditions.map { it.toSeriesCondition() })
            MatchType.ALL -> KomgaSearchCondition.AllOfSeries(conditions.map { it.toSeriesCondition() })
        }
    }
}

class BookConditionBuilder internal constructor(
    internal val conditionMatchType: MatchType = MatchType.ALL
) : BookDsl {
    internal val conditions = mutableListOf<BookDsl>()

    fun anyOf(block: BookConditionBuilder.() -> Unit) {
        val condition = BookConditionBuilder(MatchType.ANY)
        condition.block()
        conditions.add(condition)
    }

    fun allOf(block: BookConditionBuilder.() -> Unit) {
        val condition = BookConditionBuilder(MatchType.ALL)
        condition.block()
        conditions.add(condition)
    }

    fun library(block: LibraryConditionBuilder.() -> KomgaSearchOperator.Equality<KomgaLibraryId>) {
        val condition = LibraryConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun readList(block: ReadListIdConditionBuilder.() -> KomgaSearchOperator.Equality<KomgaReadListId>) {
        val condition = ReadListIdConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun seriesId(block: SeriesIdConditionBuilder.() -> KomgaSearchOperator.Equality<KomgaSeriesId>) {
        val condition = SeriesIdConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun isDeleted() {
        val condition = DeletedConditionBuilder()
        condition.operator = KomgaSearchOperator.IsTrue
        conditions.add(condition)
    }

    fun isNotDeleted() {
        val condition = DeletedConditionBuilder()
        condition.operator = KomgaSearchOperator.IsFalse
        conditions.add(condition)
    }

    fun title(block: TitleConditionBuilder.() -> KomgaSearchOperator.StringOp) {
        val condition = TitleConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun releaseDate(block: ReleaseDateConditionBuilder.() -> KomgaSearchOperator.Date) {
        val condition = ReleaseDateConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun numberSort(block: NumberSortConditionBuilder.() -> KomgaSearchOperator.Numeric<Float>) {
        val condition = NumberSortConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun tag(block: TagConditionBuilder.() -> KomgaSearchOperator.EqualityNullable<String>) {
        val condition = TagConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun readStatus(block: ReadStatusConditionBuilder.() -> KomgaSearchOperator.Equality<KomgaReadStatus>) {
        val condition = ReadStatusConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun mediaStatus(block: MediaStatusConditionBuilder.() -> KomgaSearchOperator.Equality<KomgaMediaStatus>) {
        val condition = MediaStatusConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun mediaProfile(block: MediaProfileConditionBuilder.() -> KomgaSearchOperator.Equality<MediaProfile>) {
        val condition = MediaProfileConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun author(block: AuthorConditionBuilder.() -> KomgaSearchOperator.Equality<AuthorMatch>) {
        val condition = AuthorConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    fun poster(block: PosterConditionBuilder.() -> KomgaSearchOperator.Equality<KomgaSearchCondition.PosterMatch>) {
        val condition = PosterConditionBuilder()
        condition.operator = condition.block()
        conditions.add(condition)
    }

    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return when (conditionMatchType) {
            MatchType.ANY -> KomgaSearchCondition.AnyOfBook(conditions.map { it.toBookCondition() })
            MatchType.ALL -> KomgaSearchCondition.AllOfBook(conditions.map { it.toBookCondition() })
        }
    }
}


class LibraryConditionBuilder internal constructor() : EqualityBuilder<KomgaLibraryId>(), SeriesDsl, BookDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.LibraryId(getValueOrThrow())
    }

    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.LibraryId(getValueOrThrow())
    }
}

class CollectionIdConditionBuilder internal constructor() : EqualityBuilder<KomgaCollectionId>(), SeriesDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.CollectionId(getValueOrThrow())
    }
}

class ReadListIdConditionBuilder internal constructor() : EqualityBuilder<KomgaReadListId>(), BookDsl {
    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.ReadListId(getValueOrThrow())
    }
}

class SeriesIdConditionBuilder internal constructor() : EqualityBuilder<KomgaSeriesId>(), BookDsl {
    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.SeriesId(getValueOrThrow())
    }
}

class DeletedConditionBuilder internal constructor() : BooleanOpBuilder(), SeriesDsl, BookDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.Deleted(getValueOrThrow())
    }

    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.Deleted(getValueOrThrow())
    }
}

class CompleteConditionBuilder internal constructor() : BooleanOpBuilder(), SeriesDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.Complete(getValueOrThrow())
    }
}

class OneShotConditionBuilder internal constructor() : BooleanOpBuilder(), SeriesDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.OneShot(getValueOrThrow())
    }
}

class TitleConditionBuilder internal constructor() : StringOpBuilder(), SeriesDsl, BookDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.Title(getValueOrThrow())
    }

    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.Title(getValueOrThrow())
    }
}

class TitleSortConditionBuilder internal constructor() : StringOpBuilder(), SeriesDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.TitleSort(getValueOrThrow())
    }
}

class ReleaseDateConditionBuilder internal constructor() : DateOpBuilder(), SeriesDsl, BookDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.ReleaseDate(getValueOrThrow())
    }

    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.ReleaseDate(getValueOrThrow())
    }
}

class NumberSortConditionBuilder internal constructor() : NumericOpBuilder<Float>(), BookDsl {
    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.NumberSort(getValueOrThrow())
    }
}

class TagConditionBuilder internal constructor() : EqualityNullableBuilder<String>(), SeriesDsl, BookDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.Tag(getValueOrThrow())
    }

    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.Tag(getValueOrThrow())
    }
}

class SharingLabelConditionBuilder internal constructor() : EqualityNullableBuilder<String>(), SeriesDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.SharingLabel(getValueOrThrow())
    }
}

class PublisherConditionBuilder internal constructor() : EqualityBuilder<String>(), SeriesDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.Publisher(getValueOrThrow())
    }
}

class LanguageConditionBuilder internal constructor() : EqualityBuilder<String>(), SeriesDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.Language(getValueOrThrow())
    }
}

class GenreConditionBuilder internal constructor() : EqualityNullableBuilder<String>(), SeriesDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.Genre(getValueOrThrow())
    }
}

class AgeRatingConditionBuilder internal constructor() : NumericNullableOpBuilder<Int>(), SeriesDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.AgeRating(getValueOrThrow())
    }
}

class ReadStatusConditionBuilder internal constructor() : EqualityBuilder<KomgaReadStatus>(), SeriesDsl, BookDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.ReadStatus(getValueOrThrow())
    }

    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.ReadStatus(getValueOrThrow())
    }
}

class MediaStatusConditionBuilder internal constructor() : EqualityBuilder<KomgaMediaStatus>(), BookDsl {
    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.MediaStatus(getValueOrThrow())
    }
}

class SeriesStatusConditionBuilder internal constructor() : EqualityBuilder<KomgaSeriesStatus>(), SeriesDsl {
    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.SeriesStatus(getValueOrThrow())
    }
}

class AuthorConditionBuilder internal constructor() : EqualityBuilder<AuthorMatch>(), SeriesDsl, BookDsl {

    override fun toSeriesCondition(): SeriesCondition {
        return KomgaSearchCondition.Author(getValueOrThrow())
    }

    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.Author(getValueOrThrow())
    }
}

class MediaProfileConditionBuilder internal constructor() : EqualityBuilder<MediaProfile>(), BookDsl {
    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.MediaProfile(getValueOrThrow())
    }
}

class PosterConditionBuilder internal constructor() : EqualityBuilder<KomgaSearchCondition.PosterMatch>(), BookDsl {
    override fun toBookCondition(): KomgaSearchCondition.BookCondition {
        return KomgaSearchCondition.Poster(getValueOrThrow())
    }
}


enum class MatchType() { ANY, ALL }
