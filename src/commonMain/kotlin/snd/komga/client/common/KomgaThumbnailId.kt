package snd.komga.client.common

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline


@Serializable
@JvmInline
value class KomgaThumbnailId(val value: String) {
    override fun toString() = value
}

