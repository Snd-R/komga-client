package snd.komga.client

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import snd.komga.client.actuator.KomgaActuatorClient
import snd.komga.client.announcements.KomgaAnnouncementsClient
import snd.komga.client.book.KomgaBookClient
import snd.komga.client.collection.KomgaCollectionClient
import snd.komga.client.filesystem.KomgaFileSystemClient
import snd.komga.client.library.KomgaLibraryClient
import snd.komga.client.readlist.KomgaReadListClient
import snd.komga.client.referential.KomgaReferentialClient
import snd.komga.client.series.KomgaSeriesClient
import snd.komga.client.settings.KomgaSettingsClient
import snd.komga.client.sse.KomgaSSESession
import snd.komga.client.task.KomgaTaskClient
import snd.komga.client.user.KomgaUserClient

class KomgaClientFactory private constructor(
    private val builder: Builder
) {

    private val json = Json(builder.json) {
        ignoreUnknownKeys = true
        encodeDefaults = false
    }

    private val baseUrl: () -> URLBuilder = builder.baseUrl
    private val ktor: HttpClient = configureKtor(builder.ktor ?: HttpClient())

    private fun configureKtor(client: HttpClient): HttpClient {
        return client.config {
            val cookiesStorage = builder.cookieStorage
            if (cookiesStorage != null) {
                install(HttpCookies) { storage = cookiesStorage }
            }

            install(ContentNegotiation) { json(json) }
            defaultRequest { url { this.takeFrom(baseUrl()) } }

            val username = builder.username
            val password = builder.password
            val useragent = builder.useragent
            if (username != null && password != null) {
                install(Auth) {
                    basic {
                        credentials { BasicAuthCredentials(username = username, password = password) }
                        sendWithoutRequest { true }
                    }
                }
            }

            if (useragent != null) {
                install(UserAgent) { agent = useragent }
            }

            expectSuccess = true
        }
    }

    fun libraryClient() = KomgaLibraryClient(ktor)
    fun seriesClient() = KomgaSeriesClient(ktor)
    fun bookClient() = KomgaBookClient(ktor)
    fun userClient() = KomgaUserClient(ktor)
    fun fileSystemClient() = KomgaFileSystemClient(ktor)
    fun settingsClient() = KomgaSettingsClient(ktor)
    fun taskClient() = KomgaTaskClient(ktor)
    fun actuatorClient() = KomgaActuatorClient(ktor)
    fun announcementClient() = KomgaAnnouncementsClient(ktor)
    fun collectionClient() = KomgaCollectionClient(ktor)
    fun readListClient() = KomgaReadListClient(ktor)
    fun referentialClient() = KomgaReferentialClient(ktor)

    //FIXME ktor sse session implementation does NOT terminate sse connection on session context cancellation
    // using custom implementation as a workaround
    suspend fun sseSession(): KomgaSSESession {
        val authCookie = ktor.cookies(Url(baseUrl()))
            .find { it.name == "SESSION" }
            ?.let { renderCookieHeader(it) }

        return getSseSession(
            json = json,
            baseUrl = baseUrl().buildString(),
            username = builder.username,
            password = builder.password,
            useragent = builder.useragent,
            authCookie = authCookie
        )
    }

    class Builder {
        internal var ktor: HttpClient? = null
        internal var baseUrl: () -> URLBuilder = { URLBuilder("http://localhost:25600/") }
        internal var cookieStorage: CookiesStorage? = null
        internal var json: Json = Json

        internal var username: String? = null
        internal var password: String? = null
        internal var useragent: String? = null

        fun ktor(ktor: HttpClient) = apply {
            this.ktor = ktor
        }

        fun baseUrl(block: () -> String) = apply {
            this.baseUrl = { URLBuilder(block()) }
        }

        fun baseUrlBuilder(block: () -> URLBuilder) = apply {
            this.baseUrl = block
        }

        fun cookieStorage(cookiesStorage: CookiesStorage) = apply {
            this.cookieStorage = cookiesStorage
        }

        fun username(username: String) = apply {
            this.username = username
        }

        fun useragent(useragent: String) = apply {
            this.useragent = useragent
        }

        fun password(password: String) = apply {
            this.password = password
        }

        fun build(): KomgaClientFactory {
            return KomgaClientFactory(this)
        }

    }
}

internal expect suspend fun getSseSession(
    json: Json,
    baseUrl: String,
    username: String?,
    password: String?,
    useragent: String?,
    authCookie: String?
): KomgaSSESession