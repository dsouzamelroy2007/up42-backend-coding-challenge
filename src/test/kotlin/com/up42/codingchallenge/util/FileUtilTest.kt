package com.up42.codingchallenge.util

import com.up42.codingchallenge.exception.FeatureFileReadException
import com.up42.codingchallenge.exception.ResourceNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class FileUtilTest {

    @Test
    fun `can read features from given input file`() {
        // given
        val filePath = "/static/source-data.json"

        // when
        val features = FileUtil.readFeaturesFromFile(filePath)

        // then
        assertThat(features.size).isGreaterThanOrEqualTo(8)
    }

    @Test
    fun `check if features returned have valid UUID & missionName`() {
        // given
        val filePath = "/static/source-data.json"
        val regexExp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$".toRegex()

        // when
        val features = FileUtil.readFeaturesFromFile(filePath)

        // then
        assertThat(features).allMatch { it.missionName!!.isNotBlank() }
        assertThat(features).allMatch { it.id.toString().matches(regexExp) }
    }

    @Test
    fun `should throw ResourceNotFound Exception for incorrect File Path`() {
        // given
        val filePath = "/static/source_nofile.json"

        // when, then
        val exception = assertThrows(ResourceNotFoundException::class.java) {
            FileUtil.readFeaturesFromFile(filePath)
        }
        assertEquals("Features file not found", exception.message)
    }

    @Test
    fun `no features found`() {
        // given
        val filePath = "/static/test_data_no_features.json"

        // when, then
        val exception = assertThrows(ResourceNotFoundException::class.java) {
            FileUtil.readFeaturesFromFile(filePath)
        }
        assertEquals("No features found", exception.message)
    }

    @Test
    fun `should throw error while parsing json file`() {
        // given
        val filePath = "/static/test_data_incorrect_feature_properties.json"

        // when, then
        val exception = assertThrows(FeatureFileReadException::class.java) {
            FileUtil.readFeaturesFromFile(filePath)
        }
        assertEquals("Error processing json data", exception.message)
    }
}
