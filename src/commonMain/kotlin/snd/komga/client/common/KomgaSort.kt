package snd.komga.client.common

import kotlinx.serialization.Serializable
import snd.komga.client.common.KomgaSort.Direction.ASC
import snd.komga.client.common.KomgaSort.Direction.DESC

private const val seriesTitleSort = "metadata.titleSort"
private const val seriesCreated = "created"
private const val seriesLastModified = "lastModified"
private const val seriesReleaseDate = "booksMetadata.releaseDate"
private const val seriesFolderName = "name"
private const val seriesBooksCount = "booksCount"

private const val bookCreated = "createdDate"
private const val bookSeriesTitle = "series"
private const val bookFilename = "name"
private const val bookFileSize = "fileSize"
private const val bookLastModified = "lastModified"
private const val bookNumber = "metadata.numberSort"
private const val bookReadDate = "readProgress.readDate"
private const val bookTitle = "metadata.title"
private const val bookReleaseDate = "metadata.releaseDate"
private const val bookPageCount = "metadata.pagesCount"

private const val userDateTime = "dateTime"


@Serializable
sealed interface KomgaSort {
    val orders: List<Order>

    enum class Direction {
        ASC, DESC;
    }

    @Serializable
    data class Order(
        val property: String,
        val direction: Direction,
    )

    @Serializable
    data object Unsorted : KomgaSort {
        override val orders: List<Order> = emptyList()
    }

    @Serializable
    class KomgaSeriesSort(override val orders: List<Order>) : KomgaSort {


        fun and(sort: KomgaSeriesSort): KomgaSeriesSort {
            val newOrders = orders.toMutableList()
            sort.orders.forEach { newOrders.add(it) }

            return KomgaSeriesSort(newOrders)
        }

        fun by(vararg orders: Order): KomgaSeriesSort {
            return KomgaSeriesSort(orders.toList())
        }

        companion object {
            fun byTitle(direction: Direction) = KomgaSeriesSort(listOf(Order(seriesTitleSort, direction)))
            fun byTitleAsc() = KomgaSeriesSort(listOf(Order(seriesTitleSort, ASC)))
            fun byTitleDesc() = KomgaSeriesSort(listOf(Order(seriesTitleSort, DESC)))

            fun byCreatedDate(direction: Direction) = KomgaSeriesSort(listOf(Order(seriesCreated, direction)))
            fun byCreatedDateAsc() = KomgaSeriesSort(listOf(Order(seriesCreated, ASC)))
            fun byCreatedDateDesc() = KomgaSeriesSort(listOf(Order(seriesCreated, DESC)))

            fun byReleaseDate(direction: Direction) = KomgaSeriesSort(listOf(Order(seriesReleaseDate, direction)))
            fun byReleaseDateAsc() = KomgaSeriesSort(listOf(Order(seriesReleaseDate, ASC)))
            fun byReleaseDateDesc() = KomgaSeriesSort(listOf(Order(seriesReleaseDate, DESC)))

            fun byFolderName(direction: Direction) = KomgaSeriesSort(listOf(Order(seriesFolderName, direction)))
            fun byFolderNameAsc() = KomgaSeriesSort(listOf(Order(seriesFolderName, ASC)))
            fun byFolderNameDesc() = KomgaSeriesSort(listOf(Order(seriesFolderName, DESC)))

            fun byBooksCount(direction: Direction) = KomgaSeriesSort(listOf(Order(seriesBooksCount, direction)))
            fun byBooksCountAsc() = KomgaSeriesSort(listOf(Order(seriesBooksCount, ASC)))
            fun byBooksCountDesc() = KomgaSeriesSort(listOf(Order(seriesBooksCount, DESC)))

            fun byLastModifiedDate(direction: Direction) = KomgaSeriesSort(listOf(Order(seriesLastModified, direction)))
            fun byLastModifiedDateAsc() = KomgaSeriesSort(listOf(Order(seriesLastModified, ASC)))
            fun byLastModifiedDateDesc() = KomgaSeriesSort(listOf(Order(seriesLastModified, DESC)))
        }
    }

    @Serializable
    class KomgaBooksSort(override val orders: List<Order>) : KomgaSort {

        fun and(sort: KomgaBooksSort): KomgaBooksSort {
            val newOrders = orders.toMutableList()
            sort.orders.forEach { newOrders.add(it) }

            return KomgaBooksSort(newOrders)
        }

        fun by(vararg orders: Order): KomgaBooksSort {
            return KomgaBooksSort(orders.toList())
        }

        companion object {

            fun byCreatedDate(direction: Direction) = KomgaBooksSort(listOf(Order(bookCreated, direction)))
            fun byCreatedDateAsc() = KomgaBooksSort(listOf(Order(bookCreated, ASC)))
            fun byCreatedDateDesc() = KomgaBooksSort(listOf(Order(bookCreated, DESC)))

            fun byFileName(direction: Direction) = KomgaBooksSort(listOf(Order(bookFilename, direction)))
            fun byFileNameAsc() = KomgaBooksSort(listOf(Order(bookFilename, ASC)))
            fun byFileNameDesc() = KomgaBooksSort(listOf(Order(bookFilename, DESC)))

            fun byLastModifiedDate(direction: Direction) = KomgaBooksSort(listOf(Order(bookLastModified, direction)))
            fun byLastModifiedDateAsc() = KomgaBooksSort(listOf(Order(bookLastModified, ASC)))
            fun byLastModifiedDateDesc() = KomgaBooksSort(listOf(Order(bookLastModified, DESC)))

            fun byNumber(direction: Direction) = KomgaBooksSort(listOf(Order(bookNumber, direction)))
            fun byNumberAsc() = KomgaBooksSort(listOf(Order(bookNumber, ASC)))
            fun byNumberDesc() = KomgaBooksSort(listOf(Order(bookNumber, DESC)))

            fun byReadDate(direction: Direction) = KomgaBooksSort(listOf(Order(bookReadDate, direction)))
            fun byReadDateAsc() = KomgaBooksSort(listOf(Order(bookReadDate, ASC)))
            fun byReadDateDesc() = KomgaBooksSort(listOf(Order(bookReadDate, DESC)))

            fun byReleaseDate(direction: Direction) = KomgaBooksSort(listOf(Order(bookReleaseDate, direction)))
            fun byReleaseDateAsc() = KomgaBooksSort(listOf(Order(bookReleaseDate, ASC)))
            fun byReleaseDateDesc() = KomgaBooksSort(listOf(Order(bookReleaseDate, DESC)))

            fun bySeriesTitle(direction: Direction) = KomgaBooksSort(listOf(Order(bookSeriesTitle, direction)))
            fun bySeriesTitleAsc() = KomgaBooksSort(listOf(Order(bookSeriesTitle, ASC)))
            fun bySeriesTitleDesc() = KomgaBooksSort(listOf(Order(bookSeriesTitle, DESC)))

            fun byTitle(direction: Direction) = KomgaBooksSort(listOf(Order(bookTitle, direction)))
            fun byTitleAsc() = KomgaBooksSort(listOf(Order(bookTitle, ASC)))
            fun byTitleDesc() = KomgaBooksSort(listOf(Order(bookTitle, DESC)))

            fun byPagesCount(direction: Direction) = KomgaBooksSort(listOf(Order(bookPageCount, direction)))
            fun byPagesCountAsc() = KomgaBooksSort(listOf(Order(bookPageCount, ASC)))
            fun byPagesCountDesc() = KomgaBooksSort(listOf(Order(bookPageCount, DESC)))
        }
    }

    class KomgaUserSort(override val orders: List<Order>) : KomgaSort {

        fun and(sort: KomgaUserSort): KomgaUserSort {
            val newOrders = orders.toMutableList()
            sort.orders.forEach { newOrders.add(it) }

            return KomgaUserSort(newOrders)
        }

        fun by(vararg orders: Order): KomgaUserSort {
            return KomgaUserSort(orders.toList())
        }

        companion object {

            fun byDateTimeAsc(): KomgaUserSort {
                return KomgaUserSort(listOf(Order(userDateTime, ASC)))
            }

            fun byDateTimeDesc(): KomgaUserSort {
                return KomgaUserSort(listOf(Order(userDateTime, DESC)))
            }

        }
    }
}
