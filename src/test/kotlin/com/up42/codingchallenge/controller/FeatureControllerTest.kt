package com.up42.codingchallenge.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.up42.codingchallenge.constant.FeatureConstants.SOURCE_FEATURE_FILE_PATH
import com.up42.codingchallenge.util.FileUtil
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class FeatureControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {
    val baseUrl = "/api/features"

    @Nested
    @DisplayName("GET /api/features")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetFeatures {
        @Test
        fun `should return all features`() {
            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].id") { value("39c2f29e-c0f8-4a39-a98b-deed547d6aea") }
                    jsonPath("$[0].timestamp") { value("1554831167697") }
                    jsonPath("$[0].beginViewingDate") { value("1554831167697") }
                    jsonPath("$[0].endViewingDate") { value("1554831202043") }
                    jsonPath("$[0].missionName") { value("Sentinel-1B") }
                }
        }

        @Test
        fun `should return NOT FOUND if the requested url is incorrect`() {
            // given
            val getAllFeaturesUrl = "$baseUrl/all"

            // when/then
            mockMvc.get(getAllFeaturesUrl)
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("Integration Testing")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FeaturesIT {
        @LocalServerPort
        var port: Int = 0

        @BeforeAll
        fun beforeAll() {
            RestAssured.port = port
        }

        @Test
        fun `should return all features`() {

            val features = FileUtil.readFeaturesFromFile(SOURCE_FEATURE_FILE_PATH)
            val expectedFeaturesIT = features.withIndex().associateBy ({ it.index }, {it.value})

            RestAssured.given()
                .get(baseUrl)
                .then()
                .statusCode(200)
                .also { validatableResponse ->
                    expectedFeaturesIT.forEach { feature ->
                        validatableResponse.body("id[${feature.key}]", Matchers.equalTo(feature.value.id.toString()))
                            .body("timestamp[${feature.key}]", Matchers.equalTo(feature.value.timestamp))
                            .body("beginViewingDate[${feature.key}]", Matchers.equalTo(feature.value.beginViewingDate))
                            .body("endViewingDate[${feature.key}]", Matchers.equalTo(feature.value.endViewingDate))
                            .body("missionName[${feature.key}]", Matchers.equalTo(feature.value.missionName))
                    }
                }
        }
    }
}