package com.up42.codingchallenge.util

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.up42.codingchallenge.exception.FeatureFileReadException
import com.up42.codingchallenge.exception.ResourceNotFoundException
import com.up42.codingchallenge.model.Feature
import com.up42.codingchallenge.model.FeatureCollection
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import java.io.FileNotFoundException

object FileUtil {

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
                }.map {
                    it.apply {
                        id = properties?.id
                        timestamp = properties?.timestamp
                        beginViewingDate = properties?.acquisition?.beginViewingDate
                        endViewingDate = properties?.acquisition?.endViewingDate
                        missionName = properties?.acquisition?.missionName
                    }
                }.ifEmpty {
                    logger.error("No features to be retrieved")
                    throw ResourceNotFoundException("No features found")
                }
        } catch (e: FileNotFoundException) {
            logger.error("Error while reading file from requested path {} ", filePath, e)
            throw ResourceNotFoundException("Features file not found")
        }
    }
}
