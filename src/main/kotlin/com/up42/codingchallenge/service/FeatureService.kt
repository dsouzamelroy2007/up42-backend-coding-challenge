package com.up42.codingchallenge.service

import com.up42.codingchallenge.datasource.impl.FeatureFileDataSource
import com.up42.codingchallenge.model.Feature
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class FeatureService(@Qualifier("file") private val dataSource: FeatureFileDataSource) {
    fun getFeatures(): List<Feature> = dataSource.retrieveFeatures()
}
