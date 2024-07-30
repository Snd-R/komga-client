package snd.komga.client.common

abstract class KomgaSort(private val orders: List<Order>) : Iterable<KomgaSort.Order> {

    override fun iterator() = orders.iterator()

    companion object {
        val UNSORTED = object : KomgaSort(emptyList()) {}
    }

    enum class Direction {
        ASC, DESC;
    }

    data class Order(
        val property: String,
        val direction: Direction,
    )
}


