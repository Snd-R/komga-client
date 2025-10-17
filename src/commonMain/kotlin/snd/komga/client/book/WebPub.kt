package snd.komga.client.book

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlin.time.Instant

@Serializable
data class WPPublication(
    val context: String? = null,
    val metadata: WPMetadata,
    val links: List<WPLink>,
    val images: List<WPLink> = emptyList(),
    val readingOrder: List<WPLink> = emptyList(),
    val resources: List<WPLink> = emptyList(),
    // epub specific fields
    val toc: List<WPLink> = emptyList(),
    val landmarks: List<WPLink> = emptyList(),
    val pageList: List<WPLink> = emptyList(),
)

@Serializable
data class WPMetadata(
    val title: String,
    val identifier: String? = null,
    val type: String? = null,
    val conformsTo: String? = null,
    val sortAs: String? = null,
    val subtitle: String? = null,
    val modified: Instant? = null,
    val published: LocalDate? = null,
    val language: String? = null,
    val author: List<String> = emptyList(),
    val translator: List<String> = emptyList(),
    val editor: List<String> = emptyList(),
    val artist: List<String> = emptyList(),
    val illustrator: List<String> = emptyList(),
    val letterer: List<String> = emptyList(),
    val penciler: List<String> = emptyList(),
    val colorist: List<String> = emptyList(),
    val inker: List<String> = emptyList(),
    val contributor: List<String> = emptyList(),
    val publisher: List<String> = emptyList(),
    val subject: List<String> = emptyList(),
    val readingProgression: WPReadingProgression? = null,
    val description: String? = null,
    val numberOfPages: Int? = null,
    val belongsTo: WPBelongsTo? = null,
    // epub specific fields
    val rendition: Map<String, JsonElement> = emptyMap(),
)

@Serializable
data class WPBelongsTo(
    val series: List<WPContributor> = emptyList(),
    val collection: List<WPContributor> = emptyList(),
)

@Serializable
data class WPContributor(
    val name: String,
    val position: Float? = null,
    val links: List<WPLink> = emptyList(),
)

@Serializable
data class WPLink(
    val title: String? = null,
    val rel: String? = null,
    val href: String? = null,
    val type: String? = null,
    val templated: Boolean? = null,
    val width: Int? = null,
    val height: Int? = null,
    val alternate: List<WPLink> = emptyList(),
    val children: List<WPLink> = emptyList(),
    val properties: Map<String, Map<String, JsonElement>> = emptyMap(),
)

@Serializable
enum class WPReadingProgression {
    @SerialName("rtl")
    RTL,

    @SerialName("ltr")
    LTR,

    @SerialName("ttb")
    TTB,

    @SerialName("btt")
    BTT,

    @SerialName("auto")
    AUTO,
}
