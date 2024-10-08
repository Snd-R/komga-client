package snd.komga.client.settings

import snd.komga.client.common.PatchValue
import kotlinx.serialization.Serializable

@Serializable
data class KomgaSettingsUpdateRequest(
    val deleteEmptyCollections: PatchValue<Boolean> = PatchValue.Unset,
    val deleteEmptyReadLists: PatchValue<Boolean> = PatchValue.Unset,
    val rememberMeDurationDays: PatchValue<Int> = PatchValue.Unset,
    val renewRememberMeKey: PatchValue<Boolean> = PatchValue.Unset,
    val thumbnailSize: PatchValue<KomgaThumbnailSize> = PatchValue.Unset,
    val taskPoolSize: PatchValue<Int> = PatchValue.Unset,
    val serverPort: PatchValue<Int> = PatchValue.Unset,
    val serverContextPath: PatchValue<String> = PatchValue.Unset,
)