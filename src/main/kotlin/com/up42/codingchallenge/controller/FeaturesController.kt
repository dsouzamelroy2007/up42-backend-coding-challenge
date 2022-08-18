package com.up42.codingchallenge.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.up42.codingchallenge.model.Feature
import com.up42.codingchallenge.model.FeatureCollection
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class FeaturesController {
    @GetMapping("/features")
    fun getFeatures(): List<Feature> =
        ClassPathResource("/static/source-data.json").file.readText()
            .let { jsonString ->
                jacksonObjectMapper().readValue<List<FeatureCollection>>(jsonString)
            }.flatMap {
                it.features
            }.map {
                it.apply {
                    id = properties?.id
                    timestamp = properties?.timestamp
                    beginViewingDate = properties?.acquisition?.beginViewingDate
                    endViewingDate = properties?.acquisition?.endViewingDate
                    missionName = properties?.acquisition?.missionName
                }
            }.ifEmpty {
                throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No features found")
            }
}

