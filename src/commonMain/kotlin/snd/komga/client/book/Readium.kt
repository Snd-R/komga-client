package snd.komga.client.book

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class R2Device(
    val id: String,
    val name: String
)

@Serializable
data class R2Location(
    val fragment: List<String>? = null,
    val position: Int? = null,
    val progression: Float? = null,
    val totalProgression: Float? = null
)

@Serializable
data class R2LocatorText(
    val after: String? = null,
    val before: String? = null,
    val highlight: String? = null
)

@Serializable
data class R2Locator(
    val href: String,
    val type: String,
    val title: String? = null,
    val locations: R2Location,
    val text: R2LocatorText? = null
)

@Serializable
data class R2Progression(
    val modified: Instant,
    val device: R2Device,
    val locator: R2Locator
)
