package com.up42.codingchallenge.datasource

import com.up42.codingchallenge.dto.FeatureDto
import org.geojson.Feature

interface FeatureDataSource {
    fun retrieveFeatures(): List<FeatureDto>
    fun retrieveFeatureById(id: String): Feature
}
