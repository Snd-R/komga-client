package snd.komga.client.announcements

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
data class KomgaJsonFeed(
    val version: String,
    val title: String,
    @SerialName("home_page_url")
    val homePageUrl: String?,
    val description: String?,
    val items: List<KomgaAnnouncement> = emptyList(),
) {
    @JvmInline
    @Serializable
    value class KomgaAnnouncementId(val value: String) {
        override fun toString() = value
    }

    @Serializable
    data class KomgaAnnouncement(
        val id: KomgaAnnouncementId,
        val url: String?,
        val title: String?,
        val summary: String?,

        @SerialName("content_html")
        val contentHtml: String?,

        @SerialName("date_modified")
        val dateModified: Instant?,
        val author: Author?,
        val tags: Set<String> = emptySet(),
        @SerialName("_komga")
        val komgaExtension: KomgaExtension?,
    )

    @Serializable
    data class Author(
        val name: String?,
        val url: String?,
    )

    @Serializable
    data class KomgaExtension(
        val read: Boolean,
    )
}
