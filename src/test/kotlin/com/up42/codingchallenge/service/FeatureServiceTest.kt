package com.up42.codingchallenge.service

import com.up42.codingchallenge.datasource.impl.FeatureFileDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class FeatureServiceTest {

    private val dataSource: FeatureFileDataSource = mockk(relaxed = true)

    private val featureService = FeatureService(dataSource)

    @Test
    fun `should call its data source to retrieve features`() {
        // when
        featureService.getFeatures()

        // then
        verify(exactly = 1) { dataSource.retrieveFeatures() }
    }
}
