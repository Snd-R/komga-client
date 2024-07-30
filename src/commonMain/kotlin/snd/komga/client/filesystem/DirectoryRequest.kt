package snd.komga.client.filesystem

import kotlinx.serialization.Serializable

@Serializable
data class DirectoryRequest(val path: String)