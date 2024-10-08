package snd.komga.client.serializers

import snd.komga.client.common.PatchValue
import snd.komga.client.common.PatchValue.None
import snd.komga.client.common.PatchValue.Some
import snd.komga.client.common.PatchValue.Unset
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class UpdateValueSerializer<T : Any>(
    private val valueSerializer: KSerializer<T>
) : KSerializer<PatchValue<T>> {
    override val descriptor: SerialDescriptor = valueSerializer.descriptor

    override fun deserialize(decoder: Decoder): PatchValue<T> {
        throw SerializationException("Deserialization is unsupported")
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: PatchValue<T>) {
        when (value) {
            None -> encoder.encodeNull()
            is Some -> valueSerializer.serialize(encoder, value.value)
            Unset -> throw SerializationException("Value is unset. Make sure that property has default unset value")
        }
    }
}