package com.up42.codingchallenge.datasource

import com.up42.codingchallenge.dto.FeatureDto

interface FeatureDataSource {
    fun retrieveFeatures(): List<FeatureDto>

    // fun retrieveFeature(id: String): Feature
}
