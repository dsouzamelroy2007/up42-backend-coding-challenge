package com.up42.codingchallenge.datasource.impl

import com.up42.codingchallenge.constant.FeatureConstants
import com.up42.codingchallenge.constant.FeatureConstants.SOURCE_FEATURE_FILE_PATH
import com.up42.codingchallenge.datasource.FeatureDataSource
import com.up42.codingchallenge.dto.FeatureDto
import com.up42.codingchallenge.exception.ResourceNotFoundException
import com.up42.codingchallenge.util.FeatureUtil
import mu.KotlinLogging
import org.geojson.Feature
import org.springframework.stereotype.Repository

@Repository("file")
class FeatureFileDataSource : FeatureDataSource {

    private val logger = KotlinLogging.logger {}
    private val localFeatureCache: List<Feature> = FeatureUtil.readFeaturesFromFile(SOURCE_FEATURE_FILE_PATH)

    override fun retrieveFeatures(): List<FeatureDto> {
        logger.info("Getting all features from feature cache")
        return FeatureUtil.getFeatures(localFeatureCache)
    }

    override fun retrieveFeatureById(id: String): Feature {
        for (feature in localFeatureCache) {
            if (feature.getProperty<String>(FeatureConstants.ID) == (id)) {
                return feature
            }
        }
        logger.error("No Feature found for featureId {}", id)
        throw ResourceNotFoundException("Requested Feature not found")
    }
}
