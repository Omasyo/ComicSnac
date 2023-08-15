package com.keetr.comicsnac.network.common.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal object DateAsStringSerializer : KSerializer<LocalDateTime> {
    private const val DateFormat = "yyyy-MM-dd HH:mm:ss"

    private val formatter = DateTimeFormatter.ofPattern(DateFormat)

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val string = decoder.decodeString()
        return LocalDateTime.parse(string, formatter)
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val string = value.format(formatter)
        encoder.encodeString(string)
    }
}

internal object DateShortAsStringSerializer : KSerializer<LocalDate> {
    private const val DateFormat = "yyyy-MM-dd"

    private val formatter = DateTimeFormatter.ofPattern(DateFormat)

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDate {
        val string = decoder.decodeString()
        return LocalDate.parse(string, formatter)
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        val string = value.format(formatter)
        encoder.encodeString(string)
    }
}