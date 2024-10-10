package snd.komga.client.settings

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class HttpSettingsClient internal constructor(private val ktor: HttpClient) : KomgaSettingsClient {

    override suspend fun getSettings(): KomgaSettings {
        return ktor.get("api/v1/settings").body()
    }

    override suspend fun updateSettings(request: KomgaSettingsUpdateRequest) {
        ktor.patch("api/v1/settings") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

}