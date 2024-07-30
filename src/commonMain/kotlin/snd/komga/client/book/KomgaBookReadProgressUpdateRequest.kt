package snd.komga.client.book

import kotlinx.serialization.Serializable

@Serializable
data class KomgaBookReadProgressUpdateRequest(
    val page: Int? = null,
    val completed: Boolean? = null
)