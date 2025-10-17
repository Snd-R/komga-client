package snd.komga.client.user

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class KomgaAuthenticationActivity(
    val userId: KomgaUserId?,
    val email: String?,
    val ip: String?,
    val userAgent: String?,
    val success: Boolean,
    val error: String?,
    val dateTime: Instant,
    val source: String
)