package snd.komga.client.book

import kotlinx.serialization.Serializable

@Serializable
data class KomgaBookPage(
    val number: Int,
    val fileName: String,
    val mediaType: String,
    val width: Int?,
    val height: Int?,
    val sizeBytes: Long?,
    val size: String
)