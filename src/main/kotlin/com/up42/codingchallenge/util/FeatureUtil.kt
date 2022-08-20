package com.up42.codingchallenge.util

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.up42.codingchallenge.constant.FeatureConstants
import com.up42.codingchallenge.dto.FeatureDto
import com.up42.codingchallenge.exception.FeatureFileReadException
import com.up42.codingchallenge.exception.ResourceNotFoundException
import com.up42.codingchallenge.model.FeatureCollection
import mu.KotlinLogging
import org.geojson.Feature
import org.springframework.core.io.ClassPathResource
import java.io.FileNotFoundException
import java.util.UUID
import java.util.stream.Collectors

object FeatureUtil {

    private val objectMapper = jacksonObjectMapper()
    private val logger = KotlinLogging.logger {}

    fun readFeaturesFromFile(filePath: String): List<Feature> {
        try {
            logger.debug("reading Features from file")
            return ClassPathResource(filePath).file.readText()
                .let { jsonString ->
                    try {
                        objectMapper.readValue<List<FeatureCollection>>(jsonString)
                    } catch (e: JsonMappingException) {
                        logger.error("Error while processing json data from file ", e)
                        throw FeatureFileReadException("Error processing json data")
                    }
                }.flatMap {
                    it.features
                }.ifEmpty {
                    logger.error("No features to be retrieved")
                    throw ResourceNotFoundException("No features found")
                }
        } catch (e: FileNotFoundException) {
            logger.error("Error while reading file from requested path {} ", filePath, e)
            throw ResourceNotFoundException("Features file not found")
        }
    }

    fun getFeatures(features: List<Feature>): List<FeatureDto> {
        return features.stream()
            .map { feature: org.geojson.Feature ->
                FeatureDto(
                    UUID.fromString(feature.getProperty(FeatureConstants.ID)),
                    feature.getProperty(FeatureConstants.TIMESTAMP),
                    (feature.getProperty(FeatureConstants.ACQUISITION) as Map<*, *>)[FeatureConstants.BEGIN_VIEWING_DATE] as Long,
                    (feature.getProperty(FeatureConstants.ACQUISITION) as Map<*, *>)[FeatureConstants.END_VIEWING_DATE] as Long,
                    (feature.getProperty(FeatureConstants.ACQUISITION) as Map<*, *>)[FeatureConstants.MISSION_NAME] as String
                )
            }
            .collect(Collectors.toList())
    }
}
