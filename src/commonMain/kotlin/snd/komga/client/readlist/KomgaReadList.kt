package snd.komga.client.readlist

import snd.komga.client.book.KomgaBookId
import snd.komga.client.common.KomgaThumbnailId
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.time.Instant

@Serializable
@JvmInline
value class KomgaReadListId(val value: String) {
    override fun toString() = value
}

@Serializable
data class KomgaReadList(
    val id: KomgaReadListId,
    val name: String,
    val summary: String,
    val ordered: Boolean,
    val bookIds: List<KomgaBookId>,
    val createdDate: Instant,
    val lastModifiedDate: Instant,
    val filtered: Boolean,
)

@Serializable
data class KomgaReadListThumbnail(
    val id: KomgaThumbnailId,
    val readListId: KomgaReadListId,
    val type: String,
    val selected: Boolean,
    val mediaType: String,
    val fileSize: Long,
    val width: Int,
    val height: Int,
)
