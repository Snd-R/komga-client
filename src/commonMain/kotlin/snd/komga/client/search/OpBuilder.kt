package snd.komga.client.search

import kotlinx.datetime.DateTimePeriod
import kotlin.time.Duration
import kotlin.time.Instant

abstract class OpBuilder<T> {
}

open class EqualityBuilder<T> internal constructor() : OpBuilder<T>() {
    internal var operator: KomgaSearchOperator.Equality<T>? = null

    fun isEqualTo(value: T): KomgaSearchOperator.Equality<T> {
        val operator = KomgaSearchOperator.Is(value)
        this.operator = operator
        return operator
    }

    fun isNotEqualTo(value: T): KomgaSearchOperator.Equality<T> {
        val operator = KomgaSearchOperator.IsNot(value)
        this.operator = operator
        return operator
    }

    fun getValueOrThrow(): KomgaSearchOperator.Equality<T> {
        return checkNotNull(operator) { "operator was not set. use isEqualTo or isNotEqualTo" }
    }
}

open class EqualityNullableBuilder<T> internal constructor() : OpBuilder<T>() {
    internal var operator: KomgaSearchOperator.EqualityNullable<T>? = null

    fun isEqualTo(value: T): KomgaSearchOperator.EqualityNullable<T> {
        val operator = KomgaSearchOperator.Is(value)
        this.operator = operator
        return operator
    }

    fun isNotEqualTo(value: T): KomgaSearchOperator.EqualityNullable<T> {
        val operator = KomgaSearchOperator.IsNot(value)
        this.operator = operator
        return operator
    }

    fun isNull(): KomgaSearchOperator.EqualityNullable<T> {
        val operator = KomgaSearchOperator.IsNullT<T>()
        this.operator = operator
        return operator
    }

    fun getValueOrThrow(): KomgaSearchOperator.EqualityNullable<T> {
        return checkNotNull(operator) { "operator was not set. use isEqualTo or isNotEqualTo" }
    }
}

open class BooleanOpBuilder internal constructor() : OpBuilder<Boolean>() {
    internal var operator: KomgaSearchOperator.Boolean? = null

    fun isTrue(): KomgaSearchOperator.Boolean {
        val operator = KomgaSearchOperator.IsTrue
        this.operator = operator
        return operator
    }

    fun isFalse(): KomgaSearchOperator.Boolean {
        val operator = KomgaSearchOperator.IsFalse
        this.operator = operator
        return operator
    }

    fun getValueOrThrow(): KomgaSearchOperator.Boolean {
        return checkNotNull(operator) { "operator was not set. use isEqualTo or isNotEqualTo" }
    }
}


open class StringOpBuilder internal constructor() : OpBuilder<String>() {
    internal var operator: KomgaSearchOperator.StringOp? = null

    fun isEqualTo(value: String): KomgaSearchOperator.StringOp {
        val operator = KomgaSearchOperator.Is(value)
        this.operator = operator
        return operator
    }

    fun isNotEqualTo(value: String): KomgaSearchOperator.StringOp {
        val operator = KomgaSearchOperator.IsNot(value)
        this.operator = operator
        return operator
    }

    fun contains(value: String): KomgaSearchOperator.StringOp {
        val operator = KomgaSearchOperator.Contains(value)
        this.operator = operator
        return operator
    }

    fun doeNotContain(value: String): KomgaSearchOperator.StringOp {
        val operator = KomgaSearchOperator.DoesNotContain(value)
        this.operator = operator
        return operator
    }

    fun beginsWith(value: String): KomgaSearchOperator.StringOp {
        val operator = KomgaSearchOperator.BeginsWith(value)
        this.operator = operator
        return operator
    }

    fun doesNotBeginWith(value: String): KomgaSearchOperator.StringOp {
        val operator = KomgaSearchOperator.DoesNotBeginWith(value)
        this.operator = operator
        return operator
    }

    fun endsWith(value: String): KomgaSearchOperator.StringOp {
        val operator = KomgaSearchOperator.EndsWith(value)
        this.operator = operator
        return operator
    }

    fun doesNotEndWith(value: String): KomgaSearchOperator.StringOp {
        val operator = KomgaSearchOperator.DoesNotEndWith(value)
        this.operator = operator
        return operator
    }

    fun getValueOrThrow(): KomgaSearchOperator.StringOp {
        return checkNotNull(operator) { "operator was not set. use isEqualTo or isNotEqualTo" }
    }
}

open class DateOpBuilder internal constructor() : OpBuilder<Instant>() {
    internal var operator: KomgaSearchOperator.Date? = null

    fun isBefore(value: Instant): KomgaSearchOperator.Date {
        val operator = KomgaSearchOperator.Before(value)
        this.operator = operator
        return operator
    }

    fun isAfter(value: Instant): KomgaSearchOperator.Date {
        val operator = KomgaSearchOperator.After(value)
        this.operator = operator
        return operator
    }

    fun isInLast(value: Duration): KomgaSearchOperator.Date {
        val operator = KomgaSearchOperator.IsInTheLast(value)
        this.operator = operator
        return operator
    }

    fun isNotInLast(value: Duration): KomgaSearchOperator.Date {
        val operator = KomgaSearchOperator.IsNotInTheLast(value)
        this.operator = operator
        return operator
    }

    fun isNull(value: DateTimePeriod): KomgaSearchOperator.Date {
        val operator = KomgaSearchOperator.IsNull
        this.operator = operator
        return operator
    }

    fun isNotNull(value: DateTimePeriod): KomgaSearchOperator.Date {
        val operator = KomgaSearchOperator.IsNotNull
        this.operator = operator
        return operator
    }


    fun getValueOrThrow(): KomgaSearchOperator.Date {
        return checkNotNull(operator) { "operator was not set. use isEqualTo or isNotEqualTo" }
    }
}


open class NumericOpBuilder<T> internal constructor() : OpBuilder<T>() {
    internal var operator: KomgaSearchOperator.Numeric<T>? = null

    fun isEqualTo(value: T): KomgaSearchOperator.Numeric<T> {
        val operator = KomgaSearchOperator.Is(value)
        this.operator = operator
        return operator
    }

    fun isNotEqualTo(value: T): KomgaSearchOperator.Numeric<T> {
        val operator = KomgaSearchOperator.IsNot(value)
        this.operator = operator
        return operator
    }

    fun greaterThan(value: T): KomgaSearchOperator.Numeric<T> {
        val operator = KomgaSearchOperator.GreaterThan(value)
        this.operator = operator
        return operator
    }

    fun lessThan(value: T): KomgaSearchOperator.Numeric<T> {
        val operator = KomgaSearchOperator.LessThan(value)
        this.operator = operator
        return operator
    }

    fun getValueOrThrow(): KomgaSearchOperator.Numeric<T> {
        return checkNotNull(operator) { "operator was not set. use isEqualTo or isNotEqualTo" }
    }
}

open class NumericNullableOpBuilder<T> internal constructor() : OpBuilder<T>() {
    internal var operator: KomgaSearchOperator.NumericNullable<T>? = null

    fun isEqualTo(value: T): KomgaSearchOperator.NumericNullable<T> {
        val operator = KomgaSearchOperator.Is(value)
        this.operator = operator
        return operator
    }

    fun isNotEqualTo(value: T): KomgaSearchOperator.NumericNullable<T> {
        val operator = KomgaSearchOperator.IsNot(value)
        this.operator = operator
        return operator
    }

    fun greaterThan(value: T): KomgaSearchOperator.NumericNullable<T> {
        val operator = KomgaSearchOperator.GreaterThan(value)
        this.operator = operator
        return operator
    }

    fun lessThan(value: T): KomgaSearchOperator.NumericNullable<T> {
        val operator = KomgaSearchOperator.LessThan(value)
        this.operator = operator
        return operator
    }

    fun isNull(): KomgaSearchOperator.NumericNullable<T> {
        val operator = KomgaSearchOperator.IsNullT<T>()
        this.operator = operator
        return operator
    }

    fun isNotNull(): KomgaSearchOperator.NumericNullable<T> {
        val operator = KomgaSearchOperator.IsNotNullT<T>()
        this.operator = operator
        return operator
    }

    fun getValueOrThrow(): KomgaSearchOperator.NumericNullable<T> {
        return checkNotNull(operator) { "operator was not set. use isEqualTo or isNotEqualTo" }
    }
}
