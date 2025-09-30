@file:OptIn(ExperimentalSerializationApi::class)

package snd.komga.client.search

import kotlinx.datetime.DateTimePeriod
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.time.Instant

class KomgaSearchOperator {
    @Serializable
    @JsonClassDiscriminator("operator")
    sealed interface Equality<T>

    @Serializable
    @JsonClassDiscriminator("operator")
    sealed interface EqualityNullable<T>

    @Serializable
    @JsonClassDiscriminator("operator")
    sealed interface StringOp

    @Serializable
    @JsonClassDiscriminator("operator")
    sealed interface Numeric<T>

    @Serializable
    @JsonClassDiscriminator("operator")
    sealed interface NumericNullable<T>

    @Serializable
    @JsonClassDiscriminator("operator")
    sealed interface Date

    @Serializable
    @JsonClassDiscriminator("operator")
    sealed interface Boolean

    @Serializable
    @SerialName("is")
    @JsonClassDiscriminator("operator")
    data class Is<T>(
        val value: T,
    ) : Equality<T>,
        EqualityNullable<T>,
        StringOp,
        Numeric<T>,
        NumericNullable<T>

    @Serializable
    @SerialName("isNot")
    data class IsNot<T>(
        val value: T,
    ) : Equality<T>,
        EqualityNullable<T>,
        StringOp,
        Numeric<T>,
        NumericNullable<T>

    @Serializable
    @SerialName("contains")
    data class Contains(
        val value: String,
    ) : StringOp

    @Serializable
    @SerialName("doesNotContain")
    data class DoesNotContain(
        val value: String,
    ) : StringOp

    @Serializable
    @SerialName("beginsWith")
    data class BeginsWith(
        val value: String,
    ) : StringOp

    @Serializable
    @SerialName("doesNotBeginWith")
    data class DoesNotBeginWith(
        val value: String,
    ) : StringOp

    @Serializable
    @SerialName("endsWith")
    data class EndsWith(
        val value: String,
    ) : StringOp

    @Serializable
    @SerialName("doesNotEndWith")
    data class DoesNotEndWith(
        val value: String,
    ) : StringOp

    @Serializable
    @SerialName("greaterThan")
    data class GreaterThan<T>(
        val value: T,
    ) : Numeric<T>,
        NumericNullable<T>

    @Serializable
    @SerialName("lessThan")
    data class LessThan<T>(
        val value: T,
    ) : Numeric<T>,
        NumericNullable<T>

    @Serializable
    @SerialName("before")
    data class Before(
        val dateTime: Instant,
    ) : Date

    @Serializable
    @SerialName("after")
    data class After(
        val dateTime: Instant,
    ) : Date

    @Serializable
    @SerialName("isInTheLast")
    data class IsInTheLast(
        val duration: DateTimePeriod,
    ) : Date

    @Serializable
    @SerialName("isNotInTheLast")
    data class IsNotInTheLast(
        val duration: DateTimePeriod,
    ) : Date

    @Serializable
    @SerialName("isTrue")
    data object IsTrue : Boolean

    @Serializable
    @SerialName("isFalse")
    data object IsFalse : Boolean

    @Serializable
    @SerialName("isNull")
    data object IsNull : Date

    @Serializable
    @SerialName("isNotNull")
    data object IsNotNull : Date

    @Serializable
    @SerialName("isNull")
    class IsNullT<T> :
        NumericNullable<T>,
        EqualityNullable<T>

    @Serializable
    @SerialName("isNotNull")
    class IsNotNullT<T> :
        NumericNullable<T>,
        EqualityNullable<T>
}
