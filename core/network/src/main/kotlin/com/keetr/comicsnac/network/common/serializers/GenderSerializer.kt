package com.keetr.comicsnac.network.common.serializers

import com.keetr.comicsnac.network.common.models.GenderApiModel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object GenderAsIntSerializer : KSerializer<GenderApiModel> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Gender", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): GenderApiModel {
        return when(decoder.decodeInt()) {
            0 -> GenderApiModel.Other
            1 -> GenderApiModel.Male
            2 -> GenderApiModel.Female
            else -> GenderApiModel.All
        }
    }

    override fun serialize(encoder: Encoder, value: GenderApiModel) {
        encoder.encodeInt(value.id)
    }
}