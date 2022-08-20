package com.up42.codingchallenge.controller

import com.up42.codingchallenge.dto.FeatureDto
import com.up42.codingchallenge.service.FeatureService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/features")
class FeatureController(private val service: FeatureService) {

    @GetMapping
    fun getFeatures(): List<FeatureDto> = service.getFeatures()
}
