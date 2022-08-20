package com.up42.codingchallenge.datasource

import com.up42.codingchallenge.datasource.impl.FeatureFileDataSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class FeatureFileDataSourceTest {
    private val featureFileDataSource = FeatureFileDataSource()

    @Test
    fun `should provide a collection of features`() {
        // when
        val features = featureFileDataSource.retrieveFeatures()

        // then
        assertThat(features.size).isGreaterThanOrEqualTo(1)
    }
}
