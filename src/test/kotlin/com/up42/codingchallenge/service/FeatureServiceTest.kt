package com.up42.codingchallenge.service

import com.up42.codingchallenge.datasource.impl.FeatureFileDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.UUID

internal class FeatureServiceTest {

    private val dataSource: FeatureFileDataSource = mockk(relaxed = true)
    @Nested
    @DisplayName("Test calls to repository")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FeatureDataSourceCallTest {
        private val featureService = FeatureService(dataSource)

        @Test
        fun `should call its data source to retrieve features`() {
            // when
            featureService.getFeatures()

            // then
            verify(exactly = 1) { dataSource.retrieveFeatures() }
        }
        @Test
        fun `should call its data source to get a Quicklook`() {
            // given
            val featureId = "cf5dbe37-ab95-4af1-97ad-2637aec4ddf0"

            // when
            featureService.getQuicklook(featureId)

            // then
            verify(exactly = 1) { dataSource.retrieveFeatureById(featureId) }
        }
    }

    @Nested
    @DisplayName("Test using mock calls")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FeatureServiceMockTest {
        private val featureService: FeatureService = mockk(relaxed = true)

        @Test
        fun `should retrieve features`() {

            // when
            every { featureService.getFeatures() } returns listOf(
                mockk {
                    every { dataSource.retrieveFeatures() } returns mockk {
                        every { id } returns UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea")
                        every { timestamp } returns 1554831167697
                        every { beginViewingDate } returns 1554831167697
                        every { endViewingDate } returns 1554831202043
                        every { missionName } returns "Sentinel-1B"
                    }
                },
                mockk {
                    every { dataSource.retrieveFeatures() } returns mockk {
                        every { id } returns UUID.fromString("0b598c52-7bf2-4df0-9d09-94229cdfbc0b")
                        every { timestamp } returns 1560661222337
                        every { beginViewingDate } returns 1560661222337
                        every { endViewingDate } returns 1560661247336
                        every { missionName } returns "Sentinel-1A"
                    }
                }
            )

            // then
            Assertions.assertEquals(2, featureService.getFeatures().size)
        }
        @Test
        fun `should retrieve quicklook from feature`() {
            // given
            val featureId = "0b598c52-7bf2-4df0-9d09-94229cdfbc0b"

            // when
            every { featureService.getQuicklook(any()) } returns ByteArray(10)

            // then
            Assertions.assertInstanceOf(ByteArray::class.java, featureService.getQuicklook(featureId))
        }
    }
}
