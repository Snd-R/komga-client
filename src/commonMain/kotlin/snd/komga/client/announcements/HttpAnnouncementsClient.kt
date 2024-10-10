package snd.komga.client.announcements

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import snd.komga.client.announcements.KomgaJsonFeed.KomgaAnnouncementId

class HttpAnnouncementsClient internal constructor(private val ktor: HttpClient) : KomgaAnnouncementsClient {

    override suspend fun getAnnouncements(): KomgaJsonFeed {
        return ktor.get("api/v1/announcements").body()
    }

    override suspend fun markAnnouncementsRead(announcements: List<KomgaAnnouncementId>) {
        ktor.put("api/v1/announcements") {
            contentType(ContentType.Application.Json)
            setBody(announcements)
        }
    }
}