package com.up42.codingchallenge.datasource.impl

import com.up42.codingchallenge.constant.FeatureConstants.SOURCE_FEATURE_FILE_PATH
import com.up42.codingchallenge.datasource.FeatureDataSource
import com.up42.codingchallenge.model.Feature
import com.up42.codingchallenge.util.FileUtil
import org.springframework.stereotype.Repository

@Repository("file")
class FeatureFileDataSource : FeatureDataSource {

    private val localFeatureCache = FileUtil.readFeaturesFromFile(SOURCE_FEATURE_FILE_PATH)

    override fun retrieveFeatures(): List<Feature> {
        return localFeatureCache
    }
}
