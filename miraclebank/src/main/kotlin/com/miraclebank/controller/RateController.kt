package com.miraclebank.controller

import com.miraclebank.model.entity.Rate
import com.miraclebank.repository.RateRepository
import com.miraclebank.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger
import java.time.LocalDate

@RequestMapping("/rate")
@RestController
class RateController : CommandLineRunner {

    @Autowired
    lateinit var rateRepository: RateRepository

    @Autowired
    lateinit var userService: UserService


    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun getAllRates() : Iterable<Rate> {
        return rateRepository.findAll();
    }

    override fun run(vararg p0: String?) {
        rateRepository.save(Rate(1, "USD", "RUR", BigInteger.ONE, BigInteger.TEN, LocalDate.now()))
        rateRepository.save(Rate(2, "USD", "RUR", BigInteger.ONE, BigInteger.TEN, LocalDate.now()))
        rateRepository.save(Rate(3, "USD", "RUR", BigInteger.ONE, BigInteger.TEN, LocalDate.now()))
        rateRepository.save(Rate(4, "USD", "RUR", BigInteger.ONE, BigInteger.TEN, LocalDate.now()))
        rateRepository.save(Rate(5, "USD", "RUR", BigInteger.ONE, BigInteger.TEN, LocalDate.now()))
        userService.create("admin", "admin", "USER")
        userService.create("tomcat", "tomcat", "USER")
    }
}

