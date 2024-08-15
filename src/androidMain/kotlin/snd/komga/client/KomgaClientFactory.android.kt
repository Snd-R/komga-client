package snd.komga.client

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import snd.komga.client.sse.KomgaSSESession
import snd.komga.client.sse.OkHttpKomgaSseSession

internal actual suspend fun getSseSession(
    json: Json,
    baseUrl: String,
    username: String?,
    password: String?,
    useragent: String?,
    authCookie: String?
): KomgaSSESession {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient.Builder().build()
        val session = OkHttpKomgaSseSession(
            client = client,
            json = json,
            baseUrl = baseUrl.toHttpUrl(),
            username = username,
            password = password,
            useragent = useragent,
            authCookie = authCookie
        )
        session.connect()
        session
    }
}

