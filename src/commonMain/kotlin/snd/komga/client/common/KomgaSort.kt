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
private const val bookFilename = "name"
private const val bookFileSize = "fileSize"
private const val bookLastModified = "lastModified"
private const val bookNumber = "metadata.numberSort"
private const val bookReadDate = "readProgress.readDate"
private const val bookReleaseDate = "metadata.releaseDate"

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
            fun byTitleAsc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesTitleSort, ASC)))
            }

            fun byTitleDesc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesTitleSort, DESC)))
            }

            fun byCreatedDateAsc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesCreated, ASC)))
            }

            fun byCreatedDateDesc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesCreated, DESC)))
            }

            fun byReleaseDateAsc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesReleaseDate, ASC)))
            }

            fun byReleaseDateDesc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesReleaseDate, DESC)))
            }

            fun byFolderNameAsc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesFolderName, ASC)))
            }

            fun byFolderNameDesc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesFolderName, DESC)))
            }

            fun byBooksCountAsc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesBooksCount, ASC)))
            }

            fun byBooksCountDesc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesBooksCount, DESC)))
            }

            fun byLastModifiedDateAsc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesLastModified, ASC)))
            }

            fun byLastModifiedDateDesc(): KomgaSeriesSort {
                return KomgaSeriesSort(listOf(Order(seriesLastModified, DESC)))
            }
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

            fun byCreatedDateAsc(): KomgaBooksSort {
                return KomgaBooksSort(listOf(Order(bookCreated, ASC)))
            }

            fun byCreatedDateDesc(): KomgaBooksSort {
                return KomgaBooksSort(listOf(Order(bookCreated, DESC)))
            }

            fun byFileNameAsc(): KomgaBooksSort {
                return KomgaBooksSort(listOf(Order(bookFilename, ASC)))
            }

            fun byFileNameDesc(): KomgaBooksSort {
                return KomgaBooksSort(listOf(Order(bookFilename, DESC)))
            }

            fun byLastModifiedDateDesc(): KomgaBooksSort {
                return KomgaBooksSort(listOf(Order(bookLastModified, DESC)))
            }

            fun byNumberAsc(): KomgaBooksSort {
                return KomgaBooksSort(listOf(Order(bookNumber, ASC)))
            }

            fun byNumberDesc(): KomgaBooksSort {
                return KomgaBooksSort(listOf(Order(bookNumber, DESC)))
            }

            fun byReadDateDesc(): KomgaBooksSort {
                return KomgaBooksSort(listOf(Order(bookReadDate, DESC)))
            }

            fun byReleaseDateAsc(): KomgaBooksSort {
                return KomgaBooksSort(listOf(Order(bookReleaseDate, ASC)))
            }

            fun byReleaseDateDesc(): KomgaBooksSort {
                return KomgaBooksSort(listOf(Order(bookReleaseDate, DESC)))
            }
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
