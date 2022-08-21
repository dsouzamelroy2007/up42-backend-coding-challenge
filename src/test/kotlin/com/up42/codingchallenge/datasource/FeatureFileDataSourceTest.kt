package com.up42.codingchallenge.datasource

import com.up42.codingchallenge.constant.FeatureConstants
import com.up42.codingchallenge.datasource.impl.FeatureFileDataSource
import com.up42.codingchallenge.exception.ResourceNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
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
    @Test
    fun `should provide a single feature`() {
        // given
        val featureId = "cf5dbe37-ab95-4af1-97ad-2637aec4ddf0"

        // when
        val feature = featureFileDataSource.retrieveFeatureById(featureId)

        // then
        assertThat(feature).isNotNull
        assertThat(feature.getProperty<String>(FeatureConstants.ID)).isEqualTo(featureId)
    }
    @Test
    fun `should throw Exception when feature not found`() {
        // given
        val featureId = "cf51e37-ab95-4af1-97ad-2637aec4ddf0"

        // when, then
        val exception = Assertions.assertThrows(ResourceNotFoundException::class.java) {
            featureFileDataSource.retrieveFeatureById(featureId)
        }
        Assertions.assertEquals("Requested Feature not found", exception.message)
    }
}
