package com.up42.codingchallenge.datasource

import com.up42.codingchallenge.model.Feature

interface FeatureDataSource {
    fun retrieveFeatures(): Collection<Feature>

   // fun retrieveFeature(id: String): Feature
}