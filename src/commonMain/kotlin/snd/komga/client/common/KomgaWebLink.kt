package snd.komga.client.common

import kotlinx.serialization.Serializable

@Serializable
data class KomgaWebLink(
    val label: String,
    val url: String,
)
