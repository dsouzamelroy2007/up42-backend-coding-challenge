package com.up42.codingchallenge.datasource.impl

import com.up42.codingchallenge.datasource.FeatureDataSource
import com.up42.codingchallenge.model.Feature
import com.up42.codingchallenge.util.FileUtil
import org.springframework.stereotype.Repository

@Repository
class FeatureFileDataSource : FeatureDataSource {

    private val featureFilePath = "/static/source-data.json"
    private val localFeatureCache = FileUtil.readFeaturesFromFile(featureFilePath)

    override fun retrieveFeatures(): List<Feature> {
        return localFeatureCache
    }
}
