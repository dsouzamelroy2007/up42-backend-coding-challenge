package com.up42.codingchallenge.service

import com.up42.codingchallenge.constant.FeatureConstants
import com.up42.codingchallenge.datasource.impl.FeatureFileDataSource
import com.up42.codingchallenge.dto.FeatureDto
import com.up42.codingchallenge.exception.QuicklookNotFoundException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.Base64

@Service
class FeatureService(@Qualifier("file") private val dataSource: FeatureFileDataSource) {
    fun getFeatures(): List<FeatureDto> = dataSource.retrieveFeatures()
    fun getQuicklook(featureId: String): ByteArray {
        val feature = dataSource.retrieveFeatureById(featureId)
        val quicklook = feature.getProperty<Any>(FeatureConstants.QUICKLOOK) ?: throw QuicklookNotFoundException("No quicklook data for the feature with $featureId")
        return Base64.getMimeDecoder().decode(quicklook.toString())
    }
}
