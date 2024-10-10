package snd.komga.client.announcements

import snd.komga.client.announcements.KomgaJsonFeed
import snd.komga.client.announcements.KomgaJsonFeed.KomgaAnnouncementId

interface KomgaAnnouncementsClient {
    suspend fun getAnnouncements(): KomgaJsonFeed
    suspend fun markAnnouncementsRead(announcements: List<KomgaAnnouncementId>)
}