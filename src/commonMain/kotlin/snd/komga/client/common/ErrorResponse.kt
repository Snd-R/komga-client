package snd.komga.client.common

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.serialization.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlin.time.Instant

@Serializable
data class ErrorResponse(
    val error: String,
    val message: String,
    val path: String,
    val status: Int,
    val timestamp: Instant
)

suspend fun ResponseException.toErrorResponse(): ErrorResponse? =
    try {
        response.body()
    } catch (e: SerializationException) {
        null
    } catch (e: JsonConvertException) {
        null
    }
