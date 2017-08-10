package com.miraclebank.model.entity

import java.math.BigInteger
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Rate(@Id val rateId: Long,
           val currencyCode: String? = "UAH",
           val nationalCurrencyCode: String? = "USD",
           val buy: BigInteger, val sale: BigInteger,
           val rateDate: LocalDate) {
    constructor() : this(Long.MAX_VALUE, "", "", BigInteger.ONE, BigInteger.ONE, LocalDate.now())
}