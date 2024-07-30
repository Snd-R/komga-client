
package snd.komga.client.book

import snd.komga.client.common.KomgaAuthor
import snd.komga.client.common.KomgaWebLink
import snd.komga.client.common.PatchValue
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class KomgaBookMetadataUpdateRequest(
    val title: PatchValue<String> = PatchValue.Unset,
    val titleLock: PatchValue<Boolean> = PatchValue.Unset,
    val summary: PatchValue<String> = PatchValue.Unset,
    val summaryLock: PatchValue<Boolean> = PatchValue.Unset,
    val number: PatchValue<String> = PatchValue.Unset,
    val numberLock: PatchValue<Boolean> = PatchValue.Unset,
    val numberSort: PatchValue<Float> = PatchValue.Unset,
    val numberSortLock: PatchValue<Boolean> = PatchValue.Unset,
    val releaseDate: PatchValue<LocalDate> = PatchValue.Unset,
    val releaseDateLock: PatchValue<Boolean> = PatchValue.Unset,
    val authors: PatchValue<List<KomgaAuthor>> = PatchValue.Unset,
    val authorsLock: PatchValue<Boolean> = PatchValue.Unset,
    val tags: PatchValue<List<String>> = PatchValue.Unset,
    val tagsLock: PatchValue<Boolean> = PatchValue.Unset,
    val isbn: PatchValue<String> = PatchValue.Unset,
    val isbnLock: PatchValue<Boolean> = PatchValue.Unset,
    val links: PatchValue<List<KomgaWebLink>> = PatchValue.Unset,
    val linksLock: PatchValue<Boolean> = PatchValue.Unset,
)