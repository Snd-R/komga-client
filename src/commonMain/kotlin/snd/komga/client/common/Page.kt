package snd.komga.client.common

import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlin.math.ceil

@Serializable
data class Page<T>(
    val content: List<T>,
    val pageable: Pageable,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean,
    val number: Int,
    val sort: Sort,
    val first: Boolean,
    val numberOfElements: Int,
    val size: Int,
    val empty: Boolean
) {
    companion object {
        fun <T> empty() = Page<T>(
            content = emptyList(),
            pageable = Pageable(
                sort = Sort(
                    sorted = false,
                    unsorted = true,
                    empty = true
                ),
                pageNumber = 0,
                pageSize = 0,
                offset = 0,
                paged = false,
                unpaged = true
            ),
            totalElements = 0,
            totalPages = 0,
            last = true,
            number = 0,
            sort = Sort(
                sorted = false,
                unsorted = true,
                empty = true
            ),
            first = true,
            numberOfElements = 0,
            size = 0,
            empty = true
        )

        fun <T> page(content: List<T>, pageable: Pageable, total: Int): Page<T> {
            val number = if (pageable.paged) pageable.pageNumber else 0
            val size = if (pageable.paged) pageable.pageSize else content.size
            val totalPages = if (size == 0) 1 else ceil(total.toDouble() / size.toDouble()).toInt()
            return Page(
                content = content,
                pageable = pageable,
                totalElements = total,
                totalPages = totalPages,
                last = (number + 1) < totalPages,
                number = number,
                sort = pageable.sort,
                first = number > 0,
                numberOfElements = content.size,
                size = size,
                empty = content.isEmpty()
            )
        }
    }
}

@Serializable
data class Pageable(
    val sort: Sort,
    val pageNumber: Int,
    val pageSize: Int,
    val offset: Int,
    val paged: Boolean,
    val unpaged: Boolean,
)

@Serializable
data class Sort(
    val sorted: Boolean,
    val unsorted: Boolean,
    val empty: Boolean
)

@Serializable
data class KomgaPageRequest(
    val pageIndex: Int? = null,
    val size: Int? = null,
    val sort: KomgaSort = KomgaSort.Unsorted,
    val unpaged: Boolean? = false,
) {
    companion object {
        val DEFAULT = KomgaPageRequest(0, 20, KomgaSort.Unsorted, false)
    }
}

fun KomgaPageRequest.toParams(): Parameters {
    val builder = ParametersBuilder()
    size?.let { builder.append("size", it.toString()) }
    pageIndex?.let { builder.append("page", it.toString()) }
    unpaged?.let { builder.append("unpaged", it.toString()) }

    val sort = buildString {
        for (order in sort.orders) {
            append(order.property)
            append(",")
            append(order.direction.name.lowercase())
        }
    }
    if (sort.isNotBlank()) {
        builder.append("sort", sort)
    }

    return builder.build()
}

fun KomgaPageRequest.toMap(): Map<String, String> {
    val map = HashMap<String, String>()

    size?.let { map["size"] = it.toString() }
    pageIndex?.let { map["page"] = it.toString() }
    unpaged?.let { map["unpaged"] = it.toString() }
    val sort = buildString {
        for (order in sort.orders) {
            append(order.property)
            append(",")
            append(order.direction.name.lowercase())
        }
    }
    if (sort.isNotBlank()) {
        map["sort"] = sort
    }

    return map
}
