package snd.komga.client.serializers

import snd.komga.client.common.PatchValue
import snd.komga.client.common.PatchValue.None
import snd.komga.client.common.PatchValue.Some
import snd.komga.client.common.PatchValue.Unset
import snd.komga.client.user.KomgaAgeRestriction
import snd.komga.client.user.KomgaUserUpdateRequest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.serializer

class KomgaUpdateRequestSerializer : KSerializer<KomgaUserUpdateRequest> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("UpdateRequest") {
        element<KomgaAgeRestriction?>("ageRestriction", isOptional = true)
        element<Set<String>?>("labelsAllow", isOptional = true)
        element<Set<String>?>("labelsExclude", isOptional = true)
        element<Set<String>?>("roles", isOptional = true)
        element<Set<String>?>("sharedLibrariesIds", isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: KomgaUserUpdateRequest) {
        encoder.encodeStructure(descriptor) {
            encode(value.ageRestriction, 0)
            encode(value.labelsAllow, 1)
            encode(value.labelsExclude, 2)
            encode(value.roles, 3)
            encode(value.sharedLibraries, 4)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private inline fun <reified T : Any> CompositeEncoder.encode(value: PatchValue<T>, index: Int) {
        when (value) {
            is Unset -> {}
            is Some -> encodeSerializableElement(descriptor, index, serializer<T>(), value.value)
            is None -> encodeNullableSerializableElement(descriptor, index, serializer<T>(), null)
        }
    }

    override fun deserialize(decoder: Decoder): KomgaUserUpdateRequest {
        TODO("Not yet implemented")
    }

}