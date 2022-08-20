package com.up42.codingchallenge.datasource.impl

import com.up42.codingchallenge.constant.FeatureConstants.SOURCE_FEATURE_FILE_PATH
import com.up42.codingchallenge.datasource.FeatureDataSource
import com.up42.codingchallenge.dto.FeatureDto
import com.up42.codingchallenge.util.FeatureUtil
import org.springframework.stereotype.Repository

@Repository("file")
class FeatureFileDataSource : FeatureDataSource {

    private val localFeatureCache = FeatureUtil.readFeaturesFromFile(SOURCE_FEATURE_FILE_PATH)

    override fun retrieveFeatures(): List<FeatureDto> {
        return FeatureUtil.getFeatures(localFeatureCache)
    }
}
