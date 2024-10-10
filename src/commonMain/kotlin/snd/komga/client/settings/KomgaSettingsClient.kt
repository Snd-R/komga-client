package snd.komga.client.settings

interface KomgaSettingsClient {

    suspend fun getSettings(): KomgaSettings
    suspend fun updateSettings(request: KomgaSettingsUpdateRequest)

}