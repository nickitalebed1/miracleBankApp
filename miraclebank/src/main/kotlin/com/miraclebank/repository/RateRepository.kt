package com.miraclebank.repository

import com.miraclebank.model.entity.Rate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RateRepository : CrudRepository<Rate, Long>